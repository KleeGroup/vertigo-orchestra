package io.vertigo.orchestra;

import java.util.List;

import javax.inject.Inject;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.Home;
import io.vertigo.core.component.di.injector.Injector;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.metamodel.TaskDefinitionBuilder;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.dynamox.task.TaskEngineProc;
import io.vertigo.util.ListBuilder;

/**
 * Test Junit de Vertigo Orchestra.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class AbstractOrchestraTestCaseJU4 {
	private static AutoCloseableApp app;

	@Inject
	private VTransactionManager transactionManager;
	@Inject
	private TaskManager taskManager;

	@BeforeClass
	public static final void setUp() throws Exception {
		app = new AutoCloseableApp(MyAppConfig.config());
	}

	@AfterClass
	public static final void tearDown() throws Exception {
		if (app != null) {
			app.close();
		}
	}

	public final void setUpInjection() throws Exception {
		if (app != null) {
			Injector.injectMembers(this, Home.getApp().getComponentSpace());
		}
	}

	@Before
	public void doSetUp() throws Exception {
		setUpInjection();
		//A chaque test on supprime tout
		try (VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			final List<String> requests = new ListBuilder<String>()
					.add(" delete from o_activity_log;")
					.add(" delete from o_activity_workspace;")
					.add(" delete from o_process_planification;")
					.add(" delete from o_activity_execution;")
					.add(" delete from o_process_execution;")
					.add(" delete from o_activity;")
					.add(" delete from o_process;")
					.build();

			for (final String request : requests) {
				final TaskDefinition taskDefinition = new TaskDefinitionBuilder("TK_CLEAN")
						.withDataSpace("orchestra")
						.withEngine(TaskEngineProc.class)
						.withRequest(request)
						.build();
				final Task task = new TaskBuilder(taskDefinition).build();
				taskManager.execute(task);
			}
			transaction.commit();
		}

	}

}
