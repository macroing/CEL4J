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
	
//	TODO: Add Javadocs!
	public ClassBound getClassBound() {
		return this.classBound;
	}
	
//	TODO: Add Javadocs!
	public Identifier getIdentifier() {
		return this.identifier;
	}
	
//	TODO: Add Javadocs!
	public List<InterfaceBound> getInterfaceBounds() {
		return new ArrayList<>(this.interfaceBounds);
	}
	
//	TODO: Add Javadocs!
	public String toExternalForm() {
		return String.format("%s extends %s%s", this.identifier.toExternalForm(), this.classBound.toExternalForm(), doGenerateInterfaceBoundsStringInExternalForm());
	}
	
//	TODO: Add Javadocs!
	public String toInternalForm() {
		return String.format("%s%s%s", this.identifier.toInternalForm(), this.classBound.toInternalForm(), this.interfaceBounds.stream().map(interfaceBound -> interfaceBound.toInternalForm()).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("TypeParameter: [Identifier=%s], [ClassBound=%s], [InterfaceBounds=%s], [InternalForm=%s]", getIdentifier(), getClassBound(), getInterfaceBounds(), toInternalForm());
	}
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.identifier, this.classBound, this.interfaceBounds);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static TypeParameter parseTypeParameter(final String string) {
		return Parsers.parseTypeParameter(new TextScanner(string));
	}
	
//	TODO: Add Javadocs!
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