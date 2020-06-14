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

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;
import org.macroing.cel4j.util.Lists;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code PackageSpecifier} denotes a PackageSpecifier as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PackageSpecifier implements Node {
	private final List<Identifier> identifiers;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	PackageSpecifier(final List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public List<Identifier> getIdentifiers() {
		return new ArrayList<>(this.identifiers);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code PackageSpecifier} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code PackageSpecifier} instance in external form
	 */
	public String toExternalForm() {
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(final Identifier identifier : this.identifiers) {
			stringBuilder.append(identifier.toExternalForm());
			stringBuilder.append(".");
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code PackageSpecifier} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code PackageSpecifier} instance in internal form
	 */
	public String toInternalForm() {
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(final Identifier identifier : this.identifiers) {
			stringBuilder.append(identifier.toInternalForm());
			stringBuilder.append("/");
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code PackageSpecifier} instance.
	 * 
	 * @return a {@code String} representation of this {@code PackageSpecifier} instance
	 */
	@Override
	public String toString() {
		return String.format("PackageSpecifier: [Identifiers=%s], [InternalForm=%s]", getIdentifiers(), toInternalForm());
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
				for(final Identifier identifier : this.identifiers) {
					if(!identifier.accept(nodeHierarchicalVisitor)) {
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
	 * Compares {@code object} to this {@code PackageSpecifier} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code PackageSpecifier}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code PackageSpecifier} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code PackageSpecifier}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof PackageSpecifier)) {
			return false;
		} else if(!Objects.equals(PackageSpecifier.class.cast(object).identifiers, this.identifiers)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code PackageSpecifier} instance.
	 * 
	 * @return a hash code for this {@code PackageSpecifier} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.identifiers);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static PackageSpecifier parsePackageSpecifier(final String string) {
		return Parsers.parsePackageSpecifier(new TextScanner(string));
	}
	
//	TODO: Add Javadocs!
	public static PackageSpecifier valueOf(final Identifier identifier, final Identifier... identifiers) {
		return new PackageSpecifier(ParameterArguments.requireNonNullList(Lists.toList(identifier, identifiers), "identifiers"));
	}
}