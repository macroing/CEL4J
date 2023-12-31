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

import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code TypeVariableSignature} denotes a TypeVariableSignature as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class TypeVariableSignature implements ReferenceTypeSignature {
	private final Identifier identifier;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	TypeVariableSignature(final Identifier identifier) {
		this.identifier = identifier;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Identifier} associated with this {@code TypeVariableSignature} instance.
	 * 
	 * @return the {@code Identifier} associated with this {@code TypeVariableSignature} instance
	 */
	public Identifier getIdentifier() {
		return this.identifier;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TypeVariableSignature} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code TypeVariableSignature} instance in external form
	 */
	@Override
	public String toExternalForm() {
		return String.format("%s", this.identifier.toExternalForm());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TypeVariableSignature} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code TypeVariableSignature} instance in internal form
	 */
	@Override
	public String toInternalForm() {
		return String.format("T%s;", this.identifier.toInternalForm());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TypeVariableSignature} instance.
	 * 
	 * @return a {@code String} representation of this {@code TypeVariableSignature} instance
	 */
	@Override
	public String toString() {
		return String.format("TypeVariableSignature: [Identifier=%s], [InternalForm=%s]", getIdentifier(), toInternalForm());
	}
	
	/**
	 * Accepts a {@link NodeHierarchicalVisitor}.
	 * <p>
	 * Returns the result of {@code nodeHierarchicalVisitor.visitLeave(this)}.
	 * <p>
	 * If {@code nodeHierarchicalVisitor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}, a {@code NodeTraversalException} will be thrown with the {@code RuntimeException} wrapped.
	 * <p>
	 * This implementation will:
	 * <ul>
	 * <li>throw a {@code NullPointerException} if {@code nodeHierarchicalVisitor} is {@code null}.</li>
	 * <li>throw a {@code NodeTraversalException} if {@code nodeHierarchicalVisitor} throws a {@code RuntimeException}.</li>
	 * <li>traverse its only child {@code Node}, an {@link Identifier}.</li>
	 * </ul>
	 * 
	 * @param nodeHierarchicalVisitor the {@code NodeHierarchicalVisitor} to accept
	 * @return the result of {@code nodeHierarchicalVisitor.visitLeave(this)}
	 * @throws NodeTraversalException thrown if, and only if, a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code nodeHierarchicalVisitor} is {@code null}
	 */
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
	
	/**
	 * Compares {@code object} to this {@code TypeVariableSignature} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code TypeVariableSignature} is an instance of {@code ArrayType}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code TypeVariableSignature} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code TypeVariableSignature}, and their respective values are equal, {@code false} otherwise
	 */
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
	
	/**
	 * Returns a hash code for this {@code TypeVariableSignature} instance.
	 * 
	 * @return a hash code for this {@code TypeVariableSignature} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.identifier);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Parses {@code string} into a {@code TypeVariableSignature} instance.
	 * <p>
	 * Returns a {@code TypeVariableSignature} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code TypeVariableSignature} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static TypeVariableSignature parseTypeVariableSignature(final String string) {
		return Parsers.parseTypeVariableSignature(new TextScanner(string));
	}
	
	/**
	 * Returns a {@code TypeVariableSignature} instance with {@code identifier} as its associated {@link Identifier}.
	 * <p>
	 * If {@code identifier} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param identifier the associated {@code Identifier}
	 * @return a {@code TypeVariableSignature} instance with {@code identifier} as its associated {@code Identifier}
	 * @throws NullPointerException thrown if, and only if, {@code identifier} is {@code null}
	 */
	public static TypeVariableSignature valueOf(final Identifier identifier) {
		return new TypeVariableSignature(Objects.requireNonNull(identifier, "identifier == null"));
	}
}