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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.Lists;
import org.macroing.cel4j.util.ParameterArguments;

//TODO: Add Javadocs!
public final class PackageSpecifier implements Node {
	private final List<Identifier> identifiers;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	PackageSpecifier(final List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public List<Identifier> getIdentifiers() {
		return new ArrayList<>(this.identifiers);
	}
	
//	TODO: Add Javadocs!
	public String toExternalForm() {
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(final Identifier identifier : this.identifiers) {
			stringBuilder.append(identifier.toExternalForm());
			stringBuilder.append(".");
		}
		
		return stringBuilder.toString();
	}
	
//	TODO: Add Javadocs!
	public String toInternalForm() {
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(final Identifier identifier : this.identifiers) {
			stringBuilder.append(identifier.toInternalForm());
			stringBuilder.append("/");
		}
		
		return stringBuilder.toString();
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("PackageSpecifier: [Identifiers=%s], [InternalForm=%s]", getIdentifiers(), toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				for(final Identifier identifier : this.identifiers) {
					if(!identifier.accept(nodeHierarchicalVisitor)) {
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
		} else if(!(object instanceof PackageSpecifier)) {
			return false;
		} else if(!Objects.equals(PackageSpecifier.class.cast(object).identifiers, this.identifiers)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.identifiers);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static PackageSpecifier parsePackageSpecifier(final String string) {
		return Parsers.parsePackageSpecifier(TextScanner.newInstance(string));
	}
	
//	TODO: Add Javadocs!
	public static PackageSpecifier valueOf(final Identifier identifier, final Identifier... identifiers) {
		return new PackageSpecifier(ParameterArguments.requireNonNullList(Lists.toList(identifier, identifiers), "identifiers"));
	}
}