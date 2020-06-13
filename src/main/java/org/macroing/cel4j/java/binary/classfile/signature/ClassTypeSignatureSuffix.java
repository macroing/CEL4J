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
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

//TODO: Add Javadocs!
public final class ClassTypeSignatureSuffix implements Node {
	private final SimpleClassTypeSignature simpleClassTypeSignature;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ClassTypeSignatureSuffix(final SimpleClassTypeSignature simpleClassTypeSignature) {
		this.simpleClassTypeSignature = simpleClassTypeSignature;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public SimpleClassTypeSignature getSimpleClassTypeSignature() {
		return this.simpleClassTypeSignature;
	}
	
//	TODO: Add Javadocs!
	public String toExternalForm() {
		return String.format(".%s", this.simpleClassTypeSignature.toExternalForm());
	}
	
//	TODO: Add Javadocs!
	public String toInternalForm() {
		return String.format(".%s", this.simpleClassTypeSignature.toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("ClassTypeSignatureSuffix: [SimpleClassTypeSignature=%s], [InternalForm=%s]", getSimpleClassTypeSignature(), toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				this.simpleClassTypeSignature.accept(nodeHierarchicalVisitor);
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
		} else if(!(object instanceof ClassTypeSignatureSuffix)) {
			return false;
		} else if(!Objects.equals(ClassTypeSignatureSuffix.class.cast(object).simpleClassTypeSignature, this.simpleClassTypeSignature)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.simpleClassTypeSignature);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static ClassTypeSignatureSuffix parseClassTypeSignatureSuffix(final String string) {
		if(string.charAt(0) == '.') {
			return valueOf(SimpleClassTypeSignature.parseSimpleClassTypeSignature(string.substring(1)));
		}
		
		throw new IllegalArgumentException(String.format("Illegal ClassTypeSignatureSuffix: %s", string));
	}
	
//	TODO: Add Javadocs!
	public static ClassTypeSignatureSuffix valueOf(final SimpleClassTypeSignature simpleClassTypeSignature) {
		return new ClassTypeSignatureSuffix(Objects.requireNonNull(simpleClassTypeSignature, "simpleClassTypeSignature == null"));
	}
}