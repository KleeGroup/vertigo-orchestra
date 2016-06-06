package io.vertigo.orchestra.impl.definition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.dao.definition.DefinitionPAO;
import io.vertigo.orchestra.dao.definition.OActivityDAO;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.definition.ActivityDefinition;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessDefinitionBuilder;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OActivity;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.execution.ActivityEngine;
import io.vertigo.orchestra.scheduler.ProcessSchedulerManager;
import io.vertigo.util.ClassUtil;
import io.vertigo.util.StringUtil;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public final class ProcessDefinitionManagerImpl implements ProcessDefinitionManager {

	@Inject
	private OProcessDAO processDao;
	@Inject
	private DefinitionPAO definitionPAO;
	@Inject
	private OActivityDAO activityDAO;

	@Inject
	private ProcessSchedulerManager processSchedulerManager;

	private void createDefinition(final ProcessDefinition processDefinition) {
		Assertion.checkNotNull(processDefinition);
		//-----
		final OProcess process = new OProcess();

		process.setName(processDefinition.getName());
		process.setLabel(processDefinition.getLabel());
		process.setCronExpression(processDefinition.getCronExpression().getOrElse(null));
		process.setInitialParams(processDefinition.getInitialParams().getOrElse(null));
		process.setMultiexecution(processDefinition.isMultiExecution());
		process.setRescuePeriod(processDefinition.getRescuePeriod());
		process.setMetadatas(processDefinition.getMetadatas().getOrElse(null));
		process.setNeedUpdate(processDefinition.getNeedUpdate());
		if (processDefinition.getCronExpression().isDefined()) {
			process.setTrtCd("SCHEDULED");
		} else {
			process.setTrtCd("MANUAL");
		}

		final List<ActivityDefinition> activities = processDefinition.getActivities();

		process.setActive(Boolean.TRUE);
		process.setActiveVersion(Boolean.TRUE);
		processDao.save(process);

		// We update the id
		processDefinition.setId(process.getProId());

		int activityNumber = 1;
		for (final ActivityDefinition activity : activities) {
			final OActivity oActivity = new OActivity();
			oActivity.setName(activity.getName());
			oActivity.setLabel(activity.getLabel());
			oActivity.setEngine(activity.getEngineClass().getSimpleName());
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
		final OProcess process = getOProcessByName(processName);
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
			Collections.sort(activitiesByProcess, new OActivityComparator());
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
			definitionBuilder.withCronExpression(process.getCronExpression());
		}
		if (!StringUtil.isEmpty(process.getInitialParams())) {
			definitionBuilder.withInitialParams(process.getInitialParams());
		}
		if (process.getNeedUpdate() != null && process.getNeedUpdate()) {
			definitionBuilder.withNeedUpdate();
		}
		if (!StringUtil.isEmpty(process.getMetadatas())) {
			definitionBuilder.withMetadatas(process.getMetadatas());
		}
		if (process.getMultiexecution()) {
			definitionBuilder.withMultiExecution();
		}
		for (final OActivity activity : oActivities) {
			definitionBuilder.addActivity(activity.getName(), activity.getLabel(), ClassUtil.classForName(activity.getEngine(), ActivityEngine.class));
		}
		final ProcessDefinition processDefinition = definitionBuilder.build();
		processDefinition.setId(process.getProId());
		return processDefinition;

	}

	public final class OActivityComparator implements Comparator<OActivity> {

		/** {@inheritDoc} */
		@Override
		public int compare(final OActivity actikvity1, final OActivity activity2) {
			return actikvity1.getNumber() - activity2.getNumber();
		}

	}

	/** {@inheritDoc} */
	@Override
	public boolean processDefinitionExist(final String processName) {
		Assertion.checkNotNull(processName);
		// ---
		return 0 != definitionPAO.getProcessesByName(processName);
	}

	/** {@inheritDoc} */
	@Override
	public void createOrUpdateDefinitionIfNeeded(final ProcessDefinition processDefinition) {
		Assertion.checkNotNull(processDefinition);
		Assertion.checkNotNull(processDefinition.getName());
		// ---
		final String processName = processDefinition.getName();
		if (processDefinitionExist(processName)) {
			final ProcessDefinition existingDefinition = getProcessDefinition(processName);
			if (existingDefinition.getNeedUpdate()) {
				updateDefinition(processDefinition);
			}
		} else {
			createDefinition(processDefinition);
		}

	}

	private void updateDefinition(final ProcessDefinition processDefinition) {
		Assertion.checkNotNull(processDefinition);
		Assertion.checkNotNull(processDefinition.getName());
		// ---
		final String processName = processDefinition.getName();
		definitionPAO.disableOldProcessDefinitions(processName);
		// on supprime toute la planification existante
		processSchedulerManager.resetFuturePlanificationOfProcess(processName);
		createDefinition(processDefinition);

	}

	/** {@inheritDoc} */
	@Override
	public void updateProcessDefinitionProperties(final String processName, final Option<String> cronExpression, final boolean multiExecution, final int rescuePeriod, final boolean active) {
		Assertion.checkNotNull(processName);
		Assertion.checkNotNull(cronExpression);
		Assertion.checkNotNull(rescuePeriod);
		// ---
		final OProcess process = getOProcessByName(processName);
		if (cronExpression.isDefined()) {
			process.setTrtCd("SCHEDULED");
		} else {
			process.setTrtCd("MANUAL");
		}
		process.setCronExpression(cronExpression.getOrElse(null));
		process.setMultiexecution(multiExecution);
		process.setRescuePeriod(rescuePeriod);
		process.setActive(active);
		processDao.save(process);
		// on supprime toute la planification existante
		processSchedulerManager.resetFuturePlanificationOfProcess(processName);
	}

	/** {@inheritDoc} */
	@Override
	public void updateProcessDefinitionInitialParams(final String processName, final Option<String> initialParams) {
		Assertion.checkNotNull(processName);
		Assertion.checkNotNull(initialParams);
		// ---
		final OProcess process = getOProcessByName(processName);
		process.setInitialParams(initialParams.getOrElse(null));
		processDao.save(process);

	}

	private OProcess getOProcessByName(final String processName) {
		Assertion.checkNotNull(processName);
		// ---
		final Option<OProcess> processOption = processDao.getActiveProcessByName(processName);
		// ---
		Assertion.checkState(processOption.isDefined(), "Cannot find process with name {0}", processName);
		// ---
		return processOption.get();
	}

}
