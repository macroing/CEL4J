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
package org.macroing.cel4j.java.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.InnerClassesAttribute;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantClassInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.java.binary.classfile.descriptor.ClassName;
import org.macroing.cel4j.java.binary.classfile.signature.ClassSignature;
import org.macroing.cel4j.java.binary.classfile.signature.SuperClassSignature;
import org.macroing.cel4j.java.binary.classfile.signature.SuperInterfaceSignature;
import org.macroing.cel4j.java.binary.classfile.signature.TypeParameters;
import org.macroing.cel4j.java.binary.classfile.support.MethodInfos;
import org.macroing.cel4j.java.binary.reader.ClassFileReader;
import org.macroing.cel4j.node.NodeFormatException;

/**
 * An {@code InterfaceType} is a {@link Type} implementation that represents an interface type.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class InterfaceType extends Type {
	private static final Map<String, ClassFile> CLASS_FILES = new HashMap<>();
	private static final Map<String, InterfaceType> INTERFACE_TYPES = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final AtomicBoolean hasInitializedClassFile;
	private final AtomicBoolean hasInitializedClassSignature;
	private final AtomicBoolean hasInitializedExternalName;
	private final AtomicBoolean hasInitializedFields;
	private final AtomicBoolean hasInitializedImportableTypes;
	private final AtomicBoolean hasInitializedInterfaceTypes;
	private final AtomicBoolean hasInitializedMethods;
	private final AtomicBoolean hasInitializedModifiers;
	private final AtomicBoolean hasInitializedSuperClassSignature;
	private final AtomicBoolean hasInitializedSuperInterfaceSignatures;
	private final AtomicBoolean hasInitializedTypeParameters;
	private final AtomicReference<ClassFile> classFile;
	private final AtomicReference<ClassSignature> classSignature;
	private final AtomicReference<String> externalName;
	private final AtomicReference<SuperClassSignature> superClassSignature;
	private final AtomicReference<TypeParameters> typeParameters;
	private final Class<?> clazz;
	private final List<Field> fields;
	private final List<InterfaceType> interfaceTypes;
	private final List<Method> methods;
	private final List<Modifier> modifiers;
	private final List<SuperInterfaceSignature> superInterfaceSignatures;
	private final List<Type> importableTypes;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private InterfaceType(final Class<?> clazz) {
		this.hasInitializedClassFile = new AtomicBoolean();
		this.hasInitializedClassSignature = new AtomicBoolean();
		this.hasInitializedExternalName = new AtomicBoolean();
		this.hasInitializedFields = new AtomicBoolean();
		this.hasInitializedImportableTypes = new AtomicBoolean();
		this.hasInitializedInterfaceTypes = new AtomicBoolean();
		this.hasInitializedMethods = new AtomicBoolean();
		this.hasInitializedModifiers = new AtomicBoolean();
		this.hasInitializedSuperClassSignature = new AtomicBoolean();
		this.hasInitializedSuperInterfaceSignatures = new AtomicBoolean();
		this.hasInitializedTypeParameters = new AtomicBoolean();
		this.classFile = new AtomicReference<>();
		this.classSignature = new AtomicReference<>();
		this.externalName = new AtomicReference<>();
		this.superClassSignature = new AtomicReference<>();
		this.typeParameters = new AtomicReference<>();
		this.clazz = clazz;
		this.fields = new ArrayList<>();
		this.interfaceTypes = new ArrayList<>();
		this.methods = new ArrayList<>();
		this.modifiers = new ArrayList<>();
		this.superInterfaceSignatures = new ArrayList<>();
		this.importableTypes = new ArrayList<>();
//		this.optionalSuperClassSignature = this.optionalClassSignature.isPresent() ? Optional.of(this.optionalClassSignature.get().getSuperClassSignature()) : Optional.empty();
//		this.optionalTypeParameters = this.optionalClassSignature.isPresent() ? this.optionalClassSignature.get().getTypeParameters() : Optional.empty();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link ClassFile} instance associated with this {@code InterfaceType} instance.
	 * 
	 * @return the {@code ClassFile} instance associated with this {@code InterfaceType} instance
	 */
	public ClassFile getClassFile() {
		doInitializeClassFile();
		
		return this.classFile.get();
	}
	
	/**
	 * Returns a {@code List} that contains all {@link AttributeInfo} instances associated with this {@code InterfaceType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code InterfaceType} instance.
	 * 
	 * @return a {@code List} that contains all {@code AttributeInfo} instances associated with this {@code InterfaceType} instance
	 */
	public List<AttributeInfo> getAttributeInfos() {
		return getClassFile().getAttributeInfos();
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Field} instances associated with this {@code InterfaceType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code InterfaceType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Field} instances associated with this {@code InterfaceType} instance
	 */
	public List<Field> getFields() {
		doInitializeFields();
		
		return this.fields;
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Field} instances associated with this {@code InterfaceType} instance sorted according to their natural order.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code InterfaceType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Field} instances associated with this {@code InterfaceType} instance sorted according to their natural order
	 */
	public List<Field> getFieldsSorted() {
		return getFields().stream().sorted().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link InterfaceType} instances associated with this {@code InterfaceType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code InterfaceType} instance.
	 * 
	 * @return a {@code List} that contains all {@code InterfaceType} instances associated with this {@code InterfaceType} instance
	 */
	public List<InterfaceType> getInterfaceTypes() {
		doInitializeInterfaceTypes();
		
		return this.interfaceTypes;
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Method} instances associated with this {@code InterfaceType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code InterfaceType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Method} instances associated with this {@code InterfaceType} instance
	 */
	public List<Method> getMethods() {
		doInitializeMethods();
		
		return this.methods;
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Method} instances associated with this {@code InterfaceType} instance sorted according to their natural order.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code InterfaceType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Method} instances associated with this {@code InterfaceType} instance sorted according to their natural order
	 */
	public List<Method> getMethodsSorted() {
		return getMethods().stream().sorted().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Modifier} instances associated with this {@code InterfaceType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code InterfaceType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Modifier} instances associated with this {@code InterfaceType} instance
	 */
	public List<Modifier> getModifiers() {
		doInitializeModifiers();
		
		return new ArrayList<>(this.modifiers);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Type} instances associated with this {@code InterfaceType} instance that are importable.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code InterfaceType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Type} instances associated with this {@code InterfaceType} instance that are importable
	 */
	@Override
	public List<Type> getImportableTypes() {
		doInitializeImportableTypes();
		
		return new ArrayList<>(this.importableTypes);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link SuperInterfaceSignature} instances associated with this {@code InterfaceType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code InterfaceType} instance.
	 * 
	 * @return a {@code List} that contains all {@code SuperInterfaceSignature} instances associated with this {@code InterfaceType} instance
	 */
	public List<SuperInterfaceSignature> getSuperInterfaceSignatures() {
		doInitializeSuperInterfaceSignatures();
		
		return new ArrayList<>(this.superInterfaceSignatures);
	}
	
	/**
	 * Returns the optional {@link ClassSignature} instance associated with this {@code InterfaceType} instance.
	 * 
	 * @return the optional {@code ClassSignature} instance associated with this {@code InterfaceType} instance
	 */
	public Optional<ClassSignature> getOptionalClassSignature() {
		doInitializeClassSignature();
		
		return Optional.ofNullable(this.classSignature.get());
	}
	
	/**
	 * Returns the optional super {@code ClassType} instance associated with this {@code InterfaceType} instance.
	 * 
	 * @return the optional super {@code ClassType} instance associated with this {@code InterfaceType} instance
	 */
	public Optional<ClassType> getOptionalSuperClassType() {
		return hasSuperClass() ? Optional.of(ClassType.valueOf(ClassName.parseClassNameSuperClass(getClassFile()).toExternalForm())) : Optional.empty();
	}
	
	/**
	 * Returns the optional {@link SuperClassSignature} instance associated with this {@code InterfaceType} instance.
	 * 
	 * @return the optional {@code SuperClassSignature} instance associated with this {@code InterfaceType} instance
	 */
	public Optional<SuperClassSignature> getOptionalSuperClassSignature() {
		doInitializeSuperClassSignature();
		
		return Optional.ofNullable(this.superClassSignature.get());
	}
	
	/**
	 * Returns the optional {@link TypeParameters} instance associated with this {@code InterfaceType} instance.
	 * 
	 * @return the optional {@code TypeParameters} instance associated with this {@code InterfaceType} instance
	 */
	public Optional<TypeParameters> getOptionalTypeParameters() {
		doInitializeTypeParameters();
		
		return Optional.ofNullable(this.typeParameters.get());
	}
	
	/**
	 * Returns the external name of this {@code InterfaceType} instance.
	 * 
	 * @return the external name of this {@code InterfaceType} instance
	 */
	@Override
	public String getExternalName() {
		doInitializeExternalName();
		
		return this.externalName.get();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code InterfaceType} instance.
	 * 
	 * @return a {@code String} representation of this {@code InterfaceType} instance
	 */
	@Override
	public String toString() {
		return String.format("InterfaceType.valueOf(%s.class)", getExternalName());
	}
	
	/**
	 * Compares {@code object} to this {@code InterfaceType} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code InterfaceType}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code InterfaceType} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code InterfaceType}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof InterfaceType)) {
			return false;
		} else if(!Objects.equals(getClassFile(), InterfaceType.class.cast(object).getClassFile())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code InterfaceType} instance has {@link Field} instances, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code InterfaceType} instance has {@code Field} instances, {@code false} otherwise
	 */
	public boolean hasFields() {
		return getFields().size() > 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code InterfaceType} instance has a {@link Method} instance with a signature that is equal to the signature of {@code method}, {@code false} otherwise.
	 * <p>
	 * If {@code method} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method will only check the current {@code InterfaceType} instance. The method {@link #hasMethodInherited(Method)} checks in extended {@code InterfaceType} instances and the optionally extended {@link ClassType} instance.
	 * 
	 * @param method a {@code Method} instance
	 * @return {@code true} if, and only if, this {@code InterfaceType} instance has a {@code Method} instance with a signature that is equal to the signature of {@code method}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code method} is {@code null}
	 */
	@Override
	public boolean hasMethod(final Method method) {
		Objects.requireNonNull(method, "method == null");
		
		return getMethods().stream().anyMatch(currentMethod -> method.isSignatureEqualTo(currentMethod));
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code InterfaceType} instance has inherited a {@link Method} instance with a signature that is equal to the signature of {@code method}, {@code false} otherwise.
	 * <p>
	 * If {@code method} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method will only check the extended {@code InterfaceType} instances and the optionally extended {@link ClassType} instance. The method {@link #hasMethod(Method)} checks in the current {@code InterfaceType} instance.
	 * 
	 * @param method a {@code Method} instance
	 * @return {@code true} if, and only if, this {@code InterfaceType} instance has inherited a {@code Method} instance with a signature that is equal to the signature of {@code method}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code method} is {@code null}
	 */
	@Override
	public boolean hasMethodInherited(final Method method) {
		Objects.requireNonNull(method, "method == null");
		
		for(final InterfaceType interfaceType : getInterfaceTypes()) {
			if(interfaceType.hasMethod(method) || interfaceType.hasMethodInherited(method)) {
				return true;
			}
		}
		
		final Optional<ClassType> optionalSuperClassType = getOptionalSuperClassType();
		
		if(optionalSuperClassType.isPresent()) {
			final ClassType superClassType = optionalSuperClassType.get();
			
			if(superClassType.hasMethod(method) || superClassType.hasMethodInherited(method)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code InterfaceType} instance has {@link Method} instances, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code InterfaceType} instance has {@code Method} instances, {@code false} otherwise
	 */
	public boolean hasMethods() {
		return getMethods().size() > 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code InterfaceType} instance is extending a super class, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code InterfaceType} instance is extending a super class, {@code false} otherwise
	 */
	public boolean hasSuperClass() {
		return getClassFile().getSuperClass() >= 1;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code InterfaceType} instance is an inner interface, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code InterfaceType} instance is an inner interface, {@code false} otherwise
	 */
	@Override
	public boolean isInnerType() {
		return InnerClassesAttribute.find(getClassFile()).filter(innerClassesAttribute -> innerClassesAttribute.getInnerClasses().stream().anyMatch(innerClass -> innerClass.getOuterClassInfoIndex() != 0)).isPresent();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code InterfaceType} instance is public, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code InterfaceType} instance is public, {@code false} otherwise
	 */
	public boolean isPublic() {
		return getClassFile().isPublic();
	}
	
	/**
	 * Returns a hash code for this {@code InterfaceType} instance.
	 * 
	 * @return a hash code for this {@code InterfaceType} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getClassFile());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code InterfaceType} instance that represents {@code clazz}.
	 * <p>
	 * If {@code clazz} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code clazz.isInterface() == false}, a {@code TypeException} will be thrown.
	 * <p>
	 * This method will cache all {@code InterfaceType} instances.
	 * 
	 * @param clazz a {@code Class} instance
	 * @return an {@code InterfaceType} instance that represents {@code clazz}
	 * @throws NullPointerException thrown if, and only if, {@code clazz} is {@code null}
	 * @throws TypeException thrown if, and only if, {@code clazz.isInterface() == false}
	 */
	public static InterfaceType valueOf(final Class<?> clazz) {
		Objects.requireNonNull(clazz, "clazz == null");
		
		if(!clazz.isInterface()) {
			throw new TypeException(String.format("An InterfaceType must refer to an interface type: %s", clazz));
		}
		
		try {
			synchronized(INTERFACE_TYPES) {
				return INTERFACE_TYPES.computeIfAbsent(clazz.getName(), name -> new InterfaceType(clazz));
			}
		} catch(final NodeFormatException e) {
			throw new TypeException(e);
		}
	}
	
	/**
	 * Returns an {@code InterfaceType} instance that represents {@code Class.forName(className)}.
	 * <p>
	 * If {@code className} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code Class.forName(className)} fails or {@code Class.forName(className).isInterface() == false}, a {@code TypeException} will be thrown.
	 * <p>
	 * This method will cache all {@code InterfaceType} instances.
	 * 
	 * @param className the fully qualified name of the desired class
	 * @return an {@code InterfaceType} instance that represents {@code Class.forName(className)}
	 * @throws NullPointerException thrown if, and only if, {@code className} is {@code null}
	 * @throws TypeException thrown if, and only if, {@code Class.forName(className)} fails or {@code Class.forName(className).isInterface() == false}
	 */
	public static InterfaceType valueOf(final String className) {
		try {
			return valueOf(Class.forName(Objects.requireNonNull(className, "className == null")));
		} catch(final ClassNotFoundException | LinkageError e) {
			throw new TypeException(e);
		}
	}
	
	/**
	 * Clears the cache.
	 */
	public static void clearCache() {
		synchronized(CLASS_FILES) {
			CLASS_FILES.clear();
		}
		
		synchronized(INTERFACE_TYPES) {
			INTERFACE_TYPES.clear();
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<Type> doGetImportableTypes() {
		final Set<Type> importableTypes = new LinkedHashSet<>();
		
		getFields().forEach(field -> doAddImportableTypeIfNecessary(field.getType(), importableTypes));
		getInterfaceTypes().forEach(interfaceType -> doAddImportableTypeIfNecessary(interfaceType, importableTypes));
		getMethods().forEach(method -> method.getImportableTypes().forEach(type -> doAddImportableTypeIfNecessary(type, importableTypes)));
		getOptionalTypeParameters().ifPresent(typeParameters -> typeParameters.collectNames().forEach(name -> doAddImportableTypeIfNecessary(Type.valueOf(name), importableTypes)));
		
		return new ArrayList<>(importableTypes);
	}
	
	private List<Type> doGetImportableTypesSorted() {
		final List<Type> importableTypes = doGetImportableTypes();
		
		Collections.sort(importableTypes, (a, b) -> a.getExternalName().compareTo(b.getExternalName()));
		
		return importableTypes;
	}
	
	private void doAddImportableTypeIfNecessary(final Type importableType, final Set<Type> importableTypes) {
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
		
		final String externalPackageNameThis = getExternalPackageName();
		final String externalPackageNameType = type.getExternalPackageName();
		
		if(externalPackageNameType.equals("java.lang")) {
			return;
		}
		
		if(externalPackageNameType.equals(externalPackageNameThis)) {
			return;
		}
		
		importableTypes.add(type);
	}
	
	private void doInitializeClassFile() {
		if(this.hasInitializedClassFile.compareAndSet(false, true)) {
			synchronized(CLASS_FILES) {
				this.classFile.set(CLASS_FILES.computeIfAbsent(this.clazz.getName(), key -> new ClassFileReader().read(this.clazz)));
			}
		}
	}
	
	private void doInitializeClassSignature() {
		if(this.hasInitializedClassSignature.compareAndSet(false, true)) {
			this.classSignature.set(ClassSignature.parseClassSignatureOptionally(getClassFile()).orElse(null));
		}
	}
	
	private void doInitializeExternalName() {
		if(this.hasInitializedExternalName.compareAndSet(false, true)) {
			this.externalName.set(ClassName.parseClassNameThisClass(getClassFile()).toExternalForm());
		}
	}
	
	private void doInitializeFields() {
		if(this.hasInitializedFields.compareAndSet(false, true)) {
			final ClassFile classFile = getClassFile();
			
			classFile.getFieldInfos().stream().filter(fieldInfo -> fieldInfo.isInterfaceCompatible()).forEach(fieldInfo -> this.fields.add(new Field(classFile, fieldInfo, this)));
		}
	}
	
	private void doInitializeImportableTypes() {
		if(this.hasInitializedImportableTypes.compareAndSet(false, true)) {
			this.importableTypes.addAll(doGetImportableTypesSorted());
		}
	}
	
	private void doInitializeInterfaceTypes() {
		if(this.hasInitializedInterfaceTypes.compareAndSet(false, true)) {
			final ClassFile classFile = getClassFile();
			
			final List<Integer> interfaceIndices = classFile.getInterfaces();
			final List<InterfaceType> interfaceTypes = this.interfaceTypes;
			
			for(final int interfaceIndex : interfaceIndices) {
				final String interfaceNameInternalForm = ConstantUTF8Info.findByNameIndex(classFile, classFile.getCPInfo(interfaceIndex, ConstantClassInfo.class)).getStringValue();
				final String interfaceNameExternalForm = ClassName.parseClassName(interfaceNameInternalForm).toExternalForm();
				
				interfaceTypes.add(InterfaceType.valueOf(interfaceNameExternalForm));
			}
		}
	}
	
	private void doInitializeMethods() {
		if(this.hasInitializedMethods.compareAndSet(false, true)) {
			final ClassFile classFile = getClassFile();
			
			MethodInfos.findMethods(classFile).forEach(methodInfo -> this.methods.add(new Method(classFile, methodInfo, this)));
		}
	}
	
	private void doInitializeModifiers() {
		if(this.hasInitializedModifiers.compareAndSet(false, true)) {
			final List<Modifier> modifiers = this.modifiers;
			
			if(isPublic()) {
				modifiers.add(Modifier.PUBLIC);
			}
		}
	}
	
	private void doInitializeSuperClassSignature() {
		if(this.hasInitializedSuperClassSignature.compareAndSet(false, true)) {
			getOptionalClassSignature().ifPresent(classSignature -> this.superClassSignature.set(classSignature.getSuperClassSignature()));
		}
	}
	
	private void doInitializeSuperInterfaceSignatures() {
		if(this.hasInitializedSuperInterfaceSignatures.compareAndSet(false, true)) {
			getOptionalClassSignature().ifPresent(classSignature -> this.superInterfaceSignatures.addAll(classSignature.getSuperInterfaceSignatures()));
		}
	}
	
	private void doInitializeTypeParameters() {
		if(this.hasInitializedTypeParameters.compareAndSet(false, true)) {
			getOptionalClassSignature().ifPresent(classSignature -> this.typeParameters.set(classSignature.getTypeParameters().orElse(null)));
		}
	}
}