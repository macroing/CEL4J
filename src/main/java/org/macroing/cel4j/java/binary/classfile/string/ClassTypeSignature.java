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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

//TODO: Add Javadocs!
public final class ClassTypeSignature implements ReferenceTypeSignature, SuperClassSignature, SuperInterfaceSignature {
	private final List<ClassTypeSignatureSuffix> classTypeSignatureSuffixes;
	private final Optional<PackageSpecifier> packageSpecifier;
	private final SimpleClassTypeSignature simpleClassTypeSignature;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	ClassTypeSignature(final SimpleClassTypeSignature simpleClassTypeSignature, final Optional<PackageSpecifier> packageSpecifier, final List<ClassTypeSignatureSuffix> classTypeSignatureSuffixes) {
		this.simpleClassTypeSignature = simpleClassTypeSignature;
		this.packageSpecifier = packageSpecifier;
		this.classTypeSignatureSuffixes = classTypeSignatureSuffixes;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public List<ClassTypeSignatureSuffix> getClassTypeSignatureSuffixes() {
		return new ArrayList<>(this.classTypeSignatureSuffixes);
	}
	
//	TODO: Add Javadocs!
	public Optional<PackageSpecifier> getPackageSpecifier() {
		return this.packageSpecifier;
	}
	
//	TODO: Add Javadocs!
	public SimpleClassTypeSignature getSimpleClassTypeSignature() {
		return this.simpleClassTypeSignature;
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toExternalForm() {
		return String.format("%s%s%s", this.packageSpecifier.isPresent() ? this.packageSpecifier.get().toExternalForm() : "", this.simpleClassTypeSignature.toExternalForm(), this.classTypeSignatureSuffixes.stream().map(classTypeSignatureSuffix -> classTypeSignatureSuffix.toExternalForm()).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toInternalForm() {
		return String.format("L%s%s%s;", this.packageSpecifier.isPresent() ? this.packageSpecifier.get().toInternalForm() : "", this.simpleClassTypeSignature.toInternalForm(), this.classTypeSignatureSuffixes.stream().map(classTypeSignatureSuffix -> classTypeSignatureSuffix.toInternalForm()).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("ClassTypeSignature: [PackageSpecifier=%s], [SimpleClassTypeSignature=%s], [ClassTypeSignatureSuffixes=%s], [InternalForm=%s]", getPackageSpecifier(), getSimpleClassTypeSignature(), getClassTypeSignatureSuffixes(), toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				if(this.packageSpecifier.isPresent()) {
					final PackageSpecifier packageSpecifier = this.packageSpecifier.get();
					
					if(!packageSpecifier.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
					
					if(!this.simpleClassTypeSignature.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
					
					for(final ClassTypeSignatureSuffix classTypeSignatureSuffix : this.classTypeSignatureSuffixes) {
						if(!classTypeSignatureSuffix.accept(nodeHierarchicalVisitor)) {
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
		} else if(!(object instanceof ClassTypeSignature)) {
			return false;
		} else if(!Objects.equals(ClassTypeSignature.class.cast(object).simpleClassTypeSignature, this.simpleClassTypeSignature)) {
			return false;
		} else if(!Objects.equals(ClassTypeSignature.class.cast(object).packageSpecifier, this.packageSpecifier)) {
			return false;
		} else if(!Objects.equals(ClassTypeSignature.class.cast(object).classTypeSignatureSuffixes, this.classTypeSignatureSuffixes)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.simpleClassTypeSignature, this.packageSpecifier, this.classTypeSignatureSuffixes);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static ClassTypeSignature parseClassTypeSignature(final String string) {
		return Parsers.parseClassTypeSignature(TextScanner.newInstance(string));
	}
	
//	TODO: Add Javadocs!
	public static ClassTypeSignature valueOf(final SimpleClassTypeSignature simpleClassTypeSignature, final ClassTypeSignatureSuffix... classTypeSignatureSuffixes) {
		return new ClassTypeSignature(Objects.requireNonNull(simpleClassTypeSignature, "simpleClassTypeSignature == null"), Optional.empty(), ParameterArguments.requireNonNullList(Arrays.asList(classTypeSignatureSuffixes.clone()), "classTypeSignatureSuffixes"));
	}
	
//	TODO: Add Javadocs!
	public static ClassTypeSignature valueOf(final SimpleClassTypeSignature simpleClassTypeSignature, final PackageSpecifier packageSpecifier, final ClassTypeSignatureSuffix... classTypeSignatureSuffixes) {
		return new ClassTypeSignature(Objects.requireNonNull(simpleClassTypeSignature, "simpleClassTypeSignature == null"), Optional.of(packageSpecifier), ParameterArguments.requireNonNullList(Arrays.asList(classTypeSignatureSuffixes.clone()), "classTypeSignatureSuffixes"));
	}
}