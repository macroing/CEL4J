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
import java.util.stream.Collectors;

import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.SignatureAttribute;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code MethodSignature} denotes a MethodSignature as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class MethodSignature implements Signature {
	private final List<JavaTypeSignature> javaTypeSignatures;
	private final List<ThrowsSignature> throwsSignatures;
	private final Optional<TypeParameters> typeParameters;
	private final Result result;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	MethodSignature(final Builder builder) {
		this.javaTypeSignatures = new ArrayList<>(builder.javaTypeSignatures);
		this.throwsSignatures = new ArrayList<>(builder.throwsSignatures);
		this.typeParameters = builder.typeParameters;
		this.result = builder.result;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@link JavaTypeSignature} instances associated with this {@code MethodSignature} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code MethodSignature} instance.
	 * 
	 * @return a {@code List} with all {@code JavaTypeSignature} instances associated with this {@code MethodSignature} instance
	 */
	public List<JavaTypeSignature> getJavaTypeSignatures() {
		return new ArrayList<>(this.javaTypeSignatures);
	}
	
	/**
	 * Returns a {@code List} with all {@link ThrowsSignature} instances associated with this {@code MethodSignature} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code MethodSignature} instance.
	 * 
	 * @return a {@code List} with all {@code ThrowsSignature} instances associated with this {@code MethodSignature} instance
	 */
	public List<ThrowsSignature> getThrowsSignatures() {
		return new ArrayList<>(this.throwsSignatures);
	}
	
	/**
	 * Returns an {@code Optional} of type {@link TypeParameters} with the optional {@code TypeParameters} instance associated with this {@code MethodSignature} instance.
	 * 
	 * @return an {@code Optional} of type {@code TypeParameters} with the optional {@code TypeParameters} instance associated with this {@code MethodSignature} instance.
	 */
	public Optional<TypeParameters> getTypeParameters() {
		return this.typeParameters;
	}
	
	/**
	 * Returns the {@link Result} associated with this {@code MethodSignature} instance.
	 * 
	 * @return the {@code Result} associated with this {@code MethodSignature} instance
	 */
	public Result getResult() {
		return this.result;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code MethodSignature} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code MethodSignature} instance in external form
	 */
	@Override
	public String toExternalForm() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.typeParameters.isPresent() ? this.typeParameters.get().toExternalForm() + " " : "");
		stringBuilder.append(this.result.toExternalForm());
		stringBuilder.append("(");
		stringBuilder.append(this.javaTypeSignatures.stream().map(javaTypeSignature -> javaTypeSignature.toExternalForm()).collect(Collectors.joining(", ")));
		stringBuilder.append(")");
		stringBuilder.append((this.throwsSignatures.size() > 0 ? " throws " + this.throwsSignatures.stream().map(javaTypeSignature -> javaTypeSignature.toExternalForm()).collect(Collectors.joining(",")) : ""));
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code MethodSignature} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code MethodSignature} instance in internal form
	 */
	@Override
	public String toInternalForm() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.typeParameters.isPresent() ? this.typeParameters.get().toInternalForm() : "");
		stringBuilder.append("(");
		stringBuilder.append(this.javaTypeSignatures.stream().map(javaTypeSignature -> javaTypeSignature.toInternalForm()).<StringBuilder>collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
		stringBuilder.append(")");
		stringBuilder.append(this.result.toInternalForm());
		stringBuilder.append(this.throwsSignatures.stream().map(javaTypeSignature -> javaTypeSignature.toInternalForm()).<StringBuilder>collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code MethodSignature} instance.
	 * 
	 * @return a {@code String} representation of this {@code MethodSignature} instance
	 */
	@Override
	public String toString() {
		return String.format("MethodSignature: [TypeParameters=%s], [JavaTypeSignatures=%s], [Result=%s], [ThrowsSignatures=%s], [InternalForm=%s]", getTypeParameters(), getJavaTypeSignatures(), getResult(), getThrowsSignatures(), toInternalForm());
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
				if(this.typeParameters.isPresent()) {
					final TypeParameters typeParameters = this.typeParameters.get();
					
					if(!typeParameters.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				if(!this.result.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
				
				for(final JavaTypeSignature javaTypeSignature : this.javaTypeSignatures) {
					if(!javaTypeSignature.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				for(final ThrowsSignature throwsSignature : this.throwsSignatures) {
					if(!throwsSignature.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code MethodSignature} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code MethodSignature}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code MethodSignature} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code MethodSignature}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof MethodSignature)) {
			return false;
		} else if(!Objects.equals(MethodSignature.class.cast(object).typeParameters, this.typeParameters)) {
			return false;
		} else if(!Objects.equals(MethodSignature.class.cast(object).javaTypeSignatures, this.javaTypeSignatures)) {
			return false;
		} else if(!Objects.equals(MethodSignature.class.cast(object).result, this.result)) {
			return false;
		} else if(!Objects.equals(MethodSignature.class.cast(object).throwsSignatures, this.throwsSignatures)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code MethodSignature} instance.
	 * 
	 * @return a hash code for this {@code MethodSignature} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.typeParameters, this.javaTypeSignatures, this.result, this.throwsSignatures);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Parses the {@code MethodSignature} of {@code signatureAttribute} in {@code classFile}.
	 * <p>
	 * Returns a {@code MethodSignature} instance.
	 * <p>
	 * If either {@code classFile} or {@code signatureAttribute} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the {@link CPInfo} on the index {@code signatureAttribute.getSignatureIndex()} is not a {@link ConstantUTF8Info} instance, or the {@code getString()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed,
	 * an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code signatureAttribute.getSignatureIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @param signatureAttribute a {@link SignatureAttribute} instance
	 * @return a {@code MethodSignature} instance
	 * @throws IllegalArgumentException thrown if, and only if, the {@code CPInfo} on the index {@code signatureAttribute.getSignatureIndex()} is not a {@code ConstantUTF8Info} instance, or the {@code getString()} method of the {@code ConstantUTF8Info}
	 *                                  instance returns a {@code String} that is malformed
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code signatureAttribute.getSignatureIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code signatureAttribute} are {@code null}
	 */
	public static MethodSignature parseMethodSignature(final ClassFile classFile, final SignatureAttribute signatureAttribute) {
		return parseMethodSignature(classFile.getCPInfo(signatureAttribute.getSignatureIndex(), ConstantUTF8Info.class).getString());
	}
	
	/**
	 * Parses {@code string} into a {@code MethodSignature} instance.
	 * <p>
	 * Returns a {@code MethodSignature} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code MethodSignature} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static MethodSignature parseMethodSignature(final String string) {
		return Parsers.parseMethodSignature(new TextScanner(string));
	}
	
	/**
	 * Parses the {@code MethodSignature} of {@code methodInfo} in {@code classFile}, if present.
	 * <p>
	 * Returns an {@code Optional} of type {@code MethodSignature}.
	 * <p>
	 * If either {@code classFile} or {@code methodInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@link MethodInfo} instance that is equal to {@code methodInfo}, {@code methodInfo} contains a {@link SignatureAttribute} {@code signatureAttribute} but the {@link CPInfo} on the index
	 * {@code signatureAttribute.getSignatureIndex()} is not a {@link ConstantUTF8Info} instance, or the {@code getString()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed, an {@code IllegalArgumentException}
	 * will be thrown.
	 * <p>
	 * If, for the {@code SignatureAttribute} {@code signatureAttribute} in {@code methodInfo}, {@code signatureAttribute.getSignatureIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an
	 * {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @param methodInfo a {@code MethodInfo} instance
	 * @return an {@code Optional} of type {@code MethodSignature}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code MethodInfo} instance that is equal to {@code methodInfo}, {@code methodInfo} contains a {@code SignatureAttribute} {@code signatureAttribute}
	 *                                  but the  {@code CPInfo} on the index {@code signatureAttribute.getSignatureIndex()} is not a {@code ConstantUTF8Info} instance, or the {@code getString()} method of the {@code ConstantUTF8Info} instance returns a
	 *                                  {@code String} that is malformed
	 * @throws IndexOutOfBoundsException thrown if, and only if, for the {@code SignatureAttribute} {@code signatureAttribute} in {@code methodInfo}, {@code signatureAttribute.getSignatureIndex()} is less than {@code 0}, or greater than or equal to
	 *                                   {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code methodInfo} are {@code null}
	 */
	public static Optional<MethodSignature> parseMethodSignatureOptionally(final ClassFile classFile, final MethodInfo methodInfo) {
		return SignatureAttribute.find(classFile.getMethodInfo(methodInfo)).map(signatureAttribute -> MethodSignature.parseMethodSignature(classFile, signatureAttribute));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * This {@code Builder} class is used for building {@link MethodSignature} instances.
	 * 
	 * @since 1.0.0
	 * @author J&#246;rgen Lundgren
	 */
	public static final class Builder {
		final List<JavaTypeSignature> javaTypeSignatures = new ArrayList<>();
		final List<ThrowsSignature> throwsSignatures = new ArrayList<>();
		final Optional<TypeParameters> typeParameters;
		final Result result;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		private Builder(final Result result, final Optional<TypeParameters> typeParameters) {
			this.result = result;
			this.typeParameters = typeParameters;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/**
		 * Adds {@code javaTypeSignature} to this {@code Builder} instance.
		 * <p>
		 * Returns the {@code Builder} instance itself.
		 * <p>
		 * If {@code javaTypeSignature} is {@code null}, a {@code NullPointerException} will be thrown.
		 * 
		 * @param javaTypeSignature the {@link JavaTypeSignature} instance to add
		 * @return the {@code Builder} instance itself
		 * @throws NullPointerException thrown if, and only if, {@code javaTypeSignature} is {@code null}
		 */
		public Builder addJavaTypeSignature(final JavaTypeSignature javaTypeSignature) {
			this.javaTypeSignatures.add(Objects.requireNonNull(javaTypeSignature, "javaTypeSignature == null"));
			
			return this;
		}
		
		/**
		 * Adds {@code throwsSignature} to this {@code Builder} instance.
		 * <p>
		 * Returns the {@code Builder} instance itself.
		 * <p>
		 * If {@code throwsSignature} is {@code null}, a {@code NullPointerException} will be thrown.
		 * 
		 * @param throwsSignature the {@link ThrowsSignature} instance to add
		 * @return the {@code Builder} instance itself
		 * @throws NullPointerException thrown if, and only if, {@code throwsSignature} is {@code null}
		 */
		public Builder addThrowsSignature(final ThrowsSignature throwsSignature) {
			this.throwsSignatures.add(Objects.requireNonNull(throwsSignature, "throwsSignature == null"));
			
			return this;
		}
		
		/**
		 * Removes {@code javaTypeSignature} from this {@code Builder} instance.
		 * <p>
		 * Returns the {@code Builder} instance itself.
		 * <p>
		 * If {@code javaTypeSignature} is {@code null}, a {@code NullPointerException} will be thrown.
		 * 
		 * @param javaTypeSignature the {@link JavaTypeSignature} instance to remove
		 * @return the {@code Builder} instance itself
		 * @throws NullPointerException thrown if, and only if, {@code javaTypeSignature} is {@code null}
		 */
		public Builder removeJavaTypeSignature(final JavaTypeSignature javaTypeSignature) {
			this.javaTypeSignatures.remove(Objects.requireNonNull(javaTypeSignature, "javaTypeSignature == null"));
			
			return this;
		}
		
		/**
		 * Removes {@code throwsSignature} from this {@code Builder} instance.
		 * <p>
		 * Returns the {@code Builder} instance itself.
		 * <p>
		 * If {@code throwsSignature} is {@code null}, a {@code NullPointerException} will be thrown.
		 * 
		 * @param throwsSignature the {@link ThrowsSignature} instance to remove
		 * @return the {@code Builder} instance itself
		 * @throws NullPointerException thrown if, and only if, {@code throwsSignature} is {@code null}
		 */
		public Builder removeThrowsSignature(final ThrowsSignature throwsSignature) {
			this.throwsSignatures.remove(Objects.requireNonNull(throwsSignature, "throwsSignature == null"));
			
			return this;
		}
		
		/**
		 * Builds the {@link MethodSignature} instance.
		 * <p>
		 * Returns a {@code MethodSignature} instance.
		 * 
		 * @return a {@code MethodSignature} instance
		 */
		public MethodSignature build() {
			return new MethodSignature(this);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/**
		 * Returns a new {@code Builder} instance with {@code result} as its associated {@link Result}.
		 * <p>
		 * If {@code result} is {@code null}, a {@code NullPointerException} will be thrown.
		 * 
		 * @param result the associated {@code Result}
		 * @return a new {@code Builder} instance with {@code result} as its associated {@code Result}
		 * @throws NullPointerException thrown if, and only if, {@code result} is {@code null}
		 */
		public static Builder newInstance(final Result result) {
			return new Builder(Objects.requireNonNull(result, "result == null"), Optional.empty());
		}
		
		/**
		 * Returns a new {@code Builder} instance with {@code result} and {@code typeParameters} as its associated {@link Result} and {@link TypeParameters}, respectively.
		 * <p>
		 * If either {@code result} or {@code typeParameters} are {@code null}, a {@code NullPointerException} will be thrown.
		 * 
		 * @param result the associated {@code Result}
		 * @param typeParameters the associated {@code TypeParameters}
		 * @return a new {@code Builder} instance with {@code result} and {@code typeParameters} as its associated {@code Result} and {@code TypeParameters}, respectively
		 * @throws NullPointerException thrown if, and only if, either {@code result} or {@code typeParameters} are {@code null}
		 */
		public static Builder newInstance(final Result result, final TypeParameters typeParameters) {
			return new Builder(Objects.requireNonNull(result, "result == null"), Optional.of(typeParameters));
		}
	}
}