package io.vertigo.orchestra.services.execution;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
@RunWith(Suite.class)
@SuiteClasses(value = {
		ExecutionTest.class,
		LocalExecutionTest.class
})
public class AllTestExecution {
	// Les annotations sont suffisantes.
}
