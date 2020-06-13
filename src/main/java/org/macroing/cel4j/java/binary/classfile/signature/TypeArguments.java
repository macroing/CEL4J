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
	
//	TODO: Add Javadocs!
	public List<TypeArgument> getTypeArguments() {
		return new ArrayList<>(this.typeArguments);
	}
	
//	TODO: Add Javadocs!
	public String toExternalForm() {
		return String.format("<%s>", this.typeArguments.stream().map(typeArgument -> typeArgument.toExternalForm()).collect(Collectors.joining(", ")));
	}
	
//	TODO: Add Javadocs!
	public String toInternalForm() {
		return String.format("<%s>", this.typeArguments.stream().map(typeArgument -> typeArgument.toInternalForm()).<StringBuilder>collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("TypeArguments: [TypeArguments=%s], [InternalForm=%s]", getTypeArguments(), toInternalForm());
	}
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.typeArguments);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static TypeArguments parseTypeArguments(final String string) {
		return Parsers.parseTypeArguments(new TextScanner(string));
	}
	
//	TODO: Add Javadocs!
	public static TypeArguments valueOf(final TypeArgument typeArgument, final TypeArgument... typeArguments) {
		return new TypeArguments(ParameterArguments.requireNonNullList(Lists.toList(typeArgument, typeArguments), "typeArguments"));
	}
}