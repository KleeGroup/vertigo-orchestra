package io.vertigo.orchestra.definition;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.execution.ActivityEngine;

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
	private final Class<? extends ActivityEngine> engineClass;

	/**
	 * Constructor only used by its builder.
	 */
	ActivityDefinition(final String name, final String label, final Class<? extends ActivityEngine> engineClass) {
		Assertion.checkArgNotEmpty(name);
		Assertion.checkArgNotEmpty(label);
		Assertion.checkNotNull(engineClass);
		//-----
		this.name = name;
		this.label = label;
		this.engineClass = engineClass;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public Class<? extends ActivityEngine> getEngineClass() {
		return engineClass;
	}

}
