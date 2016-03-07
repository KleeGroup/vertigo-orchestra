package io.vertigo.orchestra.impl.definition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.dao.definition.OTaskDAO;
import io.vertigo.orchestra.definition.Activity;
import io.vertigo.orchestra.definition.Process;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;
import io.vertigo.util.StringUtil;

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

	@Override
	public Process getProcessDefinition(final String processName) {
		Assertion.checkNotNull(processName);
		// ---
		final OProcess process = processDao.getActiveProcessByName(processName);
		final DtList<OTask> tasks = taskDAO.getTasksByProId(process.getProId());

		return decodeProcessDefinition(process, tasks);
	}

	@Override
	public List<Process> getAllProcessDefinitions() {
		final DtList<OProcess> processes = processDao.getAllActiveProcesses();
		final DtList<OTask> tasks = taskDAO.getAllTasksInActiveProcesses();
		// ---
		final List<Process> processDefinitions = new ArrayList<>();

		for (final OProcess process : processes) {
			final List<OTask> taskByProcess = new ArrayList<>();
			for (final OTask task : tasks) {
				if (task.getProId().equals(process.getProId())) {
					taskByProcess.add(task);
				}
			}
			taskByProcess.sort(new OTaskComparator());
			processDefinitions.add(decodeProcessDefinition(process, taskByProcess));
		}
		return processDefinitions;

	}

	private static Process decodeProcessDefinition(final OProcess process, final List<OTask> tasks) {
		Assertion.checkNotNull(process);
		Assertion.checkNotNull(tasks);
		// ---
		final ProcessDefinitionBuilder definitionBuilder = new ProcessDefinitionBuilder(process.getName());
		if (!StringUtil.isEmpty(process.getCronExpression())) {
			definitionBuilder.withCron(process.getCronExpression());
		}
		if (!StringUtil.isEmpty(process.getInitialParams())) {
			definitionBuilder.withInitialParams(process.getInitialParams());
		}
		if (process.getMultiexecution()) {
			definitionBuilder.withMultiExecution();
		}
		for (final OTask task : tasks) {
			definitionBuilder.addActivity(task.getName(), task.getEngine());
		}
		final Process processDefinition = definitionBuilder.build();
		processDefinition.setId(process.getProId());
		return processDefinition;

	}

	class OTaskComparator implements Comparator<OTask> {

		/** {@inheritDoc} */
		@Override
		public int compare(final OTask task1, final OTask task2) {
			return (int) (task1.getNumber() - task2.getNumber());
		}

	}
}
