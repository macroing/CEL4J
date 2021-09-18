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

final class Method implements Comparable<Method> {
	private final ClassFile classFile;
	private final ParameterList parameterList;
	private final Type enclosingType;
	private final Type returnType;
	private final List<Type> typesToImport;
	private final MethodInfo methodInfo;
	private final Optional<CodeAttribute> optionalCodeAttribute;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	Method(final ClassFile classFile, final MethodInfo methodInfo, final Type enclosingType) {
		this.classFile = Objects.requireNonNull(classFile, "classFile == null");
		this.methodInfo = Objects.requireNonNull(methodInfo, "methodInfo == null");
		this.enclosingType = Objects.requireNonNull(enclosingType, "enclosingType == null");
		this.parameterList = ParameterList.load(classFile, methodInfo);
		this.returnType = Type.valueOf(MethodDescriptor.parseMethodDescriptor(classFile, methodInfo).getReturnDescriptor());
		this.typesToImport = new ArrayList<>();
		this.optionalCodeAttribute = CodeAttribute.find(this.methodInfo);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Document decompile() {
		return decompile(new DecompilerConfiguration());
	}
	
	public Document decompile(final DecompilerConfiguration decompilerConfiguration) {
		return decompile(decompilerConfiguration, new Document());
	}
	
	public Document decompile(final DecompilerConfiguration decompilerConfiguration, final Document document) {
		Objects.requireNonNull(decompilerConfiguration, "decompilerConfiguration == null");
		Objects.requireNonNull(document, "document == null");
		
		final boolean isAnnotatingDeprecatedMethods = decompilerConfiguration.isAnnotatingDeprecatedMethods();
		final boolean isAnnotatingOverriddenMethods = decompilerConfiguration.isAnnotatingOverriddenMethods();
		final boolean isDisplayingAttributeInfos = decompilerConfiguration.isDisplayingAttributeInfos();
		final boolean isDisplayingInstructions = decompilerConfiguration.isDisplayingInstructions();
		
		final ParameterList parameterList = getParameterList();
		final Type enclosingType = getEnclosingType();
		
		final List<Type> typesToImport = getTypesToImport();
		
		final String modifiers = Strings.optional(doDiscardInterfaceMethodModifiers(decompilerConfiguration, enclosingType, getModifiers()), "", " ", " ", modifier -> modifier.getKeyword());
		final String returnType = UtilitiesToRefactor.generateReturnTypeWithOptionalTypeParameters(decompilerConfiguration, this, typesToImport);
		final String name = getName();
		final String parameters = parameterList.toExternalForm(decompilerConfiguration, this, typesToImport);
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
	
	public ParameterList getParameterList() {
		return this.parameterList;
	}
	
	public Type getEnclosingType() {
		return this.enclosingType;
	}
	
	public Type getReturnType() {
		return this.returnType;
	}
	
	public List<AttributeInfo> getAttributeInfos() {
		return this.methodInfo.getAttributeInfos();
	}
	
	public List<Instruction> getInstructions() {
		if(this.optionalCodeAttribute.isPresent()) {
			final CodeAttribute codeAttribute = this.optionalCodeAttribute.get();
			
			return codeAttribute.getInstructions();
		}
		
		return new ArrayList<>();
	}
	
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
	
	public List<Type> getTypesToImport() {
		if(this.typesToImport.size() > 0) {
			return new ArrayList<>(this.typesToImport);
		}
		
		final Set<Type> typesToImport = new LinkedHashSet<>();
		
		doAddTypeToImportIfNecessary(getReturnType(), typesToImport);
		
		for(final Parameter parameter : getParameterList().getParameters()) {
			doAddTypeToImportIfNecessary(parameter.getType(), typesToImport);
		}
		
		if(this.optionalCodeAttribute.isPresent()) {
			final List<String> typeNames = Instructions.findTypeNames(this.classFile, this.optionalCodeAttribute.get());
			
			for(final String typeName : typeNames) {
				doAddTypeToImportIfNecessary(Type.valueOf(typeName), typesToImport);
			}
		}
		
		this.typesToImport.clear();
		this.typesToImport.addAll(typesToImport);
		
		return new ArrayList<>(this.typesToImport);
	}
	
	public Optional<MethodSignature> getMethodSignature() {
		return MethodSignature.parseMethodSignatureOptionally(this.classFile, this.methodInfo);
	}
	
	public String getName() {
		return ConstantUTF8Info.findByNameIndex(this.classFile, this.methodInfo).getStringValue();
	}
	
	@Override
	public String toString() {
		return String.format("JMethod: [Name=%s], [ReturnType=%s], [Parameters=%s]", getName(), getReturnType(), getParameterList().getParameters());
	}
	
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
	
	public boolean isAbstract() {
		return this.methodInfo.isAbstract();
	}
	
	public boolean isDeprecated() {
		return DeprecatedAttribute.find(this.methodInfo).isPresent();
	}
	
	public boolean isFinal() {
		return this.methodInfo.isFinal();
	}
	
	public boolean isEnclosedByInterface() {
		return this.enclosingType instanceof InterfaceType;
	}
	
	public boolean isNative() {
		return this.methodInfo.isNative();
	}
	
	public boolean isPackageProtected() {
		return !isPrivate() && !isProtected() && !isPublic();
	}
	
	public boolean isPrivate() {
		return this.methodInfo.isPrivate();
	}
	
	public boolean isProtected() {
		return this.methodInfo.isProtected();
	}
	
	public boolean isPublic() {
		return this.methodInfo.isPublic();
	}
	
	public boolean isSignatureEqualTo(final Method jMethod) {
		final Method jMethodThis = this;
		final Method jMethodThat = jMethod;
		
		final String nameThis = jMethodThis.getName();
		final String nameThat = jMethodThat.getName();
		
		final ClassFile classFileThis = jMethodThis.classFile;
		final ClassFile classFileThat = jMethodThat.classFile;
		
		final MethodInfo methodInfoThis = jMethodThis.methodInfo;
		final MethodInfo methodInfoThat = jMethodThat.methodInfo;
		
		final MethodDescriptor methodDescriptorThis = MethodDescriptor.parseMethodDescriptor(classFileThis, methodInfoThis);
		final MethodDescriptor methodDescriptorThat = MethodDescriptor.parseMethodDescriptor(classFileThat, methodInfoThat);
		
		final List<ParameterDescriptor> parameterDescriptorsThis = methodDescriptorThis.getParameterDescriptors();
		final List<ParameterDescriptor> parameterDescriptorsThat = methodDescriptorThat.getParameterDescriptors();
		
		return nameThis.equals(nameThat) && parameterDescriptorsThis.equals(parameterDescriptorsThat);
	}
	
	public boolean isStatic() {
		return this.methodInfo.isStatic();
	}
	
	public boolean isStrict() {
		return this.methodInfo.isStrict();
	}
	
	public boolean isSynchronized() {
		return this.methodInfo.isSynchronized();
	}
	
	public boolean isSynthetic() {
		return this.methodInfo.isSynthetic();
	}
	
	public boolean isVarargs() {
		return this.methodInfo.isVarargs();
	}
	
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
	
	@Override
	public int hashCode() {
		return Objects.hash(this.classFile, this.methodInfo);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static boolean isInDifferentGroups(final Method methodA, final Method methodB) {
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
	
	private void doAddTypeToImportIfNecessary(final Type typeToImport, final Set<Type> typesToImport) {
		Type type = typeToImport;
		
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
		
		typesToImport.add(type);
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