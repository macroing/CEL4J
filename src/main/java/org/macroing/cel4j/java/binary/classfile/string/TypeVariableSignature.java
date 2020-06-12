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

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;

//TODO: Add Javadocs!
public final class TypeVariableSignature implements ReferenceTypeSignature {
	private final Identifier identifier;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	TypeVariableSignature(final Identifier identifier) {
		this.identifier = identifier;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Identifier getIdentifier() {
		return this.identifier;
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toExternalForm() {
		return String.format("%s", this.identifier.toExternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toInternalForm() {
		return String.format("T%s;", this.identifier.toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("TypeVariableSignature: [Identifier=%s], [InternalForm=%s]", getIdentifier(), toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				this.identifier.accept(nodeHierarchicalVisitor);
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof TypeVariableSignature)) {
			return false;
		} else if(!Objects.equals(TypeVariableSignature.class.cast(object).identifier, this.identifier)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.identifier);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static TypeVariableSignature parseTypeVariableSignature(final String string) {
		return Parsers.parseTypeVariableSignature(new TextScanner(string));
	}
	
//	TODO: Add Javadocs!
	public static TypeVariableSignature valueOf(final Identifier identifier) {
		return new TypeVariableSignature(Objects.requireNonNull(identifier, "identifier == null"));
	}
}