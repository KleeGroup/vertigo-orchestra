package io.vertigo.orchestra.impl.definition;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.definition.Activity;
import io.vertigo.orchestra.definition.Process;
import io.vertigo.util.ListBuilder;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessDefinitionBuilder implements Builder<Process> {

	private final String name;
	private Option<String> cronExpression = Option.<String> none();
	private Option<String> initialParams = Option.<String> none();
	private boolean multiExecution = false;
	private final ListBuilder<Activity> activitiesBuilder = new ListBuilder<>();

	/**
	 * Constructeur.
	 */
	public ProcessDefinitionBuilder(final String processName) {
		Assertion.checkArgNotEmpty(processName);
		//-----
		name = processName;

	}

	/**
	 * Processus a déclanchement automatique.
	 * @return this
	 */
	public ProcessDefinitionBuilder withMultiExecution() {
		multiExecution = true;
		return this;
	}

	/**
	 * Définit le variable de début de process.
	 * @param initialParams les paramètres initiaux sous format JSON
	 * @return this
	 */
	public ProcessDefinitionBuilder withInitialParams(final String initialParameters) {
		Assertion.checkNotNull(initialParameters);
		// ---
		initialParams = Option.<String> some(initialParameters);
		return this;
	}

	/**
	 * Définit l'expression cron du process.
	 * @return this
	 */
	public ProcessDefinitionBuilder withCron(final String scheduleExpression) {
		Assertion.checkNotNull(scheduleExpression);
		// ---
		cronExpression = Option.<String> some(scheduleExpression);
		return this;
	}

	/**
	 * Ajoute un delai entre deux executions d'une tache récurrente.
	 * @return this
	 */
	public ProcessDefinitionBuilder addActivity(final String activityName, final String engine) {
		Assertion.checkArgNotEmpty(activityName);
		Assertion.checkArgNotEmpty(engine);
		// ---
		final Activity activity = new ActivityImpl(activityName, engine);
		activitiesBuilder.add(activity);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public Process build() {
		return new ProcessImpl(name, cronExpression, initialParams, multiExecution, activitiesBuilder.unmodifiable().build());
	}

}
