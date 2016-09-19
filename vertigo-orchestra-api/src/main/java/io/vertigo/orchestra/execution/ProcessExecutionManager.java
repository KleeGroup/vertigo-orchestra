package io.vertigo.orchestra.execution;

import java.util.Optional;

import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.lang.Manager;

/**
 * TODO : Description de la classe.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface ProcessExecutionManager extends Manager {

	void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state);

	void setActivityExecutionPending(final Long activityExecutionId, final ActivityExecutionWorkspace workspace);

	Optional<VFile> getLogFileForProcess(final Long processExecutionId);

	Optional<VFile> getLogFileForActivity(Long actityExecutionId);

	Optional<VFile> getTechnicalLogFileForActivity(Long actityExecutionId);
}
