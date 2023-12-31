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
 * An {@code ArrayTypeSignature} denotes an ArrayTypeSignature as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ArrayTypeSignature implements ReferenceTypeSignature {
	private final JavaTypeSignature javaTypeSignature;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ArrayTypeSignature(final JavaTypeSignature javaTypeSignature) {
		this.javaTypeSignature = javaTypeSignature;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link JavaTypeSignature} associated with this {@code ArrayTypeSignature} instance.
	 * 
	 * @return the {@code JavaTypeSignature} associated with this {@code ArrayTypeSignature} instance
	 */
	public JavaTypeSignature getJavaTypeSignature() {
		return this.javaTypeSignature;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ArrayTypeSignature} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code ArrayTypeSignature} instance in external form
	 */
	@Override
	public String toExternalForm() {
		return String.format("%s[]", this.javaTypeSignature.toExternalForm());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ArrayTypeSignature} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code ArrayTypeSignature} instance in internal form
	 */
	@Override
	public String toInternalForm() {
		return String.format("[%s", this.javaTypeSignature.toInternalForm());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ArrayTypeSignature} instance.
	 * 
	 * @return a {@code String} representation of this {@code ArrayTypeSignature} instance
	 */
	@Override
	public String toString() {
		return String.format("ArrayTypeSignature: [JavaTypeSignature=%s], [InternalForm=%s]", getJavaTypeSignature(), toInternalForm());
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
	 * <li>traverse its only child {@code Node}, a {@link JavaTypeSignature}.</li>
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
				this.javaTypeSignature.accept(nodeHierarchicalVisitor);
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code ArrayTypeSignature} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code ArrayTypeSignature} is an instance of {@code ArrayType}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ArrayTypeSignature} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ArrayTypeSignature}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ArrayTypeSignature)) {
			return false;
		} else if(!Objects.equals(ArrayTypeSignature.class.cast(object).javaTypeSignature, this.javaTypeSignature)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code ArrayTypeSignature} instance.
	 * 
	 * @return a hash code for this {@code ArrayTypeSignature} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.javaTypeSignature);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code ArrayTypeSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code arrayTypeSignature}.
	 * <p>
	 * If {@code arrayTypeSignature} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * ArrayTypeSignature.excludePackageName(arrayTypeSignature, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param arrayTypeSignature an {@code ArrayTypeSignature} instance
	 * @return an {@code ArrayTypeSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code arrayTypeSignature}
	 * @throws NullPointerException thrown if, and only if, {@code arrayTypeSignature} is {@code null}
	 */
	public static ArrayTypeSignature excludePackageName(final ArrayTypeSignature arrayTypeSignature) {
		return excludePackageName(arrayTypeSignature, "java.lang");
	}
	
	/**
	 * Returns an {@code ArrayTypeSignature} instance that excludes all package names that are equal to {@code packageName} from {@code arrayTypeSignature}.
	 * <p>
	 * If either {@code arrayTypeSignature} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param arrayTypeSignature an {@code ArrayTypeSignature} instance
	 * @param packageName the package name to exclude
	 * @return an {@code ArrayTypeSignature} instance that excludes all package names that are equal to {@code packageName} from {@code arrayTypeSignature}
	 * @throws NullPointerException thrown if, and only if, either {@code arrayTypeSignature} or {@code packageName} are {@code null}
	 */
	public static ArrayTypeSignature excludePackageName(final ArrayTypeSignature arrayTypeSignature, final String packageName) {
		Objects.requireNonNull(arrayTypeSignature, "arrayTypeSignature == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, arrayTypeSignature);
	}
	
	/**
	 * Parses {@code string} into an {@code ArrayTypeSignature} instance.
	 * <p>
	 * Returns an {@code ArrayTypeSignature} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return an {@code ArrayTypeSignature} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static ArrayTypeSignature parseArrayTypeSignature(final String string) {
		return Parsers.parseArrayTypeSignature(new TextScanner(string));
	}
	
	/**
	 * Returns an {@code ArrayTypeSignature} instance with {@code javaTypeSignature} as its associated {@link JavaTypeSignature}.
	 * <p>
	 * If {@code javaTypeSignature} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param javaTypeSignature the associated {@code JavaTypeSignature}
	 * @return an {@code ArrayTypeSignature} instance with {@code javaTypeSignature} as its associated {@code JavaTypeSignature}
	 * @throws NullPointerException thrown if, and only if, {@code javaTypeSignature} is {@code null}
	 */
	public static ArrayTypeSignature valueOf(final JavaTypeSignature javaTypeSignature) {
		return new ArrayTypeSignature(Objects.requireNonNull(javaTypeSignature, "javaTypeSignature == null"));
	}
}