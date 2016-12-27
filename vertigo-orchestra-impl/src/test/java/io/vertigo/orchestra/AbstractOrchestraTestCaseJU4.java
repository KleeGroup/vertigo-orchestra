package io.vertigo.orchestra;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

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
	private static AutoCloseableApp app;

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

	@Before
	public final void setUpInjection() throws Exception {
		if (app != null) {
			Injector.injectMembers(this, Home.getApp().getComponentSpace());
		}
	}

}
