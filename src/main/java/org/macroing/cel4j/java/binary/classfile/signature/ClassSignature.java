/**
 * Copyright 2009 - 2021 J&#246;rgen Lundgren
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
import java.util.stream.Collectors;

import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.SignatureAttribute;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code ClassSignature} denotes a ClassSignature as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ClassSignature implements Signature {
	private final List<SuperInterfaceSignature> superInterfaceSignatures;
	private final Optional<TypeParameters> typeParameters;
	private final SuperClassSignature superClassSignature;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	ClassSignature(final SuperClassSignature superClassSignature, final List<SuperInterfaceSignature> superInterfaceSignatures, final Optional<TypeParameters> typeParameters) {
		this.superInterfaceSignatures = superInterfaceSignatures;
		this.typeParameters = typeParameters;
		this.superClassSignature = superClassSignature;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@link SuperInterfaceSignature} instances associated with this {@code ClassSignature} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code ClassSignature} instance.
	 * 
	 * @return a {@code List} with all {@code SuperInterfaceSignature} instances associated with this {@code ClassSignature} instance
	 */
	public List<SuperInterfaceSignature> getSuperInterfaceSignatures() {
		return new ArrayList<>(this.superInterfaceSignatures);
	}
	
	/**
	 * Returns an {@code Optional} of type {@link TypeParameters} with the optional {@code TypeParameters} instance associated with this {@code ClassSignature} instance.
	 * 
	 * @return an {@code Optional} of type {@code TypeParameters} with the optional {@code TypeParameters} instance associated with this {@code ClassSignature} instance.
	 */
	public Optional<TypeParameters> getTypeParameters() {
		return this.typeParameters;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassSignature} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code ClassSignature} instance in external form
	 */
	@Override
	public String toExternalForm() {
		return String.format("%s%s%s", this.typeParameters.isPresent() ? this.typeParameters.get().toExternalForm() + " " : "", "extends " + this.superClassSignature.toExternalForm(), " implements " + this.superInterfaceSignatures.stream().map(superInterfaceSignature -> superInterfaceSignature.toExternalForm()).collect(Collectors.joining(",")));
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassSignature} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code ClassSignature} instance in internal form
	 */
	@Override
	public String toInternalForm() {
		return String.format("%s%s%s", this.typeParameters.isPresent() ? this.typeParameters.get().toInternalForm() : "", this.superClassSignature.toInternalForm(), this.superInterfaceSignatures.stream().map(superInterfaceSignature -> superInterfaceSignature.toInternalForm()).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
	}
	
	/**
	 * Returns the {@link SuperClassSignature} associated with this {@code ClassSignature} instance.
	 * 
	 * @return the {@code SuperClassSignature} associated with this {@code ClassSignature} instance
	 */
	public SuperClassSignature getSuperClassSignature() {
		return this.superClassSignature;
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
	 * <li>traverse its child {@code Node}s.</li>
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
	
	/**
	 * Compares {@code object} to this {@code ClassSignature} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ClassSignature}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ClassSignature} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ClassSignature}, and their respective values are equal, {@code false} otherwise
	 */
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
	
	/**
	 * Returns a hash code for this {@code ClassSignature} instance.
	 * 
	 * @return a hash code for this {@code ClassSignature} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.superClassSignature, this.typeParameters, this.superInterfaceSignatures);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code ClassSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code classSignature}.
	 * <p>
	 * If {@code classSignature} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * ClassSignature.excludePackageName(classSignature, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param classSignature a {@code ClassSignature} instance
	 * @return a {@code ClassSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code classSignature}
	 * @throws NullPointerException thrown if, and only if, {@code classSignature} is {@code null}
	 */
	public static ClassSignature excludePackageName(final ClassSignature classSignature) {
		return excludePackageName(classSignature, "java.lang");
	}
	
	/**
	 * Returns a {@code ClassSignature} instance that excludes all package names that are equal to {@code packageName} from {@code classSignature}.
	 * <p>
	 * If either {@code classSignature} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param classSignature a {@code ClassSignature} instance
	 * @param packageName the package name to exclude
	 * @return a {@code ClassSignature} instance that excludes all package names that are equal to {@code packageName} from {@code classSignature}
	 * @throws NullPointerException thrown if, and only if, either {@code classSignature} or {@code packageName} are {@code null}
	 */
	public static ClassSignature excludePackageName(final ClassSignature classSignature, final String packageName) {
		Objects.requireNonNull(classSignature, "classSignature == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, classSignature);
	}
	
	/**
	 * Parses {@code string} into a {@code ClassSignature} instance.
	 * <p>
	 * Returns a {@code ClassSignature} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code ClassSignature} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static ClassSignature parseClassSignature(final String string) {
		return Parsers.parseClassSignature(new TextScanner(string));
	}
	
	/**
	 * Returns a {@code ClassSignature} with {@code superClassSignature} and {@code superInterfaceSignatures} as its associated {@link SuperClassSignature} and {@link SuperInterfaceSignature} instances, respectively.
	 * <p>
	 * If either {@code superClassSignature}, {@code superInterfaceSignatures} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param superClassSignature the associated {@code SuperClassSignature}
	 * @param superInterfaceSignatures the associated {@code SuperInterfaceSignature} instances
	 * @return a {@code ClassSignature} with {@code superClassSignature} and {@code superInterfaceSignatures} as its associated {@code SuperClassSignature} and {@code SuperInterfaceSignature} instances, respectively
	 * @throws NullPointerException thrown if, and only if, either {@code superClassSignature}, {@code superInterfaceSignatures} or any of its elements are {@code null}
	 */
	public static ClassSignature valueOf(final SuperClassSignature superClassSignature, final List<SuperInterfaceSignature> superInterfaceSignatures) {
		return new ClassSignature(Objects.requireNonNull(superClassSignature, "superClassSignature == null"), new ArrayList<>(ParameterArguments.requireNonNullList(superInterfaceSignatures, "superInterfaceSignatures")), Optional.empty());
	}
	
	/**
	 * Returns a {@code ClassSignature} with {@code superClassSignature}, {@code superInterfaceSignatures} and {@code typeParameters} as its associated {@link SuperClassSignature}, {@link SuperInterfaceSignature} instances and {@link TypeParameters},
	 * respectively.
	 * <p>
	 * If either {@code superClassSignature}, {@code superInterfaceSignatures}, any of its elements or {@code typeParameters} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param superClassSignature the associated {@code SuperClassSignature}
	 * @param superInterfaceSignatures the associated {@code SuperInterfaceSignature} instances
	 * @param typeParameters the associated {@code TypeParameters}
	 * @return a {@code ClassSignature} with {@code superClassSignature}, {@code superInterfaceSignatures} and {@code typeParameters} as its associated {@code SuperClassSignature}, {@code SuperInterfaceSignature} instances and {@code TypeParameters},
	 *         respectively
	 * @throws NullPointerException thrown if, and only if, either {@code superClassSignature}, {@code superInterfaceSignatures}, any of its elements or {@code typeParameters} are {@code null}
	 */
	public static ClassSignature valueOf(final SuperClassSignature superClassSignature, final List<SuperInterfaceSignature> superInterfaceSignatures, final TypeParameters typeParameters) {
		return new ClassSignature(Objects.requireNonNull(superClassSignature, "superClassSignature == null"), new ArrayList<>(ParameterArguments.requireNonNullList(superInterfaceSignatures, "superInterfaceSignatures")), Optional.of(typeParameters));
	}
	
	/**
	 * Parses the {@code ClassSignature} of {@code classFile}, if present.
	 * <p>
	 * Returns an {@code Optional} of type {@code ClassSignature}.
	 * <p>
	 * If {@code classFile} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If, for the {@code SignatureAttribute} {@code signatureAttribute} in {@code classFile}, the {@link CPInfo} on the index {@code signatureAttribute.getSignatureIndex()} is not a {@link ConstantUTF8Info} instance, or the {@code getStringValue()}
	 * method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If, for the {@code SignatureAttribute} {@code signatureAttribute} in {@code classFile}, {@code signatureAttribute.getSignatureIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an
	 * {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @return an {@code Optional} of type {@code ClassSignature}
	 * @throws IllegalArgumentException thrown if, and only if, for the {@code SignatureAttribute} {@code signatureAttribute} in {@code classFile}, the {@code CPInfo} on the index {@code signatureAttribute.getSignatureIndex()} is not a
	 *                                  {@code ConstantUTF8Info} instance, or the {@code getStringValue()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed
	 * @throws IndexOutOfBoundsException thrown if, and only if, for the {@code SignatureAttribute} {@code signatureAttribute} in {@code classFile}, {@code signatureAttribute.getSignatureIndex()} is less than {@code 0}, or greater than or equal to
	 *                                   {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, {@code classFile} is {@code null}
	 */
	public static Optional<ClassSignature> parseClassSignatureOptionally(final ClassFile classFile) {
		return SignatureAttribute.find(classFile).map(signatureAttribute -> Signature.parseSignature(classFile, signatureAttribute)).filter(signature -> signature instanceof ClassSignature).map(signature -> ClassSignature.class.cast(signature));
	}
}