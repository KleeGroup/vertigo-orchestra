package io.vertigo.orchestra.impl.execution;

import io.vertigo.app.Home;
import io.vertigo.core.component.di.injector.Injector;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.dao.execution.OTaskLogDAO;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.domain.execution.OTaskLog;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.execution.OTaskEngine;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.util.ClassUtil;

import javax.inject.Inject;

import org.apache.log4j.Logger;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ProcessExecutionManagerImpl implements ProcessExecutionManager {
	private static final Logger LOGGER = Logger.getLogger(ProcessExecutionManager.class);

	@Inject
	private VTransactionManager transactionManager;
	@Inject
	private OTaskLogDAO taskLogDAO;

	private final SequentialExecutorPlugin sequentialExecutor;

	//--------------------------------------------------------------------------------------------------
	//--- Constructeur
	//--------------------------------------------------------------------------------------------------
	@Inject
	public ProcessExecutionManagerImpl(final SequentialExecutorPlugin sequentialExecutor) {
		Assertion.checkNotNull(sequentialExecutor);
		// ---
		this.sequentialExecutor = sequentialExecutor;

	}

	//--------------------------------------------------------------------------------------------------
	//--- Public
	//--------------------------------------------------------------------------------------------------
	//	private TaskExecutionWorkspace getWorkspaceForTaskExecution(final Long tkeId, final Boolean in) {
	//		Assertion.checkNotNull(tkeId);
	//		Assertion.checkNotNull(in);
	//		// ---
	//		if (transactionManager.hasCurrentTransaction()) {
	//			return sequentialExecutor.getWorkspaceForTaskExecution(tkeId, in);
	//		}
	//		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
	//			final TaskExecutionWorkspace workspace = sequentialExecutor.getWorkspaceForTaskExecution(tkeId, in);
	//			transaction.commit();
	//			return workspace;
	//		}
	//
	//	}

	private void saveTaskLogs(final Long tkeId, final TaskLogger taskLogger) {
		Assertion.checkNotNull(tkeId);
		Assertion.checkNotNull(taskLogger);
		//
		if (transactionManager.hasCurrentTransaction()) {
			doSaveTaskLogs(tkeId, taskLogger);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				doSaveTaskLogs(tkeId, taskLogger);
				transaction.commit();
			}
		}

	}

	private void doSaveTaskLogs(final Long tkeId, final TaskLogger taskLogger) {
		Assertion.checkNotNull(tkeId);
		Assertion.checkNotNull(taskLogger);
		//
		final OTaskLog taskLog = new OTaskLog();
		taskLog.setTkeId(tkeId);
		taskLog.setLog(taskLogger.getLogAsString());
		taskLogDAO.save(taskLog);
	}

	private void saveTaskExecutionWorkspace(final Long tkeId, final TaskExecutionWorkspace workspace, final Boolean in) {
		Assertion.checkNotNull(tkeId);
		Assertion.checkNotNull(workspace);
		Assertion.checkNotNull(in);
		// ---
		if (transactionManager.hasCurrentTransaction()) {
			sequentialExecutor.saveTaskExecutionWorkspace(tkeId, workspace, in);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				sequentialExecutor.saveTaskExecutionWorkspace(tkeId, workspace, in);
				transaction.commit();
			}
		}

	}

	/** {@inheritDoc} */
	@Override
	public TaskExecutionWorkspace execute(final OTaskExecution taskExecution, final TaskExecutionWorkspace workspace) {
		TaskExecutionWorkspace resultWorkspace = workspace;

		try {
			changeExecutionState(taskExecution, ExecutionState.RUNNING);
			// ---
			final OTaskEngine taskEngine = Injector.newInstance(
					ClassUtil.classForName(taskExecution.getEngine(), OTaskEngine.class), Home.getApp().getComponentSpace());

			try {
				// We try the execution and we keep the result
				resultWorkspace = taskEngine.execute(workspace);

			} catch (final Exception e) {
				// In case of failure we keep the current workspace
				resultWorkspace.setFailure();

			} finally {
				// We save the workspace which is the minimal state
				saveTaskExecutionWorkspace(taskExecution.getTkeId(), resultWorkspace, false);
				if (taskEngine instanceof AbstractOTaskEngine) {
					// If the engine extends the abstractEngine we can provide the services associated (LOGGING,...)
					saveTaskLogs(taskExecution.getTkeId(), ((AbstractOTaskEngine) taskEngine).getLogger());
				}
			}

		} catch (final Exception e) {
			// Informative log
			resultWorkspace.setFailure();
			logError(taskExecution, e);
		}

		return resultWorkspace;
	}

	private void changeExecutionState(final OTaskExecution taskExecution, final ExecutionState executionState) {
		Assertion.checkNotNull(taskExecution);
		Assertion.checkNotNull(executionState);
		// ---
		if (transactionManager.hasCurrentTransaction()) {
			sequentialExecutor.changeExecutionState(taskExecution, executionState);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				sequentialExecutor.changeExecutionState(taskExecution, executionState);
				transaction.commit();
			}
		}

	}

	private static void logError(final OTaskExecution taskExecution, final Throwable e) {
		LOGGER.error("Erreur de la tache : " + taskExecution.getEngine(), e);
	}
}
