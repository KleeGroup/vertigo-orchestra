package io.vertigo.orchestra.impl.execution;

import javax.inject.Inject;

import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.execution.ProcessExecutionManager;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ProcessExecutionManagerImpl implements ProcessExecutionManager {

	private final VTransactionManager transactionManager;
	private final SequentialExecutorPlugin sequentialExecutorPlugin;

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
		if (transactionManager.hasCurrentTransaction()) {
			sequentialExecutorPlugin.endPendingActivityExecution(activityExecutionId, token, state);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				sequentialExecutorPlugin.endPendingActivityExecution(activityExecutionId, token, state);
				transaction.commit();
			}
		}
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
}
