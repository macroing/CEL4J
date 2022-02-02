/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
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

import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * An {@code InterfaceBound} denotes an InterfaceBound as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class InterfaceBound implements Node {
	private final ReferenceTypeSignature referenceTypeSignature;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private InterfaceBound(final ReferenceTypeSignature referenceTypeSignature) {
		this.referenceTypeSignature = referenceTypeSignature;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link ReferenceTypeSignature} associated with this {@code InterfaceBound} instance.
	 * 
	 * @return the {@code ReferenceTypeSignature} associated with this {@code InterfaceBound} instance
	 */
	public ReferenceTypeSignature getReferenceTypeSignature() {
		return this.referenceTypeSignature;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code InterfaceBound} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code InterfaceBound} instance in external form
	 */
	public String toExternalForm() {
		return this.referenceTypeSignature.toExternalForm();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code InterfaceBound} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code InterfaceBound} instance in internal form
	 */
	public String toInternalForm() {
		return ":" + this.referenceTypeSignature.toInternalForm();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code InterfaceBound} instance.
	 * 
	 * @return a {@code String} representation of this {@code InterfaceBound} instance
	 */
	@Override
	public String toString() {
		return String.format("InterfaceBound: [ReferenceTypeSignature=%s], [InternalForm=%s]", getReferenceTypeSignature(), toInternalForm());
	}
	
	/**
	 * Accepts a {@link NodeHierarchicalVisitor}.
	 * <p>
	 * Returns the result of {@code nodeHierarchicalVisitor.visitLeave(this)}.
	 * <p>
	 * If {@code nodeHierarchicalVisitor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}, a {@code NodeTraversalException} will be thrown with the {@code RuntimeException} wrapped.
	 * <p>
	 * This implementation will:
	 * <ul>
	 * <li>throw a {@code NullPointerException} if {@code nodeHierarchicalVisitor} is {@code null}.</li>
	 * <li>throw a {@code NodeTraversalException} if {@code nodeHierarchicalVisitor} throws a {@code RuntimeException}.</li>
	 * <li>traverse its only child {@code Node}, a {@link ReferenceTypeSignature}.</li>
	 * </ul>
	 * 
	 * @param nodeHierarchicalVisitor the {@code NodeHierarchicalVisitor} to accept
	 * @return the result of {@code nodeHierarchicalVisitor.visitLeave(this)}
	 * @throws NodeTraversalException thrown if, and only if, a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code nodeHierarchicalVisitor} is {@code null}
	 */
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
	
	/**
	 * Compares {@code object} to this {@code InterfaceBound} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code InterfaceBound}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code InterfaceBound} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code InterfaceBound}, and their respective values are equal, {@code false} otherwise
	 */
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
	
	/**
	 * Returns a hash code for this {@code InterfaceBound} instance.
	 * 
	 * @return a hash code for this {@code InterfaceBound} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.referenceTypeSignature);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code InterfaceBound} instance that excludes all package names that are equal to {@code "java.lang"} from {@code interfaceBound}.
	 * <p>
	 * If {@code interfaceBound} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * InterfaceBound.excludePackageName(interfaceBound, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param interfaceBound an {@code InterfaceBound} instance
	 * @return an {@code InterfaceBound} instance that excludes all package names that are equal to {@code "java.lang"} from {@code interfaceBound}
	 * @throws NullPointerException thrown if, and only if, {@code interfaceBound} is {@code null}
	 */
	public static InterfaceBound excludePackageName(final InterfaceBound interfaceBound) {
		return excludePackageName(interfaceBound, "java.lang");
	}
	
	/**
	 * Returns an {@code InterfaceBound} instance that excludes all package names that are equal to {@code packageName} from {@code interfaceBound}.
	 * <p>
	 * If either {@code interfaceBound} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param interfaceBound an {@code InterfaceBound} instance
	 * @param packageName the package name to exclude
	 * @return an {@code InterfaceBound} instance that excludes all package names that are equal to {@code packageName} from {@code interfaceBound}
	 * @throws NullPointerException thrown if, and only if, either {@code interfaceBound} or {@code packageName} are {@code null}
	 */
	public static InterfaceBound excludePackageName(final InterfaceBound interfaceBound, final String packageName) {
		Objects.requireNonNull(interfaceBound, "interfaceBound == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, interfaceBound);
	}
	
	/**
	 * Parses {@code string} into an {@code InterfaceBound} instance.
	 * <p>
	 * Returns an {@code InterfaceBound} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return an {@code InterfaceBound} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static InterfaceBound parseInterfaceBound(final String string) {
		return Parsers.parseInterfaceBound(new TextScanner(string));
	}
	
	/**
	 * Returns an {@code InterfaceBound} instance with {@code referenceTypeSignature} as its associated {@link ReferenceTypeSignature}.
	 * <p>
	 * If {@code referenceTypeSignature} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param referenceTypeSignature the associated {@code ReferenceTypeSignature}
	 * @return an {@code InterfaceBound} instance with {@code referenceTypeSignature} as its associated {@code ReferenceTypeSignature}
	 * @throws NullPointerException thrown if, and only if, {@code referenceTypeSignature} is {@code null}
	 */
	public static InterfaceBound valueOf(final ReferenceTypeSignature referenceTypeSignature) {
		return new InterfaceBound(Objects.requireNonNull(referenceTypeSignature, "referenceTypeSignature == null"));
	}
}