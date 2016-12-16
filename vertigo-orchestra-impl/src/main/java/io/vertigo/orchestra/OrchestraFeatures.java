package io.vertigo.orchestra;

import io.vertigo.app.config.Features;
import io.vertigo.app.config.Param;
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
import io.vertigo.orchestra.impl.definition.plugins.db.DbProcessDefinitionStorePlugin;
import io.vertigo.orchestra.impl.definition.plugins.memory.MemoryProcessDefinitionStorePlugin;
import io.vertigo.orchestra.impl.execution.NodeManagerImpl;
import io.vertigo.orchestra.impl.execution.ProcessExecutionManagerImpl;
import io.vertigo.orchestra.impl.execution.plugins.db.DbSequentialExecutorPlugin;
import io.vertigo.orchestra.impl.execution.plugins.simple.SimpleExecutorPlugin;
import io.vertigo.orchestra.impl.scheduler.ProcessSchedulerManagerImpl;
import io.vertigo.orchestra.impl.scheduler.plugins.db.DbProcessSchedulerPlugin;
import io.vertigo.orchestra.impl.scheduler.plugins.simple.SimpleSchedulerPlugin;
import io.vertigo.orchestra.scheduler.ProcessSchedulerManager;

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
				.addPlugin(DbSequentialExecutorPlugin.class,
						Param.create("nodeName", nodeName),
						Param.create("workersCount", String.valueOf(workersCount)),
						Param.create("executionPeriodSeconds", String.valueOf(daemonPeriodSeconds)))
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
				.addPlugin(SimpleSchedulerPlugin.class)
				.addPlugin(SimpleExecutorPlugin.class,
						Param.create("workersCount", String.valueOf(workersCount)));

		return this;
	}

	/** {@inheritDoc} */
	@Override
	protected void buildFeatures() {
		getModuleConfigBuilder()
				.addComponent(NodeManager.class, NodeManagerImpl.class)
				.addComponent(ProcessDefinitionManager.class, ProcessDefinitionManagerImpl.class)
				.addComponent(ProcessSchedulerManager.class, ProcessSchedulerManagerImpl.class)
				.addComponent(ProcessExecutionManager.class, ProcessExecutionManagerImpl.class)
				.addComponent(OrchestraManager.class, OrchestraManagerImpl.class)
				//----Definitions
				.addDefinitionResource("classes", DtDefinitions.class.getName());

	}
}
