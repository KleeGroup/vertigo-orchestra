package io.vertigo.orchestra.impl.definition;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.definition.ActivityDefinition;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.util.ListBuilder;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessDefinitionBuilder implements Builder<ProcessDefinition> {

	private final String name;
	private final String label;
	private Option<String> cronExpression = Option.<String> none();
	private Option<String> initialParams = Option.<String> none();
	private boolean multiExecution = false;
	private boolean needUpdate = false;
	private int rescuePeriod = 0;
	private final ListBuilder<ActivityDefinition> activitiesBuilder = new ListBuilder<>();

	private Option<String> metadata = Option.<String> none();

	/**
	 * Constructeur.
	 */
	public ProcessDefinitionBuilder(final String processName, final String processlabel) {
		Assertion.checkArgNotEmpty(processName);
		//-----
		name = processName;
		label = processlabel;

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
	 * Processus a déclanchement automatique.
	 * @return this
	 */
	public ProcessDefinitionBuilder withRescuePeriod(final int processRescuePeriod) {
		rescuePeriod = processRescuePeriod;
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
	public ProcessDefinitionBuilder addActivity(final String activityName, final String activityLabel, final String engine) {
		Assertion.checkArgNotEmpty(activityName);
		Assertion.checkArgNotEmpty(engine);
		// ---
		final ActivityDefinition activity = new ActivityImpl(activityName, activityLabel, engine);
		activitiesBuilder.add(activity);
		return this;
	}

	/**
	 * Définit le informations du process.
	 * @param processMetadata les métadonnées sous format JSON
	 * @return this
	 */
	public ProcessDefinitionBuilder withMetadatas(final String processMetadata) {
		Assertion.checkNotNull(processMetadata);
		// ---
		metadata = Option.<String> some(processMetadata);
		return this;
	}

	/**
	 * Définit le informations du process.
	 * @param processMetadata les métadonnées sous format JSON
	 * @return this
	 */
	public ProcessDefinitionBuilder withNeedUpdate() {
		needUpdate = true;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ProcessDefinition build() {
		return new ProcessImpl(name, label, cronExpression, initialParams, multiExecution, rescuePeriod, metadata, needUpdate, activitiesBuilder.unmodifiable().build());
	}

}
