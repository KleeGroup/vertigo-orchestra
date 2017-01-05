package io.vertigo.orchestra.definition;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.process.execution.ActivityEngine;

/**
 * Définition d'une activité d'un processus Orchestra.
 * Une activity est caractérisé par son nom et son "engine" qui represente son execution.
 * @author mlaroche
 * @version $Id$
 */
public final class ActivityDefinition {
	private final String name;
	private final String label;
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
