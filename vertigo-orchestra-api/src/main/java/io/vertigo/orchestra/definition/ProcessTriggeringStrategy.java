package io.vertigo.orchestra.definition;

import java.util.Map;
import java.util.Optional;

import io.vertigo.lang.Assertion;

/**
 * Strategy for trigerring the process.
 * @author mlaroche
 *
 */
public final class ProcessTriggeringStrategy {
	private final Optional<String> cronExpression;
	private final Map<String, String> initialParams;
	private final boolean multiExecution;
	private final int rescuePeriodInSeconds;

	/**
	 * Constructor.
	 * @param cronExpression an optional cronexpression
	 * @param initialParams initialsParams added to the firstWorkspace
	 * @param multiExecution if the process accepts multiexecution at the same time
	 * @param rescuePeriodInSeconds the time in seconds a planification is still valid
	 */
	ProcessTriggeringStrategy(
			final Optional<String> cronExpression,
			final Map<String, String> initialParams,
			final boolean multiExecution,
			final int rescuePeriodInSeconds) {
		Assertion.checkNotNull(cronExpression);
		Assertion.checkNotNull(initialParams);
		// --
		this.cronExpression = cronExpression;
		this.initialParams = initialParams;
		this.multiExecution = multiExecution;
		this.rescuePeriodInSeconds = rescuePeriodInSeconds;
	}

	public Optional<String> getCronExpression() {
		return cronExpression;
	}

	public Map<String, String> getInitialParams() {
		return initialParams;
	}

	public boolean isMultiExecution() {
		return multiExecution;
	}

	public int getRescuePeriod() {
		return rescuePeriodInSeconds;
	}

}
