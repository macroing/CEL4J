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
import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

//TODO: Add Javadocs!
public final class ArrayType implements FieldType {
	private final ComponentType componentType;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ArrayType(final ComponentType componentType) {
		this.componentType = componentType;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public ComponentType getComponentType() {
		return this.componentType;
	}
	
//	TODO: Add Javadocs!
	@Override
	public String getTerm() {
		return "[";
	}
	
//	TODO: Add Javadocs!
	@Override
	public String getType() {
		return Constants.REFERENCE_TYPE;
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toExternalForm() {
		return String.format("%s[]", getComponentType().toExternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toInternalForm() {
		return String.format("%s%s", getTerm(), getComponentType().toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("ArrayType: [Term=%s], [Type=%s], [ComponentType=%s], [ExternalForm=%s], [InternalForm=%s]", getTerm(), getType(), getComponentType(), toExternalForm(), toInternalForm());
	}
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
	public int getDimensions() {
		return 1 + (this.componentType instanceof ArrayType ? ArrayType.class.cast(this.componentType).getDimensions() : 0);
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.componentType);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static ArrayType parseArrayType(final String string) {
		return Parsers.parseArrayType(TextScanner.newInstance(string));
	}
	
//	TODO: Add Javadocs!
	public static ArrayType valueOf(final ComponentType componentType) {
		return new ArrayType(Objects.requireNonNull(componentType, "componentType == null"));
	}
}