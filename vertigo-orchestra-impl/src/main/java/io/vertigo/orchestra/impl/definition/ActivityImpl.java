package io.vertigo.orchestra.impl.definition;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.definition.ActivityDefinition;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ActivityImpl implements ActivityDefinition {

	private final String name;
	private final String label;
	private final String engine;

	/**
	 * Constructor only used by its builder.
	 * @param name
	 * @param engine
	 */
	ActivityImpl(final String name, final String label, final String engine) {
		Assertion.checkNotNull(name);
		Assertion.checkNotNull(engine);
		//-----
		this.name = name;
		this.label = label;
		this.engine = engine;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public String getLabel() {
		return label;
	}

	/** {@inheritDoc} */
	@Override
	public String getEngine() {
		return engine;
	}

}
