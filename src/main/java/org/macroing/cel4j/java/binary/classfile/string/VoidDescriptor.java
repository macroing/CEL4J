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

import org.macroing.cel4j.scanner.TextScanner;

//TODO: Add Javadocs!
public enum VoidDescriptor implements Result, ReturnDescriptor {
//	TODO: Add Javadocs!
	VOID(Constants.VOID_TERM, Constants.VOID_TYPE);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String term;
	private final String type;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private VoidDescriptor(final String term, final String type) {
		this.term = term;
		this.type = type;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public String getTerm() {
		return this.term;
	}
	
//	TODO: Add Javadocs!
	@Override
	public String getType() {
		return this.type;
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toExternalForm() {
		return this.type;
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toInternalForm() {
		return getTerm();
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("VoidDescriptor: [Term=%s], [Type=%s], [ExternalForm=%s], [InternalForm=%s]", getTerm(), getType(), toExternalForm(), toInternalForm());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static VoidDescriptor parseVoidDescriptor(final String string) {
		return Parsers.parseVoidDescriptor(new TextScanner(string));
	}
}