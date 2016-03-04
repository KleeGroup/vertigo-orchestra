package io.vertigo.orchestra.impl.definition;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;
import io.vertigo.util.ListBuilder;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessDefinitionBuilder implements Builder<ProcessDefinition> {
	private final OProcess process;
	private final ListBuilder<OTask> tasksBuilder = new ListBuilder<>();

	/**
	 * Constructeur.
	 */
	public ProcessDefinitionBuilder(final String processName) {
		Assertion.checkArgNotEmpty(processName);
		//-----
		process = new OProcess();
		process.setName(processName);
		process.setMultiexecution(false);// By default no multi-execution

	}

	/**
	 * Processus a déclanchement automatique.
	 * @return this
	 */
	public ProcessDefinitionBuilder withMultiExecution() {
		process.setMultiexecution(true);
		return this;
	}

	/**
	 * Définit le variable de début de process.
	 * @param initialParams les paramètres initiaux sous format JSON
	 * @return this
	 */
	public ProcessDefinitionBuilder withInitialParams(final String initialParams) {
		// ---
		process.setInitialParams(initialParams);
		return this;
	}

	/**
	 * Définit l'expression cron du process.
	 * @return this
	 */
	public ProcessDefinitionBuilder withCron(final String cronExpression) {
		Assertion.checkArgNotEmpty(cronExpression);
		// ---
		process.setCronExpression(cronExpression);
		return this;
	}

	/**
	 * Ajoute un delai entre deux executions d'une tache récurrente.
	 * @return this
	 */
	public ProcessDefinitionBuilder addTask(final String taskName, final String engine, final boolean milestone) {
		Assertion.checkArgNotEmpty(taskName);
		Assertion.checkArgNotEmpty(engine);
		// ---
		final OTask task = new OTask();
		task.setName(taskName);
		task.setEngine(engine);
		task.setMilestone(milestone);
		tasksBuilder.add(task);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ProcessDefinition build() {
		if (process.getCronExpression() != null) {
			process.setTrtCd("SCHEDULED");
		} else {
			process.setTrtCd("MANUAL");
		}
		return new ProcessDefinitionImpl(process, tasksBuilder.unmodifiable().build());
	}

}
