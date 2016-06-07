package io.vertigo.orchestra;

import java.util.Date;
import java.util.List;

import io.vertigo.lang.Manager;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.execution.ExecutionState;

/**
 * Orchestra high-level services.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface OrchestraManager extends Manager {

	void createOrUpdateDefinitionIfNeeded(ProcessDefinition processDefinition);

	ProcessDefinition getProcessDefinition(String processName);

	List<ProcessDefinition> getAllProcesses();

	void scheduleNow(String processName, Option<String> initialParams);

	void scheduleAt(String processName, Date expectedTime, Option<String> initialParams);

	void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state);
}
