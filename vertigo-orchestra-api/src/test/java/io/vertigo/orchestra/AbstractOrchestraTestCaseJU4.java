package io.vertigo.orchestra;

import org.junit.After;
import org.junit.Before;

import io.vertigo.app.App;
import io.vertigo.app.Home;
import io.vertigo.core.component.di.injector.Injector;

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
		Injector.injectMembers(this, Home.getApp().getComponentSpace());
		doSetUp();
	}

	@After
	public void tearDown() throws Exception {
		if (app != null) {
			doTearDown();
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
