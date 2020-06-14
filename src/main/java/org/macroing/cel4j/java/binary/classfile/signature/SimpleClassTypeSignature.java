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
package org.macroing.cel4j.java.binary.classfile.signature;

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Objects;
import java.util.Optional;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code SimpleClassTypeSignature} denotes a SimpleClassTypeSignature as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class SimpleClassTypeSignature implements Node {
	private final Identifier identifier;
	private final Optional<TypeArguments> typeArguments;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private SimpleClassTypeSignature(final Identifier identifier, final Optional<TypeArguments> typeArguments) {
		this.identifier = identifier;
		this.typeArguments = typeArguments;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Identifier} associated with this {@code SimpleClassTypeSignature} instance.
	 * 
	 * @return the {@code Identifier} associated with this {@code SimpleClassTypeSignature} instance
	 */
	public Identifier getIdentifier() {
		return this.identifier;
	}
	
	/**
	 * Returns an {@code Optional} of type {@link TypeArguments} with the optional {@code TypeArguments} instance associated with this {@code SimpleClassTypeSignature} instance.
	 * 
	 * @return an {@code Optional} of type {@code TypeArguments} with the optional {@code TypeArguments} instance associated with this {@code SimpleClassTypeSignature} instance.
	 */
	public Optional<TypeArguments> getTypeArguments() {
		return this.typeArguments;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code SimpleClassTypeSignature} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code SimpleClassTypeSignature} instance in external form
	 */
	public String toExternalForm() {
		return String.format("%s%s", this.identifier.toExternalForm(), this.typeArguments.isPresent() ? this.typeArguments.get().toExternalForm() : "");
	}
	
	/**
	 * Returns a {@code String} representation of this {@code SimpleClassTypeSignature} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code SimpleClassTypeSignature} instance in internal form
	 */
	public String toInternalForm() {
		return String.format("%s%s", this.identifier.toInternalForm(), this.typeArguments.isPresent() ? this.typeArguments.get().toInternalForm() : "");
	}
	
	/**
	 * Returns a {@code String} representation of this {@code SimpleClassTypeSignature} instance.
	 * 
	 * @return a {@code String} representation of this {@code SimpleClassTypeSignature} instance
	 */
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
	
	/**
	 * Compares {@code object} to this {@code SimpleClassTypeSignature} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code SimpleClassTypeSignature}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code SimpleClassTypeSignature} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code SimpleClassTypeSignature}, and their respective values are equal, {@code false} otherwise
	 */
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
	
	/**
	 * Returns a hash code for this {@code SimpleClassTypeSignature} instance.
	 * 
	 * @return a hash code for this {@code SimpleClassTypeSignature} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.identifier, this.typeArguments);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static SimpleClassTypeSignature parseSimpleClassTypeSignature(final String string) {
		return Parsers.parseSimpleClassTypeSignature(new TextScanner(string));
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