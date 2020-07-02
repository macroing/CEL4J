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
import org.macroing.cel4j.java.binary.classfile.attributeinfo.MethodParametersAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.Parameter;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.java.binary.classfile.descriptor.MethodDescriptor;
import org.macroing.cel4j.java.binary.classfile.descriptor.ParameterDescriptor;
import org.macroing.cel4j.java.binary.classfile.descriptor.ReturnDescriptor;
import org.macroing.cel4j.java.binary.classfile.signature.JavaTypeSignature;
import org.macroing.cel4j.java.binary.classfile.signature.MethodSignature;
import org.macroing.cel4j.java.binary.classfile.signature.Result;
import org.macroing.cel4j.java.binary.classfile.signature.TypeParameters;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

final class JMethod {
	private final ClassFile classFile;
	private final JType enclosingType;
	private final JType returnType;
	private final MethodInfo methodInfo;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	JMethod(final ClassFile classFile, final MethodInfo methodInfo, final JType enclosingType) {
		this.classFile = Objects.requireNonNull(classFile, "classFile == null");
		this.methodInfo = Objects.requireNonNull(methodInfo, "methodInfo == null");
		this.enclosingType = Objects.requireNonNull(enclosingType, "enclosingType == null");
		this.returnType = JType.valueOf(doGetReturnTypeName(classFile, methodInfo));
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
		
		final JType enclosingType = getEnclosingType();
		
		final List<JType> typesToImport = getTypesToImport();
		
		final String modifiers = Strings.optional(doDiscardInterfaceMethodModifiers(decompilerConfiguration, enclosingType, getModifiers()), "", " ", " ", modifier -> modifier.getKeyword());
		final String returnType = doGenerateReturnTypeWithOptionalTypeParameters(decompilerConfiguration, this, typesToImport);
		final String name = getName();
		final String parameters = doGenerateParameters(decompilerConfiguration, this, typesToImport);
		final String returnStatement = doGenerateDefaultReturnStatement(this);
		
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
	
	public JType getEnclosingType() {
		return this.enclosingType;
	}
	
	public JType getReturnType() {
		return this.returnType;
	}
	
	public List<AttributeInfo> getAttributeInfos() {
		return new ArrayList<>(this.methodInfo.getAttributeInfos());
	}
	
	public List<Instruction> getInstructions() {
		final Optional<CodeAttribute> optionalCodeAttribute = CodeAttribute.find(this.methodInfo);
		
		if(optionalCodeAttribute.isPresent()) {
			final CodeAttribute codeAttribute = optionalCodeAttribute.get();
			
			return codeAttribute.getInstructions();
		}
		
		return new ArrayList<>();
	}
	
	public List<JModifier> getModifiers() {
		final List<JModifier> modifiers = new ArrayList<>();
		
		if(isPrivate()) {
			modifiers.add(JModifier.PRIVATE);
		} else if(isProtected()) {
			modifiers.add(JModifier.PROTECTED);
		} else if(isPublic()) {
			modifiers.add(JModifier.PUBLIC);
		}
		
		if(isStatic()) {
			modifiers.add(JModifier.STATIC);
		}
		
		if(isEnclosedByInterface() && !isAbstract() && !isStatic()) {
			modifiers.add(JModifier.DEFAULT);
		}
		
		if(isAbstract()) {
			modifiers.add(JModifier.ABSTRACT);
		} else if(isFinal()) {
			modifiers.add(JModifier.FINAL);
		}
		
		if(isSynchronized()) {
			modifiers.add(JModifier.SYNCHRONIZED);
		}
		
		if(isNative()) {
			modifiers.add(JModifier.NATIVE);
		}
		
		if(isStrict()) {
			modifiers.add(JModifier.STRICT_F_P);
		}
		
		return modifiers;
	}
	
	public List<JParameter> getParameters() {
		return getParameters((index, fullyQualifiedName) -> "localVariable" + index);
	}
	
	public List<JParameter> getParameters(final JLocalVariableNameGenerator jLocalVariableNameGenerator) {
		Objects.requireNonNull(jLocalVariableNameGenerator, "jLocalVariableNameGenerator == null");
		
		final List<JParameter> jParameters = new ArrayList<>();
		
		final MethodDescriptor methodDescriptor = MethodDescriptor.parseMethodDescriptor(this.classFile, this.methodInfo);
		
		final List<ParameterDescriptor> parameterDescriptors = methodDescriptor.getParameterDescriptors();
		
		final Optional<MethodParametersAttribute> optionalMethodParametersAttribute = MethodParametersAttribute.find(this.methodInfo);
		
		if(optionalMethodParametersAttribute.isPresent()) {
			final MethodParametersAttribute methodParametersAttribute = optionalMethodParametersAttribute.get();
			
			final List<Parameter> parameters = methodParametersAttribute.getParameters();
			
			for(int i = 0; i < parameters.size(); i++) {
				final Parameter parameter = parameters.get(i);
				
				final ParameterDescriptor parameterDescriptor = parameterDescriptors.get(i);
				
				final JType type = JType.valueOf(doGetParameterTypeName(parameterDescriptor));
				
				final int nameIndex = parameter.getNameIndex();
				
				final String name = nameIndex != 0 ? this.classFile.getCPInfo(nameIndex, ConstantUTF8Info.class).getStringValue() : jLocalVariableNameGenerator.generateLocalVariableName(type, i);
				
				final boolean isFinal = parameter.isFinal();
				
				jParameters.add(new JParameter(type, name, isFinal));
			}
		} else {
			for(int i = 0; i < parameterDescriptors.size(); i++) {
				final ParameterDescriptor parameterDescriptor = parameterDescriptors.get(i);
				
				final JType type = JType.valueOf(doGetParameterTypeName(parameterDescriptor));
				
				final String name = jLocalVariableNameGenerator.generateLocalVariableName(type, i);
				
				jParameters.add(new JParameter(type, name));
			}
		}
		
		return jParameters;
	}
	
	public List<JType> getTypesToImport() {
		final Set<JType> typesToImport = new LinkedHashSet<>();
		
		doAddTypeToImportIfNecessary(getReturnType(), typesToImport);
		
		for(final JParameter parameter : getParameters()) {
			doAddTypeToImportIfNecessary(parameter.getType(), typesToImport);
		}
		
		return new ArrayList<>(typesToImport);
	}
	
	public Optional<MethodSignature> getMethodSignature() {
		return MethodSignature.parseMethodSignatureOptionally(this.classFile, this.methodInfo);
	}
	
	public String getName() {
		return ConstantUTF8Info.findByNameIndex(this.classFile, this.methodInfo).getStringValue();
	}
	
	@Override
	public String toString() {
		return String.format("JMethod: [Name=%s], [ReturnType=%s], [Parameters=%s]", getName(), getReturnType(), getParameters());
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JMethod)) {
			return false;
		} else if(!Objects.equals(this.classFile, JMethod.class.cast(object).classFile)) {
			return false;
		} else if(!Objects.equals(this.methodInfo, JMethod.class.cast(object).methodInfo)) {
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
		return this.enclosingType instanceof JInterface;
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
	
	public boolean isSignatureEqualTo(final JMethod jMethod) {
		final JMethod jMethodThis = this;
		final JMethod jMethodThat = jMethod;
		
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
	public int hashCode() {
		return Objects.hash(this.classFile, this.methodInfo);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doAddTypeToImportIfNecessary(final JType typeToImport, final Set<JType> typesToImport) {
		JType type = typeToImport;
		
		while(type instanceof JArray) {
			type = JArray.class.cast(type).getComponentType();
		}
		
		if(type instanceof JPrimitive) {
			return;
		}
		
		if(type instanceof JVoid) {
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
	
	private static List<JModifier> doDiscardInterfaceMethodModifiers(final DecompilerConfiguration decompilerConfiguration, final JType enclosingType, final List<JModifier> oldModifiers) {
		final boolean isDiscardingAbstractInterfaceMethodModifier = decompilerConfiguration.isDiscardingAbstractInterfaceMethodModifier();
		final boolean isDiscardingPublicInterfaceMethodModifier = decompilerConfiguration.isDiscardingPublicInterfaceMethodModifier();
		
		final List<JModifier> newModifiers = new ArrayList<>();
		
		for(final JModifier oldModifier : oldModifiers) {
			if(enclosingType instanceof JInterface && oldModifier == JModifier.ABSTRACT && isDiscardingAbstractInterfaceMethodModifier) {
				continue;
			} else if(enclosingType instanceof JInterface && oldModifier == JModifier.PUBLIC && isDiscardingPublicInterfaceMethodModifier) {
				continue;
			} else {
				newModifiers.add(oldModifier);
			}
		}
		
		return newModifiers;
	}
	
	private static String doGenerateDefaultReturnStatement(final JMethod jMethod) {
		final JType returnType = jMethod.getReturnType();
		
		if(returnType instanceof JVoid) {
			return "";
		} else if(returnType instanceof JPrimitive) {
			final JPrimitive jPrimitive = JPrimitive.class.cast(returnType);
			
			if(jPrimitive.equals(JPrimitive.BOOLEAN)) {
				return "return false;";
			} else if(jPrimitive.equals(JPrimitive.BYTE)) {
				return "return 0;";
			} else if(jPrimitive.equals(JPrimitive.CHAR)) {
				return "return '\\u0000';";
			} else if(jPrimitive.equals(JPrimitive.DOUBLE)) {
				return "return 0.0D;";
			} else if(jPrimitive.equals(JPrimitive.FLOAT)) {
				return "return 0.0F;";
			} else if(jPrimitive.equals(JPrimitive.INT)) {
				return "return 0;";
			} else if(jPrimitive.equals(JPrimitive.LONG)) {
				return "return 0L;";
			} else {
				return "return 0;";
			}
		} else {
			return "return null;";
		}
	}
	
	private static String doGenerateParameters(final DecompilerConfiguration decompilerConfiguration, final JMethod jMethod, final List<JType> typesToImport) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final JLocalVariableNameGenerator jLocalVariableNameGenerator = (type, index) -> decompilerConfiguration.getLocalVariableNameGenerator().generateLocalVariableName(type.getName(), index);
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		final Optional<MethodSignature> optionalMethodSignature = jMethod.getMethodSignature();
		
		final List<JParameter> jParameters = jMethod.getParameters(jLocalVariableNameGenerator);
		
		if(jParameters.size() > 0) {
			final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(jMethod.getEnclosingType().getPackageName(), isDiscardingUnnecessaryPackageNames, typesToImport, isImportingTypes);
			
			if(optionalMethodSignature.isPresent()) {
				final MethodSignature methodSignature = optionalMethodSignature.get();
				
				final List<JavaTypeSignature> javaTypeSignatures = methodSignature.getJavaTypeSignatures();
				
				for(int i = 0; i < javaTypeSignatures.size(); i++) {
					final JParameter jParameter = jParameters.get(i);
					
					final JavaTypeSignature javaTypeSignature = javaTypeSignatures.get(i);
					
					stringBuilder.append(i > 0 ? ", " : "");
					stringBuilder.append(jParameter.isFinal() ? "final " : "");
					stringBuilder.append(Names.filterPackageNames(jPackageNameFilter, javaTypeSignature.toExternalForm()));
					stringBuilder.append(" ");
					stringBuilder.append(jParameter.getName());
				}
			} else {
				for(int i = 0; i < jParameters.size(); i++) {
					final JParameter jParameter = jParameters.get(i);
					
					stringBuilder.append(i > 0 ? ", " : "");
					stringBuilder.append(jParameter.isFinal() ? "final " : "");
					stringBuilder.append(Names.filterPackageNames(jPackageNameFilter, jParameter.getType().getName()));
					stringBuilder.append(" ");
					stringBuilder.append(jParameter.getName());
				}
			}
		}
		
		return stringBuilder.toString();
	}
	
	private static String doGenerateReturnTypeWithOptionalTypeParameters(final DecompilerConfiguration decompilerConfiguration, final JMethod jMethod, final List<JType> typesToImport) {
		final boolean isDiscardingExtendsObject = decompilerConfiguration.isDiscardingExtendsObject();
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final Optional<MethodSignature> optionalMethodSignature = jMethod.getMethodSignature();
		
		final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(jMethod.getEnclosingType().getPackageName(), isDiscardingUnnecessaryPackageNames, typesToImport, isImportingTypes);
		
		if(optionalMethodSignature.isPresent()) {
			final MethodSignature methodSignature = optionalMethodSignature.get();
			
			final Optional<TypeParameters> optionalTypeParameters = methodSignature.getTypeParameters();
			
			final Result result = methodSignature.getResult();
			
			final StringBuilder stringBuilder = new StringBuilder();
			
			if(optionalTypeParameters.isPresent()) {
				final TypeParameters typeParameters = optionalTypeParameters.get();
				
				final String string = isDiscardingExtendsObject ? typeParameters.toExternalForm().replaceAll(" extends java\\.lang\\.Object", "") : typeParameters.toExternalForm();
				
				stringBuilder.append(Names.filterPackageNames(jPackageNameFilter, string));
				stringBuilder.append(" ");
			}
			
			stringBuilder.append(Names.filterPackageNames(jPackageNameFilter, result.toExternalForm(), jMethod.getReturnType().isInnerType()));
			
			return stringBuilder.toString();
		}
		
		return Names.filterPackageNames(jPackageNameFilter, jMethod.getReturnType().getName(), jMethod.getReturnType().isInnerType());
	}
	
	private static String doGetParameterTypeName(final ParameterDescriptor parameterDescriptor) {
		final String externalForm = parameterDescriptor.toExternalForm();
		final String internalForm = parameterDescriptor.toInternalForm();
		final String parameterTypeName = internalForm.indexOf('[') >= 0 ? internalForm.replace('/', '.') : externalForm;
		
		return parameterTypeName;
	}
	
	private static String doGetReturnTypeName(final ClassFile classFile, final MethodInfo methodInfo) {
		final MethodDescriptor methodDescriptor = MethodDescriptor.parseMethodDescriptor(classFile, methodInfo);
		
		final ReturnDescriptor returnDescriptor = methodDescriptor.getReturnDescriptor();
		
		final String externalForm = returnDescriptor.toExternalForm();
		final String internalForm = returnDescriptor.toInternalForm();
		final String returnTypeName = internalForm.indexOf('[') >= 0 ? internalForm.replace('/', '.') : externalForm;
		
		return returnTypeName;
	}
}