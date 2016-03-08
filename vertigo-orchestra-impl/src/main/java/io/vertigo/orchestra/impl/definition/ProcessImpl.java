package io.vertigo.orchestra.impl.definition;

import java.util.List;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.definition.Activity;
import io.vertigo.orchestra.definition.Process;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessImpl implements Process {

	private final String name;
	private final Option<String> cronExpression;
	private final Option<String> initialParams;
	private final boolean multiExecution;
	private final List<Activity> activities;

	private long id;

	/**
	 * Constructor only used by its builder.
	 * @param name
	 * @param cronExpression
	 * @param initialParams
	 * @param multiExecution
	 * @param activities
	 */
	ProcessImpl(final String name, final Option<String> cronExpression, final Option<String> initialParams, final boolean multiExecution, final List<Activity> activities) {
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
	public List<Activity> getActivities() {
		return activities;
	}

}
