package io.vertigo.orchestra.definition;

import java.util.Optional;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;
import io.vertigo.orchestra.execution.ActivityEngine;
import io.vertigo.util.ListBuilder;

/**
 * Builder d'une définition de processus Orchestra.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessDefinitionBuilder implements Builder<ProcessDefinition> {

	private final String name;
	private final String label;
	private Optional<String> myCronExpression = Optional.empty();
	private Optional<String> myInitialParams = Optional.empty();
	private boolean multiExecution;
	private boolean needUpdate;
	private int myRescuePeriod;
	private final ListBuilder<ActivityDefinition> activitiesBuilder = new ListBuilder<>();

	private Optional<String> myMetadatas = Optional.empty();

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
	 * Processus autorisant la multi-execution.
	 * @return this
	 */
	public ProcessDefinitionBuilder withMultiExecution() {
		multiExecution = true;
		return this;
	}

	/**
	 * Durée pendant laquelle une planification peut être restaurée (durée de validité).
	 * @param rescuePeriod la durée en secondes
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
		myInitialParams = Optional.of(initialParams);
		return this;
	}

	/**
	 * Définit l'expression cron du process.
	 * @return this
	 */
	public ProcessDefinitionBuilder withCronExpression(final String cronExpression) {
		Assertion.checkNotNull(cronExpression);
		// ---
		myCronExpression = Optional.of(cronExpression);
		return this;
	}

	/**
	 * Ajoute une activité à un processus.
	 * @return this
	 */

	/**
	 * Ajoute une activité à un processus.
	 * @param activityName le nom de l'activité (Code)
	 * @param activityLabel Le libelle de l'activité (Ihm)
	 * @param engineClass Le moteur d'exécution de l'activité
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
		myMetadatas = Optional.of(metadatas);
		return this;
	}

	/**
	 * Définit si au prochain démarrage de l'application la définition doit être mise à jour.
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
