package io.vertigo.orchestra;

import io.vertigo.app.config.Features;
import io.vertigo.orchestra.dao.definition.DefinitionPAO;
import io.vertigo.orchestra.dao.definition.OActivityDAO;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.dao.execution.ExecutionPAO;
import io.vertigo.orchestra.dao.execution.OActivityExecutionDAO;
import io.vertigo.orchestra.dao.execution.OActivityLogDAO;
import io.vertigo.orchestra.dao.execution.OActivityWorkspaceDAO;
import io.vertigo.orchestra.dao.execution.ONodeDAO;
import io.vertigo.orchestra.dao.execution.OProcessExecutionDAO;
import io.vertigo.orchestra.dao.planification.OProcessPlanificationDAO;
import io.vertigo.orchestra.dao.planification.PlanificationPAO;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.DtDefinitions;
import io.vertigo.orchestra.execution.NodeManager;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.orchestra.impl.OrchestraManagerImpl;
import io.vertigo.orchestra.impl.definition.ProcessDefinitionManagerImpl;
import io.vertigo.orchestra.impl.execution.NodeManagerImpl;
import io.vertigo.orchestra.impl.execution.ProcessExecutionManagerImpl;
import io.vertigo.orchestra.impl.execution.SequentialExecutorPlugin;
import io.vertigo.orchestra.impl.scheduler.ProcessSchedulerManagerImpl;
import io.vertigo.orchestra.impl.scheduler.ProcessSchedulerPlugin;
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
				.addComponent(NodeManager.class, NodeManagerImpl.class)
				.addComponent(ProcessDefinitionManager.class, ProcessDefinitionManagerImpl.class)
				.addComponent(ProcessSchedulerManager.class, ProcessSchedulerManagerImpl.class)
				.beginPlugin(ProcessSchedulerPlugin.class)
					.addParam("nodeName", nodeName)
					.addParam("planningPeriodSeconds", period)
					.addParam("forecastDurationSeconds", "60")
				.endPlugin()
				.addComponent(ProcessExecutionManager.class, ProcessExecutionManagerImpl.class)
				.beginPlugin(SequentialExecutorPlugin.class)
					.addParam("nodeName", nodeName)
					.addParam("workersCount", "3")
					.addParam("executionPeriodSeconds", period)
				.endPlugin()
				.addComponent(OrchestraManager.class, OrchestraManagerImpl.class)
				//----DAO
				.addComponent(OProcessDAO.class)
				.addComponent(OActivityDAO.class)
				.addComponent(OProcessPlanificationDAO.class)
				.addComponent(OActivityExecutionDAO.class)
				.addComponent(OProcessExecutionDAO.class)
				.addComponent(OActivityWorkspaceDAO.class)
				.addComponent(OActivityLogDAO.class)
				.addComponent(ONodeDAO.class)
				//----PAO
				.addComponent(DefinitionPAO.class)
				.addComponent(ExecutionPAO.class)
				.addComponent(PlanificationPAO.class)
				//----Definitions
				.addDefinitionResource("kpr", "io/vertigo/orchestra/execution.kpr")
				.addDefinitionResource("classes", DtDefinitions.class.getName());
				//---WS
//				/.addComponent(WSMonitoring.class);
		// @formatter:on

	}
}
