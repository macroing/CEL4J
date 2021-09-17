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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;
import org.macroing.cel4j.util.Lists;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code TypeArguments} denotes a TypeArguments as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class TypeArguments implements Node {
	private final List<TypeArgument> typeArguments;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	TypeArguments(final List<TypeArgument> typeArguments) {
		this.typeArguments = typeArguments;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@link TypeArgument} instances associated with this {@code TypeArguments} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code TypeArguments} instance.
	 * 
	 * @return a {@code List} with all {@code TypeArgument} instances associated with this {@code TypeArguments} instance
	 */
	public List<TypeArgument> getTypeArguments() {
		return new ArrayList<>(this.typeArguments);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TypeArguments} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code TypeArguments} instance in external form
	 */
	public String toExternalForm() {
		return String.format("<%s>", this.typeArguments.stream().map(typeArgument -> typeArgument.toExternalForm()).collect(Collectors.joining(", ")));
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TypeArguments} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code TypeArguments} instance in internal form
	 */
	public String toInternalForm() {
		return String.format("<%s>", this.typeArguments.stream().map(typeArgument -> typeArgument.toInternalForm()).<StringBuilder>collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TypeArguments} instance.
	 * 
	 * @return a {@code String} representation of this {@code TypeArguments} instance
	 */
	@Override
	public String toString() {
		return String.format("TypeArguments: [TypeArguments=%s], [InternalForm=%s]", getTypeArguments(), toInternalForm());
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
	 * <li>traverse its child {@code Node} instances.</li>
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
				for(final TypeArgument typeArgument : this.typeArguments) {
					if(!typeArgument.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code TypeArguments} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code TypeArguments}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code TypeArguments} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code TypeArguments}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof TypeArguments)) {
			return false;
		} else if(!Objects.equals(TypeArguments.class.cast(object).typeArguments, this.typeArguments)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code TypeArguments} instance.
	 * 
	 * @return a hash code for this {@code TypeArguments} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.typeArguments);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code TypeArguments} instance that excludes all package names that are equal to {@code "java.lang"} from {@code typeArguments}.
	 * <p>
	 * If {@code typeArguments} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * TypeArguments.excludePackageName(typeArguments, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param typeArguments a {@code TypeArguments} instance
	 * @return a {@code TypeArguments} instance that excludes all package names that are equal to {@code "java.lang"} from {@code typeArguments}
	 * @throws NullPointerException thrown if, and only if, {@code typeArguments} is {@code null}
	 */
	public static TypeArguments excludePackageName(final TypeArguments typeArguments) {
		return excludePackageName(typeArguments, "java.lang");
	}
	
	/**
	 * Returns a {@code TypeArguments} instance that excludes all package names that are equal to {@code packageName} from {@code typeArguments}.
	 * <p>
	 * If either {@code typeArguments} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param typeArguments a {@code TypeArguments} instance
	 * @param packageName the package name to exclude
	 * @return a {@code TypeArguments} instance that excludes all package names that are equal to {@code packageName} from {@code typeArguments}
	 * @throws NullPointerException thrown if, and only if, either {@code typeArguments} or {@code packageName} are {@code null}
	 */
	public static TypeArguments excludePackageName(final TypeArguments typeArguments, final String packageName) {
		Objects.requireNonNull(typeArguments, "typeArguments == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, typeArguments);
	}
	
	/**
	 * Parses {@code string} into a {@code TypeArguments} instance.
	 * <p>
	 * Returns a {@code TypeArguments} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code TypeArguments} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static TypeArguments parseTypeArguments(final String string) {
		return Parsers.parseTypeArguments(new TextScanner(string));
	}
	
	/**
	 * Returns a {@code TypeArguments} with {@code typeArgument} and all {@link TypeArgument} instances in {@code typeArguments} as its associated {@code TypeArgument} instances.
	 * <p>
	 * If either {@code typeArgument}, {@code typeArguments} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param typeArgument the associated {@code TypeArgument}
	 * @param typeArguments the associated {@code TypeArgument} instances
	 * @return a {@code TypeArguments} with {@code typeArgument} and all {@code TypeArgument} instances in {@code typeArguments} as its associated {@code TypeArgument} instances
	 * @throws NullPointerException thrown if, and only if, either {@code typeArgument}, {@code typeArguments} or any of its elements are {@code null}
	 */
	public static TypeArguments valueOf(final TypeArgument typeArgument, final TypeArgument... typeArguments) {
		return new TypeArguments(ParameterArguments.requireNonNullList(Lists.toList(typeArgument, typeArguments), "typeArguments"));
	}
}