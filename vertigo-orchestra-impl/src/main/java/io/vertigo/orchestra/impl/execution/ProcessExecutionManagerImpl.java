package io.vertigo.orchestra.impl.execution;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.activation.FileTypeMap;
import javax.inject.Inject;

import io.vertigo.core.param.ParamManager;
import io.vertigo.dynamo.file.FileManager;
import io.vertigo.dynamo.file.model.InputStreamBuilder;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.dao.execution.OActivityLogDAO;
import io.vertigo.orchestra.domain.execution.OActivityLog;
import io.vertigo.orchestra.execution.ActivityExecutionWorkspace;
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
	private static final String TECHNICAL_LOG_PREFIX = "technicalLog_";
	private static final String TECHNICAL_LOG_EXTENSION = ".log";

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
	public void setActivityExecutionPending(final Long activityExecutionId, final ActivityExecutionWorkspace workspace) {
		// We need to be as short as possible for the commit
		if (transactionManager.hasCurrentTransaction()) {
			try (final VTransactionWritable transaction = transactionManager.createAutonomousTransaction()) {
				sequentialExecutorPlugin.setActivityExecutionPending(activityExecutionId, workspace);
				transaction.commit();
			}
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				sequentialExecutorPlugin.setActivityExecutionPending(activityExecutionId, workspace);
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

	/** {@inheritDoc} */
	@Override
	public Option<VFile> getTechnicalLogFileForActivity(final Long actityExecutionId) {
		Assertion.checkNotNull(actityExecutionId);
		// ---

		final Option<OActivityLog> activityLog = activityLogDAO.getActivityLogByAceId(actityExecutionId);
		if (activityLog.isDefined()) {
			final byte[] stringByteArray = activityLog.get().getLog().getBytes(StandardCharsets.UTF_8);

			final InputStreamBuilder inputStreamBuilder = new InputStreamBuilder() {
				@Override
				public InputStream createInputStream() throws IOException {
					return new ByteArrayInputStream(stringByteArray);
				}
			};

			final String fileName = TECHNICAL_LOG_PREFIX + actityExecutionId + TECHNICAL_LOG_EXTENSION;
			final VFile file = fileManager.createFile(fileName, FileTypeMap.getDefaultFileTypeMap().getContentType(fileName), new Date(), stringByteArray.length, inputStreamBuilder);

			return Option.<VFile> some(file);
		}
		return Option.<VFile> none();
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
