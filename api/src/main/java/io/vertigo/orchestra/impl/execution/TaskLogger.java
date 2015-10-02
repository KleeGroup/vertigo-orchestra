package io.vertigo.orchestra.impl.execution;

import org.apache.log4j.Logger;

import io.vertigo.orchestra.execution.OTaskEngine;

/**
 * Task Logger.
 *
 * @author mlaroche.
 */
public class TaskLogger {

	private final StringBuilder log = new StringBuilder();

	private final Logger LOGGER = Logger.getLogger(OTaskEngine.class);

	/**
	 * Constructeur package protected.
	 * @param log
	 */
	TaskLogger() {
		// Nothing in the constructor
	}

	public void info(final String message) {
		// We log in Orchestra
		log.append("[Info] ").append(message).append("\n");
		// We log in Log4j
		LOGGER.info(message);
	}

	public void warn(final String message) {
		// We log in Orchestra
		log.append("[Warn] ").append(message).append("\n");
		// We log in Log4j
		LOGGER.warn(message);
	}

	public void error(final String message) {
		// We log in Orchestra
		log.append("[Error] ").append(message).append("\n");
		// We log in Log4j
		LOGGER.error(message);
	}

	public String getLogAsString() {
		return log.toString();
	}

}
