package io.vertigo.orchestra.definition;

import java.util.List;
import java.util.Optional;

import io.vertigo.lang.Assertion;

/**
 * Définition d'un processus Orchestra.
 * Une définition doit être créee par un le builder associé.
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessDefinition {
	//TODO : ID doit être immutable!!
	//---immutables
	private long id;
	private final String name;
	private final List<ActivityDefinition> activities;
	//---params dev / admin
	private final String label;
	private final Optional<String> cronExpression;
	private final Optional<String> initialParams;
	private final boolean multiExecution;
	private final int rescuePeriod;
	private final Optional<String> metadatas;
	private final boolean needUpdate;

	/**
	 * Constructor only used by its builder.
	 * @param name
	 * @param cronExpression
	 * @param initialParams
	 * @param multiExecution
	 * @param activities
	 */
	ProcessDefinition(
			final String name,
			final String label,
			final Optional<String> cronExpression,
			final Optional<String> initialParams,
			final boolean multiExecution,
			final int rescuePeriod,
			final Optional<String> metadatas,
			final boolean needUpdate,
			final List<ActivityDefinition> activities) {
		Assertion.checkArgNotEmpty(name);
		Assertion.checkArgNotEmpty(label);
		Assertion.checkNotNull(cronExpression);
		Assertion.checkNotNull(initialParams);
		Assertion.checkNotNull(metadatas);
		Assertion.checkNotNull(activities);
		//---
		this.name = name;
		this.label = label;
		this.cronExpression = cronExpression;
		this.initialParams = initialParams;
		this.multiExecution = multiExecution;
		this.rescuePeriod = rescuePeriod;
		this.activities = activities;
		this.metadatas = metadatas;
		this.needUpdate = needUpdate;
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public Optional<String> getCronExpression() {
		return cronExpression;
	}

	public Optional<String> getInitialParams() {
		return initialParams;
	}

	public int getRescuePeriod() {
		return rescuePeriod;
	}

	public boolean isMultiExecution() {
		return multiExecution;
	}

	public Optional<String> getMetadatas() {
		return metadatas;
	}

	public List<ActivityDefinition> getActivities() {
		return activities;
	}

	public boolean getNeedUpdate() {
		return needUpdate;
	}

}
