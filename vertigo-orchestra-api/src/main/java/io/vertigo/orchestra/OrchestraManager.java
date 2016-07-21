package io.vertigo.orchestra;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import io.vertigo.lang.Manager;
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

	void scheduleNow(String processName, Optional<String> initialParams);

	void scheduleAt(String processName, Date expectedTime, Optional<String> initialParams);

	void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state);
}
