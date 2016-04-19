package io.vertigo.orchestra.execution;

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
		Assertion.checkState(!STATUS_KEY.equals(key), "Status cannot be set directly");
		// ---
		jsonValue.addProperty(key, value);
	}

	public void removeKey(final String key) {
		jsonValue.remove(key);
	}

	public String getStringForStorage() {
		return jsonValue.toString();
	}

	// --- Always here

	public void setActivityExecutionId(final Long aceId) {
		jsonValue.addProperty(ACE_ID_KEY, aceId.toString());
	}

	public Long getActivityExecutionId() {
		return Long.valueOf(getValue(ACE_ID_KEY));
	}

	public void setProcessExecutionId(final Long preId) {
		jsonValue.addProperty(PRE_ID_KEY, preId.toString());
	}

	public Long getProcessExecutionId() {
		return Long.valueOf(getValue(PRE_ID_KEY));
	}

	public void setProcessName(final String processName) {
		jsonValue.addProperty(PROCESS_NAME_KEY, processName);
	}

	public String getProcessName() {
		return getValue(PROCESS_NAME_KEY);
	}

	public void setToken(final String token) {
		jsonValue.addProperty(TOKEN_KEY, token);
	}

	public String getToken() {
		return getValue(TOKEN_KEY);
	}

	// --- Execution State in workspace

	public void setSuccess() {
		jsonValue.addProperty(STATUS_KEY, "ok");
	}

	public void setFailure() {
		jsonValue.addProperty(STATUS_KEY, "ko");
	}

	public void setPending() {
		jsonValue.addProperty(STATUS_KEY, "pending");
	}

	public boolean isSuccess() {
		return "ok".equals(getValue(STATUS_KEY));
	}

	public boolean isFailure() {
		return "ko".equals(getValue(STATUS_KEY));
	}

	public boolean isPending() {
		return "pending".equals(getValue(STATUS_KEY));
	}

	public void resetStatus() {
		jsonValue.remove(STATUS_KEY);
	}

}
