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
public class DumbErrorOTaskEngine implements OTaskEngine {

	/** {@inheritDoc} */
	@Override
	public Map<String, String> execute(final Map<String, String> params) {
		final Map<String, String> result = new HashMap<>(params);
		result.put("status", "ko");
		try {
			Thread.sleep(1000 * 2);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

}
