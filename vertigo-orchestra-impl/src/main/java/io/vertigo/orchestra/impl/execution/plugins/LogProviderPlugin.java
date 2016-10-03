package io.vertigo.orchestra.impl.execution.plugins;

import java.util.Optional;

import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.lang.Plugin;

/**
 * Interface de plugin de récuperation de log.
 * @author mlaroche
 *
 */
public interface LogProviderPlugin extends Plugin {

	/**
	 * @see io.vertigo.orchestra.execution.ProcessExecutionManager#getLogFileForProcess(Long)
	 */
	Optional<VFile> getLogFileForProcess(final Long processExecutionId);

	/**
	 * @see io.vertigo.orchestra.execution.ProcessExecutionManager#getLogFileForActivity(Long)
	 */
	Optional<VFile> getLogFileForActivity(final Long actityExecutionId);

	/**
	 * @see io.vertigo.orchestra.execution.ProcessExecutionManager#getTechnicalLogFileForActivity(Long)
	 */
	Optional<VFile> getTechnicalLogFileForActivity(final Long actityExecutionId);

}
