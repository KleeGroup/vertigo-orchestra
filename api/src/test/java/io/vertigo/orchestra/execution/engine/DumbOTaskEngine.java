package io.vertigo.orchestra.execution.engine;

import io.vertigo.orchestra.execution.OTaskEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class DumbOTaskEngine implements OTaskEngine {

	/** {@inheritDoc} */
	@Override
	public Map<String, String> execute(final Map<String, String> params) {
		final Map<String, String> result = new HashMap<>(params);
		System.out.println("Je suis l'execution du dumb engine");
		result.put("status", "ok");
		try {
			Thread.sleep(1000 * 10);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

}
