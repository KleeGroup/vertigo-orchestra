package io.vertigo.orchestra.execution;

import io.vertigo.lang.Component;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.impl.execution.TaskExecutionWorkspace;

public interface OTaskManager extends Component {

	/**
	 * Réalise l'exécution d'une tache.
	 */
	public TaskExecutionWorkspace execute(final OTaskExecution taskExecution, final TaskExecutionWorkspace workspace);

}
