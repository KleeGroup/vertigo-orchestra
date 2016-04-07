package io.vertigo.orchestra.impl.definition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.dao.definition.OActivityDAO;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.definition.ActivityDefinition;
import io.vertigo.orchestra.definition.ProcessDefinition;
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
	public void createDefinition(final ProcessDefinition processDefinition) {
		Assertion.checkNotNull(processDefinition);
		//-----
		final OProcess process = new OProcess();

		process.setName(processDefinition.getName());
		process.setLabel(processDefinition.getLabel());
		process.setCronExpression(processDefinition.getCronExpression().getOrElse(null));
		process.setInitialParams(processDefinition.getInitialParams().getOrElse(null));
		process.setMultiexecution(processDefinition.getMultiexecution());
		process.setRescuePeriod(processDefinition.getRescuePeriod());
		if (processDefinition.getCronExpression().isDefined()) {
			process.setTrtCd("SCHEDULED");
		} else {
			process.setTrtCd("MANUAL");
		}

		final List<ActivityDefinition> activities = processDefinition.getActivities();

		process.setActive(Boolean.TRUE);
		processDao.save(process);

		// We update the id
		processDefinition.setId(process.getProId());

		long activityNumber = 1L;
		for (final ActivityDefinition activity : activities) {
			final OActivity oActivity = new OActivity();
			oActivity.setName(activity.getName());
			oActivity.setLabel(activity.getLabel());
			oActivity.setEngine(activity.getEngine());
			oActivity.setProId(process.getProId());
			oActivity.setNumber(activityNumber);
			activityDAO.save(oActivity);// We have 10 activities max so we can iterate
			activityNumber++;
		}

	}

	@Override
	public ProcessDefinition getProcessDefinition(final String processName) {
		Assertion.checkNotNull(processName);
		// ---
		final Option<OProcess> processOption = processDao.getActiveProcessByName(processName);
		// ---
		Assertion.checkState(processOption.isDefined(), "Cannot find process with name {0}", processName);
		// ---
		final OProcess process = processOption.get();
		final DtList<OActivity> activities = activityDAO.getActivitiesByProId(process.getProId());

		return decodeProcessDefinition(process, activities);
	}

	@Override
	public List<ProcessDefinition> getAllProcessDefinitions() {
		final DtList<OProcess> processes = processDao.getAllActiveProcesses();
		final DtList<OActivity> activities = activityDAO.getAllActivitiesInActiveProcesses();
		// ---
		final List<ProcessDefinition> processDefinitions = new ArrayList<>();

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

	private static ProcessDefinition decodeProcessDefinition(final OProcess process, final List<OActivity> oActivities) {
		Assertion.checkNotNull(process);
		Assertion.checkNotNull(oActivities);
		// ---
		final ProcessDefinitionBuilder definitionBuilder = new ProcessDefinitionBuilder(process.getName(), process.getLabel());
		definitionBuilder.withRescuePeriod(process.getRescuePeriod());
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
			definitionBuilder.addActivity(activity.getName(), activity.getLabel(), activity.getEngine());
		}
		final ProcessDefinition processDefinition = definitionBuilder.build();
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

	/** {@inheritDoc} */
	@Override
	public boolean processDefinitionExist(final String processName) {
		Assertion.checkNotNull(processName);
		// ---
		return processDao.getActiveProcessByName(processName).isDefined();
	}
}
