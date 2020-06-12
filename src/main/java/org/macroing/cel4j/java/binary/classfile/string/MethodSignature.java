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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.SignatureAttribute;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;

//TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
	public List<JavaTypeSignature> getJavaTypeSignatures() {
		return new ArrayList<>(this.javaTypeSignatures);
	}
	
//	TODO: Add Javadocs!
	public List<ThrowsSignature> getThrowsSignatures() {
		return new ArrayList<>(this.throwsSignatures);
	}
	
//	TODO: Add Javadocs!
	public Optional<TypeParameters> getTypeParameters() {
		return this.typeParameters;
	}
	
//	TODO: Add Javadocs!
	public Result getResult() {
		return this.result;
	}
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("MethodSignature: [TypeParameters=%s], [JavaTypeSignatures=%s], [Result=%s], [ThrowsSignatures=%s], [InternalForm=%s]", getTypeParameters(), getJavaTypeSignatures(), getResult(), getThrowsSignatures(), toInternalForm());
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
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.typeParameters, this.javaTypeSignatures, this.result, this.throwsSignatures);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static MethodSignature parseMethodSignature(final ClassFile classFile, final SignatureAttribute signatureAttribute) {
		return parseMethodSignature(classFile.getCPInfo(signatureAttribute.getSignatureIndex(), ConstantUTF8Info.class).getString());
	}
	
//	TODO: Add Javadocs!
	public static MethodSignature parseMethodSignature(final String string) {
		return Parsers.parseMethodSignature(new TextScanner(string));
	}
	
//	TODO: Add Javadocs!
	public static Optional<MethodSignature> parseMethodSignatureOptionally(final ClassFile classFile, final MethodInfo methodInfo) {
		return SignatureAttribute.find(classFile.getMethodInfo(methodInfo)).map(signatureAttribute -> MethodSignature.parseMethodSignature(classFile, signatureAttribute));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
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
		
//		TODO: Add Javadocs!
		public Builder addJavaTypeSignature(final JavaTypeSignature javaTypeSignature) {
			this.javaTypeSignatures.add(Objects.requireNonNull(javaTypeSignature, "javaTypeSignature == null"));
			
			return this;
		}
		
//		TODO: Add Javadocs!
		public Builder addThrowsSignature(final ThrowsSignature throwsSignature) {
			this.throwsSignatures.add(Objects.requireNonNull(throwsSignature, "throwsSignature == null"));
			
			return this;
		}
		
//		TODO: Add Javadocs!
		public Builder removeJavaTypeSignature(final JavaTypeSignature javaTypeSignature) {
			this.javaTypeSignatures.remove(Objects.requireNonNull(javaTypeSignature, "javaTypeSignature == null"));
			
			return this;
		}
		
//		TODO: Add Javadocs!
		public Builder removeThrowsSignature(final ThrowsSignature throwsSignature) {
			this.throwsSignatures.remove(Objects.requireNonNull(throwsSignature, "throwsSignature == null"));
			
			return this;
		}
		
//		TODO: Add Javadocs!
		public MethodSignature build() {
			return new MethodSignature(this);
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
//		TODO: Add Javadocs!
		public static Builder newInstance(final Result result) {
			return new Builder(Objects.requireNonNull(result, "result == null"), Optional.empty());
		}
		
//		TODO: Add Javadocs!
		public static Builder newInstance(final Result result, final TypeParameters typeParameters) {
			return new Builder(Objects.requireNonNull(result, "result == null"), Optional.of(typeParameters));
		}
	}
}