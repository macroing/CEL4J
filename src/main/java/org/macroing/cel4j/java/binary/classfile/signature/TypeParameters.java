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
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;
import org.macroing.cel4j.util.Lists;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code TypeParameters} denotes a TypeParameters as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class TypeParameters implements Node {
	private final List<TypeParameter> typeParameters;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	TypeParameters(final List<TypeParameter> typeParameters) {
		this.typeParameters = typeParameters;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} of type {@code String} that contains all class and interface names in this {@code TypeParameters} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * typeParameters.collectNames((packageSpecifier, identifier) -> packageSpecifier + identifier);
	 * }
	 * </pre>
	 * 
	 * @return a {@code List} of type {@code String} that contains all class and interface names in this {@code TypeParameters} instance
	 */
	public List<String> collectNames() {
		return collectNames((packageSpecifier, identifier) -> packageSpecifier + identifier);
	}
	
	/**
	 * Returns a {@code List} of type {@code String} that contains all class and interface names in this {@code TypeParameters} instance.
	 * <p>
	 * If {@code nameUpdater} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code BiFunction} {@code nameUpdater} has two parameter arguments of type {@code String}. They represent the external form of both the optional {@link PackageSpecifier} and the {@link Identifier}, respectively. If no
	 * {@code PackageSpecifier} is present, an empty {@code String} will be passed as parameter argument. The current name is returned by concatenating the two. It can look like this
	 * {@code (packageSpecifier, identifier) -> packageSpecifier + identifier;}. If {@code null} is returned, the name will not be added to the {@code List}.
	 * 
	 * @param nameUpdater a {@code BiFunction} that is used to update the names
	 * @return a {@code List} of type {@code String} that contains all class and interface names in this {@code TypeParameters} instance
	 * @throws NullPointerException thrown if, and only if, {@code nameUpdater} is {@code null}
	 */
	public List<String> collectNames(final BiFunction<String, String, String> nameUpdater) {
		final TypeParametersNameCollectorNodeHierarchicalVisitor typeParametersNameCollectorNodeHierarchicalVisitor = new TypeParametersNameCollectorNodeHierarchicalVisitor(Objects.requireNonNull(nameUpdater, "nameUpdater == null"));
		
		accept(typeParametersNameCollectorNodeHierarchicalVisitor);
		
		return typeParametersNameCollectorNodeHierarchicalVisitor.getNames();
	}
	
	/**
	 * Returns a {@code List} with all {@link TypeParameter} instances associated with this {@code TypeParameters} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code TypeParameters} instance.
	 * 
	 * @return a {@code List} with all {@code TypeParameter} instances associated with this {@code TypeParameters} instance
	 */
	public List<TypeParameter> getTypeParameters() {
		return new ArrayList<>(this.typeParameters);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TypeParameters} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code TypeParameters} instance in external form
	 */
	public String toExternalForm() {
		return String.format("<%s>", this.typeParameters.stream().map(typeParameter -> typeParameter.toExternalForm()).collect(Collectors.joining(", ")));
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TypeParameters} instance in external form, with names updated by {@code nameUpdater}.
	 * <p>
	 * If {@code nameUpdater} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code BiFunction} {@code nameUpdater} has two parameter arguments of type {@code String}. They represent the external form of both the optional {@link PackageSpecifier} and the {@link Identifier}, respectively. If no
	 * {@code PackageSpecifier} is present, an empty {@code String} will be passed as parameter argument. The current name is returned by concatenating the two. It can look like this
	 * {@code (packageSpecifier, identifier) -> packageSpecifier + identifier;}. If {@code null} is returned, the {@link TypeParameter} will be removed.
	 * 
	 * @param nameUpdater a {@code BiFunction} that is used to update the names
	 * @return a {@code String} representation of this {@code TypeParameters} instance in external form, with names updated by {@code nameUpdater}
	 * @throws NullPointerException thrown if, and only if, {@code nameUpdater} is {@code null}
	 */
	public String toExternalForm(final BiFunction<String, String, String> nameUpdater) {
		final NodeHierarchicalVisitor nodeHierarchicalVisitor = new TypeParametersNameUpdaterNodeHierarchicalVisitor(Objects.requireNonNull(nameUpdater, "nameUpdater == null"));
		
		accept(nodeHierarchicalVisitor);
		
		return nodeHierarchicalVisitor.toString();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TypeParameters} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code TypeParameters} instance in internal form
	 */
	public String toInternalForm() {
		return String.format("<%s>", this.typeParameters.stream().map(typeParameter -> typeParameter.toInternalForm()).<StringBuilder>collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TypeParameters} instance.
	 * 
	 * @return a {@code String} representation of this {@code TypeParameters} instance
	 */
	@Override
	public String toString() {
		return String.format("TypeParameters: [TypeParameters=%s], [InternalForm=%s]", getTypeParameters(), toInternalForm());
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
				for(final TypeParameter typeParameter : this.typeParameters) {
					if(!typeParameter.accept(nodeHierarchicalVisitor)) {
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
	 * Compares {@code object} to this {@code TypeParameters} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code TypeParameters}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code TypeParameters} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code TypeParameters}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof TypeParameters)) {
			return false;
		} else if(!Objects.equals(TypeParameters.class.cast(object).typeParameters, this.typeParameters)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code TypeParameters} instance.
	 * 
	 * @return a hash code for this {@code TypeParameters} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.typeParameters);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Parses {@code string} into a {@code TypeParameters} instance.
	 * <p>
	 * Returns a {@code TypeParameters} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code TypeParameters} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static TypeParameters parseTypeParameters(final String string) {
		return Parsers.parseTypeParameters(new TextScanner(string));
	}
	
	/**
	 * Returns a {@code TypeParameters} with {@code typeParameter} and all {@link TypeParameter} instances in {@code typeParameters} as its associated {@code TypeParameter} instances.
	 * <p>
	 * If either {@code typeParameter}, {@code typeParameters} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param typeParameter the associated {@code TypeParameter}
	 * @param typeParameters the associated {@code TypeParameter} instances
	 * @return a {@code TypeParameters} with {@code typeParameter} and all {@code TypeParameter} instances in {@code typeParameters} as its associated {@code TypeParameter} instances
	 * @throws NullPointerException thrown if, and only if, either {@code typeParameter}, {@code typeParameters} or any of its elements are {@code null}
	 */
	public static TypeParameters valueOf(final TypeParameter typeParameter, final TypeParameter... typeParameters) {
		return new TypeParameters(ParameterArguments.requireNonNullList(Lists.toList(typeParameter, typeParameters), "typeParameters"));
	}
}