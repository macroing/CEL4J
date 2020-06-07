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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

//TODO: Add Javadocs!
public final class ArrayValueUnion implements Union {
	private final List<ElementValue> values;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public ArrayValueUnion() {
		this.values = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public ArrayValueUnion copy() {
		final ArrayValueUnion arrayValueUnion = new ArrayValueUnion();
		
		this.values.forEach(elementValue -> arrayValueUnion.addValue(elementValue.copy()));
		
		return arrayValueUnion;
	}
	
//	TODO: Add Javadocs!
	public List<ElementValue> getValues() {
		return new ArrayList<>(this.values);
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				for(final ElementValue value : this.values) {
					if(!value.accept(nodeHierarchicalVisitor)) {
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
		} else if(!(object instanceof ArrayValueUnion)) {
			return false;
		} else if(!Objects.equals(this.values, ArrayValueUnion.class.cast(object).values)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getLength() {
		int length = 2;
		
		for(final ElementValue value : this.values) {
			length += value.getLength();
		}
		
		return length;
	}
	
//	TODO: Add Javadocs!
	public int getNumValues() {
		return this.values.size();
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.values);
	}
	
//	TODO: Add Javadocs!
	public void addValue(final ElementValue value) {
		this.values.add(Objects.requireNonNull(value, "value == null"));
	}
	
//	TODO: Add Javadocs!
	public void removeValue(final ElementValue value) {
		this.values.remove(Objects.requireNonNull(value, "value == null"));
	}
	
//	TODO: Add Javadocs!
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(getNumValues());
			
			this.values.forEach(value -> value.write(dataOutput));
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}