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
import java.util.Optional;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;

//TODO: Add Javadocs!
public final class ClassBound implements Node {
//	TODO: Add Javadocs!
	public static final ClassBound EMPTY = new ClassBound(Optional.empty());
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Optional<ReferenceTypeSignature> referenceTypeSignature;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ClassBound(final Optional<ReferenceTypeSignature> referenceTypeSignature) {
		this.referenceTypeSignature = referenceTypeSignature;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Optional<ReferenceTypeSignature> getReferenceTypeSignature() {
		return this.referenceTypeSignature;
	}
	
//	TODO: Add Javadocs!
	public String toExternalForm() {
		return this.referenceTypeSignature.isPresent() ? this.referenceTypeSignature.get().toExternalForm() : "";
	}
	
//	TODO: Add Javadocs!
	public String toInternalForm() {
		return this.referenceTypeSignature.isPresent() ? ":" + this.referenceTypeSignature.get().toInternalForm() : "";
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("ClassBound: [ReferenceTypeSignature=%s]", getReferenceTypeSignature());
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				this.referenceTypeSignature.ifPresent(referenceTypeSignature -> referenceTypeSignature.accept(nodeHierarchicalVisitor));
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
		} else if(!(object instanceof ClassBound)) {
			return false;
		} else if(!Objects.equals(ClassBound.class.cast(object).referenceTypeSignature, this.referenceTypeSignature)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.referenceTypeSignature);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static ClassBound parseClassBound(final String string) {
		return Parsers.parseClassBound(new TextScanner(string));
	}
	
//	TODO: Add Javadocs!
	public static ClassBound valueOf(final ReferenceTypeSignature referenceTypeSignature) {
		return new ClassBound(Optional.of(referenceTypeSignature));
	}
}