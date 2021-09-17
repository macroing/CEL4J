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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code TypeParameter} denotes a TypeParameter as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class TypeParameter implements Node {
	private final ClassBound classBound;
	private final Identifier identifier;
	private final List<InterfaceBound> interfaceBounds;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	TypeParameter(final Identifier identifier, final ClassBound classBound, final List<InterfaceBound> interfaceBounds) {
		this.identifier = identifier;
		this.classBound = classBound;
		this.interfaceBounds = interfaceBounds;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link ClassBound} associated with this {@code TypeParameter} instance.
	 * 
	 * @return the {@code ClassBound} associated with this {@code TypeParameter} instance
	 */
	public ClassBound getClassBound() {
		return this.classBound;
	}
	
	/**
	 * Returns the {@link Identifier} associated with this {@code TypeParameter} instance.
	 * 
	 * @return the {@code Identifier} associated with this {@code TypeParameter} instance
	 */
	public Identifier getIdentifier() {
		return this.identifier;
	}
	
	/**
	 * Returns a {@code List} with all {@link InterfaceBound} instances associated with this {@code TypeParameter} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code TypeParameter} instance.
	 * 
	 * @return a {@code List} with all {@code InterfaceBound} instances associated with this {@code TypeParameter} instance
	 */
	public List<InterfaceBound> getInterfaceBounds() {
		return new ArrayList<>(this.interfaceBounds);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TypeParameter} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code TypeParameter} instance in external form
	 */
	public String toExternalForm() {
		return String.format("%s extends %s%s", this.identifier.toExternalForm(), this.classBound.toExternalForm(), doGenerateInterfaceBoundsStringInExternalForm());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TypeParameter} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code TypeParameter} instance in internal form
	 */
	public String toInternalForm() {
		return String.format("%s%s%s", this.identifier.toInternalForm(), this.classBound.toInternalForm(), this.interfaceBounds.stream().map(interfaceBound -> interfaceBound.toInternalForm()).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TypeParameter} instance.
	 * 
	 * @return a {@code String} representation of this {@code TypeParameter} instance
	 */
	@Override
	public String toString() {
		return String.format("TypeParameter: [Identifier=%s], [ClassBound=%s], [InterfaceBounds=%s], [InternalForm=%s]", getIdentifier(), getClassBound(), getInterfaceBounds(), toInternalForm());
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
				if(!this.identifier.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
				
				if(!this.classBound.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
				
				for(final InterfaceBound interfaceBound : this.interfaceBounds) {
					if(!interfaceBound.accept(nodeHierarchicalVisitor)) {
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
	 * Compares {@code object} to this {@code TypeParameter} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code TypeParameter}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code TypeParameter} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code TypeParameter}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof TypeParameter)) {
			return false;
		} else if(!Objects.equals(TypeParameter.class.cast(object).identifier, this.identifier)) {
			return false;
		} else if(!Objects.equals(TypeParameter.class.cast(object).classBound, this.classBound)) {
			return false;
		} else if(!Objects.equals(TypeParameter.class.cast(object).interfaceBounds, this.interfaceBounds)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code TypeParameter} instance.
	 * 
	 * @return a hash code for this {@code TypeParameter} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.identifier, this.classBound, this.interfaceBounds);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code TypeParameter} instance that excludes all package names that are equal to {@code "java.lang"} from {@code typeParameter}.
	 * <p>
	 * If {@code typeParameter} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * TypeParameter.excludePackageName(typeParameter, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param typeParameter a {@code TypeParameter} instance
	 * @return a {@code TypeParameter} instance that excludes all package names that are equal to {@code "java.lang"} from {@code typeParameter}
	 * @throws NullPointerException thrown if, and only if, {@code typeParameter} is {@code null}
	 */
	public static TypeParameter excludePackageName(final TypeParameter typeParameter) {
		return excludePackageName(typeParameter, "java.lang");
	}
	
	/**
	 * Returns a {@code TypeParameter} instance that excludes all package names that are equal to {@code packageName} from {@code typeParameter}.
	 * <p>
	 * If either {@code typeParameter} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param typeParameter a {@code TypeParameter} instance
	 * @param packageName the package name to exclude
	 * @return a {@code TypeParameter} instance that excludes all package names that are equal to {@code packageName} from {@code typeParameter}
	 * @throws NullPointerException thrown if, and only if, either {@code typeParameter} or {@code packageName} are {@code null}
	 */
	public static TypeParameter excludePackageName(final TypeParameter typeParameter, final String packageName) {
		Objects.requireNonNull(typeParameter, "typeParameter == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, typeParameter);
	}
	
	/**
	 * Parses {@code string} into a {@code TypeParameter} instance.
	 * <p>
	 * Returns a {@code TypeParameter} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code TypeParameter} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static TypeParameter parseTypeParameter(final String string) {
		return Parsers.parseTypeParameter(new TextScanner(string));
	}
	
	/**
	 * Returns a {@code TypeParameter} with {@code identifier}, {@code classBound} and all {@link InterfaceBound} instances in {@code interfaceBounds} as its associated {@link Identifier}, {@link ClassBound} and {@code InterfaceBound} instances, respectively.
	 * <p>
	 * If either {@code identifier}, {@code classBound}, {@code interfaceBounds} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param identifier the associated {@code Identifier}
	 * @param classBound the associated {@code ClassBound}
	 * @param interfaceBounds the associated {@code InterfaceBound} instances
	 * @return a {@code TypeParameter} with {@code identifier}, {@code classBound} and all {@code InterfaceBound} instances in {@code interfaceBounds} as its associated {@code Identifier}, {@code ClassBound} and {@code InterfaceBound} instances, respectively
	 * @throws NullPointerException thrown if, and only if, either {@code identifier}, {@code classBound}, {@code interfaceBounds} or any of its elements are {@code null}
	 */
	public static TypeParameter valueOf(final Identifier identifier, final ClassBound classBound, final InterfaceBound... interfaceBounds) {
		return new TypeParameter(Objects.requireNonNull(identifier, "identifier == null"), Objects.requireNonNull(classBound, "classBound == null"), ParameterArguments.requireNonNullList(Arrays.asList(interfaceBounds.clone()), "interfaceBounds"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String doGenerateInterfaceBoundsStringInExternalForm() {
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(final InterfaceBound interfaceBound : this.interfaceBounds) {
			stringBuilder.append(" & ");
			stringBuilder.append(interfaceBound.toExternalForm());
		}
		
		return stringBuilder.toString();
	}
}