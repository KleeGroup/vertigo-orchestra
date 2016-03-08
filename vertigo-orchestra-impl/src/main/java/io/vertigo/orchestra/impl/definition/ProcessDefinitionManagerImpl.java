package io.vertigo.orchestra.impl.definition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.dao.definition.OActivityDAO;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.definition.Activity;
import io.vertigo.orchestra.definition.Process;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OActivity;
import io.vertigo.orchestra.domain.definition.OProcess;
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
	private OActivityDAO activityDAO;

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

		process.setActive(Boolean.TRUE);
		processDao.save(process);

		// We update the id
		processDefinition.setId(process.getProId());

		long activityNumber = 1L;
		for (final Activity activity : activities) {
			final OActivity oActivity = new OActivity();
			oActivity.setName(activity.getName());
			oActivity.setEngine(activity.getEngine());
			oActivity.setProId(process.getProId());
			oActivity.setNumber(activityNumber);
			activityDAO.save(oActivity);// We have 10 activities max so we can iterate
			activityNumber++;
		}

	}

	@Override
	public Process getProcessDefinition(final String processName) {
		Assertion.checkNotNull(processName);
		// ---
		final OProcess process = processDao.getActiveProcessByName(processName);
		final DtList<OActivity> activities = activityDAO.getActivitiesByProId(process.getProId());

		return decodeProcessDefinition(process, activities);
	}

	@Override
	public List<Process> getAllProcessDefinitions() {
		final DtList<OProcess> processes = processDao.getAllActiveProcesses();
		final DtList<OActivity> activities = activityDAO.getAllActivitiesInActiveProcesses();
		// ---
		final List<Process> processDefinitions = new ArrayList<>();

		for (final OProcess process : processes) {
			final List<OActivity> activitiesByProcess = new ArrayList<>();
			for (final OActivity activity : activities) {
				if (activity.getProId().equals(process.getProId())) {
					activitiesByProcess.add(activity);
				}
			}
			activitiesByProcess.sort(new OActivityComparator());
			processDefinitions.add(decodeProcessDefinition(process, activitiesByProcess));
		}
		return processDefinitions;

	}

	private static Process decodeProcessDefinition(final OProcess process, final List<OActivity> oActivities) {
		Assertion.checkNotNull(process);
		Assertion.checkNotNull(oActivities);
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
		for (final OActivity activity : oActivities) {
			definitionBuilder.addActivity(activity.getName(), activity.getEngine());
		}
		final Process processDefinition = definitionBuilder.build();
		processDefinition.setId(process.getProId());
		return processDefinition;

	}

	class OActivityComparator implements Comparator<OActivity> {

		/** {@inheritDoc} */
		@Override
		public int compare(final OActivity actikvity1, final OActivity activity2) {
			return (int) (actikvity1.getNumber() - activity2.getNumber());
		}

	}
}
