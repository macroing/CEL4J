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
public enum BaseType implements FieldType, JavaTypeSignature {
//	TODO: Add Javadocs!
	BOOLEAN(Constants.BOOLEAN_TERM, Constants.BOOLEAN_TYPE),
	
//	TODO: Add Javadocs!
	BYTE(Constants.BYTE_TERM, Constants.BYTE_TYPE),
	
//	TODO: Add Javadocs!
	CHAR(Constants.CHAR_TERM, Constants.CHAR_TYPE),
	
//	TODO: Add Javadocs!
	DOUBLE(Constants.DOUBLE_TERM, Constants.DOUBLE_TYPE),
	
//	TODO: Add Javadocs!
	FLOAT(Constants.FLOAT_TERM, Constants.FLOAT_TYPE),
	
//	TODO: Add Javadocs!
	INT(Constants.INT_TERM, Constants.INT_TYPE),
	
//	TODO: Add Javadocs!
	LONG(Constants.LONG_TERM, Constants.LONG_TYPE),
	
//	TODO: Add Javadocs!
	SHORT(Constants.SHORT_TERM, Constants.SHORT_TYPE);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String term;
	private final String type;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private BaseType(final String term, final String type) {
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
		return this.term;
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("BaseType: [Term=%s], [Type=%s], [ExternalForm=%s], [InternalForm=%s]", getTerm(), getType(), toExternalForm(), toInternalForm());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static BaseType parseBaseType(final String string) {
		return Parsers.parseBaseType(new TextScanner(string));
	}
}