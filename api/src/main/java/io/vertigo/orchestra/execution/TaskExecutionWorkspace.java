package io.vertigo.orchestra.execution;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * TaskExecutionWorkspace.
 * This is limited to a key(string) value(string) workspace.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class TaskExecutionWorkspace {

	private static final String STATUS_KEY = "status";

	private final JsonObject jsonValue;

	public TaskExecutionWorkspace(final String stringStoredValue) {
		if (stringStoredValue != null) {
			jsonValue = new JsonParser().parse(stringStoredValue).getAsJsonObject();
		} else {
			jsonValue = new JsonObject();
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
