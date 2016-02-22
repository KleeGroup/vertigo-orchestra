package io.vertigo.orchestra.impl.execution;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.execution.ProcessExecutionManager;

import java.io.IOException;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Task Logger.
 *
 * @author mlaroche.
 */
public class TaskLogger {

	private static final String LOG_FILE_EXTENSION = ".log";
	private final StringBuilder log = new StringBuilder();

	private final Logger TASK_LOGGER;
	private final Logger LOGGER = Logger.getLogger(ProcessExecutionManager.class);

	/**
	 * Constructeur package protected.
	 */
	TaskLogger(final String engineName) {
		Assertion.checkArgNotEmpty(engineName);
		// ---
		// Creates or get the logger
		if (LogManager.exists(engineName) != null) {
			// If it exist we keep it
			TASK_LOGGER = Logger.getLogger(engineName);
		} else {
			Logger tempLogger = null;
			try {
				// If it doesn't exist we create it with the right appender
				tempLogger = Logger.getLogger(engineName);
				// Create the layout
				final PatternLayout layout = new PatternLayout();
				layout.setConversionPattern("%d{yyyyMMdd} %d{HH:mm:ss} [%t] %-5p %-11c{1} - %m %x %n");
				// Create an appender
				final Appender appender = new FileAppender(layout, engineName + LOG_FILE_EXTENSION);
				tempLogger.addAppender(appender);
			} catch (final IOException e) {
				LOGGER.warn("");
				tempLogger = LOGGER;
			} finally {
				TASK_LOGGER = tempLogger;
			}

		}
	}

	public void info(final String message) {
		// We log in Orchestra
		log.append("[Info] ").append(message).append("\n");
		// We log in Log4j
		TASK_LOGGER.info(message);
	}

	public void warn(final String message) {
		// We log in Orchestra
		log.append("[Warn] ").append(message).append("\n");
		// We log in Log4j
		TASK_LOGGER.warn(message);
	}

	public void error(final String message) {
		// We log in Orchestra
		log.append("[Error] ").append(message).append("\n");
		// We log in Log4j
		TASK_LOGGER.error(message);
	}

	public String getLogAsString() {
		return log.toString();
	}

}
