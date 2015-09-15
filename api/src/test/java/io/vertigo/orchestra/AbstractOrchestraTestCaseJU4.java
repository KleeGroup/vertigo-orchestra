package io.vertigo.orchestra;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.junit.Assert;

import io.vertigo.AbstractTestCaseJU4;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.security.OrchestraUserSession;
import io.vertigo.persona.security.VSecurityManager;
import io.vertigo.util.StringUtil;

/**
 * Test Junit de Vertigo Orchestra.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class AbstractOrchestraTestCaseJU4 extends AbstractTestCaseJU4 {

	@Inject
	private VTransactionManager transactionManager;
	@Inject
	private VSecurityManager securityManager;
	// current transaction
	private VTransactionWritable transaction;
	private boolean memDataStarted;
	// Session courante pour éviter de la perdre dans des WeakRef
	private OrchestraUserSession session;

	/**
	 * return Logger de la classe de test.
	 *
	 * @return Logger de la classe de test
	 */
	protected final Logger getLogger() {
		return Logger.getLogger(getClass());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Option<String> getPropertiesFileName() {
		final Option<String> ret;
		final String prop = System.getProperty("propertiesFile");
		if (StringUtil.isEmpty(prop)) {
			getLogger().info("Using default properties file");
			ret = Option.<String> some("/test.properties");
		} else {
			getLogger().info("Using properties file :  " + prop);
			ret = Option.<String> some("/" + prop);
		}
		return ret;
	}

	/** {@inheritDoc} */
	@Override
	protected boolean cleanHomeForTest() {
		// Avoid to reload every time
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doSetUp() throws Exception {
		Assert.assertFalse("the previous test hasn't correctly close its transaction.",
				transactionManager.hasCurrentTransaction());
		// manage transactions
		transaction = transactionManager.createCurrentTransaction();

		// If there is a current session, it must be stopped, in order to create a new one.
		if (securityManager.getCurrentUserSession().isDefined()) {
			securityManager.stopCurrentUserSession();
			session = (OrchestraUserSession) securityManager.createUserSession();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doTearDown() throws Exception {
		Assert.assertTrue("All tests must rollback a transaction.", transactionManager.hasCurrentTransaction());
		// close transaction
		transaction.rollback();
		// Stop user session
		if (securityManager.getCurrentUserSession().isDefined()) {
			securityManager.stopCurrentUserSession();
			if (session != null) {
				session = null;
			}
		}
	}

}
