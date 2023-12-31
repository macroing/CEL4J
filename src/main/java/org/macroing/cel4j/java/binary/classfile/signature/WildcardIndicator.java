/**
 * Copyright 2009 - 2024 J&#246;rgen Lundgren
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
package org.macroing.cel4j.java.binary.classfile.signature;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code WildcardIndicator} denotes a WildcardIndicator as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public enum WildcardIndicator implements Node {
	/**
	 * The {@code WildcardIndicator} instance that represents the lower bound.
	 */
	LOWER_BOUND(Constants.WILDCARD_INDICATOR_LOWER_BOUND),
	
	/**
	 * The {@code WildcardIndicator} instance that represents the upper bound.
	 */
	UPPER_BOUND(Constants.WILDCARD_INDICATOR_UPPER_BOUND);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String internalForm;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private WildcardIndicator(final String internalForm) {
		this.internalForm = internalForm;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code WildcardIndicator} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code WildcardIndicator} instance in external form
	 */
	public String toExternalForm() {
		return String.format("? %s", isUpperBound() ? "extends" : "super");
	}
	
	/**
	 * Returns a {@code String} representation of this {@code WildcardIndicator} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code WildcardIndicator} instance in internal form
	 */
	public String toInternalForm() {
		return this.internalForm;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code WildcardIndicator} instance.
	 * 
	 * @return a {@code String} representation of this {@code WildcardIndicator} instance
	 */
	@Override
	public String toString() {
		return String.format("WildcardIndicator: [InternalForm=%s]", toInternalForm());
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code WildcardIndicator} represents the lower bound, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code WildcardIndicator} represents the lower bound, {@code false} otherwise
	 */
	public boolean isLowerBound() {
		return this.internalForm.equals(Constants.WILDCARD_INDICATOR_LOWER_BOUND);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code WildcardIndicator} represents the upper bound, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code WildcardIndicator} represents the upper bound, {@code false} otherwise
	 */
	public boolean isUpperBound() {
		return this.internalForm.equals(Constants.WILDCARD_INDICATOR_UPPER_BOUND);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Parses {@code string} into a {@code WildcardIndicator} instance.
	 * <p>
	 * Returns a {@code WildcardIndicator} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code WildcardIndicator} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static WildcardIndicator parseWildcardIndicator(final String string) {
		return Parsers.parseWildcardIndicator(new TextScanner(string));
	}
}