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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;

final class TypeParametersNameUpdaterNodeHierarchicalVisitor implements NodeHierarchicalVisitor {
	private final AtomicBoolean hasAddedExtends;
	private final AtomicBoolean hasAddedTypeParameter;
	private final BiFunction<String, String, String> nameUpdater;
	private final StringBuilder stringBuilder;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public TypeParametersNameUpdaterNodeHierarchicalVisitor(final BiFunction<String, String, String> nameUpdater) {
		this.hasAddedExtends = new AtomicBoolean();
		this.hasAddedTypeParameter = new AtomicBoolean();
		this.nameUpdater = Objects.requireNonNull(nameUpdater, "nameUpdater == null");
		this.stringBuilder = new StringBuilder();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		return this.stringBuilder.toString();
	}
	
	@Override
	public boolean visitEnter(final Node node) {
		switch(node.getClass().getSimpleName()) {
			case "ClassTypeSignature":
				final ClassTypeSignature classTypeSignature = ClassTypeSignature.class.cast(node);
				
				final List<ClassTypeSignatureSuffix> classTypeSignatureSuffixes = classTypeSignature.getClassTypeSignatureSuffixes();
				
				final Optional<PackageSpecifier> optionalPackageSpecifier = classTypeSignature.getPackageSpecifier();
				
				final SimpleClassTypeSignature simpleClassTypeSignature = classTypeSignature.getSimpleClassTypeSignature();
				
				final Identifier identifier = simpleClassTypeSignature.getIdentifier();
				
				final Optional<TypeArguments> optionalTypeArguments = simpleClassTypeSignature.getTypeArguments();
				
				final String name = this.nameUpdater.apply((optionalPackageSpecifier.isPresent() ? optionalPackageSpecifier.get().toExternalForm() : ""), identifier.toExternalForm());
				
				if(name != null) {
					if(this.hasAddedExtends.compareAndSet(false, true)) {
						this.stringBuilder.append(" extends ");
					} else {
						this.stringBuilder.append(" & ");
					}
					
					this.stringBuilder.append(name);
					
					if(optionalTypeArguments.isPresent()) {
						final TypeArguments typeArguments = optionalTypeArguments.get();
						
						this.stringBuilder.append(typeArguments.toExternalForm());
					}
					
					this.stringBuilder.append(classTypeSignatureSuffixes.stream().map(classTypeSignatureSuffix -> classTypeSignatureSuffix.toExternalForm()).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString());
				}
				
				return true;
			case "TypeParameter":
				this.hasAddedExtends.set(false);
				
				if(this.hasAddedTypeParameter.get()) {
					this.stringBuilder.append(", ");
				}
				
				this.stringBuilder.append(TypeParameter.class.cast(node).getIdentifier().toExternalForm());
				
				return true;
			case "TypeParameters":
				this.hasAddedExtends.set(false);
				this.hasAddedTypeParameter.set(false);
				
				this.stringBuilder.setLength(0);
				this.stringBuilder.append("<");
				
				return true;
			default:
				return true;
		}
	}
	
	@Override
	public boolean visitLeave(final Node node) {
		switch(node.getClass().getSimpleName()) {
			case "TypeParameter":
				this.hasAddedTypeParameter.set(true);
				
				return true;
			case "TypeParameters":
				this.stringBuilder.append(">");
				
				return true;
			default:
				return true;
		}
	}
}