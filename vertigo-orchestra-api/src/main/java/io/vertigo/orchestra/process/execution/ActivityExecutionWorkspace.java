package io.vertigo.orchestra.process.execution;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import io.vertigo.lang.Assertion;

/**
 * ActivityExecutionWorkspace.
 * This is limited to a key(string) value(string) workspace.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ActivityExecutionWorkspace {

	public static final String STATUS_KEY = "status";
	public static final String ACE_ID_KEY = "activityExecutionId";
	public static final String PRE_ID_KEY = "processExecutionId";
	public static final String PROCESS_NAME_KEY = "processName";
	public static final String TOKEN_KEY = "token";
	public static final String LOG_FILE_KEY = "logFile";
	public static final String PARSING_ERROR_KEY = "parsingError";

	private final JsonObject jsonValue;

	/**
	 * Construction d'un workspace.
	 * @param stringStoredValue un workspace sous forme de string
	 */
	public ActivityExecutionWorkspace(final String stringStoredValue) {
		jsonValue = parseStringValue(stringStoredValue);
	}

	/**
	 * Construction d'un workspace.
	 * @param mapParams un workspace sous forme de Map
	 */
	public ActivityExecutionWorkspace(final Map<String, String> mapParams) {
		this(new GsonBuilder().create().toJson(mapParams));
	}

	/**
	 * Retourne la valeur stockée dans le workspace correspondant à une clé.
	 * @param key la clé
	 * @return la valeur
	 */
	public String getValue(final String key) {
		if (containsKey(key)) {
			return jsonValue.get(key).getAsString();
		}
		return null;
	}

	/**
	 * Permet de savoir si une clé est déjà définie dans le workspace.
	 * @param key la clé à tester
	 * @return true si la clé existe
	 */
	public boolean containsKey(final String key) {
		return jsonValue.get(key) != null;
	}

	/**
	 * Affecte la valeur stockée dans le workspace correspondant à une clé.
	 * @param key la clé
	 * @param value la valeur
	 */
	public void setValue(final String key, final String value) {
		Assertion.checkState(!STATUS_KEY.equals(key), "Status cannot be set directly");
		// ---
		jsonValue.addProperty(key, value);
	}

	/**
	 * Retire une propriété du workspace.
	 * @param key la clé à retirer
	 */
	public void removeKey(final String key) {
		jsonValue.remove(key);
	}

	/**
	 * Ajoute de paramètres externe au workspace.
	 * @param jsonParams des paramètres suppplémentaire au format JSON
	 */
	public void addExternalParams(final String jsonParams) {
		final JsonObject jsonObject = parseStringValue(jsonParams);
		for (final Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			// we support only string params
			if (entry.getValue() != null) {
				setValue(entry.getKey(), entry.getValue().getAsString());
			}
		}
	}

	/**
	 * Transforme le workspace en string.
	 * @return le workspace sous forme de string
	 */
	public String getStringForStorage() {
		return jsonValue.toString();
	}

	// --- Always here

	/**
	 * Affecte l'id de l'execution en cours.
	 * @param aceId l'id de l'activité
	 */
	public void setActivityExecutionId(final Long aceId) {
		jsonValue.addProperty(ACE_ID_KEY, aceId.toString());
	}

	/**
	 * Retourne l'id de l'execution en cours.
	 * @return l'id de l'exécution
	 */
	public Long getActivityExecutionId() {
		return Long.valueOf(getValue(ACE_ID_KEY));
	}

	/**
	 * Affecte l'id de l'execution en cours.
	 * @param preId l'id du processus
	 */
	public void setProcessExecutionId(final Long preId) {
		jsonValue.addProperty(PRE_ID_KEY, preId.toString());
	}

	/**
	 * Retourne l'id de l'execution en cours.
	 * @return l'id de l'exécution
	 */
	public Long getProcessExecutionId() {
		return Long.valueOf(getValue(PRE_ID_KEY));
	}

	/**
	 * Affecte le nom du processus en cours de traitement.
	 * @param processName le nom du processus en cours
	 */
	public void setProcessName(final String processName) {
		jsonValue.addProperty(PROCESS_NAME_KEY, processName);
	}

	/**
	 * Retourne le nom du processus en cours de traitement.
	 * @return le nom du processus
	 */
	public String getProcessName() {
		return getValue(PROCESS_NAME_KEY);
	}

	/**
	 * Assigne le token.
	 * @param token le token
	 */
	public void setToken(final String token) {
		jsonValue.addProperty(TOKEN_KEY, token);
	}

	/**
	 * Retourne le token de sécurité de l'activité.
	 * @return le token
	 */
	public String getToken() {
		return getValue(TOKEN_KEY);
	}

	/**
	 * Assigne le chemin relatif du fichier de log (par rapport au root orchestra).
	 */
	public void setLogFile(final String logFile) {
		jsonValue.addProperty(LOG_FILE_KEY, logFile);
	}

	/**
	 * Retourne le chemin relatif du fichier de log.
	 */
	public String getLogFile() {
		return getValue(LOG_FILE_KEY);
	}

	/**
	 * Remet à zéro la variable spécifiant le fichier de log.
	 */
	public void resetLogFile() {
		jsonValue.remove(LOG_FILE_KEY);
	}

	// --- Execution State in workspace

	/**
	 * Passe l'état à succès.
	 */
	public void setSuccess() {
		jsonValue.addProperty(STATUS_KEY, "ok");
	}

	/**
	 * Passe l'état à KO.
	 */
	public void setFailure() {
		jsonValue.addProperty(STATUS_KEY, "ko");
	}

	/**
	 * Passe l'état à en attente.
	 */
	public void setPending() {
		jsonValue.addProperty(STATUS_KEY, "pending");
	}

	/**
	 * Passe l'état à fini.
	 */
	public void setFinished() {
		jsonValue.addProperty(STATUS_KEY, "finished");
	}

	/**
	 * Le status de l'activité est-il succès.
	 * @return true si le statut est succès
	 */
	public boolean isSuccess() {
		return "ok".equals(getValue(STATUS_KEY));
	}

	/**
	 * Le status de l'activité est-il KO.
	 * @return true si le statut est KO
	 */
	public boolean isFailure() {
		return "ko".equals(getValue(STATUS_KEY));
	}

	/**
	 * Le status de l'activité est-il en attente.
	 * @return true si le statut est en attente
	 */
	public boolean isPending() {
		return "pending".equals(getValue(STATUS_KEY));
	}

	/**
	 * Le status de l'activité est-il fini.
	 * @return true si le statut est fini
	 */
	public boolean isFinished() {
		return "finished".equals(getValue(STATUS_KEY));
	}

	/**
	 * Reset le status du workspace
	 */
	public void resetStatus() {
		jsonValue.remove(STATUS_KEY);
	}

	public Map<String, String> getAsMap() {
		final Map<String, String> result = new HashMap<>();
		jsonValue.entrySet().forEach(entry -> result.put(entry.getKey(), entry.getValue().getAsString()));
		return result;
	}

	private static JsonObject parseStringValue(final String stringStoredValue) {
		JsonObject tempJsonValue = new JsonObject();
		if (stringStoredValue != null) {
			try {
				tempJsonValue = new JsonParser().parse(stringStoredValue).getAsJsonObject();
			} catch (final JsonParseException e) {
				tempJsonValue.addProperty(PARSING_ERROR_KEY, e.getMessage());
			}
		}
		return tempJsonValue;
	}

}
