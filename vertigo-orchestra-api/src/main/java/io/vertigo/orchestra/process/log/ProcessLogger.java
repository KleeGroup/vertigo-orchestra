package io.vertigo.orchestra.process.log;

import java.util.Optional;

import io.vertigo.dynamo.file.model.VFile;

public interface ProcessLogger {

	/**
	 * Récupère le fichier de log d'une execution de processus.
	 * @param processExecutionId L'id de l'execution
	 * @return le fichier de log
	 */
	Optional<VFile> getLogFileForProcess(final Long processExecutionId);

	/**
	 * Récupère le fichier de log d'une execution d'activité.
	 * @param actityExecutionId l'id de l'activité
	 * @return le fichier de log de l'activité
	 */
	Optional<VFile> getLogFileForActivity(Long actityExecutionId);

	/**
	 * Récupère sous forme de fichier le log technique d'une activité. (Si l'activité possède un log)
	 * @param actityExecutionId l'id de l'activité
	 * @return le fichier de log technique de l'activité
	 */
	Optional<VFile> getTechnicalLogFileForActivity(Long actityExecutionId);
}
