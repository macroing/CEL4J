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
package org.macroing.cel4j.java.model;

import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.util.Strings;

/**
 * A {@code Modifier} represents a modifier.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public enum Modifier {
	/**
	 * A {@code Modifier} instance that represents the abstract modifier.
	 */
	ABSTRACT("abstract"),
	
	/**
	 * A {@code Modifier} instance that represents the default modifier.
	 */
	DEFAULT("default"),
	
	/**
	 * A {@code Modifier} instance that represents the final modifier.
	 */
	FINAL("final"),
	
	/**
	 * A {@code Modifier} instance that represents the native modifier.
	 */
	NATIVE("native"),
	
	/**
	 * A {@code Modifier} instance that represents the private modifier.
	 */
	PRIVATE("private"),
	
	/**
	 * A {@code Modifier} instance that represents the protected modifier.
	 */
	PROTECTED("protected"),
	
	/**
	 * A {@code Modifier} instance that represents the public modifier.
	 */
	PUBLIC("public"),
	
	/**
	 * A {@code Modifier} instance that represents the static modifier.
	 */
	STATIC("static"),
	
	/**
	 * A {@code Modifier} instance that represents the strictfp modifier.
	 */
	STRICT_F_P("strictfp"),
	
	/**
	 * A {@code Modifier} instance that represents the synchronized modifier.
	 */
	SYNCHRONIZED("synchronized"),
	
	/**
	 * A {@code Modifier} instance that represents the transient modifier.
	 */
	TRANSIENT("transient"),
	
	/**
	 * A {@code Modifier} instance that represents the volatile modifier.
	 */
	VOLATILE("volatile");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String keyword;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Modifier(final String keyword) {
		this.keyword = Objects.requireNonNull(keyword, "keyword == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} that contains the keyword for this {@code Modifier} instance.
	 * 
	 * @return a {@code String} that contains the keyword for this {@code Modifier} instance
	 */
	public String getKeyword() {
		return this.keyword;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of all {@code Modifier} instances in {@code modifiers} in external form.
	 * <p>
	 * If {@code modifiers} or at least one of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param modifiers a {@code List} of {@code Modifier} instances
	 * @return a {@code String} representation of all {@code Modifier} instances in {@code modifiers} in external form
	 * @throws NullPointerException thrown if, and only if, {@code modifiers} or at least one of its elements are {@code null}
	 */
	public static String toExternalForm(final List<Modifier> modifiers) {
		return Strings.optional(modifiers, "", " ", " ", modifier -> modifier.getKeyword());
	}
}