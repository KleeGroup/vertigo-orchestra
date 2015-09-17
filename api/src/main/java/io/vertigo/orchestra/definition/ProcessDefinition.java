package io.vertigo.orchestra.definition;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ProcessDefinition {

	private final OProcess process;
	private final DtList<OTask> tasks;

	/**
	 * Constructeur.
	 * @param process
	 * @param tasks
	 */
	public ProcessDefinition(final OProcess process, final DtList<OTask> tasks) {
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
	public DtList<OTask> getTasks() {
		return tasks;
	}

}
