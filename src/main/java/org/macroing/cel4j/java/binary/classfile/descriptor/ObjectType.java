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
package org.macroing.cel4j.java.binary.classfile.descriptor;

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;

//TODO: Add Javadocs!
public final class ObjectType implements FieldType {
	private final ClassName className;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	ObjectType(final ClassName className) {
		this.className = className;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public ClassName getClassName() {
		return this.className;
	}
	
//	TODO: Add Javadocs!
	@Override
	public String getTerm() {
		return String.format("L%s;", this.className.toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public String getType() {
		return Constants.REFERENCE_TYPE;
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toExternalForm() {
		return this.className.toExternalForm();
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toInternalForm() {
		return getTerm();
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("ObjectType: [Term=%s], [Type=%s], [ExternalForm=%s], [InternalForm=%s]", getTerm(), getType(), toExternalForm(), toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				this.className.accept(nodeHierarchicalVisitor);
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
		} else if(!(object instanceof ObjectType)) {
			return false;
		} else if(!Objects.equals(ObjectType.class.cast(object).className, this.className)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.className);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static ObjectType parseObjectType(final String string) {
		return Parsers.parseObjectType(new TextScanner(string));
	}
	
//	TODO: Add Javadocs!
	public static ObjectType valueOf(final ClassName className) {
		return new ObjectType(Objects.requireNonNull(className, "className == null"));
	}
}