package io.vertigo.orchestra;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import io.vertigo.orchestra.definitions.AllTestDefinition;
import io.vertigo.orchestra.services.execution.AllTestExecution;
import io.vertigo.orchestra.services.schedule.AllTestPlanification;
import io.vertigo.orchestra.webservices.OrchestraWsTest;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
@RunWith(Suite.class)
@SuiteClasses(value = {
		AllTestDefinition.class,
		AllTestPlanification.class,
		AllTestExecution.class,
		OrchestraWsTest.class
})
public class OrchestraTestSuite {
	// Les annotations sont suffisantes.
}
