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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.CodeAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.DeprecatedAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.Instruction;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.java.binary.classfile.descriptor.MethodDescriptor;
import org.macroing.cel4j.java.binary.classfile.descriptor.ParameterDescriptor;
import org.macroing.cel4j.java.binary.classfile.signature.MethodSignature;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

/**
 * A {@code Method} represents a method.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
final class Method implements Comparable<Method> {
	private final ClassFile classFile;
	private final List<Type> importableTypes;
	private final MethodInfo methodInfo;
	private final Optional<CodeAttribute> optionalCodeAttribute;
	private final ParameterList parameterList;
	private final Type enclosingType;
	private final Type returnType;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	Method(final ClassFile classFile, final MethodInfo methodInfo, final Type enclosingType) {
		this.classFile = Objects.requireNonNull(classFile, "classFile == null");
		this.methodInfo = Objects.requireNonNull(methodInfo, "methodInfo == null");
		this.enclosingType = Objects.requireNonNull(enclosingType, "enclosingType == null");
		this.importableTypes = new ArrayList<>();
		this.optionalCodeAttribute = CodeAttribute.find(this.methodInfo);
		this.parameterList = ParameterList.load(classFile, methodInfo);
		this.returnType = Type.valueOf(MethodDescriptor.parseMethodDescriptor(classFile, methodInfo).getReturnDescriptor());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link ClassFile} instance associated with this {@code Method} instance.
	 * 
	 * @return the {@code ClassFile} instance associated with this {@code Method} instance
	 */
	public ClassFile getClassFile() {
		return this.classFile;
	}
	
	/**
	 * Decompiles this {@code Method} instance.
	 * <p>
	 * Returns a {@link Document} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * method.decompile(new DecompilerConfiguration());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Document} instance
	 */
	public Document decompile() {
		return decompile(new DecompilerConfiguration());
	}
	
	/**
	 * Decompiles this {@code Method} instance.
	 * <p>
	 * Returns a {@link Document} instance.
	 * <p>
	 * If {@code decompilerConfiguration} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * method.decompile(decompilerConfiguration, new Document());
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
	 * Decompiles this {@code Method} instance.
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
		
		final boolean isAnnotatingDeprecatedMethods = decompilerConfiguration.isAnnotatingDeprecatedMethods();
		final boolean isAnnotatingOverriddenMethods = decompilerConfiguration.isAnnotatingOverriddenMethods();
		final boolean isDisplayingAttributeInfos = decompilerConfiguration.isDisplayingAttributeInfos();
		final boolean isDisplayingInstructions = decompilerConfiguration.isDisplayingInstructions();
		
		final ParameterList parameterList = getParameterList();
		final Type enclosingType = getEnclosingType();
		
		final List<Type> importableTypes = getImportableTypes();
		
		final String modifiers = Strings.optional(doDiscardInterfaceMethodModifiers(decompilerConfiguration, enclosingType, getModifiers()), "", " ", " ", modifier -> modifier.getKeyword());
		final String returnType = UtilitiesToRefactor.generateReturnTypeWithOptionalTypeParameters(decompilerConfiguration, this, importableTypes);
		final String name = getName();
		final String parameters = parameterList.toExternalForm(decompilerConfiguration, this, importableTypes);
		final String returnStatement = UtilitiesToRefactor.generateDefaultReturnStatement(this);
		
		final List<Instruction> instructions = getInstructions();
		
		if(isAnnotatingDeprecatedMethods && isDeprecated()) {
			document.linef("@Deprecated");
		}
		
		if(isAnnotatingOverriddenMethods && enclosingType.hasMethodOverridden(this) && !isPrivate() && !isStatic()) {
			document.linef("@Override");
		}
		
		if(isAbstract() || isNative()) {
			document.linef("%s%s %s(%s);", modifiers, returnType, name, parameters);
		} else {
			document.linef("%s%s %s(%s) {", modifiers, returnType, name, parameters);
			document.indent();
			
			if(isDisplayingAttributeInfos) {
				document.line("/*");
				
				for(final AttributeInfo attributeInfo : getAttributeInfos()) {
					document.linef(" * %s", attributeInfo.getName());
				}
				
				document.line(" */");
			}
			
			if(isDisplayingAttributeInfos && isDisplayingInstructions) {
				document.line();
			}
			
			if(isDisplayingInstructions) {
				document.linef("/*");
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
				
				document.line(" */");
			}
			
			if(!returnStatement.isEmpty()) {
				if(isDisplayingAttributeInfos || isDisplayingInstructions) {
					document.line();
				}
				
				document.linef("%s", returnStatement);
			} else if(!isDisplayingAttributeInfos && !isDisplayingInstructions) {
				document.line();
			}
			
			document.outdent();
			document.linef("}");
		}
		
		return document;
	}
	
	/**
	 * Returns a {@code List} that contains all {@link AttributeInfo} instances associated with this {@code Method} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code Method} instance.
	 * 
	 * @return a {@code List} that contains all {@code AttributeInfo} instances associated with this {@code Method} instance
	 */
	public List<AttributeInfo> getAttributeInfos() {
		return this.methodInfo.getAttributeInfos();
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Instruction} instances associated with this {@code Method} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code Method} instance.
	 * 
	 * @return a {@code List} that contains all {@code Instruction} instances associated with this {@code Method} instance
	 */
	public List<Instruction> getInstructions() {
		if(this.optionalCodeAttribute.isPresent()) {
			final CodeAttribute codeAttribute = this.optionalCodeAttribute.get();
			
			return codeAttribute.getInstructions();
		}
		
		return new ArrayList<>();
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Modifier} instances associated with this {@code Method} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code Method} instance.
	 * 
	 * @return a {@code List} that contains all {@code Modifier} instances associated with this {@code Method} instance
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
		
		if(isStatic()) {
			modifiers.add(Modifier.STATIC);
		}
		
		if(isEnclosedByInterface() && !isAbstract() && !isStatic()) {
			modifiers.add(Modifier.DEFAULT);
		}
		
		if(isAbstract()) {
			modifiers.add(Modifier.ABSTRACT);
		} else if(isFinal()) {
			modifiers.add(Modifier.FINAL);
		}
		
		if(isSynchronized()) {
			modifiers.add(Modifier.SYNCHRONIZED);
		}
		
		if(isNative()) {
			modifiers.add(Modifier.NATIVE);
		}
		
		if(isStrict()) {
			modifiers.add(Modifier.STRICT_F_P);
		}
		
		return modifiers;
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Type} instances associated with this {@code Method} instance that are importable.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code Method} instance.
	 * 
	 * @return a {@code List} that contains all {@code Type} instances associated with this {@code Method} instance that are importable
	 */
	public List<Type> getImportableTypes() {
		if(this.importableTypes.size() > 0) {
			return new ArrayList<>(this.importableTypes);
		}
		
		final Set<Type> importableTypes = new LinkedHashSet<>();
		
		doAddTypeToImportIfNecessary(getReturnType(), importableTypes);
		
		for(final Parameter parameter : getParameterList().getParameters()) {
			doAddTypeToImportIfNecessary(parameter.getType(), importableTypes);
		}
		
		if(this.optionalCodeAttribute.isPresent()) {
			final List<String> typeNames = Instructions.findTypeNames(this.classFile, this.optionalCodeAttribute.get());
			
			for(final String typeName : typeNames) {
				doAddTypeToImportIfNecessary(Type.valueOf(typeName), importableTypes);
			}
		}
		
		this.importableTypes.clear();
		this.importableTypes.addAll(importableTypes);
		
		return new ArrayList<>(this.importableTypes);
	}
	
	/**
	 * Returns the optional {@link MethodSignature} instance associated with this {@code Method} instance.
	 * 
	 * @return the optional {@code MethodSignature} instance associated with this {@code Method} instance
	 */
	public Optional<MethodSignature> getMethodSignature() {
		return MethodSignature.parseMethodSignatureOptionally(this.classFile, this.methodInfo);
	}
	
	/**
	 * Returns the {@link ParameterList} instance associated with this {@code Method} instance.
	 * 
	 * @return the {@code ParameterList} instance associated with this {@code Method} instance
	 */
	public ParameterList getParameterList() {
		return this.parameterList;
	}
	
	/**
	 * Returns the name of this {@code Method} instance.
	 * 
	 * @return the name of this {@code Method} instance
	 */
	public String getName() {
		return ConstantUTF8Info.findByNameIndex(this.classFile, this.methodInfo).getStringValue();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Method} instance.
	 * 
	 * @return a {@code String} representation of this {@code Method} instance
	 */
	@Override
	public String toString() {
		return "Method";
	}
	
	/**
	 * Returns the enclosing {@link Type} instance associated with this {@code Method} instance.
	 * 
	 * @return the enclosing {@code Type} instance associated with this {@code Method} instance
	 */
	public Type getEnclosingType() {
		return this.enclosingType;
	}
	
	/**
	 * Returns the return {@link Type} instance associated with this {@code Method} instance.
	 * 
	 * @return the return {@code Type} instance associated with this {@code Method} instance
	 */
	public Type getReturnType() {
		return this.returnType;
	}
	
	/**
	 * Compares {@code object} to this {@code Method} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Method}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Method} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Method}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Method)) {
			return false;
		} else if(!Objects.equals(this.classFile, Method.class.cast(object).classFile)) {
			return false;
		} else if(!Objects.equals(this.methodInfo, Method.class.cast(object).methodInfo)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Method} instance is abstract, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Method} instance is abstract, {@code false} otherwise
	 */
	public boolean isAbstract() {
		return this.methodInfo.isAbstract();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Method} instance is deprecated, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Method} instance is deprecated, {@code false} otherwise
	 */
	public boolean isDeprecated() {
		return DeprecatedAttribute.find(this.methodInfo).isPresent();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Method} instance is final, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Method} instance is final, {@code false} otherwise
	 */
	public boolean isFinal() {
		return this.methodInfo.isFinal();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Method} instance is enclosed by an interface type, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Method} instance is enclosed by an interface type, {@code false} otherwise
	 */
	public boolean isEnclosedByInterface() {
		return this.enclosingType instanceof InterfaceType;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Method} instance is native, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Method} instance is native, {@code false} otherwise
	 */
	public boolean isNative() {
		return this.methodInfo.isNative();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Method} instance is package protected, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Method} instance is package protected, {@code false} otherwise
	 */
	public boolean isPackageProtected() {
		return !isPrivate() && !isProtected() && !isPublic();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Method} instance is private, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Method} instance is private, {@code false} otherwise
	 */
	public boolean isPrivate() {
		return this.methodInfo.isPrivate();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Method} instance is protected, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Method} instance is protected, {@code false} otherwise
	 */
	public boolean isProtected() {
		return this.methodInfo.isProtected();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Method} instance is public, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Method} instance is public, {@code false} otherwise
	 */
	public boolean isPublic() {
		return this.methodInfo.isPublic();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Method} instance has a signature that is equal to the signature of {@code method}, {@code false} otherwise.
	 * <p>
	 * If {@code method} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param method a {@code Method} instance
	 * @return {@code true} if, and only if, this {@code Method} instance has a signature that is equal to the signature of {@code method}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code method} is {@code null}
	 */
	public boolean isSignatureEqualTo(final Method method) {
		final Method methodThis = this;
		final Method methodThat = method;
		
		final String nameThis = methodThis.getName();
		final String nameThat = methodThat.getName();
		
		final ClassFile classFileThis = methodThis.classFile;
		final ClassFile classFileThat = methodThat.classFile;
		
		final MethodInfo methodInfoThis = methodThis.methodInfo;
		final MethodInfo methodInfoThat = methodThat.methodInfo;
		
		final MethodDescriptor methodDescriptorThis = MethodDescriptor.parseMethodDescriptor(classFileThis, methodInfoThis);
		final MethodDescriptor methodDescriptorThat = MethodDescriptor.parseMethodDescriptor(classFileThat, methodInfoThat);
		
		final List<ParameterDescriptor> parameterDescriptorsThis = methodDescriptorThis.getParameterDescriptors();
		final List<ParameterDescriptor> parameterDescriptorsThat = methodDescriptorThat.getParameterDescriptors();
		
		return nameThis.equals(nameThat) && parameterDescriptorsThis.equals(parameterDescriptorsThat);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Method} instance is static, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Method} instance is static, {@code false} otherwise
	 */
	public boolean isStatic() {
		return this.methodInfo.isStatic();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Method} instance is strict, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Method} instance is strict, {@code false} otherwise
	 */
	public boolean isStrict() {
		return this.methodInfo.isStrict();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Method} instance is synchronized, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Method} instance is synchronized, {@code false} otherwise
	 */
	public boolean isSynchronized() {
		return this.methodInfo.isSynchronized();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Method} instance is synthetic, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Method} instance is synthetic, {@code false} otherwise
	 */
	public boolean isSynthetic() {
		return this.methodInfo.isSynthetic();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Method} instance is varargs, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Method} instance is varargs, {@code false} otherwise
	 */
	public boolean isVarargs() {
		return this.methodInfo.isVarargs();
	}
	
	/**
	 * Compares this {@code Method} instance with {@code method} for order.
	 * <p>
	 * Returns a negative integer, zero or a positive integer as this {@code Method} instance is less than, equal to or greater than {@code method}.
	 * <p>
	 * If {@code method} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param method a {@code Method} instance
	 * @return a negative integer, zero or a positive integer as this {@code Method} instance is less than, equal to or greater than {@code method}
	 * @throws NullPointerException thrown if, and only if, {@code method} is {@code null}
	 */
	@Override
	public int compareTo(final Method method) {
		final Method methodThis = this;
		final Method methodThat = method;
		
		final boolean isPublicThis = methodThis.isPublic();
		final boolean isPublicThat = methodThat.isPublic();
		
		if(isPublicThis != isPublicThat) {
			return isPublicThis ? -1 : 1;
		}
		
		final boolean isProtectedThis = methodThis.isProtected();
		final boolean isProtectedThat = methodThat.isProtected();
		
		if(isProtectedThis != isProtectedThat) {
			return isProtectedThis ? -1 : 1;
		}
		
		final boolean isPackageProtectedThis = methodThis.isPackageProtected();
		final boolean isPackageProtectedThat = methodThat.isPackageProtected();
		
		if(isPackageProtectedThis != isPackageProtectedThat) {
			return isPackageProtectedThis ? -1 : 1;
		}
		
		final boolean isPrivateThis = methodThis.isPrivate();
		final boolean isPrivateThat = methodThat.isPrivate();
		
		if(isPrivateThis != isPrivateThat) {
			return isPrivateThis ? -1 : 1;
		}
		
		final boolean isStaticThis = methodThis.isStatic();
		final boolean isStaticThat = methodThat.isStatic();
		
		if(isStaticThis != isStaticThat) {
			return isStaticThis ? 1 : -1;
		}
		
		final int returnType = methodThis.getReturnType().getSimpleName().compareTo(methodThat.getReturnType().getSimpleName());
		
		if(returnType != 0) {
			return returnType;
		}
		
		final int name = methodThis.getName().compareTo(methodThat.getName());
		
		if(name != 0) {
			return name;
		}
		
		return methodThis.getParameterList().compareTo(methodThat.getParameterList());
	}
	
	/**
	 * Returns a hash code for this {@code Method} instance.
	 * 
	 * @return a hash code for this {@code Method} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.classFile, this.methodInfo);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, {@code methodA} and {@code methodB} are in different groups, {@code false} otherwise.
	 * <p>
	 * If either {@code methodA} or {@code methodB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param methodA a {@code Method} instance
	 * @param methodB a {@code Method} instance
	 * @return {@code true} if, and only if, {@code methodA} and {@code methodB} are in different groups, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code methodA} or {@code methodB} are {@code null}
	 */
	public static boolean inDifferentGroups(final Method methodA, final Method methodB) {
		if(methodA.isPublic() != methodB.isPublic()) {
			return true;
		}
		
		if(methodA.isProtected() != methodB.isProtected()) {
			return true;
		}
		
		if(methodA.isPackageProtected() != methodB.isPackageProtected()) {
			return true;
		}
		
		if(methodA.isPrivate() != methodB.isPrivate()) {
			return true;
		}
		
		if(methodA.isStatic() != methodB.isStatic()) {
			return true;
		}
		
		return false;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doAddTypeToImportIfNecessary(final Type importableType, final Set<Type> importableTypes) {
		Type type = importableType;
		
		while(type instanceof ArrayType) {
			type = ArrayType.class.cast(type).getComponentType();
		}
		
		if(type instanceof PrimitiveType) {
			return;
		}
		
		if(type instanceof VoidType) {
			return;
		}
		
		final String packageNameThis = getEnclosingType().getPackageName();
		final String packageNameType = type.getPackageName();
		
		if(packageNameType.equals("java.lang")) {
			return;
		}
		
		if(packageNameType.equals(packageNameThis)) {
			return;
		}
		
		importableTypes.add(type);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static List<Modifier> doDiscardInterfaceMethodModifiers(final DecompilerConfiguration decompilerConfiguration, final Type enclosingType, final List<Modifier> oldModifiers) {
		final boolean isDiscardingAbstractInterfaceMethodModifier = decompilerConfiguration.isDiscardingAbstractInterfaceMethodModifier();
		final boolean isDiscardingPublicInterfaceMethodModifier = decompilerConfiguration.isDiscardingPublicInterfaceMethodModifier();
		
		final List<Modifier> newModifiers = new ArrayList<>();
		
		for(final Modifier oldModifier : oldModifiers) {
			if(enclosingType instanceof InterfaceType && oldModifier == Modifier.ABSTRACT && isDiscardingAbstractInterfaceMethodModifier) {
				continue;
			} else if(enclosingType instanceof InterfaceType && oldModifier == Modifier.PUBLIC && isDiscardingPublicInterfaceMethodModifier) {
				continue;
			} else {
				newModifiers.add(oldModifier);
			}
		}
		
		return newModifiers;
	}
}