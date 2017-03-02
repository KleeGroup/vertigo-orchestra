package io.vertigo.orchestra.plugins.services;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import io.vertigo.commons.codec.Codec;

/**
 * Encodes a map to a simple json (as a string).
 * Decodes a json (as a string) into a simple map.
 * @author pchretien
 */
public final class MapCodec implements Codec<Map<String, String>, String> {
	private static final Gson GSON = new GsonBuilder().create();

	@Override
	public Map<String, String> decode(final String toEncode) {
		if (toEncode == null) {
			return Collections.emptyMap();
		}
		return new JsonParser()
				.parse(toEncode)
				.getAsJsonObject()
				.entrySet()
				.stream()
				.collect(Collectors.toMap(
						entry -> entry.getKey(),
						entry -> entry.getValue().getAsString()));
	}

	@Override
	public String encode(final Map<String, String> encoded) {
		return GSON.toJson(encoded);
	}
}
