package io.vertigo.orchestra.impl.scheduler;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.scheduler.ProcessSchedulerManager;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessSchedulerManagerImpl implements ProcessSchedulerManager {
	private final VTransactionManager transactionManager;
	private final ProcessSchedulerPlugin processScheduler;

	@Inject
	public ProcessSchedulerManagerImpl(
			final VTransactionManager transactionManager,
			final ProcessSchedulerPlugin processScheduler) {
		Assertion.checkNotNull(transactionManager);
		Assertion.checkNotNull(processScheduler);
		//---
		this.transactionManager = transactionManager;
		this.processScheduler = processScheduler;
	}

	//--------------------------------------------------------------------------------------------------
	//--- Public
	//--------------------------------------------------------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public void scheduleAt(final Long proId, final Date planifiedTime, final Optional<String> initialParamsOption) {
		Assertion.checkNotNull(proId);
		// ---
		if (transactionManager.hasCurrentTransaction()) {
			processScheduler.scheduleAt(proId, planifiedTime, initialParamsOption);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				processScheduler.scheduleAt(proId, planifiedTime, initialParamsOption);
				transaction.commit();
			}
		}
	}

	/** {@inheritDoc} */

	/** {@inheritDoc} */
	@Override
	public List<Long> getProcessToExecute() {
		if (transactionManager.hasCurrentTransaction()) {
			return processScheduler.getProcessToExecute();
		}
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			final List<Long> processesToExecute = processScheduler.getProcessToExecute();
			transaction.commit();
			return processesToExecute;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void triggerPlanification(final Long prpId) {
		if (transactionManager.hasCurrentTransaction()) {
			processScheduler.triggerPlanification(prpId);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				processScheduler.triggerPlanification(prpId);
				transaction.commit();
			}
		}

	}

	/** {@inheritDoc} */
	@Override
	public void misfirePlanification(final Long prpId) {
		if (transactionManager.hasCurrentTransaction()) {
			processScheduler.misfirePlanification(prpId);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				processScheduler.misfirePlanification(prpId);
				transaction.commit();
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void resetFuturePlanificationOfProcess(final String processName) {
		Assertion.checkNotNull(processName);
		// ---
		if (transactionManager.hasCurrentTransaction()) {
			processScheduler.resetFuturePlanificationOfProcess(processName);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				processScheduler.resetFuturePlanificationOfProcess(processName);
				transaction.commit();
			}
		}

	}

}
