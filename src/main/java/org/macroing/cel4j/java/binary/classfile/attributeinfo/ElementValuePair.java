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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

//TODO: Add Javadocs!
public final class ElementValuePair implements Node {
	private final ElementValue value;
	private final int elementNameIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ElementValuePair(final int elementNameIndex, final ElementValue value) {
		this.elementNameIndex = elementNameIndex;
		this.value = value;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public ElementValue getValue() {
		return this.value;
	}
	
//	TODO: Add Javadocs!
	public ElementValuePair copy() {
		return new ElementValuePair(this.elementNameIndex, this.value.copy());
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				if(!this.value.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
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
		} else if(!(object instanceof ElementValuePair)) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.elementNameIndex), Integer.valueOf(ElementValuePair.class.cast(object).elementNameIndex))) {
			return false;
		} else if(!Objects.equals(this.value, ElementValuePair.class.cast(object).value)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	public int getElementNameIndex() {
		return this.elementNameIndex;
	}
	
//	TODO: Add Javadocs!
	public int getLength() {
		return 2 + this.value.getLength();
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.elementNameIndex), this.value);
	}
	
//	TODO: Add Javadocs!
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(this.elementNameIndex);
			
			this.value.write(dataOutput);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static ElementValuePair newInstance(final int elementNameIndex, final ElementValue value) {
		return new ElementValuePair(ParameterArguments.requireRange(elementNameIndex, 1, Integer.MAX_VALUE, "elementNameIndex"), Objects.requireNonNull(value, "value == null"));
	}
}