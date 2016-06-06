package io.vertigo.orchestra.impl.execution;

import java.io.File;

import javax.inject.Inject;

import io.vertigo.core.param.ParamManager;
import io.vertigo.dynamo.file.FileManager;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.dao.execution.OActivityLogDAO;
import io.vertigo.orchestra.domain.execution.OActivityLog;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.execution.ProcessExecutionManager;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessExecutionManagerImpl implements ProcessExecutionManager {

	private static final String ROOT_DIRECTORY = "orchestra.root.directory";

	private final VTransactionManager transactionManager;
	private final SequentialExecutorPlugin sequentialExecutorPlugin;

	@Inject
	private OActivityLogDAO activityLogDAO;
	@Inject
	private ParamManager paramManager;
	@Inject
	private FileManager fileManager;

	@Inject
	public ProcessExecutionManagerImpl(final VTransactionManager transactionManager,
			final SequentialExecutorPlugin sequentialExecutorPlugin) {
		Assertion.checkNotNull(transactionManager);
		Assertion.checkNotNull(sequentialExecutorPlugin);
		// ---
		this.transactionManager = transactionManager;
		this.sequentialExecutorPlugin = sequentialExecutorPlugin;
	}

	/** {@inheritDoc} */
	@Override
	public void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state) {
		// transaction is managed inside the plugin
		sequentialExecutorPlugin.endPendingActivityExecution(activityExecutionId, token, state);
	}

	/** {@inheritDoc} */
	@Override
	public void setActivityExecutionPending(final Long activityExecutionId) {
		// We need to be as short as possible for the commit
		if (transactionManager.hasCurrentTransaction()) {
			try (final VTransactionWritable transaction = transactionManager.createAutonomousTransaction()) {
				sequentialExecutorPlugin.setActivityExecutionPending(activityExecutionId);
				transaction.commit();
			}
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				sequentialExecutorPlugin.setActivityExecutionPending(activityExecutionId);
				transaction.commit();
			}
		}

	}

	/** {@inheritDoc} */
	@Override
	public Option<VFile> getLogFileForProcess(final Long processExecutionId) {
		Assertion.checkNotNull(processExecutionId);
		// ---
		final Option<OActivityLog> activityLog = activityLogDAO.getLogByPreId(processExecutionId);
		return getLogFileFromActivityLog(activityLog);
	}

	/** {@inheritDoc} */
	@Override
	public Option<VFile> getLogFileForActivity(final Long actityExecutionId) {
		Assertion.checkNotNull(actityExecutionId);
		// ---
		final Option<OActivityLog> activityLog = activityLogDAO.getActivityLogByAceId(actityExecutionId);
		return getLogFileFromActivityLog(activityLog);
	}

	private Option<VFile> getLogFileFromActivityLog(final Option<OActivityLog> activityLog) {
		Assertion.checkNotNull(activityLog);
		// ---
		if (activityLog.isDefined()) {
			final File file = new File(paramManager.getStringValue(ROOT_DIRECTORY) + activityLog.get().getLogFile());
			if (file.exists()) {
				return Option.<VFile> some(fileManager.createFile(file));
			}
			throw new RuntimeException("Log File" + file.getAbsolutePath() + " not found");
		}
		return Option.<VFile> none();
	}
}
