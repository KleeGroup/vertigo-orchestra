package io.vertigo.orchestra;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import io.vertigo.orchestra.execution.AllTestExecution;
import io.vertigo.orchestra.planfication.AllTestPlanification;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
@RunWith(Suite.class)
@SuiteClasses(value = { AllTestPlanification.class, AllTestExecution.class })
public class AllTestOrchestra {
	// Les annotations sont suffisantes.
}
