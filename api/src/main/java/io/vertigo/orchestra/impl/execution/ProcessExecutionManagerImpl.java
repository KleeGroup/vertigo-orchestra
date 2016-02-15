package io.vertigo.orchestra.impl.execution;

import javax.inject.Inject;

import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.dao.execution.OTaskLogDAO;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.domain.execution.OTaskLog;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.execution.ProcessExecutionManager;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ProcessExecutionManagerImpl implements ProcessExecutionManager {

	@Inject
	private VTransactionManager transactionManager;

	private final SequentialExecutorPlugin sequentialExecutor;

	@Inject
	private OTaskLogDAO taskLogDAO;

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

	/** {@inheritDoc} */
	@Override
	public void changeExecutionState(final OTaskExecution taskExecution, final ExecutionState executionState) {
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

	/** {@inheritDoc} */
	@Override
	public void saveTaskLogs(final Long tkeId, final TaskLogger taskLogger) {
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

	@Override
	public TaskExecutionWorkspace getWorkspaceForTaskExecution(final Long tkeId, final Boolean in) {
		Assertion.checkNotNull(tkeId);
		Assertion.checkNotNull(in);
		// ---
		if (transactionManager.hasCurrentTransaction()) {
			return sequentialExecutor.getWorkspaceForTaskExecution(tkeId, in);
		}
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			final TaskExecutionWorkspace workspace = sequentialExecutor.getWorkspaceForTaskExecution(tkeId, in);
			transaction.commit();
			return workspace;
		}

	}

	@Override
	public void saveTaskExecutionWorkspace(final Long tkeId, final TaskExecutionWorkspace workspace, final Boolean in) {
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

}
