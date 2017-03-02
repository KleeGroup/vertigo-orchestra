package io.vertigo.orchestra.definitions;

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
		MemoryDefinitionsTest.class,
		DbDefinitionsTest.class
})
public class AllTestDefinition {
	// Les annotations sont suffisantes.
}
