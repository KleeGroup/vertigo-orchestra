package io.vertigo.orchestra.impl.execution;

import java.util.Optional;

import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.lang.Plugin;

public interface LogProviderPlugin extends Plugin {

	Optional<VFile> getLogFileForProcess(final Long processExecutionId);

	Optional<VFile> getLogFileForActivity(final Long actityExecutionId);

	Optional<VFile> getTechnicalLogFileForActivity(final Long actityExecutionId);

}
