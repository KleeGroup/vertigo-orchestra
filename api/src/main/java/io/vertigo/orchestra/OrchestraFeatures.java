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
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.DtDefinitions;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.orchestra.impl.definition.ProcessDefinitionManagerImpl;
import io.vertigo.orchestra.impl.execution.ProcessExecutionManagerImpl;
import io.vertigo.orchestra.impl.planner.ProcessPlannerManagerImpl;
import io.vertigo.orchestra.planner.ProcessPlannerManager;
import io.vertigo.orchestra.plugins.execution.DbSequentialCoordinatorPlugin;
import io.vertigo.orchestra.process.execution.manager.OCoordinationManager;
import io.vertigo.orchestra.process.execution.manager.OCoordinationManagerImpl;
import io.vertigo.orchestra.process.execution.manager.OTaskManager;
import io.vertigo.orchestra.process.execution.manager.OTaskManagerImpl;
import io.vertigo.orchestra.services.ApplicationServices;
import io.vertigo.orchestra.services.ApplicationServicesImpl;

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
				.addComponent(ProcessDefinitionManager.class, ProcessDefinitionManagerImpl.class)
				.addComponent(ProcessExecutionManager.class, ProcessExecutionManagerImpl.class)
				.addComponent(ProcessPlannerManager.class, ProcessPlannerManagerImpl.class)
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
