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

//TODO: Add Javadocs!
public final class ParameterAnnotation implements Node {
	private final List<Annotation> annotations = new ArrayList<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public ParameterAnnotation() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public List<Annotation> getAnnotations() {
		return new ArrayList<>(this.annotations);
	}
	
//	TODO: Add Javadocs!
	public ParameterAnnotation copy() {
		final ParameterAnnotation parameterAnnotation = new ParameterAnnotation();
		
		for(final Annotation annotation : this.annotations) {
			parameterAnnotation.addAnnotation(annotation.copy());
		}
		
		return parameterAnnotation;
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				for(final Annotation annotation : this.annotations) {
					if(!annotation.accept(nodeHierarchicalVisitor)) {
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
		} else if(!(object instanceof ParameterAnnotation)) {
			return false;
		} else if(!Objects.equals(this.annotations, ParameterAnnotation.class.cast(object).annotations)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	public int getLength() {
		int length = 2;
		
		for(final Annotation annotation : this.annotations) {
			length += annotation.getLength();
		}
		
		return length;
	}
	
//	TODO: Add Javadocs!
	public int getNumAnnotations() {
		return this.annotations.size();
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.annotations);
	}
	
//	TODO: Add Javadocs!
	public void addAnnotation(final Annotation annotation) {
		this.annotations.add(Objects.requireNonNull(annotation, "annotation == null"));
	}
	
//	TODO: Add Javadocs!
	public void removeAnnotation(final Annotation annotation) {
		this.annotations.remove(Objects.requireNonNull(annotation, "annotation == null"));
	}
	
//	TODO: Add Javadocs!
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(getNumAnnotations());
			
			for(final Annotation annotation : this.annotations) {
				annotation.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}