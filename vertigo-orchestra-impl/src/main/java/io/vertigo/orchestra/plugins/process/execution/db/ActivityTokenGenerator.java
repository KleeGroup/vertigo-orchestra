package io.vertigo.orchestra.plugins.process.execution.db;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Token generator for activity execution
 *
 * @author mlaroche.
 * @version $Id$
 */
final class ActivityTokenGenerator {

	private static final Integer NUM_BITS = 130;
	private static final Integer RADIX = 32;

	/**
	 * Constructeur.
	 */
	private ActivityTokenGenerator() {
		super();
		// SINGLETON
	}

	static String getToken() {
		return new BigInteger(NUM_BITS, new SecureRandom()).toString(RADIX);
	}
}
