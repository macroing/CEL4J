/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
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
package org.macroing.cel4j.java.binary.classfile.descriptor;

import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * An {@code ArrayType} denotes an ArrayType as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ArrayType implements FieldType {
	private final ComponentType componentType;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ArrayType(final ComponentType componentType) {
		this.componentType = componentType;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Class} representation of this {@code ArrayType} instance.
	 * <p>
	 * If the {@code Class} cannot be found, a {@code ClassNotFoundException} will be thrown.
	 * 
	 * @return a {@code Class} representation of this {@code ArrayType} instance
	 * @throws ClassNotFoundException thrown if, and only if, the {@code Class} cannot be found
	 */
	@Override
	public Class<?> toClass() throws ClassNotFoundException {
		return Class.forName(toInternalForm().replace("/", "."));
	}
	
	/**
	 * Returns the {@link ComponentType} associated with this {@code ArrayType} instance.
	 * 
	 * @return the {@code ComponentType} associated with this {@code ArrayType} instance
	 */
	public ComponentType getComponentType() {
		return this.componentType;
	}
	
	/**
	 * Returns a {@code String} with the term associated with this {@code ArrayType} instance.
	 * 
	 * @return a {@code String} with the term associated with this {@code ArrayType} instance
	 */
	@Override
	public String getTerm() {
		return "[";
	}
	
	/**
	 * Returns a {@code String} with the type associated with this {@code ArrayType} instance.
	 * 
	 * @return a {@code String} with the type associated with this {@code ArrayType} instance
	 */
	@Override
	public String getType() {
		return Constants.REFERENCE_TYPE;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ArrayType} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code ArrayType} instance in external form
	 */
	@Override
	public String toExternalForm() {
		return String.format("%s[]", getComponentType().toExternalForm());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ArrayType} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code ArrayType} instance in internal form
	 */
	@Override
	public String toInternalForm() {
		return String.format("%s%s", getTerm(), getComponentType().toInternalForm());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ArrayType} instance.
	 * 
	 * @return a {@code String} representation of this {@code ArrayType} instance
	 */
	@Override
	public String toString() {
		return String.format("ArrayType: [Term=%s], [Type=%s], [ComponentType=%s], [ExternalForm=%s], [InternalForm=%s]", getTerm(), getType(), getComponentType(), toExternalForm(), toInternalForm());
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
	 * <li>traverse its only child {@code Node}, a {@link ComponentType}.</li>
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
				this.componentType.accept(nodeHierarchicalVisitor);
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code ArrayType} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ArrayType}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ArrayType} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ArrayType}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ArrayType)) {
			return false;
		} else if(!Objects.equals(ArrayType.class.cast(object).componentType, this.componentType)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the array dimensions of this {@code ArrayType} instance.
	 * 
	 * @return the array dimensions of this {@code ArrayType} instance
	 */
	public int getDimensions() {
		return 1 + (this.componentType instanceof ArrayType ? ArrayType.class.cast(this.componentType).getDimensions() : 0);
	}
	
	/**
	 * Returns a hash code for this {@code ArrayType} instance.
	 * 
	 * @return a hash code for this {@code ArrayType} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.componentType);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code ArrayType} instance that excludes all package names that are equal to {@code "java.lang"} from {@code arrayType}.
	 * <p>
	 * If {@code arrayType} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * ArrayType.excludePackageName(arrayType, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param arrayType an {@code ArrayType} instance
	 * @return an {@code ArrayType} instance that excludes all package names that are equal to {@code "java.lang"} from {@code arrayType}
	 * @throws NullPointerException thrown if, and only if, {@code arrayType} is {@code null}
	 */
	public static ArrayType excludePackageName(final ArrayType arrayType) {
		return excludePackageName(arrayType, "java.lang");
	}
	
	/**
	 * Returns an {@code ArrayType} instance that excludes all package names that are equal to {@code packageName} from {@code arrayType}.
	 * <p>
	 * If either {@code arrayType} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param arrayType an {@code ArrayType} instance
	 * @param packageName the package name to exclude
	 * @return an {@code ArrayType} instance that excludes all package names that are equal to {@code packageName} from {@code arrayType}
	 * @throws NullPointerException thrown if, and only if, either {@code arrayType} or {@code packageName} are {@code null}
	 */
	public static ArrayType excludePackageName(final ArrayType arrayType, final String packageName) {
		Objects.requireNonNull(arrayType, "arrayType == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, arrayType);
	}
	
	/**
	 * Parses {@code string} into an {@code ArrayType} instance.
	 * <p>
	 * Returns an {@code ArrayType} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return an {@code ArrayType} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static ArrayType parseArrayType(final String string) {
		return Parsers.parseArrayType(new TextScanner(string));
	}
	
	/**
	 * Returns an {@code ArrayType} instance with {@code componentType} as its associated {@link ComponentType}.
	 * <p>
	 * If {@code componentType} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param componentType the associated {@code ComponentType}
	 * @return an {@code ArrayType} instance with {@code componentType} as its associated {@code ComponentType}
	 * @throws NullPointerException thrown if, and only if, {@code componentType} is {@code null}
	 */
	public static ArrayType valueOf(final ComponentType componentType) {
		return new ArrayType(Objects.requireNonNull(componentType, "componentType == null"));
	}
}