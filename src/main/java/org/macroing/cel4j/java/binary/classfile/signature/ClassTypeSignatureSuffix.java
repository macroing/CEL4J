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

/**
 * A {@code ClassTypeSignatureSuffix} denotes a ClassTypeSignatureSuffix as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ClassTypeSignatureSuffix implements Node {
	private final SimpleClassTypeSignature simpleClassTypeSignature;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ClassTypeSignatureSuffix(final SimpleClassTypeSignature simpleClassTypeSignature) {
		this.simpleClassTypeSignature = simpleClassTypeSignature;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link SimpleClassTypeSignature} associated with this {@code ClassTypeSignatureSuffix} instance.
	 * 
	 * @return the {@code SimpleClassTypeSignature} associated with this {@code ClassTypeSignatureSuffix} instance
	 */
	public SimpleClassTypeSignature getSimpleClassTypeSignature() {
		return this.simpleClassTypeSignature;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassTypeSignatureSuffix} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code ClassTypeSignatureSuffix} instance in external form
	 */
	public String toExternalForm() {
		return String.format(".%s", this.simpleClassTypeSignature.toExternalForm());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassTypeSignatureSuffix} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code ClassTypeSignatureSuffix} instance in internal form
	 */
	public String toInternalForm() {
		return String.format(".%s", this.simpleClassTypeSignature.toInternalForm());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassTypeSignatureSuffix} instance.
	 * 
	 * @return a {@code String} representation of this {@code ClassTypeSignatureSuffix} instance
	 */
	@Override
	public String toString() {
		return String.format("ClassTypeSignatureSuffix: [SimpleClassTypeSignature=%s], [InternalForm=%s]", getSimpleClassTypeSignature(), toInternalForm());
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
	 * <li>traverse its only child {@code Node}, a {@link SimpleClassTypeSignature}.</li>
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
				this.simpleClassTypeSignature.accept(nodeHierarchicalVisitor);
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code ClassTypeSignatureSuffix} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ClassTypeSignatureSuffix}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ClassTypeSignatureSuffix} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ClassTypeSignatureSuffix}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ClassTypeSignatureSuffix)) {
			return false;
		} else if(!Objects.equals(ClassTypeSignatureSuffix.class.cast(object).simpleClassTypeSignature, this.simpleClassTypeSignature)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code ClassTypeSignatureSuffix} instance.
	 * 
	 * @return a hash code for this {@code ClassTypeSignatureSuffix} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.simpleClassTypeSignature);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code ClassTypeSignatureSuffix} instance that excludes all package names that are equal to {@code "java.lang"} from {@code classTypeSignatureSuffix}.
	 * <p>
	 * If {@code classTypeSignatureSuffix} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * ClassTypeSignatureSuffix.excludePackageName(classTypeSignatureSuffix, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param classTypeSignatureSuffix a {@code ClassTypeSignatureSuffix} instance
	 * @return a {@code ClassTypeSignatureSuffix} instance that excludes all package names that are equal to {@code "java.lang"} from {@code classTypeSignatureSuffix}
	 * @throws NullPointerException thrown if, and only if, {@code classTypeSignatureSuffix} is {@code null}
	 */
	public static ClassTypeSignatureSuffix excludePackageName(final ClassTypeSignatureSuffix classTypeSignatureSuffix) {
		return excludePackageName(classTypeSignatureSuffix, "java.lang");
	}
	
	/**
	 * Returns a {@code ClassTypeSignatureSuffix} instance that excludes all package names that are equal to {@code packageName} from {@code classTypeSignatureSuffix}.
	 * <p>
	 * If either {@code classTypeSignatureSuffix} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param classTypeSignatureSuffix a {@code ClassTypeSignatureSuffix} instance
	 * @param packageName the package name to exclude
	 * @return a {@code ClassTypeSignatureSuffix} instance that excludes all package names that are equal to {@code packageName} from {@code classTypeSignatureSuffix}
	 * @throws NullPointerException thrown if, and only if, either {@code classTypeSignatureSuffix} or {@code packageName} are {@code null}
	 */
	public static ClassTypeSignatureSuffix excludePackageName(final ClassTypeSignatureSuffix classTypeSignatureSuffix, final String packageName) {
		Objects.requireNonNull(classTypeSignatureSuffix, "classTypeSignatureSuffix == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, classTypeSignatureSuffix);
	}
	
	/**
	 * Parses {@code string} into a {@code ClassTypeSignatureSuffix} instance.
	 * <p>
	 * Returns a {@code ClassTypeSignatureSuffix} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code ClassTypeSignatureSuffix} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static ClassTypeSignatureSuffix parseClassTypeSignatureSuffix(final String string) {
		if(string.charAt(0) == '.') {
			return valueOf(SimpleClassTypeSignature.parseSimpleClassTypeSignature(string.substring(1)));
		}
		
		throw new IllegalArgumentException(String.format("Illegal ClassTypeSignatureSuffix: %s", string));
	}
	
	/**
	 * Returns a {@code ClassTypeSignatureSuffix} instance with {@code simpleClassTypeSignature} as its associated {@link SimpleClassTypeSignature}.
	 * <p>
	 * If {@code simpleClassTypeSignature} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param simpleClassTypeSignature the associated {@code SimpleClassTypeSignature}
	 * @return a {@code ClassTypeSignatureSuffix} instance with {@code simpleClassTypeSignature} as its associated {@code SimpleClassTypeSignature}
	 * @throws NullPointerException thrown if, and only if, {@code simpleClassTypeSignature} is {@code null}
	 */
	public static ClassTypeSignatureSuffix valueOf(final SimpleClassTypeSignature simpleClassTypeSignature) {
		return new ClassTypeSignatureSuffix(Objects.requireNonNull(simpleClassTypeSignature, "simpleClassTypeSignature == null"));
	}
}