package io.vertigo.orchestra.impl.definition;

import java.util.List;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessDefinitionImpl implements ProcessDefinition {

	private final OProcess process;
	//	private final List<OTask> tasks;

	/**
	 * Constructor only used by its builder.
	 * @param process
	 * @param tasks
	 */
	ProcessDefinitionImpl(final OProcess process, final List<OTask> tasks) {
		Assertion.checkNotNull(process);
		Assertion.checkNotNull(tasks);
		//-----
		this.process = process;
		//		this.tasks = tasks;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return process.getName();
	}

}
