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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;

final class TypeParametersNameCollectorNodeHierarchicalVisitor implements NodeHierarchicalVisitor {
	private final BiFunction<String, String, String> nameUpdater;
	private final List<String> names;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public TypeParametersNameCollectorNodeHierarchicalVisitor(final BiFunction<String, String, String> nameUpdater) {
		this.nameUpdater = Objects.requireNonNull(nameUpdater, "nameUpdater == null");
		this.names = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<String> getNames() {
		return new ArrayList<>(this.names);
	}
	
	@Override
	public boolean visitEnter(final Node node) {
		switch(node.getClass().getSimpleName()) {
			case "ClassTypeSignature":
				final ClassTypeSignature classTypeSignature = ClassTypeSignature.class.cast(node);
				
				final Optional<PackageSpecifier> optionalPackageSpecifier = classTypeSignature.getPackageSpecifier();
				
				final SimpleClassTypeSignature simpleClassTypeSignature = classTypeSignature.getSimpleClassTypeSignature();
				
				final Identifier identifier = simpleClassTypeSignature.getIdentifier();
				
				final String name = this.nameUpdater.apply((optionalPackageSpecifier.isPresent() ? optionalPackageSpecifier.get().toExternalForm() : ""), identifier.toExternalForm());
				
				if(name != null) {
					this.names.add(name);
				}
				
				return true;
			case "TypeParameters":
				this.names.clear();
				
				return true;
			default:
				return true;
		}
	}
	
	@Override
	public boolean visitLeave(final Node node) {
		return true;
	}
}