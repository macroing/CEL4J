/**
 * Copyright 2009 - 2021 J&#246;rgen Lundgren
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

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code ThrowsSignature} denotes a ThrowsSignature as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ThrowsSignature implements Node {
	private final ReferenceTypeSignature referenceTypeSignature;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ThrowsSignature(final ReferenceTypeSignature referenceTypeSignature) {
		this.referenceTypeSignature = referenceTypeSignature;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link ReferenceTypeSignature} associated with this {@code ThrowsSignature} instance.
	 * 
	 * @return the {@code ReferenceTypeSignature} associated with this {@code ThrowsSignature} instance
	 */
	public ReferenceTypeSignature getReferenceTypeSignature() {
		return this.referenceTypeSignature;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ThrowsSignature} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code ThrowsSignature} instance in external form
	 */
	public String toExternalForm() {
		return String.format("%s", this.referenceTypeSignature.toExternalForm());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ThrowsSignature} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code ThrowsSignature} instance in internal form
	 */
	public String toInternalForm() {
		return String.format("^%s", this.referenceTypeSignature.toInternalForm());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ThrowsSignature} instance.
	 * 
	 * @return a {@code String} representation of this {@code ThrowsSignature} instance
	 */
	@Override
	public String toString() {
		return String.format("ThrowsSignature: [ReferenceTypeSignature=%s], [InternalForm=%s]", getReferenceTypeSignature(), toInternalForm());
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
	 * <li>traverse its only child {@code Node}, a {@link ReferenceTypeSignature}.</li>
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
				if(!this.referenceTypeSignature.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code ThrowsSignature} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ThrowsSignature}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ThrowsSignature} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ThrowsSignature}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ThrowsSignature)) {
			return false;
		} else if(!Objects.equals(ThrowsSignature.class.cast(object).referenceTypeSignature, this.referenceTypeSignature)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code ThrowsSignature} instance.
	 * 
	 * @return a hash code for this {@code ThrowsSignature} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.referenceTypeSignature);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code ThrowsSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code throwsSignature}.
	 * <p>
	 * If {@code throwsSignature} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * ThrowsSignature.excludePackageName(throwsSignature, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param throwsSignature a {@code ThrowsSignature} instance
	 * @return a {@code ThrowsSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code throwsSignature}
	 * @throws NullPointerException thrown if, and only if, {@code throwsSignature} is {@code null}
	 */
	public static ThrowsSignature excludePackageName(final ThrowsSignature throwsSignature) {
		return excludePackageName(throwsSignature, "java.lang");
	}
	
	/**
	 * Returns a {@code ThrowsSignature} instance that excludes all package names that are equal to {@code packageName} from {@code throwsSignature}.
	 * <p>
	 * If either {@code throwsSignature} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param throwsSignature a {@code ThrowsSignature} instance
	 * @param packageName the package name to exclude
	 * @return a {@code ThrowsSignature} instance that excludes all package names that are equal to {@code packageName} from {@code throwsSignature}
	 * @throws NullPointerException thrown if, and only if, either {@code throwsSignature} or {@code packageName} are {@code null}
	 */
	public static ThrowsSignature excludePackageName(final ThrowsSignature throwsSignature, final String packageName) {
		Objects.requireNonNull(throwsSignature, "throwsSignature == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, throwsSignature);
	}
	
	/**
	 * Parses {@code string} into a {@code ThrowsSignature} instance.
	 * <p>
	 * Returns a {@code ThrowsSignature} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code ThrowsSignature} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static ThrowsSignature parseThrowsSignature(final String string) {
		return Parsers.parseThrowsSignature(new TextScanner(string));
	}
	
	/**
	 * Returns a {@code ThrowsSignature} instance with {@code classTypeSignature} as its associated {@link ReferenceTypeSignature}.
	 * <p>
	 * If {@code classTypeSignature} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param classTypeSignature the associated {@code ReferenceTypeSignature}
	 * @return a {@code ThrowsSignature} instance with {@code classTypeSignature} as its associated {@code ReferenceTypeSignature}
	 * @throws NullPointerException thrown if, and only if, {@code classTypeSignature} is {@code null}
	 */
	public static ThrowsSignature valueOf(final ClassTypeSignature classTypeSignature) {
		return new ThrowsSignature(Objects.requireNonNull(classTypeSignature, "classTypeSignature == null"));
	}
	
	/**
	 * Returns a {@code ThrowsSignature} instance with {@code typeVariableSignature} as its associated {@link ReferenceTypeSignature}.
	 * <p>
	 * If {@code typeVariableSignature} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param typeVariableSignature the associated {@code ReferenceTypeSignature}
	 * @return a {@code ThrowsSignature} instance with {@code typeVariableSignature} as its associated {@code ReferenceTypeSignature}
	 * @throws NullPointerException thrown if, and only if, {@code typeVariableSignature} is {@code null}
	 */
	public static ThrowsSignature valueOf(final TypeVariableSignature typeVariableSignature) {
		return new ThrowsSignature(Objects.requireNonNull(typeVariableSignature, "typeVariableSignature == null"));
	}
}