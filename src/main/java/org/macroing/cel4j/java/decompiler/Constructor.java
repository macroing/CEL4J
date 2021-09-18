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
package org.macroing.cel4j.java.decompiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.CodeAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.Instruction;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.DeprecatedAttribute;
import org.macroing.cel4j.java.binary.classfile.signature.MethodSignature;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

/**
 * A {@code Constructor} represents a constructor.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
final class Constructor {
	private final ClassFile classFile;
	private final MethodInfo methodInfo;
	private final ParameterList parameterList;
	private final Type enclosingType;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	Constructor(final ClassFile classFile, final MethodInfo methodInfo, final Type enclosingType) {
		this.classFile = Objects.requireNonNull(classFile, "classFile == null");
		this.methodInfo = Objects.requireNonNull(methodInfo, "methodInfo == null");
		this.parameterList = ParameterList.load(classFile, methodInfo);
		this.enclosingType = Objects.requireNonNull(enclosingType, "enclosingType == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Decompiles this {@code Constructor} instance.
	 * <p>
	 * Returns a {@link Document} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * constructor.decompile(new DecompilerConfiguration());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Document} instance
	 */
	public Document decompile() {
		return decompile(new DecompilerConfiguration());
	}
	
	/**
	 * Decompiles this {@code Constructor} instance.
	 * <p>
	 * Returns a {@link Document} instance.
	 * <p>
	 * If {@code decompilerConfiguration} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * constructor.decompile(decompilerConfiguration, new Document());
	 * }
	 * </pre>
	 * 
	 * @param decompilerConfiguration a {@link DecompilerConfiguration} instance
	 * @return a {@code Document} instance
	 * @throws NullPointerException thrown if, and only if, {@code decompilerConfiguration} is {@code null}
	 */
	public Document decompile(final DecompilerConfiguration decompilerConfiguration) {
		return decompile(decompilerConfiguration, new Document());
	}
	
	/**
	 * Decompiles this {@code Constructor} instance.
	 * <p>
	 * Returns {@code document}.
	 * <p>
	 * If either {@code decompilerConfiguration} or {@code document} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param decompilerConfiguration a {@link DecompilerConfiguration} instance
	 * @param document a {@link Document} instance
	 * @return {@code document}
	 * @throws NullPointerException thrown if, and only if, either {@code decompilerConfiguration} or {@code document} are {@code null}
	 */
	public Document decompile(final DecompilerConfiguration decompilerConfiguration, final Document document) {
		Objects.requireNonNull(decompilerConfiguration, "decompilerConfiguration == null");
		Objects.requireNonNull(document, "document == null");
		
		final ParameterList parameterList = getParameterList();
		
		final String simpleName = getEnclosingType().getSimpleName();
		final String modifiers = Strings.optional(getModifiers(), "", " ", " ", modifier -> modifier.getKeyword());
		final String type = UtilitiesToRefactor.generateTypeWithOptionalTypeParameters(decompilerConfiguration, this, simpleName);
		final String parameters = parameterList.toExternalForm(decompilerConfiguration, this, new ArrayList<>());
		
		doGenerateComment(decompilerConfiguration, document);
		
		if(isDeprecated()) {
			document.linef("@Deprecated");
		}
		
		document.linef("%s%s(%s) {", modifiers, type, parameters);
		document.indent();
		document.line();
		document.outdent();
		document.linef("}");
		
		return document;
	}
	
	/**
	 * Returns a {@code List} that contains all {@link AttributeInfo} instances associated with this {@code Constructor} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code Constructor} instance.
	 * 
	 * @return a {@code List} that contains all {@code AttributeInfo} instances associated with this {@code Constructor} instance
	 */
	public List<AttributeInfo> getAttributeInfos() {
		return this.methodInfo.getAttributeInfos();
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Instruction} instances associated with this {@code Constructor} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code Constructor} instance.
	 * 
	 * @return a {@code List} that contains all {@code Instruction} instances associated with this {@code Constructor} instance
	 */
	public List<Instruction> getInstructions() {
		return CodeAttribute.find(this.methodInfo).map(codeAttribute -> codeAttribute.getInstructions()).orElse(new ArrayList<>());
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Modifier} instances associated with this {@code Constructor} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code Constructor} instance.
	 * 
	 * @return a {@code List} that contains all {@code Modifier} instances associated with this {@code Constructor} instance
	 */
	public List<Modifier> getModifiers() {
		final List<Modifier> modifiers = new ArrayList<>();
		
		if(isPrivate()) {
			modifiers.add(Modifier.PRIVATE);
		} else if(isProtected()) {
			modifiers.add(Modifier.PROTECTED);
		} else if(isPublic()) {
			modifiers.add(Modifier.PUBLIC);
		}
		
		if(isStrict()) {
			modifiers.add(Modifier.STRICT_F_P);
		}
		
		return modifiers;
	}
	
	/**
	 * Returns the optional {@link MethodSignature} instance associated with this {@code Constructor} instance.
	 * 
	 * @return the optional {@code MethodSignature} instance associated with this {@code Constructor} instance
	 */
	public Optional<MethodSignature> getOptionalMethodSignature() {
		return MethodSignature.parseMethodSignatureOptionally(this.classFile, this.methodInfo);
	}
	
	/**
	 * Returns the {@link ParameterList} instance associated with this {@code Constructor} instance.
	 * 
	 * @return the {@code ParameterList} instance associated with this {@code Constructor} instance
	 */
	public ParameterList getParameterList() {
		return this.parameterList;
	}
	
	/**
	 * Returns the name of this {@code Constructor} instance.
	 * 
	 * @return the name of this {@code Constructor} instance
	 */
	public String getName() {
		return getEnclosingType().getName();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Constructor} instance.
	 * 
	 * @return a {@code String} representation of this {@code Constructor} instance
	 */
	@Override
	public String toString() {
		return "Constructor";
	}
	
	/**
	 * Returns the enclosing {@link Type} instance associated with this {@code Constructor} instance.
	 * 
	 * @return the enclosing {@code Type} instance associated with this {@code Constructor} instance
	 */
	public Type getEnclosingType() {
		return this.enclosingType;
	}
	
	/**
	 * Compares {@code object} to this {@code Constructor} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Constructor}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Constructor} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Constructor}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Constructor)) {
			return false;
		} else if(!Objects.equals(this.classFile, Constructor.class.cast(object).classFile)) {
			return false;
		} else if(!Objects.equals(this.methodInfo, Constructor.class.cast(object).methodInfo)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Constructor} instance is deprecated, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Constructor} instance is deprecated, {@code false} otherwise
	 */
	public boolean isDeprecated() {
		return DeprecatedAttribute.find(this.methodInfo).isPresent();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Constructor} instance is package protected, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Constructor} instance is package protected, {@code false} otherwise
	 */
	public boolean isPackageProtected() {
		return !isPrivate() && !isProtected() && !isPublic();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Constructor} instance is private, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Constructor} instance is private, {@code false} otherwise
	 */
	public boolean isPrivate() {
		return this.methodInfo.isPrivate();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Constructor} instance is protected, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Constructor} instance is protected, {@code false} otherwise
	 */
	public boolean isProtected() {
		return this.methodInfo.isProtected();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Constructor} instance is public, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Constructor} instance is public, {@code false} otherwise
	 */
	public boolean isPublic() {
		return this.methodInfo.isPublic();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Constructor} instance is strict, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Constructor} instance is strict, {@code false} otherwise
	 */
	public boolean isStrict() {
		return this.methodInfo.isStrict();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Constructor} instance is synthetic, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Constructor} instance is synthetic, {@code false} otherwise
	 */
	public boolean isSynthetic() {
		return this.methodInfo.isSynthetic();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Constructor} instance is varargs, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Constructor} instance is varargs, {@code false} otherwise
	 */
	public boolean isVarargs() {
		return this.methodInfo.isVarargs();
	}
	
	/**
	 * Returns a hash code for this {@code Constructor} instance.
	 * 
	 * @return a hash code for this {@code Constructor} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.classFile, this.methodInfo);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doGenerateComment(final DecompilerConfiguration decompilerConfiguration, final Document document) {
		final List<AttributeInfo> attributeInfos = getAttributeInfos();
		final List<Instruction> instructions = getInstructions();
		
		final boolean isDisplayingAttributeInfos = decompilerConfiguration.isDisplayingAttributeInfos() && attributeInfos.size() > 0;
		final boolean isDisplayingInstructions = decompilerConfiguration.isDisplayingInstructions() && instructions.size() > 0;
		
		if(isDisplayingAttributeInfos || isDisplayingInstructions) {
			document.line("/*");
		}
		
		if(isDisplayingAttributeInfos) {
			for(final AttributeInfo attributeInfo : attributeInfos) {
				document.linef(" * %s", attributeInfo.getName());
			}
		}
		
		if(isDisplayingAttributeInfos && isDisplayingInstructions) {
			document.line(" * ");
		}
		
		if(isDisplayingInstructions) {
			document.linef(" * %-15s    %-5s    %-13s    %-13s    %-20s    %-20s    %s", "Mnemonic", "Index", "Opcode (Hex.)", "Opcode (Dec.)", "Operands", "Branch Offsets", "Data");
			document.linef(" * ");
			
			final AtomicInteger index = new AtomicInteger();
			
			for(final Instruction instruction : instructions) {
				final String mnemonic = instruction.getMnemonic();
				final String indexAsString = String.format("%04d", Integer.valueOf(index.get()));
				final String opcodeHex = String.format("0x%02X", Integer.valueOf(instruction.getOpcode()));
				final String opcodeDec = String.format("%03d", Integer.valueOf(instruction.getOpcode()));
				final String operands = Strings.optional(IntStream.of(instruction.getOperands()).boxed().collect(Collectors.toList()), "{", "}", ", ");
				final String branchOffsets = Arrays.toString(instruction.getBranchOffsets(index.get()));
				final String description = Instructions.toString(this.classFile, instruction);
				
				document.linef(" * %-15s    %-5s    %-13s    %-13s    %-20s    %-20s    %s", mnemonic, indexAsString, opcodeHex, opcodeDec, operands, branchOffsets, description);
				
				index.addAndGet(instruction.getLength());
			}
		}
		
		if(isDisplayingAttributeInfos || isDisplayingInstructions) {
			document.linef(" */");
		}
	}
}