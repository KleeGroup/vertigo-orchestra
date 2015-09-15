package io.vertigo.orchestra;

import io.vertigo.core.App;
import io.vertigo.core.Home;
import io.vertigo.core.component.di.injector.Injector;

import org.junit.After;
import org.junit.Before;

/**
 * Test Junit de Vertigo Orchestra.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class AbstractOrchestraTestCaseJU4 {
	private App app;

	@Before
	public void setUp() throws Exception {
		app = new App(MyAppConfig.config());
		Injector.injectMembers(this, Home.getComponentSpace());
		doSetUp();
	}

	@After
	public void tearDown() throws Exception {
		doTearDown();
		if (app != null) {
			app.close();
		}
	}

	protected void doSetUp() throws Exception {
		//
	}

	protected void doTearDown() throws Exception {
		//
	}
}
