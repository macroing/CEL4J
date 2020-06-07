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
import java.util.Optional;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

//TODO: Add Javadocs!
public final class SimpleClassTypeSignature implements Node {
	private final Identifier identifier;
	private final Optional<TypeArguments> typeArguments;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private SimpleClassTypeSignature(final Identifier identifier, final Optional<TypeArguments> typeArguments) {
		this.identifier = identifier;
		this.typeArguments = typeArguments;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Identifier getIdentifier() {
		return this.identifier;
	}
	
//	TODO: Add Javadocs!
	public Optional<TypeArguments> getTypeArguments() {
		return this.typeArguments;
	}
	
//	TODO: Add Javadocs!
	public String toExternalForm() {
		return String.format("%s%s", this.identifier.toExternalForm(), this.typeArguments.isPresent() ? this.typeArguments.get().toExternalForm() : "");
	}
	
//	TODO: Add Javadocs!
	public String toInternalForm() {
		return String.format("%s%s", this.identifier.toInternalForm(), this.typeArguments.isPresent() ? this.typeArguments.get().toInternalForm() : "");
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("SimpleClassTypeSignature: [Identifier=%s], [TypeArguments=%s], [InternalForm=%s]", getIdentifier(), getTypeArguments(), toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				if(!this.identifier.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
				
				if(this.typeArguments.isPresent()) {
					this.typeArguments.get().accept(nodeHierarchicalVisitor);
				}
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
		} else if(!(object instanceof SimpleClassTypeSignature)) {
			return false;
		} else if(!Objects.equals(SimpleClassTypeSignature.class.cast(object).identifier, this.identifier)) {
			return false;
		} else if(!Objects.equals(SimpleClassTypeSignature.class.cast(object).typeArguments, this.typeArguments)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.identifier, this.typeArguments);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static SimpleClassTypeSignature parseSimpleClassTypeSignature(final String string) {
		return Parsers.parseSimpleClassTypeSignature(TextScanner.newInstance(string));
	}
	
//	TODO: Add Javadocs!
	public static SimpleClassTypeSignature valueOf(final Identifier identifier) {
		return new SimpleClassTypeSignature(Objects.requireNonNull(identifier, "identifier == null"), Optional.empty());
	}
	
//	TODO: Add Javadocs!
	public static SimpleClassTypeSignature valueOf(final Identifier identifier, final TypeArguments typeArguments) {
		return new SimpleClassTypeSignature(Objects.requireNonNull(identifier, "identifier == null"), Optional.of(typeArguments));
	}
}