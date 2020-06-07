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
package org.macroing.cel4j.java.binary.classfile.string;

import java.lang.reflect.Field;//TODO: Add Javadocs!

import org.macroing.cel4j.node.Node;

//TODO: Add Javadocs!
public enum WildcardIndicator implements Node {
//	TODO: Add Javadocs!
	LOWER_BOUND(Constants.WILDCARD_INDICATOR_LOWER_BOUND),
	
//	TODO: Add Javadocs!
	UPPER_BOUND(Constants.WILDCARD_INDICATOR_UPPER_BOUND);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String internalForm;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private WildcardIndicator(final String internalForm) {
		this.internalForm = internalForm;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public String toExternalForm() {
		return String.format("? %s", isUpperBound() ? "extends" : "super");
	}
	
//	TODO: Add Javadocs!
	public String toInternalForm() {
		return this.internalForm;
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("WildcardIndicator: [InternalForm=%s]", toInternalForm());
	}
	
//	TODO: Add Javadocs!
	public boolean isLowerBound() {
		return this.internalForm.equals(Constants.WILDCARD_INDICATOR_LOWER_BOUND);
	}
	
//	TODO: Add Javadocs!
	public boolean isUpperBound() {
		return this.internalForm.equals(Constants.WILDCARD_INDICATOR_UPPER_BOUND);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static WildcardIndicator parseWildcardIndicator(final String string) {
		return Parsers.parseWildcardIndicator(TextScanner.newInstance(string));
	}
}