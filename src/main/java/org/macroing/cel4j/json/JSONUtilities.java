/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
 * 
 * This file is part of org.macroing.cel4j.
 * 
 * org.macroing.cel4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.macroing.cel4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.macroing.cel4j. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.cel4j.json;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.macroing.cel4j.json.JSONObject.Property;

/**
 * A class that consists exclusively of static methods that performs various operations on {@link JSONType} and friends.
 * <p>
 * One operation that is supported is formatting a {@code JSONType}.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class JSONUtilities {
	private static final Pattern PATTERN = Pattern.compile("^\\s*\\[\\s*[0-9]+\\s*\\]\\s*|\\s*\\{\\s*\\w+\\s*\\}\\s*$");
	private static final Pattern PATTERN_JSONARRAY = Pattern.compile("^\\s*\\[\\s*([0-9]+)\\s*\\]\\s*$");
	private static final Pattern PATTERN_JSONOBJECT = Pattern.compile("^\\s*\\{\\s*(\\w+)\\s*\\}\\s*$");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private JSONUtilities() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an optional {@link JSONType} by traversing {@code jSONType} given {@code path} as its "path".
	 * <p>
	 * A path consists of "path elements". Each path element is separated by a dot (".").
	 * <p>
	 * Two types of path elements exists. One is for JSON arrays and the other is for JSON objects.
	 * <p>
	 * A JSON array path element looks like {@code [0]}, where the zero represents an arbitrary non-negative index of a JSON array.
	 * <p>
	 * A JSON object path element looks like <code>{property_name}</code>, where "property_name" represents an arbitrary property name of a JSON object.
	 * <p>
	 * Both JSON array- and JSON object path elements may have optional whitespace everywhere, except for the index of the JSON array path element and the property name of the JSON object path element.
	 * <p>
	 * If either {@code jSONType} or {@code path} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If at least one path element is invalid, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param jSONType the {@code JSONType} to start traversing from
	 * @param path the path to traverse along
	 * @return an optional {@code JSONType} by traversing {@code jSONType} given {@code path} as its "path"
	 * @throws IllegalArgumentException thrown if, and only if, at least one path element is invalid
	 * @throws NullPointerException thrown if, and only if, either {@code jSONType} or {@code path} are {@code null}
	 */
	public static Optional<JSONType> get(final JSONType jSONType, final String path) {
		Objects.requireNonNull(jSONType, "jSONType == null");
		Objects.requireNonNull(path, "path == null");
		
		if(path.isEmpty()) {
			return Optional.of(jSONType);
		}
		
		final String[] pathElements = path.split("\\.");
		
		JSONType currentJSONType = jSONType;
		
		for(final String pathElement : pathElements) {
			if(!PATTERN.matcher(pathElement).matches()) {
				throw new IllegalArgumentException("Illegal path-element: " + pathElement);
			}
		}
		
		for(final String pathElement : pathElements) {
			if(currentJSONType instanceof JSONArray) {
				final Matcher matcher = PATTERN_JSONARRAY.matcher(pathElement);
				
				if(matcher.matches()) {
					final int index = Integer.parseInt(matcher.group(1));
					
					final JSONArray jSONArray = JSONArray.class.cast(currentJSONType);
					
					final List<JSONType> values = jSONArray.getValues();
					
					if(index < values.size()) {
						currentJSONType = values.get(index);
					}
				} else {
					return Optional.empty();
				}
			} else if(currentJSONType instanceof JSONObject) {
				final Matcher matcher = PATTERN_JSONOBJECT.matcher(pathElement);
				
				if(matcher.matches()) {
					final String key = matcher.group(1);
					
					final JSONObject jSONObject = JSONObject.class.cast(currentJSONType);
					
					final List<Property> properties = jSONObject.getProperties();
					
					for(final Property property : properties) {
						if(property.getKey().equals(key)) {
							currentJSONType = property.getValue();
							
							break;
						}
					}
				} else {
					return Optional.empty();
				}
			} else {
				return Optional.empty();
			}
		}
		
		return Optional.of(currentJSONType);
	}
	
	/**
	 * Performs formatting to {@code jSONType}.
	 * <p>
	 * Returns a formatted {@code String}.
	 * <p>
	 * Calling this method is equivalent to {@code JSONUtilities.format(jSONType, "  ")}.
	 * <p>
	 * If {@code jSONType} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param jSONType the {@link JSONType} to format
	 * @return a formatted {@code String}
	 * @throws NullPointerException thrown if, and only if, {@code jSONType} is {@code null}
	 */
	public static String format(final JSONType jSONType) {
		return format(jSONType, "  ");
	}
	
	/**
	 * Performs formatting to {@code jSONType}.
	 * <p>
	 * Returns a formatted {@code String}.
	 * <p>
	 * If either {@code jSONType} or {@code indentation} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param jSONType the {@link JSONType} to format
	 * @param indentation the indentation {@code String} to be used
	 * @return a formatted {@code String}
	 * @throws NullPointerException thrown if, and only if, either {@code jSONType} or {@code indentation} are {@code null}
	 */
	public static String format(final JSONType jSONType, final String indentation) {
		return doFormat(Objects.requireNonNull(jSONType, "jSONType == null"), Objects.requireNonNull(indentation, "indentation == null"), new StringBuilder(), 0).toString();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String doRepeat(final String string, final int repetition) {
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(int i = 0; i < repetition; i++) {
			stringBuilder.append(string);
		}
		
		return stringBuilder.toString();
	}
	
	private static StringBuilder doFormat(final JSONType jSONType, final String indentation, final StringBuilder stringBuilder, final int depth) {
		final String indentation0 = doRepeat(indentation, depth + 0);
		final String indentation1 = doRepeat(indentation, depth + 1);
		
		if(jSONType instanceof JSONArray) {
			stringBuilder.append("[");
			stringBuilder.append("\n");
			
			final JSONArray jSONArray = JSONArray.class.cast(jSONType);
			
			final List<JSONType> values = jSONArray.getValues();
			
			final int size = values.size();
			
			for(int i = 0; i < size; i++) {
				stringBuilder.append(indentation1);
				
				doFormat(values.get(i), indentation, stringBuilder, depth + 1);
				
				if(i + 1 < size) {
					stringBuilder.append(",");
				}
				
				stringBuilder.append("\n");
			}
			
			stringBuilder.append(indentation0);
			stringBuilder.append("]");
		} else if(jSONType instanceof JSONObject) {
			stringBuilder.append("{");
			stringBuilder.append("\n");
			
			final JSONObject jSONObject = JSONObject.class.cast(jSONType);
			
			final List<Property> properties = jSONObject.getProperties();
			
			final int size = properties.size();
			
			for(int i = 0; i < size; i++) {
				final Property property = properties.get(i);
				
				stringBuilder.append(indentation1);
				stringBuilder.append("\"");
				stringBuilder.append(property.getKey());
				stringBuilder.append("\"");
				stringBuilder.append(":");
				stringBuilder.append(" ");
				
				doFormat(property.getValue(), indentation, stringBuilder, depth + 1);
				
				if(i + 1 < size) {
					stringBuilder.append(",");
				}
				
				stringBuilder.append("\n");
			}
			
			stringBuilder.append(indentation0);
			stringBuilder.append("}");
		} else {
			stringBuilder.append(jSONType.toSourceCode());
		}
		
		return stringBuilder;
	}
}