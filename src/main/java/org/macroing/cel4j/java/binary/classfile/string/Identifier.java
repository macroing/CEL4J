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
import java.util.Objects;

import org.macroing.cel4j.node.Node;

//TODO: Add Javadocs!
public final class Identifier implements Node {
	private final String internalForm;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	Identifier(final String internalForm) {
		this.internalForm = internalForm;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public String toExternalForm() {
		return this.internalForm.replace('/', '.');
	}
	
//	TODO: Add Javadocs!
	public String toInternalForm() {
		return this.internalForm;
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("Identifier: [InternalForm=%s]", toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Identifier)) {
			return false;
		} else if(!Objects.equals(Identifier.class.cast(object).internalForm, this.internalForm)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.internalForm);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static Identifier parseIdentifier(final String string) {
		return Parsers.parseIdentifier(TextScanner.newInstance(string));
	}
}