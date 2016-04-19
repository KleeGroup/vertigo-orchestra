package io.vertigo.orchestra.impl.execution;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Token generator for activity execution
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ActivityTokenGenerator {

	/**
	 * Constructeur.
	 */
	private ActivityTokenGenerator() {
		super();
		// SINGLETON
	}

	public static String getToken() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}
}
