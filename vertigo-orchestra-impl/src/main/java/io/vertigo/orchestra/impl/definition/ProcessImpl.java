package io.vertigo.orchestra.impl.definition;

import java.util.List;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.definition.ActivityDefinition;
import io.vertigo.orchestra.definition.ProcessDefinition;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessImpl implements ProcessDefinition {

	private final String name;
	private final String label;
	private final Option<String> cronExpression;
	private final Option<String> initialParams;
	private final boolean multiExecution;
	private final Long rescuePeriod;
	private final List<ActivityDefinition> activities;
	private final Option<String> metadatas;

	private long id;

	/**
	 * Constructor only used by its builder.
	 * @param name
	 * @param cronExpression
	 * @param initialParams
	 * @param multiExecution
	 * @param activities
	 */
	ProcessImpl(final String name, final String label, final Option<String> cronExpression, final Option<String> initialParams, final boolean multiExecution, final Long rescuePeriod, final Option<String> metadatas, final List<ActivityDefinition> activities) {
		Assertion.checkNotNull(name);
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
	}

	/** {@inheritDoc} */
	@Override
	public long getId() {
		return id;
	}

	/** {@inheritDoc} */
	@Override
	public void setId(final long id) {
		this.id = id;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public String getLabel() {
		return label;
	}

	/** {@inheritDoc} */
	@Override
	public Option<String> getCronExpression() {
		return cronExpression;
	}

	/** {@inheritDoc} */
	@Override
	public Option<String> getInitialParams() {
		return initialParams;
	}

	/** {@inheritDoc} */
	@Override
	public Long getRescuePeriod() {
		return rescuePeriod;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getMultiexecution() {
		return multiExecution;
	}

	/** {@inheritDoc} */
	@Override
	public Option<String> getMetadatas() {
		return metadatas;
	}

	/** {@inheritDoc} */
	@Override
	public List<ActivityDefinition> getActivities() {
		return activities;
	}

}
