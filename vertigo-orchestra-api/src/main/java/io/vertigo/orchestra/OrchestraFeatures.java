package io.vertigo.orchestra;

import io.vertigo.app.config.Features;
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
import io.vertigo.orchestra.impl.execution.SequentialExecutorPlugin;
import io.vertigo.orchestra.impl.monitoring.MonitoringServicesImpl;
import io.vertigo.orchestra.impl.scheduler.ProcessSchedulerManagerImpl;
import io.vertigo.orchestra.impl.scheduler.ProcessSchedulerPlugin;
import io.vertigo.orchestra.monitoring.MonitoringServices;
import io.vertigo.orchestra.scheduler.ProcessSchedulerManager;

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
				.addComponent(ProcessDefinitionManager.class, ProcessDefinitionManagerImpl.class)
				.addComponent(ProcessSchedulerManager.class, ProcessSchedulerManagerImpl.class)
				.beginPlugin(ProcessSchedulerPlugin.class)
					.addParam("nodeName", nodeName)
					.addParam("planningPeriod", period)// in seconds
					.addParam("forecastDuration", "60")// in seconds
				.endPlugin()
				.addComponent(ProcessExecutionManager.class, ProcessExecutionManagerImpl.class)
				.beginPlugin(SequentialExecutorPlugin.class)
					.addParam("nodeName", nodeName)
					.addParam("workersCount", "3")
					.addParam("executionPeriod", period)// in seconds
				.endPlugin()
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