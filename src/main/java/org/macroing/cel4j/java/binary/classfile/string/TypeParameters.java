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
package org.macroing.cel4j.java.binary.classfile.string;

import java.lang.reflect.Field;//TODO: Add Javadocs!
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

//TODO: Add Javadocs!
public final class TypeParameters implements Node {
	private final List<TypeParameter> typeParameters;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	TypeParameters(final List<TypeParameter> typeParameters) {
		this.typeParameters = typeParameters;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public List<String> collectNames() {
		return collectNames((packageName, simpleName) -> packageName + simpleName);
	}
	
//	TODO: Add Javadocs!
	public List<String> collectNames(final BiFunction<String, String, String> nameUpdater) {
		final TypeParametersNameCollectorNodeHierarchicalVisitor typeParametersNameCollectorNodeHierarchicalVisitor = new TypeParametersNameCollectorNodeHierarchicalVisitor(Objects.requireNonNull(nameUpdater, "nameUpdater == null"));
		
		accept(typeParametersNameCollectorNodeHierarchicalVisitor);
		
		return typeParametersNameCollectorNodeHierarchicalVisitor.getNames();
	}
	
//	TODO: Add Javadocs!
	public List<TypeParameter> getTypeParameters() {
		return new ArrayList<>(this.typeParameters);
	}
	
//	TODO: Add Javadocs!
	public String toExternalForm() {
		return String.format("<%s>", this.typeParameters.stream().map(typeParameter -> typeParameter.toExternalForm()).collect(Collectors.joining(", ")));
	}
	
//	TODO: Add Javadocs!
	public String toExternalForm(final BiFunction<String, String, String> nameUpdater) {
		final NodeHierarchicalVisitor nodeHierarchicalVisitor = new TypeParametersNameUpdaterNodeHierarchicalVisitor(Objects.requireNonNull(nameUpdater, "nameUpdater == null"));
		
		accept(nodeHierarchicalVisitor);
		
		return nodeHierarchicalVisitor.toString();
	}
	
//	TODO: Add Javadocs!
	public String toInternalForm() {
		return String.format("<%s>", this.typeParameters.stream().map(typeParameter -> typeParameter.toInternalForm()).<StringBuilder>collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("TypeParameters: [TypeParameters=%s], [InternalForm=%s]", getTypeParameters(), toInternalForm());
	}
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.typeParameters);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static TypeParameters parseTypeParameters(final String string) {
		return Parsers.parseTypeParameters(new TextScanner(string));
	}
	
//	TODO: Add Javadocs!
	public static TypeParameters valueOf(final TypeParameter typeParameter, final TypeParameter... typeParameters) {
		return new TypeParameters(ParameterArguments.requireNonNullList(Lists.toList(typeParameter, typeParameters), "typeParameters"));
	}
}