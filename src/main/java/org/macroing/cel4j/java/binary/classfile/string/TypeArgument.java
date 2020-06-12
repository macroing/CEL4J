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
public final class TypeArgument implements Node {
//	TODO: Add Javadocs!
	public static final TypeArgument UNKNOWN = new TypeArgument(Optional.empty(), Optional.empty());
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Optional<ReferenceTypeSignature> referenceTypeSignature;
	private final Optional<WildcardIndicator> wildcardIndicator;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private TypeArgument(final Optional<ReferenceTypeSignature> referenceTypeSignature, final Optional<WildcardIndicator> wildcardIndicator) {
		this.referenceTypeSignature = referenceTypeSignature;
		this.wildcardIndicator = wildcardIndicator;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Optional<ReferenceTypeSignature> getReferenceTypeSignature() {
		return this.referenceTypeSignature;
	}
	
//	TODO: Add Javadocs!
	public Optional<WildcardIndicator> getWildcardIndicator() {
		return this.wildcardIndicator;
	}
	
//	TODO: Add Javadocs!
	public String toExternalForm() {
		final StringBuilder stringBuilder = new StringBuilder();
		
		if(!this.referenceTypeSignature.isPresent() && !this.wildcardIndicator.isPresent()) {
			stringBuilder.append("?");
		} else if(this.referenceTypeSignature.isPresent()) {
			if(this.wildcardIndicator.isPresent()) {
				stringBuilder.append(this.wildcardIndicator.get().toExternalForm());
				stringBuilder.append(" ");
			}
			
			stringBuilder.append(this.referenceTypeSignature.get().toExternalForm());
		}
		
		return stringBuilder.toString();
	}
	
//	TODO: Add Javadocs!
	public String toInternalForm() {
		final StringBuilder stringBuilder = new StringBuilder();
		
		if(!this.referenceTypeSignature.isPresent() && !this.wildcardIndicator.isPresent()) {
			stringBuilder.append(Constants.TYPE_ARGUMENT_UNKNOWN);
		} else if(this.referenceTypeSignature.isPresent()) {
			stringBuilder.append(this.referenceTypeSignature.get().toInternalForm());
			
			if(this.wildcardIndicator.isPresent()) {
				stringBuilder.append(this.wildcardIndicator.get().toInternalForm());
			}
		}
		
		return stringBuilder.toString();
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("TypeArgument: [WildcardIndicator=%s], [ReferenceTypeSignature=%s], [InternalForm=%s]", getWildcardIndicator(), getReferenceTypeSignature(), toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				if(this.referenceTypeSignature.isPresent()) {
					final ReferenceTypeSignature referenceTypeSignature = this.referenceTypeSignature.get();
					
					if(!referenceTypeSignature.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				this.wildcardIndicator.ifPresent(wildcardIndicator -> wildcardIndicator.accept(nodeHierarchicalVisitor));
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
		} else if(!(object instanceof TypeArgument)) {
			return false;
		} else if(!Objects.equals(TypeArgument.class.cast(object).referenceTypeSignature, this.referenceTypeSignature)) {
			return false;
		} else if(!Objects.equals(TypeArgument.class.cast(object).wildcardIndicator, this.wildcardIndicator)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.referenceTypeSignature, this.wildcardIndicator);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static TypeArgument parseTypeArgument(final String string) {
		return Parsers.parseTypeArgument(new TextScanner(string));
	}
	
//	TODO: Add Javadocs!
	public static TypeArgument valueOf(final ReferenceTypeSignature referenceTypeSignature) {
		return new TypeArgument(Optional.of(referenceTypeSignature), Optional.empty());
	}
	
//	TODO: Add Javadocs!
	public static TypeArgument valueOf(final ReferenceTypeSignature referenceTypeSignature, final WildcardIndicator wildcardIndicator) {
		return new TypeArgument(Optional.of(referenceTypeSignature), Optional.of(wildcardIndicator));
	}
}