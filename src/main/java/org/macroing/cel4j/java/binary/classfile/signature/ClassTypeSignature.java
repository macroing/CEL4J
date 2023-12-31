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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code ClassTypeSignature} denotes a ClassTypeSignature as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ClassTypeSignature implements ReferenceTypeSignature, SuperClassSignature, SuperInterfaceSignature {
	private final List<ClassTypeSignatureSuffix> classTypeSignatureSuffixes;
	private final Optional<PackageSpecifier> packageSpecifier;
	private final SimpleClassTypeSignature simpleClassTypeSignature;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	ClassTypeSignature(final SimpleClassTypeSignature simpleClassTypeSignature, final List<ClassTypeSignatureSuffix> classTypeSignatureSuffixes, final Optional<PackageSpecifier> packageSpecifier) {
		this.simpleClassTypeSignature = simpleClassTypeSignature;
		this.packageSpecifier = packageSpecifier;
		this.classTypeSignatureSuffixes = classTypeSignatureSuffixes;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@link ClassTypeSignatureSuffix} instances associated with this {@code ClassTypeSignature} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code ClassTypeSignature} instance.
	 * 
	 * @return a {@code List} with all {@code ClassTypeSignatureSuffix} instances associated with this {@code ClassTypeSignature} instance
	 */
	public List<ClassTypeSignatureSuffix> getClassTypeSignatureSuffixes() {
		return new ArrayList<>(this.classTypeSignatureSuffixes);
	}
	
	/**
	 * Returns an {@code Optional} of type {@link PackageSpecifier} with the optional {@code PackageSpecifier} instance associated with this {@code ClassTypeSignature} instance.
	 * 
	 * @return an {@code Optional} of type {@code PackageSpecifier} with the optional {@code PackageSpecifier} instance associated with this {@code ClassTypeSignature} instance.
	 */
	public Optional<PackageSpecifier> getPackageSpecifier() {
		return this.packageSpecifier;
	}
	
	/**
	 * Returns the {@link SimpleClassTypeSignature} associated with this {@code ClassTypeSignature} instance.
	 * 
	 * @return the {@code SimpleClassTypeSignature} associated with this {@code ClassTypeSignature} instance
	 */
	public SimpleClassTypeSignature getSimpleClassTypeSignature() {
		return this.simpleClassTypeSignature;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassTypeSignature} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code ClassTypeSignature} instance in external form
	 */
	@Override
	public String toExternalForm() {
		return String.format("%s%s%s", this.packageSpecifier.isPresent() ? this.packageSpecifier.get().toExternalForm() : "", this.simpleClassTypeSignature.toExternalForm(), this.classTypeSignatureSuffixes.stream().map(classTypeSignatureSuffix -> classTypeSignatureSuffix.toExternalForm()).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassTypeSignature} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code ClassTypeSignature} instance in internal form
	 */
	@Override
	public String toInternalForm() {
		return String.format("L%s%s%s;", this.packageSpecifier.isPresent() ? this.packageSpecifier.get().toInternalForm() : "", this.simpleClassTypeSignature.toInternalForm(), this.classTypeSignatureSuffixes.stream().map(classTypeSignatureSuffix -> classTypeSignatureSuffix.toInternalForm()).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassTypeSignature} instance.
	 * 
	 * @return a {@code String} representation of this {@code ClassTypeSignature} instance
	 */
	@Override
	public String toString() {
		return String.format("ClassTypeSignature: [PackageSpecifier=%s], [SimpleClassTypeSignature=%s], [ClassTypeSignatureSuffixes=%s], [InternalForm=%s]", getPackageSpecifier(), getSimpleClassTypeSignature(), getClassTypeSignatureSuffixes(), toInternalForm());
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
	
	/**
	 * Compares {@code object} to this {@code ClassTypeSignature} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ClassTypeSignature}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ClassTypeSignature} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ClassTypeSignature}, and their respective values are equal, {@code false} otherwise
	 */
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
	
	/**
	 * Returns a hash code for this {@code ClassTypeSignature} instance.
	 * 
	 * @return a hash code for this {@code ClassTypeSignature} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.simpleClassTypeSignature, this.packageSpecifier, this.classTypeSignatureSuffixes);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code ClassTypeSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code classTypeSignature}.
	 * <p>
	 * If {@code classTypeSignature} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * ClassTypeSignature.excludePackageName(classTypeSignature, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param classTypeSignature a {@code ClassTypeSignature} instance
	 * @return a {@code ClassTypeSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code classTypeSignature}
	 * @throws NullPointerException thrown if, and only if, {@code classTypeSignature} is {@code null}
	 */
	public static ClassTypeSignature excludePackageName(final ClassTypeSignature classTypeSignature) {
		return excludePackageName(classTypeSignature, "java.lang");
	}
	
	/**
	 * Returns a {@code ClassTypeSignature} instance that excludes all package names that are equal to {@code packageName} from {@code classTypeSignature}.
	 * <p>
	 * If either {@code classTypeSignature} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param classTypeSignature a {@code ClassTypeSignature} instance
	 * @param packageName the package name to exclude
	 * @return a {@code ClassTypeSignature} instance that excludes all package names that are equal to {@code packageName} from {@code classTypeSignature}
	 * @throws NullPointerException thrown if, and only if, either {@code classTypeSignature} or {@code packageName} are {@code null}
	 */
	public static ClassTypeSignature excludePackageName(final ClassTypeSignature classTypeSignature, final String packageName) {
		Objects.requireNonNull(classTypeSignature, "classTypeSignature == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, classTypeSignature);
	}
	
	/**
	 * Parses {@code string} into a {@code ClassTypeSignature} instance.
	 * <p>
	 * Returns a {@code ClassTypeSignature} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code ClassTypeSignature} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static ClassTypeSignature parseClassTypeSignature(final String string) {
		return Parsers.parseClassTypeSignature(new TextScanner(string));
	}
	
	/**
	 * Returns a {@code ClassTypeSignature} with {@code simpleClassTypeSignature} and {@code classTypeSignatureSuffixes} as its associated {@link SimpleClassTypeSignature} and {@link ClassTypeSignatureSuffix} instances, respectively.
	 * <p>
	 * If either {@code simpleClassTypeSignature}, {@code classTypeSignatureSuffixes} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param simpleClassTypeSignature the associated {@code SimpleClassTypeSignature}
	 * @param classTypeSignatureSuffixes the associated {@code ClassTypeSignatureSuffix} instances
	 * @return a {@code ClassTypeSignature} with {@code simpleClassTypeSignature} and {@code classTypeSignatureSuffixes} as its associated {@code SimpleClassTypeSignature} and {@code ClassTypeSignatureSuffix} instances, respectively
	 * @throws NullPointerException thrown if, and only if, either {@code simpleClassTypeSignature}, {@code classTypeSignatureSuffixes} or any of its elements are {@code null}
	 */
	public static ClassTypeSignature valueOf(final SimpleClassTypeSignature simpleClassTypeSignature, final List<ClassTypeSignatureSuffix> classTypeSignatureSuffixes) {
		return new ClassTypeSignature(Objects.requireNonNull(simpleClassTypeSignature, "simpleClassTypeSignature == null"), new ArrayList<>(ParameterArguments.requireNonNullList(classTypeSignatureSuffixes, "classTypeSignatureSuffixes")), Optional.empty());
	}
	
	/**
	 * Returns a {@code ClassTypeSignature} with {@code simpleClassTypeSignature}, {@code classTypeSignatureSuffixes} and {@code packageSpecifier} as its associated {@link SimpleClassTypeSignature}, {@link ClassTypeSignatureSuffix} instances and {@link PackageSpecifier}, respectively.
	 * <p>
	 * If either {@code simpleClassTypeSignature}, {@code classTypeSignatureSuffixes}, any of its elements or {@code packageSpecifier} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param simpleClassTypeSignature the associated {@code SimpleClassTypeSignature}
	 * @param classTypeSignatureSuffixes the associated {@code ClassTypeSignatureSuffix} instances
	 * @param packageSpecifier the associated {@code PackageSpecifier}
	 * @return a {@code ClassTypeSignature} with {@code simpleClassTypeSignature}, {@code classTypeSignatureSuffixes} and {@code packageSpecifier} as its associated {@code SimpleClassTypeSignature}, {@code ClassTypeSignatureSuffix} instances and {@code PackageSpecifier}, respectively
	 * @throws NullPointerException thrown if, and only if, either {@code simpleClassTypeSignature}, {@code classTypeSignatureSuffixes}, any of its elements or {@code packageSpecifier} are {@code null}
	 */
	public static ClassTypeSignature valueOf(final SimpleClassTypeSignature simpleClassTypeSignature, final List<ClassTypeSignatureSuffix> classTypeSignatureSuffixes, final PackageSpecifier packageSpecifier) {
		return new ClassTypeSignature(Objects.requireNonNull(simpleClassTypeSignature, "simpleClassTypeSignature == null"), new ArrayList<>(ParameterArguments.requireNonNullList(classTypeSignatureSuffixes, "classTypeSignatureSuffixes")), Optional.of(packageSpecifier));
	}
}