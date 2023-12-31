/**
 * Copyright 2009 - 2024 J&#246;rgen Lundgren
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
import java.util.Optional;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code TypeArgument} denotes a TypeArgument as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class TypeArgument implements Node {
	/**
	 * An unknown {@code TypeArgument} instance.
	 */
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
	
	/**
	 * Returns an {@code Optional} of type {@link ReferenceTypeSignature} with the optional {@code ReferenceTypeSignature} instance associated with this {@code TypeArgument} instance.
	 * 
	 * @return an {@code Optional} of type {@code ReferenceTypeSignature} with the optional {@code ReferenceTypeSignature} instance associated with this {@code TypeArgument} instance.
	 */
	public Optional<ReferenceTypeSignature> getReferenceTypeSignature() {
		return this.referenceTypeSignature;
	}
	
	/**
	 * Returns an {@code Optional} of type {@link WildcardIndicator} with the optional {@code WildcardIndicator} instance associated with this {@code TypeArgument} instance.
	 * 
	 * @return an {@code Optional} of type {@code WildcardIndicator} with the optional {@code WildcardIndicator} instance associated with this {@code TypeArgument} instance.
	 */
	public Optional<WildcardIndicator> getWildcardIndicator() {
		return this.wildcardIndicator;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code TypeArgument} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code TypeArgument} instance in external form
	 */
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
	
	/**
	 * Returns a {@code String} representation of this {@code TypeArgument} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code TypeArgument} instance in internal form
	 */
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
	
	/**
	 * Returns a {@code String} representation of this {@code TypeArgument} instance.
	 * 
	 * @return a {@code String} representation of this {@code TypeArgument} instance
	 */
	@Override
	public String toString() {
		return String.format("TypeArgument: [WildcardIndicator=%s], [ReferenceTypeSignature=%s], [InternalForm=%s]", getWildcardIndicator(), getReferenceTypeSignature(), toInternalForm());
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
	 * <li>traverse its child {@code Node} instances.</li>
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
	
	/**
	 * Compares {@code object} to this {@code TypeArgument} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code TypeArgument}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code TypeArgument} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code TypeArgument}, and their respective values are equal, {@code false} otherwise
	 */
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
	
	/**
	 * Returns a hash code for this {@code TypeArgument} instance.
	 * 
	 * @return a hash code for this {@code TypeArgument} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.referenceTypeSignature, this.wildcardIndicator);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code TypeArgument} instance that excludes all package names that are equal to {@code "java.lang"} from {@code typeArgument}.
	 * <p>
	 * If {@code typeArgument} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * TypeArgument.excludePackageName(typeArgument, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param typeArgument a {@code TypeArgument} instance
	 * @return a {@code TypeArgument} instance that excludes all package names that are equal to {@code "java.lang"} from {@code typeArgument}
	 * @throws NullPointerException thrown if, and only if, {@code typeArgument} is {@code null}
	 */
	public static TypeArgument excludePackageName(final TypeArgument typeArgument) {
		return excludePackageName(typeArgument, "java.lang");
	}
	
	/**
	 * Returns a {@code TypeArgument} instance that excludes all package names that are equal to {@code packageName} from {@code typeArgument}.
	 * <p>
	 * If either {@code typeArgument} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param typeArgument a {@code TypeArgument} instance
	 * @param packageName the package name to exclude
	 * @return a {@code TypeArgument} instance that excludes all package names that are equal to {@code packageName} from {@code typeArgument}
	 * @throws NullPointerException thrown if, and only if, either {@code typeArgument} or {@code packageName} are {@code null}
	 */
	public static TypeArgument excludePackageName(final TypeArgument typeArgument, final String packageName) {
		Objects.requireNonNull(typeArgument, "typeArgument == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, typeArgument);
	}
	
	/**
	 * Parses {@code string} into a {@code TypeArgument} instance.
	 * <p>
	 * Returns a {@code TypeArgument} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code TypeArgument} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static TypeArgument parseTypeArgument(final String string) {
		return Parsers.parseTypeArgument(new TextScanner(string));
	}
	
	/**
	 * Returns a {@code TypeArgument} instance with {@code referenceTypeSignature} as its associated {@link ReferenceTypeSignature}.
	 * <p>
	 * If {@code referenceTypeSignature} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param referenceTypeSignature the associated {@code ReferenceTypeSignature}
	 * @return a {@code TypeArgument} instance with {@code referenceTypeSignature} as its associated {@code ReferenceTypeSignature}
	 * @throws NullPointerException thrown if, and only if, {@code referenceTypeSignature} is {@code null}
	 */
	public static TypeArgument valueOf(final ReferenceTypeSignature referenceTypeSignature) {
		return new TypeArgument(Optional.of(referenceTypeSignature), Optional.empty());
	}
	
	/**
	 * Returns a {@code TypeArgument} with {@code referenceTypeSignature} and {@code wildcardIndicator} as its associated {@link ReferenceTypeSignature} and {@link WildcardIndicator}, respectively.
	 * <p>
	 * If either {@code referenceTypeSignature} or {@code wildcardIndicator} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param referenceTypeSignature the associated {@code ReferenceTypeSignature}
	 * @param wildcardIndicator the associated {@code WildcardIndicator}
	 * @return a {@code TypeArgument} with {@code referenceTypeSignature} and {@code wildcardIndicator} as its associated {@code ReferenceTypeSignature} and {@code WildcardIndicator}, respectively
	 * @throws NullPointerException thrown if, and only if, either {@code referenceTypeSignature} or {@code wildcardIndicator} are {@code null}
	 */
	public static TypeArgument valueOf(final ReferenceTypeSignature referenceTypeSignature, final WildcardIndicator wildcardIndicator) {
		return new TypeArgument(Optional.of(referenceTypeSignature), Optional.of(wildcardIndicator));
	}
}