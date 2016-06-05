package io.vertigo.orchestra.definition;

import io.vertigo.lang.Assertion;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ActivityDefinition {
	private final String name;
	private final String label;
	//TODO : mettre ActivityEngine
	private final String engine;

	/**
	 * Constructor only used by its builder.
	 * @param name
	 * @param engine
	 */
	ActivityDefinition(final String name, final String label, final String engine) {
		Assertion.checkArgNotEmpty(name);
		Assertion.checkArgNotEmpty(label);
		Assertion.checkArgNotEmpty(engine);
		//-----
		this.name = name;
		this.label = label;
		this.engine = engine;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public String getEngine() {
		return engine;
	}

}
