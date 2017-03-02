package io.vertigo.orchestra.plugins.definitions.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.VSystemException;
import io.vertigo.orchestra.dao.definition.DefinitionPAO;
import io.vertigo.orchestra.dao.definition.OActivityDAO;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.dao.planification.PlanificationPAO;
import io.vertigo.orchestra.definitions.ActivityDefinition;
import io.vertigo.orchestra.definitions.ProcessDefinition;
import io.vertigo.orchestra.definitions.ProcessDefinitionBuilder;
import io.vertigo.orchestra.definitions.ProcessType;
import io.vertigo.orchestra.domain.definition.OActivity;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.impl.definitions.ProcessDefinitionStorePlugin;
import io.vertigo.orchestra.plugins.services.MapCodec;
import io.vertigo.orchestra.services.execution.ActivityEngine;
import io.vertigo.util.ClassUtil;
import io.vertigo.util.StringUtil;

/**
 * Plugin de gestion des définitions en base de données.
 * @author mlaroche
 *
 */
@Transactional
public class DbProcessDefinitionStorePlugin implements ProcessDefinitionStorePlugin {

	@Inject
	private PlanificationPAO planificationPAO;
	@Inject
	private OProcessDAO processDao;
	@Inject
	private DefinitionPAO definitionPAO;
	@Inject
	private OActivityDAO activityDAO;

	private final MapCodec mapCodec = new MapCodec();

	private void createDefinition(final ProcessDefinition processDefinition) {
		Assertion.checkNotNull(processDefinition);
		//-----
		final OProcess process = new OProcess();

		process.setName(processDefinition.getName());
		process.setLabel(processDefinition.getLabel());
		process.setCronExpression(processDefinition.getTriggeringStrategy().getCronExpression().orElse(null));
		process.setInitialParams(mapCodec.encode(processDefinition.getTriggeringStrategy().getInitialParams()));
		process.setMultiexecution(processDefinition.getTriggeringStrategy().isMultiExecution());
		process.setRescuePeriod(processDefinition.getTriggeringStrategy().getRescuePeriod());
		if (!processDefinition.getMetadatas().isEmpty()) {
			process.setMetadatas(new GsonBuilder().create().toJson(processDefinition.getMetadatas()));
		}
		process.setNeedUpdate(processDefinition.getNeedUpdate());
		if (processDefinition.getTriggeringStrategy().getCronExpression().isPresent()) {
			process.setTrtCd("SCHEDULED");
		} else {
			process.setTrtCd("MANUAL");
		}

		final List<ActivityDefinition> activities = processDefinition.getActivities();

		process.setActive(processDefinition.isActive());
		process.setActiveVersion(Boolean.TRUE);
		processDao.save(process);

		// We update the id
		processDefinition.setId(process.getProId());

		int activityNumber = 1;
		for (final ActivityDefinition activity : activities) {
			final OActivity oActivity = new OActivity();
			oActivity.setName(activity.getName());
			oActivity.setLabel(activity.getLabel());
			oActivity.setEngine(activity.getEngineClass().getName());
			oActivity.setProId(process.getProId());
			oActivity.setNumber(activityNumber);
			activityDAO.save(oActivity);// We have 10 activities max so we can iterate
			activityNumber++;
		}

	}

	/** {@inheritDoc} */
	@Override
	public ProcessDefinition getProcessDefinition(final String processName) {
		Assertion.checkArgNotEmpty(processName);
		// ---
		final OProcess process = getOProcessByName(processName);
		final DtList<OActivity> activities = activityDAO.getActivitiesByProId(process.getProId());

		return decodeProcessDefinition(process, activities);
	}

	/** {@inheritDoc} */
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

	private ProcessDefinition decodeProcessDefinition(final OProcess process, final List<OActivity> oActivities) {
		Assertion.checkNotNull(process);
		Assertion.checkNotNull(oActivities);
		// ---
		final ProcessDefinitionBuilder processDefinitionBuilder = new ProcessDefinitionBuilder(process.getName(), process.getLabel(), ProcessType.SUPERVISED);
		processDefinitionBuilder.withRescuePeriod(process.getRescuePeriod());
		if (!StringUtil.isEmpty(process.getCronExpression())) {
			processDefinitionBuilder.withCronExpression(process.getCronExpression());
		}
		processDefinitionBuilder.addInitialParams(mapCodec.decode(process.getInitialParams()));
		if (process.getNeedUpdate() != null && process.getNeedUpdate()) {
			processDefinitionBuilder.withNeedUpdate();
		}
		if (!StringUtil.isEmpty(process.getMetadatas())) {
			// voir si on fait mieux
			processDefinitionBuilder.withMetadatas(new GsonBuilder().create().fromJson(process.getMetadatas(),
					new TypeToken<Map<String, String>>() {
						private static final long serialVersionUID = 1L;/*rien*/
					}.getType()));
		}
		if (process.getMultiexecution()) {
			processDefinitionBuilder.withMultiExecution();
		}
		if (!process.getActive()) {
			processDefinitionBuilder.inactive();
		}
		for (final OActivity activity : oActivities) {
			processDefinitionBuilder.addActivity(activity.getName(), activity.getLabel(), ClassUtil.classForName(activity.getEngine(), ActivityEngine.class));
		}
		final ProcessDefinition processDefinition = processDefinitionBuilder.build();
		processDefinition.setId(process.getProId());
		return processDefinition;

	}

	private static final class OActivityComparator implements Comparator<OActivity>, Serializable {

		private static final long serialVersionUID = 1L;

		/** {@inheritDoc} */
		@Override
		public int compare(final OActivity actikvity1, final OActivity activity2) {
			return actikvity1.getNumber() - activity2.getNumber();
		}

	}

	/** {@inheritDoc} */
	@Override
	public void createOrUpdateDefinition(final ProcessDefinition processDefinition) {
		Assertion.checkNotNull(processDefinition);
		// ---
		final String processName = processDefinition.getName();

		final int count = definitionPAO.getProcessesByName(processName);
		final boolean exists = count > 0;
		if (exists) {
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
		// ---
		final String processName = processDefinition.getName();
		definitionPAO.disableOldProcessDefinitions(processName);
		// on supprime toute la planification existante
		planificationPAO.cleanFuturePlanifications(processName);
		createDefinition(processDefinition);

	}

	/** {@inheritDoc} */
	@Override
	public void updateProcessDefinitionProperties(final ProcessDefinition processDefinition, final Optional<String> cronExpression, final boolean multiExecution, final int rescuePeriod,
			final boolean active) {
		Assertion.checkNotNull(processDefinition);
		Assertion.checkNotNull(cronExpression);
		Assertion.checkNotNull(rescuePeriod);
		// ---
		final OProcess process = getOProcessByName(processDefinition.getName());
		if (cronExpression.isPresent()) {
			process.setTrtCd("SCHEDULED");
		} else {
			process.setTrtCd("MANUAL");
		}
		process.setCronExpression(cronExpression.orElse(null));
		process.setMultiexecution(multiExecution);
		process.setRescuePeriod(rescuePeriod);
		process.setActive(active);
		processDao.save(process);
		// on supprime toute la planification existante
		planificationPAO.cleanFuturePlanifications(processDefinition.getName());
	}

	/** {@inheritDoc} */
	@Override
	public void updateProcessDefinitionInitialParams(final ProcessDefinition processDefinition, final Map<String, String> initialParams) {
		Assertion.checkNotNull(processDefinition);
		Assertion.checkNotNull(initialParams);
		// ---
		final OProcess process = getOProcessByName(processDefinition.getName());
		process.setInitialParams(mapCodec.encode(initialParams));
		processDao.save(process);

	}

	private OProcess getOProcessByName(final String processName) {
		Assertion.checkArgNotEmpty(processName);
		// ---
		return processDao.getActiveProcessByName(processName)
				.orElseThrow(() -> new VSystemException("Cannot find process with name {0}", processName));
	}

	@Override
	public boolean processDefinitionExists(final String processName) {
		Assertion.checkArgNotEmpty(processName);
		// ---
		return processDao.getActiveProcessByName(processName).isPresent();
	}

	@Override
	public ProcessType getHandledProcessType() {
		return ProcessType.SUPERVISED;
	}

}
