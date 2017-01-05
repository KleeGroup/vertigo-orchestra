package io.vertigo.orchestra.impl.process.execution;

import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.definition.ProcessType;
import io.vertigo.orchestra.process.execution.ProcessExecutor;

/**
 * Plugin d'execution des processus orchestra.
 * @author mlaroche
 *
 */
public interface ProcessExecutorPlugin extends ProcessExecutor, Plugin {
	/**
	 * Retourne le type de processus géré par le plugin
	 * @return le type de processus géré
	 */
	ProcessType getHandledProcessType();

}
