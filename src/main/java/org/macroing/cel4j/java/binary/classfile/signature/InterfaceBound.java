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
import org.macroing.cel4j.scanner.TextScanner;

//TODO: Add Javadocs!
public final class InterfaceBound implements Node {
	private final ReferenceTypeSignature referenceTypeSignature;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private InterfaceBound(final ReferenceTypeSignature referenceTypeSignature) {
		this.referenceTypeSignature = referenceTypeSignature;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public ReferenceTypeSignature getReferenceTypeSignature() {
		return this.referenceTypeSignature;
	}
	
//	TODO: Add Javadocs!
	public String toExternalForm() {
		return this.referenceTypeSignature.toExternalForm();
	}
	
//	TODO: Add Javadocs!
	public String toInternalForm() {
		return ":" + this.referenceTypeSignature.toInternalForm();
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("InterfaceBound: [ReferenceTypeSignature=%s], [InternalForm=%s]", getReferenceTypeSignature(), toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				this.referenceTypeSignature.accept(nodeHierarchicalVisitor);
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
		} else if(!(object instanceof InterfaceBound)) {
			return false;
		} else if(!Objects.equals(InterfaceBound.class.cast(object).referenceTypeSignature, this.referenceTypeSignature)) {
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
	public static InterfaceBound parseInterfaceBound(final String string) {
		return Parsers.parseInterfaceBound(new TextScanner(string));
	}
	
//	TODO: Add Javadocs!
	public static InterfaceBound valueOf(final ReferenceTypeSignature referenceTypeSignature) {
		return new InterfaceBound(Objects.requireNonNull(referenceTypeSignature, "referenceTypeSignature == null"));
	}
}