package io.vertigo.orchestra.definition;

import java.util.List;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;

/**
 * TODO : Description de la classe.
 *
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
	private final Option<String> cronExpression;
	private final Option<String> initialParams;
	private final boolean multiExecution;
	private final int rescuePeriod;
	private final Option<String> metadatas;
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
			final Option<String> cronExpression,
			final Option<String> initialParams,
			final boolean multiExecution,
			final int rescuePeriod,
			final Option<String> metadatas,
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

	public Option<String> getCronExpression() {
		return cronExpression;
	}

	public Option<String> getInitialParams() {
		return initialParams;
	}

	public int getRescuePeriod() {
		return rescuePeriod;
	}

	public boolean isMultiExecution() {
		return multiExecution;
	}

	public Option<String> getMetadatas() {
		return metadatas;
	}

	public List<ActivityDefinition> getActivities() {
		return activities;
	}

	public boolean getNeedUpdate() {
		return needUpdate;
	}

}
