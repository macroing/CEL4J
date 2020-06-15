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
import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

//TODO: Add Javadocs!
public final class AnnotationValueUnion implements Union {
	private final Annotation annotationValue;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public AnnotationValueUnion(final Annotation annotationValue) {
		this.annotationValue = Objects.requireNonNull(annotationValue, "annotationValue == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Annotation getAnnotationValue() {
		return this.annotationValue;
	}
	
//	TODO: Add Javadocs!
	@Override
	public AnnotationValueUnion copy() {
		return new AnnotationValueUnion(this.annotationValue.copy());
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				if(!this.annotationValue.accept(nodeHierarchicalVisitor)) {
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
		} else if(!(object instanceof AnnotationValueUnion)) {
			return false;
		} else if(!Objects.equals(this.annotationValue, AnnotationValueUnion.class.cast(object).annotationValue)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getLength() {
		return this.annotationValue.getLength();
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.annotationValue);
	}
	
//	TODO: Add Javadocs!
	@Override
	public void write(final DataOutput dataOutput) {
		this.annotationValue.write(dataOutput);
	}
}