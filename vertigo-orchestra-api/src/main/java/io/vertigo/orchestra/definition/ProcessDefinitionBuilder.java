package io.vertigo.orchestra.definition;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.execution.ActivityEngine;
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
	private Option<String> myCronExpression = Option.none();
	private Option<String> myInitialParams = Option.none();
	private boolean multiExecution;
	private boolean needUpdate;
	private int myRescuePeriod;
	private final ListBuilder<ActivityDefinition> activitiesBuilder = new ListBuilder<>();

	private Option<String> myMetadatas = Option.none();

	/**
	 * Constructor.
	 */
	public ProcessDefinitionBuilder(final String processName, final String processLabel) {
		Assertion.checkArgNotEmpty(processName);
		//-----
		name = processName;
		label = processLabel;

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
	public ProcessDefinitionBuilder withRescuePeriod(final int rescuePeriod) {
		myRescuePeriod = rescuePeriod;
		return this;
	}

	/**
	 * Définit le variable de début de process.
	 * @param initialParams les paramètres initiaux sous format JSON
	 * @return this
	 */
	public ProcessDefinitionBuilder withInitialParams(final String initialParams) {
		Assertion.checkNotNull(initialParams);
		// ---
		myInitialParams = Option.some(initialParams);
		return this;
	}

	/**
	 * Définit l'expression cron du process.
	 * @return this
	 */
	public ProcessDefinitionBuilder withCronExpression(final String cronExpression) {
		Assertion.checkNotNull(cronExpression);
		// ---
		myCronExpression = Option.some(cronExpression);
		return this;
	}

	/**
	 * Ajoute un delai entre deux executions d'une tache récurrente.
	 * @return this
	 */
	public ProcessDefinitionBuilder addActivity(final String activityName, final String activityLabel, final Class<? extends ActivityEngine> engineClass) {
		final ActivityDefinition activity = new ActivityDefinition(activityName, activityLabel, engineClass);
		activitiesBuilder.add(activity);
		return this;
	}

	/**
	 * Définit le informations du process.
	 * @param metadatas les métadonnées sous format JSON
	 * @return this
	 */
	public ProcessDefinitionBuilder withMetadatas(final String metadatas) {
		Assertion.checkNotNull(metadatas);
		// ---
		myMetadatas = Option.some(metadatas);
		return this;
	}

	/**
	 * Définit le informations du process.
	 * @return this
	 */
	public ProcessDefinitionBuilder withNeedUpdate() {
		needUpdate = true;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ProcessDefinition build() {
		return new ProcessDefinition(name, label, myCronExpression, myInitialParams, multiExecution, myRescuePeriod, myMetadatas, needUpdate, activitiesBuilder.unmodifiable().build());
	}

}
