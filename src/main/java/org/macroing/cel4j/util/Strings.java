/**
 * Copyright 2009 - 2020 J&#246;rgen Lundgren
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
package org.macroing.cel4j.util;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A class that consists exclusively of static methods that performs various operations on {@code String}s.
 * <p>
 * The {@code String} format methods support the following formats:
 * <ul>
 * <li>{@code "CamelCase"}</li>
 * <li>{@code "camelCaseModified"}</li>
 * <li>{@code "dash-separated-lower-case"}</li>
 * <li>{@code "DASH-SEPARATED-UPPER-CASE"}</li>
 * <li>{@code "underscore_separated_lower_case"}</li>
 * <li>{@code "UNDERSCORE_SEPARATED_UPPER_CASE"}</li>
 * <li>{@code "unseparatedlowercase"}</li>
 * <li>{@code "UNSEPARATEDUPPERCASE"}</li>
 * </ul>
 * These methods require a {@code String} parameter argument called {@code string}. It is important to know that the format of {@code string} matters. All methods do support the formats {@code "CamelCase"}, {@code "camelCaseModified"},
 * {@code "dash-separated-lower-case"} and {@code "underscore_separated_lower_case"}. However, only two of the methods, {@link #formatUnseparatedLowerCase(String)} and {@link #formatUnseparatedUpperCase(String)}, do support the formats
 * {@code "DASH-SEPARATED-UPPER-CASE"}, {@code "UNDERSCORE_SEPARATED_UPPER_CASE"}, {@code "unseparatedlowercase"} and {@code "UNSEPARATEDUPPERCASE"}.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Strings {
	private Strings() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Formats {@code string} similar to {@code "CamelCase"}.
	 * <p>
	 * Returns the formatted {@code String}.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@code formatUnderscoreSeparatedLowerCase(string)}, replaces the first letter as well as all letters preceded by an underscore with an upper case letter. Then it removes all underscores.
	 * 
	 * @param string the {@code String} to format
	 * @return the formatted {@code String}
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static String formatCamelCase(final String string) {
		final StringBuilder stringBuilder = new StringBuilder();
		
		final String stringUnderscoreSeparatedLowerCase = formatUnderscoreSeparatedLowerCase(string);
		
		for(int c = 0, p = -1; c < stringUnderscoreSeparatedLowerCase.length(); c++, p++) {
			final char characterC = stringUnderscoreSeparatedLowerCase.charAt(c);
			
			if(p >= 0) {
				final char characterP = stringUnderscoreSeparatedLowerCase.charAt(p);
				
				if(characterP == '_') {
					stringBuilder.append(Character.toUpperCase(characterC));
				} else if(characterC != '_') {
					stringBuilder.append(Character.toLowerCase(characterC));
				}
			} else {
				stringBuilder.append(Character.toUpperCase(characterC));
			}
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * Formats {@code string} similar to {@code "camelCaseModified"}.
	 * <p>
	 * Returns the formatted {@code String}.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@code formatUnderscoreSeparatedLowerCase(string)}, replaces the first letter with a lower case letter and all letters preceded by an underscore with an upper case letter. Then it removes all underscores.
	 * 
	 * @param string the {@code String} to format
	 * @return the formatted {@code String}
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static String formatCamelCaseModified(final String string) {
		final StringBuilder stringBuilder = new StringBuilder();
		
		final String stringUnderscoreSeparatedLowerCase = formatUnderscoreSeparatedLowerCase(string);
		
		for(int c = 0, p = -1; c < stringUnderscoreSeparatedLowerCase.length(); c++, p++) {
			final char characterC = stringUnderscoreSeparatedLowerCase.charAt(c);
			
			if(p >= 0) {
				final char characterP = stringUnderscoreSeparatedLowerCase.charAt(p);
				
				if(characterP == '_') {
					stringBuilder.append(Character.toUpperCase(characterC));
				} else if(characterC != '_') {
					stringBuilder.append(Character.toLowerCase(characterC));
				}
			} else {
				stringBuilder.append(Character.toLowerCase(characterC));
			}
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * Formats {@code string} similar to {@code "dash-separated-lower-case"}.
	 * <p>
	 * Returns the formatted {@code String}.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@code formatUnderscoreSeparatedLowerCase(string)} and replaces all underscores with dashes.
	 * 
	 * @param string the {@code String} to format
	 * @return the formatted {@code String}
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static String formatDashSeparatedLowerCase(final String string) {
		return formatUnderscoreSeparatedLowerCase(string).replace('_', '-');
	}
	
	/**
	 * Formats {@code string} similar to {@code "DASH-SEPARATED-UPPER-CASE"}.
	 * <p>
	 * Returns the formatted {@code String}.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@code formatUnderscoreSeparatedUpperCase(string)} and replaces all underscores with dashes.
	 * 
	 * @param string the {@code String} to format
	 * @return the formatted {@code String}
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static String formatDashSeparatedUpperCase(final String string) {
		return formatUnderscoreSeparatedUpperCase(string).replace('_', '-');
	}
	
	/**
	 * Formats {@code string} similar to {@code "underscore_separated_lower_case"}.
	 * <p>
	 * Returns the formatted {@code String}.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method first replaces all dashes with underscores. Then it iterates through the characters one by one. Each time two upper case letters or one lower case letter and one upper case letter follows, an underscore is added between them. Any
	 * underscores at the beginning or end of the {@code String} will be removed.
	 * 
	 * @param string the {@code String} to format
	 * @return the formatted {@code String}
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static String formatUnderscoreSeparatedLowerCase(final String string) {
		final String string0 = string.replace('-', '_');
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(int c = 0, n = 1, p = -1; c < string0.length(); c++, n++, p++) {
			final char characterC = string0.charAt(c);
			
			if(n < string0.length() && p >= 0) {
//				This occurs at: "Hello_World"
//				                 p^n
				
				final char characterN = string0.charAt(n);
				final char characterP = string0.charAt(p);
				
				if(characterC == '_' && characterP != '_') {
					stringBuilder.append(characterC);
				} else if(Character.isLowerCase(characterC) && Character.isUpperCase(characterN)) {
					stringBuilder.append(characterC);
					stringBuilder.append('_');
				} else if(Character.isUpperCase(characterC) && Character.isUpperCase(characterN)) {
					stringBuilder.append(Character.toLowerCase(characterC));
					stringBuilder.append('_');
				} else if(characterC != '_') {
					stringBuilder.append(Character.toLowerCase(characterC));
				}
			} else if(n < string0.length()) {
//				This occurs at: "Hello_World"
//				                 ^n
				
				final char characterN = string0.charAt(n);
				
				if(characterC == '_') {
					stringBuilder.append(characterC);
				} else if(Character.isLowerCase(characterC) && Character.isUpperCase(characterN)) {
					stringBuilder.append(characterC);
					stringBuilder.append('_');
				} else if(Character.isUpperCase(characterC) && Character.isUpperCase(characterN)) {
					stringBuilder.append(Character.toLowerCase(characterC));
					stringBuilder.append('_');
				} else {
					stringBuilder.append(Character.toLowerCase(characterC));
				}
			} else if(p >= 0) {
//				This occurs at: "Hello_World"
//				                          p^
				final char characterP = string0.charAt(p);
				
				if(characterC == '_' && characterP != '_') {
					stringBuilder.append(characterC);
				} else {
					stringBuilder.append(Character.toLowerCase(characterC));
				}
			} else if(characterC != '_') {
//				This occurs at: "N"
//				                 ^
				stringBuilder.append(Character.toLowerCase(characterC));
			}
		}
		
		final String string1 = stringBuilder.toString();
		final String string2 = string1.replaceAll("^_+", "");
		final String string3 = string2.replaceAll("_+$", "");
		
		return string3;
	}
	
	/**
	 * Formats {@code string} similar to {@code "UNDERSCORE_SEPARATED_UPPER_CASE"}.
	 * <p>
	 * Returns the formatted {@code String}.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@code formatUnderscoreSeparatedLowerCase(string)} followed by {@code toUpperCase()}.
	 * 
	 * @param string the {@code String} to format
	 * @return the formatted {@code String}
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static String formatUnderscoreSeparatedUpperCase(final String string) {
		return formatUnderscoreSeparatedLowerCase(string).toUpperCase();
	}
	
	/**
	 * Formats {@code string} similar to {@code "unseparatedlowercase"}.
	 * <p>
	 * Returns the formatted {@code String}.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@code formatUnderscoreSeparatedLowerCase(string)} and removes all underscores.
	 * 
	 * @param string the {@code String} to format
	 * @return the formatted {@code String}
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static String formatUnseparatedLowerCase(final String string) {
		return formatUnderscoreSeparatedLowerCase(string).replace("_", "");
	}
	
	/**
	 * Formats {@code string} similar to {@code "UNSEPARATEDUPPERCASE"}.
	 * <p>
	 * Returns the formatted {@code String}.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@code formatUnderscoreSeparatedUpperCase(string)} and removes all underscores.
	 * 
	 * @param string the {@code String} to format
	 * @return the formatted {@code String}
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static String formatUnseparatedUpperCase(final String string) {
		return formatUnderscoreSeparatedUpperCase(string).replace("_", "");
	}
	
	/**
	 * Returns a {@code String} representation of a {@code List} of {@code Object}s.
	 * <p>
	 * If either {@code objects} or an element in {@code objects} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Utilities.optional(objects, "", "", ", ");
	 * }
	 * </pre>
	 * 
	 * @param <T> the generic type of {@code objects}
	 * @param objects a {@code List} with {@code Object}s
	 * @return a {@code String} representation of a {@code List} of {@code Object}s
	 * @throws NullPointerException thrown if, and only if, either {@code objects} or an element in {@code objects} are {@code null}
	 */
	public static <T> String optional(final List<T> objects) {
		return optional(objects, "", "", ", ");
	}
	
	/**
	 * Returns a {@code String} representation of a {@code List} of {@code Object}s.
	 * <p>
	 * If either {@code objects}, an element in {@code objects}, {@code left}, {@code right} or {@code separator} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Utilities.optional(objects, "", "", ", ", object -> object);
	 * }
	 * </pre>
	 * 
	 * @param <T> the generic type of {@code objects}
	 * @param objects a {@code List} with {@code Object}s
	 * @param left the {@code String} that will be appended first, if {@code objects} is not empty
	 * @param right the {@code String} that will be appended last, if {@code objects} is not empty
	 * @param separator the {@code String} that will be used as separator or delimiter between the {@code String} representation of each {@code Object} in {@code objects}
	 * @return a {@code String} representation of a {@code List} of {@code Object}s
	 * @throws NullPointerException thrown if, and only if, either {@code objects}, an element in {@code objects}, {@code left}, {@code right} or {@code separator} are {@code null}
	 */
	public static <T> String optional(final List<T> objects, final String left, final String right, final String separator) {
		return optional(objects, left, right, separator, object -> object);
	}
	
	/**
	 * Returns a {@code String} representation of a {@code List} of {@code Object}s.
	 * <p>
	 * If either {@code objects}, an element in {@code objects}, {@code left}, {@code right}, {@code separator} or {@code mapper} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The method is called {@code optional} because, if {@code objects} is empty, an empty {@code String} will be returned. Otherwise, {@code left} will be appended first, followed by the {@code String} representation of the mapped {@code Object} of
	 * each {@code Object} in {@code objects}. Each {@code Object} will be separated with {@code separator}. The last part to be appended is {@code right}.
	 * 
	 * @param <T> the generic type of {@code objects}
	 * @param <U> the second generic type of {@code mapper}
	 * @param objects a {@code List} with {@code Object}s
	 * @param left the {@code String} that will be appended first, if {@code objects} is not empty
	 * @param right the {@code String} that will be appended last, if {@code objects} is not empty
	 * @param separator the {@code String} that will be used as separator or delimiter between the {@code String} representation of each {@code Object} in {@code objects}
	 * @param mapper a {@code Function} to map elements of {@code objects} from one type to another
	 * @return a {@code String} representation of a {@code List} of {@code Object}s
	 * @throws NullPointerException thrown if, and only if, either {@code objects}, an element in {@code objects}, {@code left}, {@code right}, {@code separator} or {@code mapper} are {@code null}
	 */
	public static <T, U> String optional(final List<T> objects, final String left, final String right, final String separator, final Function<T, U> mapper) {
		ParameterArguments.requireNonNullList(objects, "objects");
		
		Objects.requireNonNull(left, "left == null");
		Objects.requireNonNull(right, "right == null");
		Objects.requireNonNull(separator, "separator == null");
		Objects.requireNonNull(mapper, "mapper == null");
		
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(objects.size() > 0 ? left : "");
		stringBuilder.append(objects.stream().map(object -> mapper.apply(object).toString()).collect(Collectors.joining(separator)));
		stringBuilder.append(objects.size() > 0 ? right : "");
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns a {@code String} that consists of {@code string} repeated {@code repetition} times.
	 * <p>
	 * If {@code string} is {@code null}, the {@code String} literal {@code "null"} will be repeated {@code repetition} times.
	 * <p>
	 * If {@code repetition} is less than or equal to {@code 0}, an empty {@code String} will be returned.
	 * 
	 * @param string the {@code String} to repeat
	 * @param repetition how many times {@code string} should be repeated
	 * @return a {@code String} that consists of {@code string} repeated {@code repetition} times
	 */
	public static String repeat(final String string, final int repetition) {
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(int i = 0; i < repetition; i++) {
			stringBuilder.append(string);
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns a {@code String} containing the text of the file denoted by {@code file}.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the file cannot be read, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param file the {@code File} denoting the file to read text from
	 * @return a {@code String} containing the text of the file denoted by {@code file}
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, the file cannot be read
	 */
	public static String toString(final File file) {
		try {
			return new String(Files.readAllBytes(file.toPath()));
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}