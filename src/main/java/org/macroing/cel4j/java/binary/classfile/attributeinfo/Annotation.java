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

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

//TODO: Add Javadocs!
public final class Annotation implements Node {
	private final List<ElementValuePair> elementValuePairs;
	private int typeIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Annotation(final int typeIndex) {
		this.typeIndex = typeIndex;
		this.elementValuePairs = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Annotation copy() {
		final Annotation annotation = new Annotation(this.typeIndex);
		
		this.elementValuePairs.forEach(elementValuePair -> annotation.addElementValuePair(elementValuePair.copy()));
		
		return annotation;
	}
	
//	TODO: Add Javadocs!
	public List<ElementValuePair> getElementValuePairs() {
		return new ArrayList<>(this.elementValuePairs);
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				for(final ElementValuePair elementValuePair : this.elementValuePairs) {
					if(!elementValuePair.accept(nodeHierarchicalVisitor)) {
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
		} else if(!(object instanceof Annotation)) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.typeIndex), Integer.valueOf(Annotation.class.cast(object).typeIndex))) {
			return false;
		} else if(!Objects.equals(this.elementValuePairs, Annotation.class.cast(object).elementValuePairs)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	public int getLength() {
		int length = 4;
		
		for(final ElementValuePair elementValuePair : this.elementValuePairs) {
			length += elementValuePair.getLength();
		}
		
		return length;
	}
	
//	TODO: Add Javadocs!
	public int getNumElementValuePairs() {
		return this.elementValuePairs.size();
	}
	
//	TODO: Add Javadocs!
	public int getTypeIndex() {
		return this.typeIndex;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.typeIndex), this.elementValuePairs);
	}
	
//	TODO: Add Javadocs!
	public void addElementValuePair(final ElementValuePair elementValuePair) {
		this.elementValuePairs.add(Objects.requireNonNull(elementValuePair, "elementValuePair == null"));
	}
	
//	TODO: Add Javadocs!
	public void removeElementValuePair(final ElementValuePair elementValuePair) {
		this.elementValuePairs.remove(Objects.requireNonNull(elementValuePair, "elementValuePair == null"));
	}
	
//	TODO: Add Javadocs!
	public void setTypeIndex(final int typeIndex) {
		this.typeIndex = ParameterArguments.requireRange(typeIndex, 1, Integer.MAX_VALUE, "typeIndex");
	}
	
//	TODO: Add Javadocs!
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(this.typeIndex);
			dataOutput.writeShort(getNumElementValuePairs());
			
			this.elementValuePairs.forEach(elementValuePair -> elementValuePair.write(dataOutput));
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static Annotation newInstance(final int typeIndex) {
		return new Annotation(ParameterArguments.requireRange(typeIndex, 1, Integer.MAX_VALUE));
	}
}