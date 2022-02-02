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
 * An {@code ObjectType} denotes an ObjectType as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ObjectType implements FieldType {
	private final ClassName className;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	ObjectType(final ClassName className) {
		this.className = className;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Class} representation of this {@code ObjectType} instance.
	 * <p>
	 * If the {@code Class} cannot be found, a {@code ClassNotFoundException} will be thrown.
	 * 
	 * @return a {@code Class} representation of this {@code ObjectType} instance
	 * @throws ClassNotFoundException thrown if, and only if, the {@code Class} cannot be found
	 */
	@Override
	public Class<?> toClass() throws ClassNotFoundException {
		return Class.forName(toExternalForm());
	}
	
	/**
	 * Returns the {@link ClassName} associated with this {@code ObjectType} instance.
	 * 
	 * @return the {@code ClassName} associated with this {@code ObjectType} instance
	 */
	public ClassName getClassName() {
		return this.className;
	}
	
	/**
	 * Returns a {@code String} with the term associated with this {@code ObjectType} instance.
	 * 
	 * @return a {@code String} with the term associated with this {@code ObjectType} instance
	 */
	@Override
	public String getTerm() {
		return String.format("L%s;", this.className.toInternalForm());
	}
	
	/**
	 * Returns a {@code String} with the type associated with this {@code ObjectType} instance.
	 * 
	 * @return a {@code String} with the type associated with this {@code ObjectType} instance
	 */
	@Override
	public String getType() {
		return Constants.REFERENCE_TYPE;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ObjectType} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code ObjectType} instance in external form
	 */
	@Override
	public String toExternalForm() {
		return this.className.toExternalForm();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ObjectType} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code ObjectType} instance in internal form
	 */
	@Override
	public String toInternalForm() {
		return getTerm();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ObjectType} instance.
	 * 
	 * @return a {@code String} representation of this {@code ObjectType} instance
	 */
	@Override
	public String toString() {
		return String.format("ObjectType: [Term=%s], [Type=%s], [ExternalForm=%s], [InternalForm=%s]", getTerm(), getType(), toExternalForm(), toInternalForm());
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
	 * <li>traverse its only child {@code Node}, a {@link ClassName}.</li>
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
				this.className.accept(nodeHierarchicalVisitor);
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code ObjectType} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ObjectType}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ObjectType} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ObjectType}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ObjectType)) {
			return false;
		} else if(!Objects.equals(ObjectType.class.cast(object).className, this.className)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code ObjectType} instance.
	 * 
	 * @return a hash code for this {@code ObjectType} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.className);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code ObjectType} instance that excludes all package names that are equal to {@code "java.lang"} from {@code objectType}.
	 * <p>
	 * If {@code objectType} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * ObjectType.excludePackageName(objectType, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param objectType an {@code ObjectType} instance
	 * @return an {@code ObjectType} instance that excludes all package names that are equal to {@code "java.lang"} from {@code objectType}
	 * @throws NullPointerException thrown if, and only if, {@code objectType} is {@code null}
	 */
	public static ObjectType excludePackageName(final ObjectType objectType) {
		return excludePackageName(objectType, "java.lang");
	}
	
	/**
	 * Returns an {@code ObjectType} instance that excludes all package names that are equal to {@code packageName} from {@code objectType}.
	 * <p>
	 * If either {@code objectType} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param objectType an {@code ObjectType} instance
	 * @param packageName the package name to exclude
	 * @return an {@code ObjectType} instance that excludes all package names that are equal to {@code packageName} from {@code objectType}
	 * @throws NullPointerException thrown if, and only if, either {@code objectType} or {@code packageName} are {@code null}
	 */
	public static ObjectType excludePackageName(final ObjectType objectType, final String packageName) {
		Objects.requireNonNull(objectType, "objectType == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, objectType);
	}
	
	/**
	 * Parses {@code string} into an {@code ObjectType} instance.
	 * <p>
	 * Returns an {@code ObjectType} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return an {@code ObjectType} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static ObjectType parseObjectType(final String string) {
		return Parsers.parseObjectType(new TextScanner(string));
	}
	
	/**
	 * Returns an {@code ObjectType} instance with {@code className} as its associated {@link ClassName}.
	 * <p>
	 * If {@code className} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param className the associated {@code ClassName}
	 * @return an {@code ObjectType} instance with {@code className} as its associated {@code ClassName}
	 * @throws NullPointerException thrown if, and only if, {@code className} is {@code null}
	 */
	public static ObjectType valueOf(final ClassName className) {
		return new ObjectType(Objects.requireNonNull(className, "className == null"));
	}
}