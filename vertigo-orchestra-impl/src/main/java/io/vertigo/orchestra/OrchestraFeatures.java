package io.vertigo.orchestra;

import io.vertigo.app.config.Features;
import io.vertigo.core.param.Param;
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
import io.vertigo.orchestra.impl.definition.ProcessDefinitionManagerImpl;
import io.vertigo.orchestra.impl.node.NodeManagerImpl;
import io.vertigo.orchestra.impl.process.ProcessManagerImpl;
import io.vertigo.orchestra.monitoring.dao.summary.SummaryPAO;
import io.vertigo.orchestra.monitoring.dao.uidefinitions.UidefinitionsPAO;
import io.vertigo.orchestra.monitoring.dao.uiexecutions.UiexecutionsPAO;
import io.vertigo.orchestra.node.NodeManager;
import io.vertigo.orchestra.plugins.definition.db.DbProcessDefinitionStorePlugin;
import io.vertigo.orchestra.plugins.definition.memory.MemoryProcessDefinitionStorePlugin;
import io.vertigo.orchestra.plugins.process.execution.db.DbProcessExecutorPlugin;
import io.vertigo.orchestra.plugins.process.execution.memory.MemoryProcessExecutorPlugin;
import io.vertigo.orchestra.plugins.process.log.db.DbProcessLoggerPlugin;
import io.vertigo.orchestra.plugins.process.report.db.DbProcessReportPlugin;
import io.vertigo.orchestra.plugins.process.schedule.db.DbProcessSchedulerPlugin;
import io.vertigo.orchestra.plugins.process.schedule.memory.MemoryProcessSchedulerPlugin;
import io.vertigo.orchestra.process.ProcessManager;

/**
 * Defines extension orchestra.
 * @author pchretien
 */
public final class OrchestraFeatures extends Features {

	/**
	 * Constructeur de la feature.
	 */
	public OrchestraFeatures() {
		super("orchestra");
	}

	/**
	 * Activate Orchestra with Database.
	 * @param nodeName the node of the app
	 * @param daemonPeriodSeconds the period for scheduling and execution
	 * @param workersCount the number of workers
	 * @param forecastDurationSeconds the time to forecast planifications
	 * @return these features
	 */
	public OrchestraFeatures withDataBase(final String nodeName, final int daemonPeriodSeconds, final int workersCount, final int forecastDurationSeconds) {
		getModuleConfigBuilder()
				.withNoAPI()
				.addPlugin(DbProcessDefinitionStorePlugin.class)
				.addPlugin(DbProcessSchedulerPlugin.class,
						Param.create("nodeName", nodeName),
						Param.create("planningPeriodSeconds", String.valueOf(daemonPeriodSeconds)),
						Param.create("forecastDurationSeconds", String.valueOf(forecastDurationSeconds)))
				.addPlugin(DbProcessExecutorPlugin.class,
						Param.create("nodeName", nodeName),
						Param.create("workersCount", String.valueOf(workersCount)),
						Param.create("executionPeriodSeconds", String.valueOf(daemonPeriodSeconds)))
				.addPlugin(DbProcessReportPlugin.class)
				.addPlugin(DbProcessLoggerPlugin.class)
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
				.addComponent(UidefinitionsPAO.class)
				.addComponent(UiexecutionsPAO.class)
				.addComponent(SummaryPAO.class)
				//----Definitions
				.addDefinitionResource("kpr", "io/vertigo/orchestra/execution.kpr");
		return this;
	}

	/**
	 * Activate Orchestra with Memory.
	 * @param workersCount the number of workers
	 * @return these features
	 */
	public OrchestraFeatures withMemory(final int workersCount) {
		getModuleConfigBuilder()
				.addPlugin(MemoryProcessDefinitionStorePlugin.class)
				.addPlugin(MemoryProcessSchedulerPlugin.class)
				.addPlugin(MemoryProcessExecutorPlugin.class,
						Param.create("workersCount", String.valueOf(workersCount)));

		return this;
	}

	/** {@inheritDoc} */
	@Override
	protected void buildFeatures() {
		getModuleConfigBuilder()
				.addComponent(NodeManager.class, NodeManagerImpl.class)
				.addComponent(ProcessDefinitionManager.class, ProcessDefinitionManagerImpl.class)
				.addComponent(ProcessManager.class, ProcessManagerImpl.class)
				//----Definitions
				.addDefinitionResource("classes", DtDefinitions.class.getName());

	}
}
