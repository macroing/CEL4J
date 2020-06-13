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

import java.util.Objects;
import java.util.Optional;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code ClassBound} denotes a ClassBound as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ClassBound implements Node {
	/**
	 * An empty {@code ClassBound} instance.
	 */
	public static final ClassBound EMPTY = new ClassBound(Optional.empty());
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Optional<ReferenceTypeSignature> referenceTypeSignature;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ClassBound(final Optional<ReferenceTypeSignature> referenceTypeSignature) {
		this.referenceTypeSignature = referenceTypeSignature;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code Optional} of type {@link ReferenceTypeSignature} with the optional {@code ReferenceTypeSignature} instance associated with this {@code ClassBound} instance.
	 * 
	 * @return an {@code Optional} of type {@code ReferenceTypeSignature} with the optional {@code ReferenceTypeSignature} instance associated with this {@code ClassBound} instance.
	 */
	public Optional<ReferenceTypeSignature> getReferenceTypeSignature() {
		return this.referenceTypeSignature;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassBound} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code ClassBound} instance in external form
	 */
	public String toExternalForm() {
		return this.referenceTypeSignature.isPresent() ? this.referenceTypeSignature.get().toExternalForm() : "";
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassBound} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code ClassBound} instance in internal form
	 */
	public String toInternalForm() {
		return this.referenceTypeSignature.isPresent() ? ":" + this.referenceTypeSignature.get().toInternalForm() : "";
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassBound} instance.
	 * 
	 * @return a {@code String} representation of this {@code ClassBound} instance
	 */
	@Override
	public String toString() {
		return String.format("ClassBound: [ReferenceTypeSignature=%s]", getReferenceTypeSignature());
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
	 * <li>traverse its only child {@code Node}, a {@link ReferenceTypeSignature}, if present.</li>
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
				this.referenceTypeSignature.ifPresent(referenceTypeSignature -> referenceTypeSignature.accept(nodeHierarchicalVisitor));
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code ClassBound} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ClassBound}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ClassBound} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ClassBound}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ClassBound)) {
			return false;
		} else if(!Objects.equals(ClassBound.class.cast(object).referenceTypeSignature, this.referenceTypeSignature)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code ClassBound} instance.
	 * 
	 * @return a hash code for this {@code ClassBound} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.referenceTypeSignature);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Parses {@code string} into a {@code ClassBound} instance.
	 * <p>
	 * Returns a {@code ClassBound} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code ClassBound} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static ClassBound parseClassBound(final String string) {
		return Parsers.parseClassBound(new TextScanner(string));
	}
	
	/**
	 * Returns a {@code ClassBound} instance with {@code referenceTypeSignature} as its associated {@link ReferenceTypeSignature}.
	 * <p>
	 * If {@code referenceTypeSignature} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param referenceTypeSignature the associated {@code ReferenceTypeSignature}
	 * @return a {@code ClassBound} instance with {@code referenceTypeSignature} as its associated {@code ReferenceTypeSignature}
	 * @throws NullPointerException thrown if, and only if, {@code referenceTypeSignature} is {@code null}
	 */
	public static ClassBound valueOf(final ReferenceTypeSignature referenceTypeSignature) {
		return new ClassBound(Optional.of(referenceTypeSignature));
	}
}