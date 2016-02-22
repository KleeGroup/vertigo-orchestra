package io.vertigo.orchestra.impl.scheduler;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.scheduler.ProcessSchedulerManager;

import java.util.Date;

import javax.inject.Inject;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ProcessSchedulerManagerImpl implements ProcessSchedulerManager {
	private final VTransactionManager transactionManager;

	private final ProcessSchedulerPlugin processScheduler;

	@Inject
	public ProcessSchedulerManagerImpl(
			final VTransactionManager transactionManager,
			final ProcessSchedulerPlugin processScheduler) {
		Assertion.checkNotNull(transactionManager);
		Assertion.checkNotNull(processScheduler);
		// ---
		this.transactionManager = transactionManager;
		this.processScheduler = processScheduler;
	}

	//--------------------------------------------------------------------------------------------------
	//--- Public
	//--------------------------------------------------------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public void scheduleAt(final Long proId, final Date planifiedTime, final Option<String> initialParamsOption) {
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
	public DtList<OProcessPlanification> getProcessToExecute() {
		if (transactionManager.hasCurrentTransaction()) {
			return processScheduler.getProcessToExecute();
		}
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			final DtList<OProcessPlanification> processesToExecute = processScheduler.getProcessToExecute();
			transaction.commit();
			return processesToExecute;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void triggerPlanification(final OProcessPlanification processPlanification) {
		if (transactionManager.hasCurrentTransaction()) {
			processScheduler.triggerPlanification(processPlanification);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				processScheduler.triggerPlanification(processPlanification);
				transaction.commit();
			}
		}

	}

	/** {@inheritDoc} */
	@Override
	public void misfirePlanification(final OProcessPlanification processPlanification) {
		if (transactionManager.hasCurrentTransaction()) {
			processScheduler.misfirePlanification(processPlanification);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				processScheduler.misfirePlanification(processPlanification);
				transaction.commit();
			}
		}
	}

}
