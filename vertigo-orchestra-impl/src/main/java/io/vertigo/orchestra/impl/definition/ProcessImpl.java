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
	private final Option<String> cronExpression;
	private final Option<String> initialParams;
	private final boolean multiExecution;
	private final List<ActivityDefinition> activities;

	private long id;

	/**
	 * Constructor only used by its builder.
	 * @param name
	 * @param cronExpression
	 * @param initialParams
	 * @param multiExecution
	 * @param activities
	 */
	ProcessImpl(final String name, final Option<String> cronExpression, final Option<String> initialParams, final boolean multiExecution, final List<ActivityDefinition> activities) {
		Assertion.checkNotNull(name);
		Assertion.checkNotNull(activities);
		//---
		this.name = name;
		this.cronExpression = cronExpression;
		this.initialParams = initialParams;
		this.multiExecution = multiExecution;
		this.activities = activities;
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
	public boolean getMultiexecution() {
		return multiExecution;
	}

	/** {@inheritDoc} */
	@Override
	public List<ActivityDefinition> getActivities() {
		return activities;
	}

}
