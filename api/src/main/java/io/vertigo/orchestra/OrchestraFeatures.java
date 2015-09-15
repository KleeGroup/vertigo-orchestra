package io.vertigo.orchestra;

import io.vertigo.core.config.Features;
import io.vertigo.orchestra.dao.application.ApplicationPAO;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.dao.definition.OTaskDAO;
import io.vertigo.orchestra.dao.execution.ExecutionPAO;
import io.vertigo.orchestra.dao.execution.OProcessExecutionDAO;
import io.vertigo.orchestra.dao.execution.OTaskExecutionDAO;
import io.vertigo.orchestra.dao.planification.OProcessPlanificationDAO;
import io.vertigo.orchestra.dao.planification.PlanificationPAO;
import io.vertigo.orchestra.domain.DtDefinitions;
import io.vertigo.orchestra.services.ApplicationServices;
import io.vertigo.orchestra.services.ApplicationServicesImpl;
import io.vertigo.orchestra.services.definition.ProcessServices;
import io.vertigo.orchestra.services.definition.ProcessServicesImpl;
import io.vertigo.orchestra.services.definition.TaskServices;
import io.vertigo.orchestra.services.definition.TaskServicesImpl;
import io.vertigo.orchestra.services.execution.ExecutionServices;
import io.vertigo.orchestra.services.execution.ExecutionServicesImpl;
import io.vertigo.orchestra.services.execution.manager.OCoordinationManager;
import io.vertigo.orchestra.services.execution.manager.OCoordinationManagerImpl;
import io.vertigo.orchestra.services.execution.manager.OTaskManager;
import io.vertigo.orchestra.services.execution.manager.OTaskManagerImpl;
import io.vertigo.orchestra.services.execution.plugin.DbSequentialCoordinatorPlugin;
import io.vertigo.orchestra.services.planification.ProcessPlanificationServices;
import io.vertigo.orchestra.services.planification.ProcessPlanificationServicesImpl;
import io.vertigo.orchestra.services.planification.manager.PlannerManager;
import io.vertigo.orchestra.services.planification.manager.PlannerManagerImpl;
import io.vertigo.orchestra.services.planification.plugin.RecurrentProcessPlannerPlugin;

/**
 * Defines extension comment.
 * @author pchretien
 */
public final class OrchestraFeatures extends Features {

	public OrchestraFeatures() {
		super("orchestra");
	}

	@Override
	protected void setUp() {
		getModuleConfigBuilder()
				.withNoAPI()
				.withInheritance(Object.class)
				.addComponent(ApplicationServices.class, ApplicationServicesImpl.class)
				.addComponent(ProcessServices.class, ProcessServicesImpl.class)
				.addComponent(TaskServices.class, TaskServicesImpl.class)
				.addComponent(ExecutionServices.class, ExecutionServicesImpl.class)
				.addComponent(ProcessPlanificationServices.class, ProcessPlanificationServicesImpl.class)
				.addComponent(PlannerManager.class, PlannerManagerImpl.class)
				.addPlugin(RecurrentProcessPlannerPlugin.class)
				.addComponent(OTaskManager.class, OTaskManagerImpl.class)
				.addComponent(OCoordinationManager.class, OCoordinationManagerImpl.class)
				.addPlugin(DbSequentialCoordinatorPlugin.class)
				//----DAO
				.addComponent(OProcessDAO.class)
				.addComponent(OTaskDAO.class)
				.addComponent(OProcessPlanificationDAO.class)
				.addComponent(OTaskExecutionDAO.class)
				.addComponent(OProcessExecutionDAO.class)
				//----PAO
				.addComponent(ExecutionPAO.class)
				.addComponent(PlanificationPAO.class)
				.addComponent(ApplicationPAO.class)
				//----Definitions
				.addDefinitionResource("kpr", "io/vertigo/orchestra/execution.kpr")
				.addDefinitionResource("classes", DtDefinitions.class.getName());
	}
}
