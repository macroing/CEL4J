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
public interface ReferenceTypeSignature extends FieldSignature, JavaTypeSignature {
//	TODO: Add Javadocs!
	static ReferenceTypeSignature parseReferenceTypeSignature(final String string) {
		return Parsers.parseReferenceTypeSignature(new TextScanner(string));
	}
}