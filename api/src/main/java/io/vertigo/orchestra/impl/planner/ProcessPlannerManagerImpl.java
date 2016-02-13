package io.vertigo.orchestra.impl.planner;

import java.util.Date;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.planner.ProcessPlannerManager;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ProcessPlannerManagerImpl implements ProcessPlannerManager {

	@Inject
	private VTransactionManager transactionManager;

	private final ProcessSchedulerPlugin processScheduler;

	//--------------------------------------------------------------------------------------------------
	//--- Constructeur
	//--------------------------------------------------------------------------------------------------
	@Inject
	public ProcessPlannerManagerImpl(final ProcessSchedulerPlugin processScheduler) {
		Assertion.checkNotNull(processScheduler);
		// ---
		this.processScheduler = processScheduler;

	}

	//--------------------------------------------------------------------------------------------------
	//--- Public
	//--------------------------------------------------------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public void plannProcessAt(final Long proId, final Date planifiedTime, final String initialParams) {
		Assertion.checkNotNull(proId);
		// ---
		if (transactionManager.hasCurrentTransaction()) {
			processScheduler.plannProcessAt(proId, planifiedTime, initialParams);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				processScheduler.plannProcessAt(proId, planifiedTime, initialParams);
				transaction.commit();
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void plannProcessAt(final Long proId, final Date planifiedTime) {
		plannProcessAt(proId, planifiedTime, null);

	}

	/** {@inheritDoc} */

	/** {@inheritDoc} */
	@Override
	public DtList<OProcessPlanification> getProcessToExecute() {
		if (transactionManager.hasCurrentTransaction()) {
			return processScheduler.getProcessToExecute();
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				final DtList<OProcessPlanification> processesToExecute = processScheduler.getProcessToExecute();
				transaction.commit();
				return processesToExecute;
			}
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
