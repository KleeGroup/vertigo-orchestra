package io.vertigo.orchestra.impl.scheduler;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.definition.ProcessDefinition;

/**
 * Plugin de gestion de la planification.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface ProcessSchedulerPlugin extends Plugin {

	void scheduleAt(final Long proId, final Date planifiedTime, final Optional<String> initialParamsOption);

	Map<ProcessDefinition, String> getProcessToExecute();

}
