package io.vertigo.orchestra;

import org.junit.After;
import org.junit.Before;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.Home;
import io.vertigo.core.component.di.injector.Injector;

/**
 * Test Junit de Vertigo Orchestra.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class AbstractOrchestraTestCaseJU4 {
	private AutoCloseableApp app;

	@Before
	public final void setUp() throws Exception {
		app = new AutoCloseableApp(MyAppConfig.config());
		Injector.injectMembers(this, Home.getApp().getComponentSpace());
		doSetUp();
	}

	@After
	public final void tearDown() throws Exception {
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
