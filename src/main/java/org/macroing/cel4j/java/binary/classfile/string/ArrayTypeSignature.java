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
public final class ArrayTypeSignature implements ReferenceTypeSignature {
	private final JavaTypeSignature javaTypeSignature;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ArrayTypeSignature(final JavaTypeSignature javaTypeSignature) {
		this.javaTypeSignature = javaTypeSignature;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public JavaTypeSignature getJavaTypeSignature() {
		return this.javaTypeSignature;
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toExternalForm() {
		return String.format("%s[]", this.javaTypeSignature.toExternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toInternalForm() {
		return String.format("[%s", this.javaTypeSignature.toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("ArrayTypeSignature: [JavaTypeSignature=%s], [InternalForm=%s]", getJavaTypeSignature(), toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				this.javaTypeSignature.accept(nodeHierarchicalVisitor);
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
		} else if(!(object instanceof ArrayTypeSignature)) {
			return false;
		} else if(!Objects.equals(ArrayTypeSignature.class.cast(object).javaTypeSignature, this.javaTypeSignature)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.javaTypeSignature);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static ArrayTypeSignature parseArrayTypeSignature(final String string) {
		return Parsers.parseArrayTypeSignature(TextScanner.newInstance(string));
	}
	
//	TODO: Add Javadocs!
	public static ArrayTypeSignature valueOf(final JavaTypeSignature javaTypeSignature) {
		return new ArrayTypeSignature(Objects.requireNonNull(javaTypeSignature, "javaTypeSignature == null"));
	}
}