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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.FieldInfo;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantClassInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.java.binary.classfile.descriptor.ClassName;
import org.macroing.cel4j.java.binary.classfile.signature.ClassSignature;
import org.macroing.cel4j.java.binary.classfile.signature.SuperClassSignature;
import org.macroing.cel4j.java.binary.classfile.signature.SuperInterfaceSignature;
import org.macroing.cel4j.java.binary.classfile.signature.TypeParameters;
import org.macroing.cel4j.java.binary.reader.ClassFileReader;
import org.macroing.cel4j.node.NodeFormatException;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

final class JInterface extends JType {
	private static final Map<String, ClassFile> CLASS_FILES = new HashMap<>();
	private static final Map<String, JInterface> J_INTERFACES = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final AtomicBoolean hasInitialized;
	private final Class<?> associatedClass;
	private final ClassFile associatedClassFile;
	private final List<JField> fields;
	private final List<JInterface> interfaces;
	private final List<JMethod> methods;
	private final List<JModifier> modifiers;
	private final List<SuperInterfaceSignature> superInterfaceSignatures;
	private final Optional<ClassSignature> optionalClassSignature;
	private final Optional<SuperClassSignature> optionalSuperClassSignature;
	private final Optional<TypeParameters> optionalTypeParameters;
	private final String name;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private JInterface(final Class<?> associatedClass, final ClassFile associatedClassFile) {
		this.hasInitialized = new AtomicBoolean();
		this.associatedClass = associatedClass;
		this.associatedClassFile = associatedClassFile;
		this.fields = new ArrayList<>();
		this.interfaces = new ArrayList<>();
		this.methods = new ArrayList<>();
		this.modifiers = new ArrayList<>();
		this.superInterfaceSignatures = new ArrayList<>();
		this.optionalClassSignature = ClassSignature.parseClassSignatureOptionally(this.associatedClassFile);
		this.optionalSuperClassSignature = this.optionalClassSignature.isPresent() ? Optional.of(this.optionalClassSignature.get().getSuperClassSignature()) : Optional.empty();
		this.optionalTypeParameters = this.optionalClassSignature.isPresent() ? this.optionalClassSignature.get().getTypeParameters() : Optional.empty();
		this.name = ClassName.parseClassNameThisClass(this.associatedClassFile).toExternalForm();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Class<?> getAssociatedClass() {
		return this.associatedClass;
	}
	
	public ClassFile getAssociatedClassFile() {
		return this.associatedClassFile;
	}
	
	public Document decompile() {
		return decompile(new DecompilerConfiguration());
	}
	
	public Document decompile(final DecompilerConfiguration decompilerConfiguration) {
		return decompile(decompilerConfiguration, new Document());
	}
	
	public Document decompile(final DecompilerConfiguration decompilerConfiguration, final Document document) {
		Objects.requireNonNull(decompilerConfiguration, "decompilerConfiguration == null");
		Objects.requireNonNull(document, "document == null");
		
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		final boolean isSeparatingGroups = decompilerConfiguration.isSeparatingGroups();
		
		final List<JType> typesToImport = getTypesToImport();
		
		final String packageName = getPackageName();
		final String modifiers = Strings.optional(getModifiers(), "", " ", " ", modifier -> modifier.getKeyword());
		final String simpleName = getSimpleName();
		final String typeParameters = doGenerateTypeParameters(decompilerConfiguration, typesToImport, getTypeParameters());
		final String extendsClause = doGenerateExtendsClause(decompilerConfiguration, getInterfaces(), typesToImport, getClassSignature(), getPackageName());
		
		final List<JField> jFields = getFields();
		final List<JMethod> jMethods = getMethods();
		
		document.linef("package %s;", packageName);
		
		if(isImportingTypes && typesToImport.size() > 0) {
			document.line();
			
			for(final JType typeToImport : typesToImport) {
				document.linef("import %s;", typeToImport.getName());
			}
		}
		
		document.linef("");
		document.linef("%sinterface %s%s%s {", modifiers, simpleName, typeParameters, extendsClause);
		document.indent();
		
		for(final JField jField : jFields) {
			jField.decompile(decompilerConfiguration, document);
		}
		
		if(jFields.size() > 0 && jMethods.size() > 0) {
			document.line();
			
			if(isSeparatingGroups) {
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line("");
			}
		}
		
		for(int i = 0; i < jMethods.size(); i++) {
			if(i > 0) {
				document.line();
			}
			
			final
			JMethod jMethod = jMethods.get(i);
			jMethod.decompile(decompilerConfiguration, document);
		}
		
		document.outdent();
		document.linef("}");
		
		return document;
	}
	
	public List<JField> getFields() {
		return this.fields;
	}
	
	public List<JInterface> getInterfaces() {
		return this.interfaces;
	}
	
	public List<JMethod> getMethods() {
		return this.methods;
	}
	
	public List<JModifier> getModifiers() {
		return new ArrayList<>(this.modifiers);
	}
	
	public List<JType> getTypesToImport() {
		final Set<JType> typesToImport = new LinkedHashSet<>();
		
		for(final JField field : this.fields) {
			doAddTypeToImportIfNecessary(field.getType(), typesToImport);
		}
		
		for(final JInterface jInterface : this.interfaces) {
			doAddTypeToImportIfNecessary(jInterface, typesToImport);
		}
		
		for(final JMethod method : this.methods) {
			final List<JType> methodTypesToImport = method.getTypesToImport();
			
			for(final JType methodTypeToImport : methodTypesToImport) {
				doAddTypeToImportIfNecessary(methodTypeToImport, typesToImport);
			}
		}
		
		final Optional<TypeParameters> optionalTypeParameters = getTypeParameters();
		
		if(optionalTypeParameters.isPresent()) {
			final TypeParameters typeParameters = optionalTypeParameters.get();
			
			final List<String> names = typeParameters.collectNames();
			
			for(final String name : names) {
				final JType jType = JType.valueOf(name);
				
				doAddTypeToImportIfNecessary(jType, typesToImport);
			}
		}
		
		return new ArrayList<>(typesToImport);
	}
	
	public List<SuperInterfaceSignature> getSuperInterfaceSignatures() {
		return new ArrayList<>(this.superInterfaceSignatures);
	}
	
	public Optional<ClassSignature> getClassSignature() {
		return ClassSignature.parseClassSignatureOptionally(this.associatedClassFile);
	}
	
	public Optional<JClass> getSuperClass() {
		return hasSuperClass() ? Optional.of(JClass.valueOf(ClassName.parseClassNameSuperClass(this.associatedClassFile).toExternalForm())) : Optional.empty();
	}
	
	public Optional<SuperClassSignature> getSuperClassSignature() {
		return this.optionalSuperClassSignature;
	}
	
	public Optional<TypeParameters> getTypeParameters() {
		return this.optionalTypeParameters;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JInterface)) {
			return false;
		} else if(!Objects.equals(this.associatedClass, JInterface.class.cast(object).associatedClass)) {
			return false;
		} else if(!Objects.equals(this.associatedClassFile, JInterface.class.cast(object).associatedClassFile)) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public boolean hasMethod(final JMethod jMethod) {
		for(final JMethod jMethod0 : getMethods()) {
			if(jMethod.isSignatureEqualTo(jMethod0)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean hasMethodInherited(final JMethod jMethod) {
		for(final JInterface jInterface : getInterfaces()) {
			if(jInterface.hasMethod(jMethod) || jInterface.hasMethodInherited(jMethod)) {
				return true;
			}
		}
		
		final Optional<JClass> optionalSuperClass = getSuperClass();
		
		if(optionalSuperClass.isPresent()) {
			final JClass superClass = optionalSuperClass.get();
			
			if(superClass.hasMethod(jMethod) || superClass.hasMethodInherited(jMethod)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasSuperClass() {
		return this.associatedClassFile.getSuperClass() >= 1;
	}
	
	@Override
	public boolean isInnerType() {
		return false;//TODO: Implement!
	}
	
	public boolean isPublic() {
		return this.associatedClassFile.isPublic();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.associatedClass, this.associatedClassFile);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static JInterface valueOf(final Class<?> associatedClass) {
		if(!associatedClass.isInterface()) {
			throw new JTypeException(String.format("A JInterface must refer to an interface: %s", associatedClass));
		}
		
		try {
			synchronized(J_INTERFACES) {
				final
				JInterface jInterface = J_INTERFACES.computeIfAbsent(associatedClass.getName(), name -> new JInterface(associatedClass, CLASS_FILES.computeIfAbsent(name, name0 -> new ClassFileReader().read(associatedClass))));
				jInterface.doInitialize();
				
				return jInterface;
			}
		} catch(final NodeFormatException e) {
			throw new JTypeException(e);
		}
	}
	
	public static JInterface valueOf(final String name) {
		try {
			return valueOf(Class.forName(name));
		} catch(final ClassNotFoundException | LinkageError e) {
			throw new JTypeException(e);
		}
	}
	
	public static void clearCache() {
		synchronized(J_INTERFACES) {
			J_INTERFACES.clear();
			
			CLASS_FILES.clear();
		}
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
		
		final String packageNameThis = getPackageName();
		final String packageNameType = type.getPackageName();
		
		if(packageNameType.equals("java.lang")) {
			return;
		}
		
		if(packageNameType.equals(packageNameThis)) {
			return;
		}
		
		typesToImport.add(type);
	}
	
	private void doInitialize() {
		if(this.hasInitialized.compareAndSet(false, true)) {
			doInitializeFields();
			doInitializeInterfaces();
			doInitializeMethods();
			doInitializeModifiers();
			doInitializeSuperInterfaceSignatures();
		}
	}
	
	private void doInitializeFields() {
		for(final FieldInfo fieldInfo : this.associatedClassFile.getFieldInfos()) {
			if(fieldInfo.isInterfaceCompatible()) {
				this.fields.add(new JField(this.associatedClassFile, fieldInfo, this));
			}
		}
	}
	
	private void doInitializeInterfaces() {
		final List<Integer> interfaceIndices = this.associatedClassFile.getInterfaces();
		
		for(final int interfaceIndex : interfaceIndices) {
			final String interfaceNameInternalForm = ConstantUTF8Info.findByNameIndex(this.associatedClassFile, this.associatedClassFile.getCPInfo(interfaceIndex, ConstantClassInfo.class)).getString();
			final String interfaceNameExternalForm = ClassName.parseClassName(interfaceNameInternalForm).toExternalForm();
			
			this.interfaces.add(JInterface.valueOf(interfaceNameExternalForm));
		}
	}
	
	private void doInitializeMethods() {
		for(final MethodInfo methodInfo : this.associatedClassFile.getMethodInfos()) {
			final String name = ConstantUTF8Info.findByNameIndex(this.associatedClassFile, methodInfo).getString();
			
			if(!name.equals("<clinit>") && !name.equals("<init>")) {
				this.methods.add(new JMethod(this.associatedClassFile, methodInfo, this));
			}
		}
	}
	
	private void doInitializeModifiers() {
		final List<JModifier> modifiers = this.modifiers;
		
		if(isPublic()) {
			modifiers.add(JModifier.PUBLIC);
		}
	}
	
	private void doInitializeSuperInterfaceSignatures() {
		final List<SuperInterfaceSignature> superInterfaceSignatures = this.superInterfaceSignatures;
		
		final Optional<ClassSignature> optionalClassSignature = this.optionalClassSignature;
		
		if(optionalClassSignature.isPresent()) {
			final ClassSignature classSignature = optionalClassSignature.get();
			
			superInterfaceSignatures.addAll(classSignature.getSuperInterfaceSignatures());
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String doGenerateExtendsClause(final DecompilerConfiguration decompilerConfiguration, final List<JInterface> jInterfaces, final List<JType> typesToImport, final Optional<ClassSignature> optionalClassSignature, final String packageName) {
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		if(jInterfaces.size() > 0) {
			stringBuilder.append(" ");
			stringBuilder.append("extends");
			stringBuilder.append(" ");
			
			final JPackageNameFilter jPackageNameFilter = JPackageNameFilter.newUnnecessaryPackageName(packageName, isDiscardingUnnecessaryPackageNames, typesToImport, isImportingTypes);
			
			if(optionalClassSignature.isPresent()) {
				final ClassSignature classSignature = optionalClassSignature.get();
				
				final List<SuperInterfaceSignature> superInterfaceSignatures = classSignature.getSuperInterfaceSignatures();
				
				for(int i = 0; i < superInterfaceSignatures.size(); i++) {
					final SuperInterfaceSignature superInterfaceSignature = superInterfaceSignatures.get(i);
					
					final String string0 = superInterfaceSignature.toExternalForm();
					final String string1 = isDiscardingUnnecessaryPackageNames ? Names.filterPackageNames(jPackageNameFilter, string0) : string0;
					
					stringBuilder.append(i > 0 ? ", " : "");
					stringBuilder.append(string1);
				}
			} else {
				for(int i = 0; i < jInterfaces.size(); i++) {
					final String string0 = jInterfaces.get(i).getName();
					final String string1 = isDiscardingUnnecessaryPackageNames ? Names.filterPackageNames(jPackageNameFilter, string0) : string0;
					
					stringBuilder.append(i > 0 ? ", " : "");
					stringBuilder.append(string1);
				}
			}
		}
		
		return stringBuilder.toString();
	}
	
	private static String doGenerateTypeParameters(final DecompilerConfiguration decompilerConfiguration, final List<JType> typesToImport, final Optional<TypeParameters> optionalTypeParameters) {
		final boolean isDiscardingExtendsObject = decompilerConfiguration.isDiscardingExtendsObject();
		final boolean isDiscardingUnnecessaryPackageNames = decompilerConfiguration.isDiscardingUnnecessaryPackageNames();
		final boolean isImportingTypes = decompilerConfiguration.isImportingTypes();
		
		if(optionalTypeParameters.isPresent()) {
			final TypeParameters typeParameters = optionalTypeParameters.get();
			
			final String typeParametersToExternalForm = typeParameters.toExternalForm((packageName0, simpleName) -> {
				if(packageName0.equals("java.lang.")) {
					if(simpleName.equals("Object")) {
						return isDiscardingExtendsObject ? null : isDiscardingUnnecessaryPackageNames ? "Object" : packageName0 + simpleName;
					} else if(isDiscardingUnnecessaryPackageNames) {
						return simpleName;
					}
				}
				
				if(isImportingTypes) {
					for(final JType typeToImport : typesToImport) {
						if(typeToImport.getName().equals(packageName0 + simpleName)) {
							return simpleName;
						}
					}
				}
				
				return packageName0 + simpleName;
			});
			
			return typeParametersToExternalForm;
		}
		
		return "";
	}
}