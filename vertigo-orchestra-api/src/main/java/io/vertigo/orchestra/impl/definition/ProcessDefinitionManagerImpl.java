package io.vertigo.orchestra.impl.definition;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.dao.definition.OTaskDAO;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;

import java.util.List;

import javax.inject.Inject;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class ProcessDefinitionManagerImpl implements ProcessDefinitionManager {

	@Inject
	private OProcessDAO processDao;
	@Inject
	private OTaskDAO taskDAO;

	/** {@inheritDoc} */
	@Override
	public DtList<OProcess> getAllScheduledProcesses() {
		return processDao.getRecurrentProcesses();
	}

	/** {@inheritDoc} */
	@Override
	public OTask getFirtTaskByProcess(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		return taskDAO.getFirstTaskByProcess(proId);
	}

	/** {@inheritDoc} */
	@Override
	public Option<OTask> getNextTaskByTskId(final Long tskId) {
		Assertion.checkNotNull(tskId);
		// ---
		return taskDAO.getNextTaskByTskId(tskId);
	}

	/** {@inheritDoc} */
	@Override
	public void createDefinition(final ProcessDefinition processDefinition) {
		Assertion.checkNotNull(processDefinition);
		//-----
		final OProcess process = processDefinition.getProcess();
		final List<OTask> tasks = processDefinition.getTasks();

		processDao.save(process);

		long taskId = 1L;
		for (final OTask task : tasks) {
			task.setProId(process.getProId());
			task.setNumber(taskId);
			taskDAO.save(task);// We have 10 tasks max so we can iterate
			taskId++;
		}

	}

}