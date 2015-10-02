package io.vertigo.orchestra;

import io.vertigo.core.config.Features;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.dao.definition.OTaskDAO;
import io.vertigo.orchestra.dao.execution.ExecutionPAO;
import io.vertigo.orchestra.dao.execution.OProcessExecutionDAO;
import io.vertigo.orchestra.dao.execution.OTaskExecutionDAO;
import io.vertigo.orchestra.dao.execution.OTaskLogDAO;
import io.vertigo.orchestra.dao.execution.OTaskWorkspaceDAO;
import io.vertigo.orchestra.dao.planification.OProcessPlanificationDAO;
import io.vertigo.orchestra.dao.planification.PlanificationPAO;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.DtDefinitions;
import io.vertigo.orchestra.execution.OTaskManager;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.orchestra.impl.definition.ProcessDefinitionManagerImpl;
import io.vertigo.orchestra.impl.execution.OTaskManagerImpl;
import io.vertigo.orchestra.impl.execution.ProcessExecutionManagerImpl;
import io.vertigo.orchestra.impl.execution.ProcessExecutionManagerInitializer;
import io.vertigo.orchestra.impl.monitoring.MonitoringServicesImpl;
import io.vertigo.orchestra.impl.planner.ProcessPlannerManagerImpl;
import io.vertigo.orchestra.impl.planner.ProcessPlannerManagerInitializer;
import io.vertigo.orchestra.monitoring.MonitoringServices;
import io.vertigo.orchestra.planner.ProcessPlannerManager;

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
		final String period = "1";
		final String nodeName = "NODE_TEST_1";

		// @formatter:off
		getModuleConfigBuilder()
				.withNoAPI()
				.withInheritance(Object.class)
				.addComponent(ProcessDefinitionManager.class, ProcessDefinitionManagerImpl.class)
				.beginComponent(ProcessPlannerManager.class, ProcessPlannerManagerImpl.class)
					.addParam("nodeName", nodeName)
					.addParam("planningPeriod", period)// in seconds
					.addParam("forecastDuration", "60")// in seconds
					.withInitializer(ProcessPlannerManagerInitializer.class)
				.endComponent()
				.beginComponent(ProcessExecutionManager.class, ProcessExecutionManagerImpl.class)
					.addParam("nodeName", nodeName)
					.addParam("executionPeriod", period)// in seconds
					.addParam("workersCount", "3")
					.withInitializer(ProcessExecutionManagerInitializer.class)
				.endComponent()
				.addComponent(OTaskManager.class, OTaskManagerImpl.class)
				//----DAO
				.addComponent(OProcessDAO.class)
				.addComponent(OTaskDAO.class)
				.addComponent(OProcessPlanificationDAO.class)
				.addComponent(OTaskExecutionDAO.class)
				.addComponent(OProcessExecutionDAO.class)
				.addComponent(OTaskWorkspaceDAO.class)
				.addComponent(OTaskLogDAO.class)
				//----PAO
				.addComponent(ExecutionPAO.class)
				.addComponent(PlanificationPAO.class)
				//----Definitions
				.addDefinitionResource("kpr", "io/vertigo/orchestra/execution.kpr")
				.addDefinitionResource("classes", DtDefinitions.class.getName())
				//---Services
				.addComponent(MonitoringServices.class, MonitoringServicesImpl.class);
				//---WS
//				/.addComponent(WSMonitoring.class);
		// @formatter:on

	}
}
