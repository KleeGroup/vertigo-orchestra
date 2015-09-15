package io.vertigo.orchestra.services.execution.manager;

import io.vertigo.core.Home;
import io.vertigo.core.component.di.injector.Injector;
import io.vertigo.dynamo.work.WorkManager;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.domain.execution.OTaskExecution;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import org.apache.log4j.Logger;

final class OLocalWorker implements Callable<Map<String, String>> {

	private static final Logger LOGGER = Logger.getLogger(WorkManager.class); // même logger que le WorkListenerImpl

	/**
	 * Pour vider les threadLocal entre deux utilisations du Thread dans le pool,
	 * on garde un accès débridé au field threadLocals de Thread.
	 * On fait le choix de ne pas vider inheritedThreadLocal qui est moins utilisé.
	 */
	private static final Field threadLocalsField;

	static {
		try {
			threadLocalsField = Thread.class.getDeclaredField("threadLocals");
		} catch (final SecurityException | NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
		threadLocalsField.setAccessible(true);
	}

	@Inject
	private OTaskManager taskManager;

	private final OTaskExecution taskExecution;
	private final Map<String, String> params;

	/**
	 * Constructeur.
	 *
	 * @param workItem WorkItem à traiter
	 */
	OLocalWorker(final OTaskExecution taskExecution, final Map<String, String> params) {
		Injector.injectMembers(this, Home.getComponentSpace());
		Assertion.checkNotNull(taskExecution);
		Assertion.checkNotNull(params);
		// -----
		this.taskExecution = taskExecution;
		this.params = params;
	}

	/** {@inheritDoc} */
	@Override
	public Map<String, String> call() {
		try {
			return taskManager.execute(taskExecution, params);
		} catch (final Exception e) {
			logError(e);
			throw asRuntimeException(e);
		} finally {
			try {
				// Vide le threadLocal
				cleanThreadLocals();
			} catch (final RuntimeException e) {
				// Ce n'est pas une cause de rejet du Work, on ne fait que logger
				logError(e);
			}
		}
	}

	private void logError(final Throwable e) {
		LOGGER.error("Erreur de la tache : " + taskExecution.getEngine(), e);
	}

	private RuntimeException asRuntimeException(final Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException(e);
	}

	/**
	 * Vide le threadLocal du thread avant de le remettre dans le pool.
	 * Ceci protège contre les WorkEngine utilsant un ThreadLocal sans le vider.
	 * Ces workEngine peuvent poser des problémes de fuites mémoires (Ex: le FastDateParser de Talend)
	 * Voir aussi:
	 * http://weblogs.java.net/blog/jjviana/archive/2010/06/10/threadlocal-thread-pool-bad-idea-or-dealing-apparent-
	 * glassfish-memor
	 */
	private static void cleanThreadLocals() {
		try {
			threadLocalsField.set(Thread.currentThread(), null);
		} catch (final IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
