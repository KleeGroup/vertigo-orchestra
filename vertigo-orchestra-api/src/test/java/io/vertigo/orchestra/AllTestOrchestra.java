package io.vertigo.orchestra;

import io.vertigo.orchestra.definition.AllTestDefinition;
import io.vertigo.orchestra.execution.AllTestExecution;
import io.vertigo.orchestra.planfication.AllTestPlanification;

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
@SuiteClasses(value = { AllTestDefinition.class, AllTestPlanification.class, AllTestExecution.class })
public class AllTestOrchestra {
	// Les annotations sont suffisantes.
}
