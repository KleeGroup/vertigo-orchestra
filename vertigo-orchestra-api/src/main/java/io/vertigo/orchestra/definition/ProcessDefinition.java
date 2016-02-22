package io.vertigo.orchestra.definition;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;

import java.util.List;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessDefinition {

	private final OProcess process;
	private final List<OTask> tasks;

	/**
	 * Constructor only used by its builder.
	 * @param process
	 * @param tasks
	 */
	ProcessDefinition(final OProcess process, final List<OTask> tasks) {
		Assertion.checkNotNull(process);
		Assertion.checkNotNull(tasks);
		//-----
		this.process = process;
		this.tasks = tasks;
	}

	/**
	 * Récupère la valeur de <code>process</code>.
	 * @return valeur de <code>process</code>.
	 */
	public OProcess getProcess() {
		return process;
	}

	/**
	 * Récupère la valeur de <code>tasks</code>.
	 * @return valeur de <code>tasks</code>.
	 */
	public List<OTask> getTasks() {
		return tasks;
	}

}
