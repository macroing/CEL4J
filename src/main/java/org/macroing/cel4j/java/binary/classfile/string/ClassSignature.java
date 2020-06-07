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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.SignatureAttribute;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

//TODO: Add Javadocs!
public final class ClassSignature implements Signature {
	private final List<SuperInterfaceSignature> superInterfaceSignatures;
	private final Optional<TypeParameters> typeParameters;
	private final SuperClassSignature superClassSignature;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	ClassSignature(final SuperClassSignature superClassSignature, final Optional<TypeParameters> typeParameters, final List<SuperInterfaceSignature> superInterfaceSignatures) {
		this.superClassSignature = superClassSignature;
		this.typeParameters = typeParameters;
		this.superInterfaceSignatures = superInterfaceSignatures;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public List<SuperInterfaceSignature> getSuperInterfaceSignatures() {
		return this.superInterfaceSignatures;
	}
	
//	TODO: Add Javadocs!
	public Optional<TypeParameters> getTypeParameters() {
		return this.typeParameters;
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toExternalForm() {
		return String.format("%s%s%s", this.typeParameters.isPresent() ? this.typeParameters.get().toExternalForm() + " " : "", "extends " + this.superClassSignature.toExternalForm(), " implements " + this.superInterfaceSignatures.stream().map(superInterfaceSignature -> superInterfaceSignature.toExternalForm()).collect(Collectors.joining(",")));
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toInternalForm() {
		return String.format("%s%s%s", this.typeParameters.isPresent() ? this.typeParameters.get().toInternalForm() : "", this.superClassSignature.toInternalForm(), this.superInterfaceSignatures.stream().map(superInterfaceSignature -> superInterfaceSignature.toInternalForm()).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
	}
	
//	TODO: Add Javadocs!
	public SuperClassSignature getSuperClassSignature() {
		return this.superClassSignature;
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				if(this.typeParameters.isPresent()) {
					final TypeParameters typeParameters = this.typeParameters.get();
					
					if(!typeParameters.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
					
					if(!this.superClassSignature.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
					
					for(final SuperInterfaceSignature superInterfaceSignature : this.superInterfaceSignatures) {
						if(!superInterfaceSignature.accept(nodeHierarchicalVisitor)) {
							return nodeHierarchicalVisitor.visitLeave(this);
						}
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
		} else if(!(object instanceof ClassSignature)) {
			return false;
		} else if(!Objects.equals(ClassSignature.class.cast(object).superClassSignature, this.superClassSignature)) {
			return false;
		} else if(!Objects.equals(ClassSignature.class.cast(object).typeParameters, this.typeParameters)) {
			return false;
		} else if(!Objects.equals(ClassSignature.class.cast(object).superInterfaceSignatures, this.superInterfaceSignatures)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.superClassSignature, this.typeParameters, this.superInterfaceSignatures);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static ClassSignature parseClassSignature(final String string) {
		return Parsers.parseClassSignature(TextScanner.newInstance(string));
	}
	
//	TODO: Add Javadocs!
	public static ClassSignature valueOf(final SuperClassSignature superClassSignature, final SuperInterfaceSignature... superInterfaceSignatures) {
		return new ClassSignature(Objects.requireNonNull(superClassSignature, "superClassSignature == null"), Optional.empty(), ParameterArguments.requireNonNullList(Arrays.asList(superInterfaceSignatures.clone()), "superInterfaceSignatures"));
	}
	
//	TODO: Add Javadocs!
	public static ClassSignature valueOf(final SuperClassSignature superClassSignature, final TypeParameters typeParameters, final SuperInterfaceSignature... superInterfaceSignatures) {
		return new ClassSignature(Objects.requireNonNull(superClassSignature, "superClassSignature == null"), Optional.of(typeParameters), ParameterArguments.requireNonNullList(Arrays.asList(superInterfaceSignatures.clone()), "superInterfaceSignatures"));
	}
	
//	TODO: Add Javadocs!
	public static Optional<ClassSignature> parseClassSignatureOptionally(final ClassFile classFile) {
		return SignatureAttribute.find(classFile).map(signatureAttribute -> Signature.parseSignature(classFile, signatureAttribute)).filter(signature -> signature instanceof ClassSignature).map(signature -> ClassSignature.class.cast(signature));
	}
}