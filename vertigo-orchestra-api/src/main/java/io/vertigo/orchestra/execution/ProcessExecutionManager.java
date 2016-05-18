package io.vertigo.orchestra.execution;

import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.lang.Manager;
import io.vertigo.lang.Option;

/**
 * TODO : Description de la classe.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface ProcessExecutionManager extends Manager {

	void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state);

	void setActivityExecutionPending(final Long activityExecutionId);

	Option<VFile> getLogFileForProcess(final Long processExecutionId);

	Option<VFile> getLogFileForActivity(Long actityExecutionId);
}
