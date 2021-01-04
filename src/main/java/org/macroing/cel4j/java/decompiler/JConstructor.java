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
import org.macroing.cel4j.java.binary.classfile.attributeinfo.MethodParametersAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.DeprecatedAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.Parameter;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.java.binary.classfile.descriptor.MethodDescriptor;
import org.macroing.cel4j.java.binary.classfile.descriptor.ParameterDescriptor;
import org.macroing.cel4j.java.binary.classfile.signature.JavaTypeSignature;
import org.macroing.cel4j.java.binary.classfile.signature.MethodSignature;
import org.macroing.cel4j.java.binary.classfile.signature.TypeParameters;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

final class JConstructor {
	private final ClassFile classFile;
	private final JType enclosingType;
	private final MethodInfo methodInfo;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	JConstructor(final ClassFile classFile, final MethodInfo methodInfo, final JType enclosingType) {
		this.classFile = Objects.requireNonNull(classFile, "classFile == null");
		this.methodInfo = Objects.requireNonNull(methodInfo, "methodInfo == null");
		this.enclosingType = Objects.requireNonNull(enclosingType, "enclosingType == null");
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
		
		final boolean isDisplayingAttributeInfos = decompilerConfiguration.isDisplayingAttributeInfos();
		final boolean isDisplayingInstructions = decompilerConfiguration.isDisplayingInstructions();
		
		final String simpleName = getEnclosingType().getSimpleName();
		final String modifiers = Strings.optional(getModifiers(), "", " ", " ", modifier -> modifier.getKeyword());
		final String type = doGenerateTypeWithOptionalTypeParameters(decompilerConfiguration, this, simpleName);
		final String parameters = doGenerateParameters(decompilerConfiguration, this);
		
		final List<Instruction> instructions = getInstructions();
		
		if(isDeprecated()) {
			document.linef("@Deprecated");
		}
		
		document.linef("%s%s(%s) {", modifiers, type, parameters);
		document.indent();
		
		if(isDisplayingAttributeInfos) {
			document.linef("/*");
			
			for(final AttributeInfo attributeInfo : getAttributeInfos()) {
				document.linef(" * %s", attributeInfo.getName());
			}
			
			document.linef(" */");
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
			
			document.linef(" */");
		}
		
		if(!isDisplayingAttributeInfos && !isDisplayingInstructions) {
			document.line();
		}
		
		document.outdent();
		document.linef("}");
		
		return document;
	}
	
	public JType getEnclosingType() {
		return this.enclosingType;
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
	
	public Optional<MethodSignature> getMethodSignature() {
		return MethodSignature.parseMethodSignatureOptionally(this.classFile, this.methodInfo);
	}
	
	public String getName() {
		return getEnclosingType().getName();
	}
	
	@Override
	public String toString() {
		return String.format("JConstructor: [Name=%s], [Parameters=%s]", getName(), getParameters());
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JConstructor)) {
			return false;
		} else if(!Objects.equals(this.classFile, JConstructor.class.cast(object).classFile)) {
			return false;
		} else if(!Objects.equals(this.methodInfo, JConstructor.class.cast(object).methodInfo)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isDeprecated() {
		return DeprecatedAttribute.find(this.methodInfo).isPresent();
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
	
	public boolean isStrict() {
		return this.methodInfo.isStrict();
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
	
	private static String doGenerateParameters(final DecompilerConfiguration decompilerConfiguration, final JConstructor jConstructor) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final JLocalVariableNameGenerator jLocalVariableNameGenerator = (type, index) -> decompilerConfiguration.getLocalVariableNameGenerator().generateLocalVariableName(type.getName(), index);
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		final Optional<MethodSignature> optionalMethodSignature = jConstructor.getMethodSignature();
		
		final List<JParameter> jParameters = jConstructor.getParameters(jLocalVariableNameGenerator);
		
		if(jParameters.size() > 0) {
			final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(jConstructor.getEnclosingType().getPackageName(), isDiscardingUnnecessaryPackageNames, new ArrayList<>(), isImportingTypes);
			
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
	
	private static String doGenerateTypeWithOptionalTypeParameters(final DecompilerConfiguration decompilerConfiguration, final JConstructor jConstructor, final String simpleName) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final Optional<MethodSignature> optionalMethodSignature = jConstructor.getMethodSignature();
		
		final JType enclosingType = jConstructor.getEnclosingType();
		
		if(optionalMethodSignature.isPresent()) {
			final MethodSignature methodSignature = optionalMethodSignature.get();
			
			final Optional<TypeParameters> optionalTypeParameters = methodSignature.getTypeParameters();
			
			final StringBuilder stringBuilder = new StringBuilder();
			
			if(optionalTypeParameters.isPresent()) {
				final TypeParameters typeParameters = optionalTypeParameters.get();
				
				final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(enclosingType.getPackageName(), isDiscardingUnnecessaryPackageNames, new ArrayList<>(), isImportingTypes);
				
				stringBuilder.append(Names.filterPackageNames(jPackageNameFilter, typeParameters.toExternalForm()));
				stringBuilder.append(" ");
			}
			
			stringBuilder.append(Objects.requireNonNull(simpleName, "simpleName == null"));
			
			return stringBuilder.toString();
		}
		
		return Objects.requireNonNull(simpleName, "simpleName == null");
	}
	
	private static String doGetParameterTypeName(final ParameterDescriptor parameterDescriptor) {
		final String externalForm = parameterDescriptor.toExternalForm();
		final String internalForm = parameterDescriptor.toInternalForm();
		final String parameterTypeName = internalForm.indexOf('[') >= 0 ? internalForm.replace('/', '.') : externalForm;
		
		return parameterTypeName;
	}
}