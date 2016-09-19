package io.vertigo.orchestra.execution;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import io.vertigo.lang.Assertion;

/**
 * Activity Logger.
 *
 * @author mlaroche.
 */
public final class ActivityLogger {

	private static final String LOG_FILE_EXTENSION = ".log";
	private final StringBuilder log = new StringBuilder();

	private final Logger ACTIVITY_LOGGER;
	private final Logger LOGGER = Logger.getLogger(ProcessExecutionManager.class);

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

	/**
	 * Constructeur package protected.
	 */
	public ActivityLogger(final String engineName) {
		Assertion.checkArgNotEmpty(engineName);
		// ---
		// Creates or get the logger
		if (LogManager.exists(engineName) != null) {
			// If it exist we keep it
			ACTIVITY_LOGGER = Logger.getLogger(engineName);
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
				ACTIVITY_LOGGER = tempLogger;
			}

		}
	}

	public void info(final String message) {

		// We log in Orchestra
		log.append(dateFormat.format(new Date())).append(" [Info] ").append(message).append("\n");
		// We log in Log4j
		ACTIVITY_LOGGER.info(message);
	}

	public void warn(final String message) {
		// We log in Orchestra
		log.append(dateFormat.format(new Date())).append(" [Warn] ").append(message).append("\n");
		// We log in Log4j
		ACTIVITY_LOGGER.warn(message);
	}

	public void error(final String message) {
		// We log in Orchestra
		log.append(dateFormat.format(new Date())).append(" [Error] ").append(message).append("\n");
		// We log in Log4j
		ACTIVITY_LOGGER.error(message);
	}

	public String getLogAsString() {
		return log.toString();
	}

}
