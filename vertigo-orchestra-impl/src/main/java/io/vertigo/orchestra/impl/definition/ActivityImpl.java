package io.vertigo.orchestra.impl.definition;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.definition.Activity;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ActivityImpl implements Activity {

	private final String name;
	private final String engine;

	/**
	 * Constructor only used by its builder.
	 * @param name
	 * @param engine
	 */
	ActivityImpl(final String name, final String engine) {
		Assertion.checkNotNull(name);
		Assertion.checkNotNull(engine);
		//-----
		this.name = name;
		this.engine = engine;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public String getEngine() {
		return engine;
	}

}
