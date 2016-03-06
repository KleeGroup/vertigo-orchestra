package io.vertigo.orchestra.impl.definition;

import java.util.List;

import javax.inject.Inject;

import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.dao.definition.OTaskDAO;
import io.vertigo.orchestra.definition.Activity;
import io.vertigo.orchestra.definition.Process;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;

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
	public void createDefinition(final Process processDefinition) {
		Assertion.checkNotNull(processDefinition);
		//-----
		final OProcess process = new OProcess();

		process.setName(processDefinition.getName());
		process.setCronExpression(processDefinition.getCronExpression().getOrElse(null));
		process.setInitialParams(processDefinition.getInitialParams().getOrElse(null));
		process.setMultiexecution(processDefinition.getMultiexecution());
		if (processDefinition.getCronExpression().isDefined()) {
			process.setTrtCd("SCHEDULED");
		} else {
			process.setTrtCd("MANUAL");
		}

		final List<Activity> activities = processDefinition.getActivities();

		processDao.save(process);

		// We update the id
		processDefinition.setId(process.getProId());

		long taskId = 1L;
		for (final Activity activity : activities) {
			final OTask task = new OTask();
			task.setName(activity.getName());
			task.setEngine(activity.getEngine());
			task.setProId(process.getProId());
			task.setNumber(taskId);
			taskDAO.save(task);// We have 10 tasks max so we can iterate
			taskId++;
		}

	}

}
