package io.vertigo.orchestra.execution;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * ActivityExecutionWorkspace.
 * This is limited to a key(string) value(string) workspace.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ActivityExecutionWorkspace {

	public static final String STATUS_KEY = "status";
	public static final String PARSING_ERROR_KEY = "parsingError";

	private final JsonObject jsonValue;

	public ActivityExecutionWorkspace(final String stringStoredValue) {
		JsonObject tempJsonValue = new JsonObject();
		if (stringStoredValue != null) {
			try {
				tempJsonValue = new JsonParser().parse(stringStoredValue).getAsJsonObject();
			} catch (final JsonParseException e) {
				tempJsonValue.addProperty(PARSING_ERROR_KEY, e.getMessage());
			} finally {
				jsonValue = tempJsonValue;
			}
		} else {
			jsonValue = tempJsonValue;
		}
	}

	public String getValue(final String key) {
		if (containsKey(key)) {
			return jsonValue.get(key).getAsString();
		}
		return null;
	}

	public boolean containsKey(final String key) {
		return jsonValue.get(key) != null;
	}

	public void setValue(final String key, final String value) {
		jsonValue.addProperty(key, value);
	}

	public void removeKey(final String key) {
		jsonValue.remove(key);
	}

	public String getStringForStorage() {
		return jsonValue.toString();
	}

	// --- Execution State in workspace

	public void setSucess() {
		jsonValue.addProperty(STATUS_KEY, "ok");
	}

	public void setFailure() {
		jsonValue.addProperty(STATUS_KEY, "ko");
	}

	public boolean isSuccess() {
		return "ok".equals(getValue(STATUS_KEY));
	}

	public boolean isFailure() {
		return "ko".equals(getValue(STATUS_KEY));
	}

	public void resetStatus() {
		jsonValue.remove(STATUS_KEY);
	}

}
