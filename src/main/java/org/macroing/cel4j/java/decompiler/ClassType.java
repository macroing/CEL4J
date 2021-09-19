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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.InnerClass;
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
 * A {@code ClassType} is a {@link Type} implementation that represents a class type.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
final class ClassType extends Type {
	private static final Map<String, ClassFile> CLASS_FILES = new HashMap<>();
	private static final Map<String, ClassType> CLASS_TYPES = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final AtomicBoolean hasInitialized;
	private final ClassFile classFile;
	private final List<Constructor> constructors;
	private final List<Field> fields;
	private final List<InnerType> innerTypes;
	private final List<InterfaceType> interfaceTypes;
	private final List<Method> methods;
	private final List<Modifier> modifiers;
	private final List<SuperInterfaceSignature> superInterfaceSignatures;
	private final List<Type> importableTypes;
	private final Optional<ClassSignature> optionalClassSignature;
	private final Optional<SuperClassSignature> optionalSuperClassSignature;
	private final Optional<TypeParameters> optionalTypeParameters;
	private final String name;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ClassType(final ClassFile classFile) {
		this.hasInitialized = new AtomicBoolean();
		this.classFile = classFile;
		this.constructors = new ArrayList<>();
		this.fields = new ArrayList<>();
		this.innerTypes = new ArrayList<>();
		this.interfaceTypes = new ArrayList<>();
		this.methods = new ArrayList<>();
		this.modifiers = new ArrayList<>();
		this.superInterfaceSignatures = new ArrayList<>();
		this.importableTypes = new ArrayList<>();
		this.optionalClassSignature = ClassSignature.parseClassSignatureOptionally(this.classFile);
		this.optionalSuperClassSignature = this.optionalClassSignature.isPresent() ? Optional.of(this.optionalClassSignature.get().getSuperClassSignature()) : Optional.empty();
		this.optionalTypeParameters = this.optionalClassSignature.isPresent() ? this.optionalClassSignature.get().getTypeParameters() : Optional.empty();
		this.name = ClassName.parseClassNameThisClass(this.classFile).toExternalForm();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link ClassFile} instance associated with this {@code ClassType} instance.
	 * 
	 * @return the {@code ClassFile} instance associated with this {@code ClassType} instance
	 */
	public ClassFile getClassFile() {
		return this.classFile;
	}
	
	/**
	 * Returns a {@code List} that contains all {@link AttributeInfo} instances associated with this {@code ClassType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code ClassType} instance.
	 * 
	 * @return a {@code List} that contains all {@code AttributeInfo} instances associated with this {@code ClassType} instance
	 */
	public List<AttributeInfo> getAttributeInfos() {
		return this.classFile.getAttributeInfos();
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Constructor} instances associated with this {@code ClassType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code ClassType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Constructor} instances associated with this {@code ClassType} instance
	 */
	public List<Constructor> getConstructors() {
		return new ArrayList<>(this.constructors);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Constructor} instances associated with this {@code ClassType} instance sorted according to their natural order.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code ClassType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Constructor} instances associated with this {@code ClassType} instance sorted according to their natural order
	 */
	public List<Constructor> getConstructorsSorted() {
		return getConstructors().stream().sorted().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Field} instances associated with this {@code ClassType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code ClassType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Field} instances associated with this {@code ClassType} instance
	 */
	public List<Field> getFields() {
		return new ArrayList<>(this.fields);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Field} instances associated with this {@code ClassType} instance sorted according to their natural order.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code ClassType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Field} instances associated with this {@code ClassType} instance sorted according to their natural order
	 */
	public List<Field> getFieldsSorted() {
		return getFields().stream().sorted().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link InnerType} instances associated with this {@code ClassType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code ClassType} instance.
	 * 
	 * @return a {@code List} that contains all {@code InnerType} instances associated with this {@code ClassType} instance
	 */
	public List<InnerType> getInnerTypes() {
		return new ArrayList<>(this.innerTypes);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link InterfaceType} instances associated with this {@code ClassType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code ClassType} instance.
	 * 
	 * @return a {@code List} that contains all {@code InterfaceType} instances associated with this {@code ClassType} instance
	 */
	public List<InterfaceType> getInterfaceTypes() {
		return new ArrayList<>(this.interfaceTypes);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Method} instances associated with this {@code ClassType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code ClassType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Method} instances associated with this {@code ClassType} instance
	 */
	public List<Method> getMethods() {
		return new ArrayList<>(this.methods);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Method} instances associated with this {@code ClassType} instance sorted according to their natural order.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code ClassType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Method} instances associated with this {@code ClassType} instance sorted according to their natural order
	 */
	public List<Method> getMethodsSorted() {
		return getMethods().stream().sorted().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Modifier} instances associated with this {@code ClassType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code ClassType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Modifier} instances associated with this {@code ClassType} instance
	 */
	public List<Modifier> getModifiers() {
		return new ArrayList<>(this.modifiers);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Type} instances associated with this {@code ClassType} instance that are importable.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code ClassType} instance.
	 * 
	 * @return a {@code List} that contains all {@code Type} instances associated with this {@code ClassType} instance that are importable
	 */
	public List<Type> getImportableTypes() {
		if(this.importableTypes.isEmpty()) {
			this.importableTypes.addAll(doGetImportableTypesSorted());
		}
		
		return new ArrayList<>(this.importableTypes);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link SuperInterfaceSignature} instances associated with this {@code ClassType} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code ClassType} instance.
	 * 
	 * @return a {@code List} that contains all {@code SuperInterfaceSignature} instances associated with this {@code ClassType} instance
	 */
	public List<SuperInterfaceSignature> getSuperInterfaceSignatures() {
		return new ArrayList<>(this.superInterfaceSignatures);
	}
	
	/**
	 * Returns the optional {@link ClassSignature} instance associated with this {@code ClassType} instance.
	 * 
	 * @return the optional {@code ClassSignature} instance associated with this {@code ClassType} instance
	 */
	public Optional<ClassSignature> getOptionalClassSignature() {
		return this.optionalClassSignature;
	}
	
	/**
	 * Returns the optional super {@code ClassType} instance associated with this {@code ClassType} instance.
	 * 
	 * @return the optional super {@code ClassType} instance associated with this {@code ClassType} instance
	 */
	public Optional<ClassType> getOptionalSuperClassType() {
		return hasSuperClass() ? Optional.of(ClassType.valueOf(ClassName.parseClassNameSuperClass(this.classFile).toExternalForm())) : Optional.empty();
	}
	
	/**
	 * Returns the optional {@link SuperClassSignature} instance associated with this {@code ClassType} instance.
	 * 
	 * @return the optional {@code SuperClassSignature} instance associated with this {@code ClassType} instance
	 */
	public Optional<SuperClassSignature> getOptionalSuperClassSignature() {
		return this.optionalSuperClassSignature;
	}
	
	/**
	 * Returns the optional {@link TypeParameters} instance associated with this {@code ClassType} instance.
	 * 
	 * @return the optional {@code TypeParameters} instance associated with this {@code ClassType} instance
	 */
	public Optional<TypeParameters> getOptionalTypeParameters() {
		return this.optionalTypeParameters;
	}
	
	/**
	 * Returns the name of this {@code ClassType} instance.
	 * 
	 * @return the name of this {@code ClassType} instance
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassType} instance.
	 * 
	 * @return a {@code String} representation of this {@code ClassType} instance
	 */
	@Override
	public String toString() {
		return String.format("ClassType.valueOf(%s.class)", getName());
	}
	
	/**
	 * Compares {@code object} to this {@code ClassType} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ClassType}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ClassType} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ClassType}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ClassType)) {
			return false;
		} else if(!Objects.equals(this.classFile, ClassType.class.cast(object).classFile)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code ClassType} instance has {@link Constructor} instances, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code ClassType} instance has {@code Constructor} instances, {@code false} otherwise
	 */
	public boolean hasConstructors() {
		return this.constructors.size() > 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code ClassType} instance has {@link Field} instances, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code ClassType} instance has {@code Field} instances, {@code false} otherwise
	 */
	public boolean hasFields() {
		return this.fields.size() > 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code ClassType} instance has {@link InnerType} instances, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code ClassType} instance has {@code InnerType} instances, {@code false} otherwise
	 */
	public boolean hasInnerTypes() {
		return this.innerTypes.size() > 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code ClassType} instance has a {@link Method} instance with a signature that is equal to the signature of {@code method}, {@code false} otherwise.
	 * <p>
	 * If {@code method} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method will only check the current {@code ClassType} instance. The method {@link #hasMethodInherited(Method)} checks in implemented {@link InterfaceType} instances and the optionally extended {@code ClassType} instance.
	 * 
	 * @param method a {@code Method} instance
	 * @return {@code true} if, and only if, this {@code ClassType} instance has a {@code Method} instance with a signature that is equal to the signature of {@code method}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code method} is {@code null}
	 */
	@Override
	public boolean hasMethod(final Method method) {
		Objects.requireNonNull(method, "method == null");
		
		return getMethods().stream().anyMatch(currentMethod -> method.isSignatureEqualTo(currentMethod));
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code ClassType} instance has inherited a {@link Method} instance with a signature that is equal to the signature of {@code method}, {@code false} otherwise.
	 * <p>
	 * If {@code method} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method will only check the implemented {@link InterfaceType} instances and the optionally extended {@code ClassType} instance. The method {@link #hasMethod(Method)} checks in the current {@code ClassType} instance.
	 * 
	 * @param method a {@code Method} instance
	 * @return {@code true} if, and only if, this {@code ClassType} instance has inherited a {@code Method} instance with a signature that is equal to the signature of {@code method}, {@code false} otherwise
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
	 * Returns {@code true} if, and only if, this {@code ClassType} instance has {@link Method} instances, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code ClassType} instance has {@code Method} instances, {@code false} otherwise
	 */
	public boolean hasMethods() {
		return this.methods.size() > 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code ClassType} instance is extending a super class, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code ClassType} instance is extending a super class, {@code false} otherwise
	 */
	public boolean hasSuperClass() {
		return !isObject() && this.classFile.getSuperClass() >= 1;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code ClassType} instance is abstract, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code ClassType} instance is abstract, {@code false} otherwise
	 */
	public boolean isAbstract() {
		return this.classFile.isAbstract();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code ClassType} instance is final, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code ClassType} instance is final, {@code false} otherwise
	 */
	public boolean isFinal() {
		return this.classFile.isFinal();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code ClassType} instance is an inner class, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code ClassType} instance is an inner class, {@code false} otherwise
	 */
	@Override
	public boolean isInnerType() {
		return InnerClassesAttribute.find(this.classFile).filter(innerClassesAttribute -> innerClassesAttribute.getInnerClasses().stream().anyMatch(innerClass -> innerClass.getOuterClassInfoIndex() != 0)).isPresent();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code ClassType} instance represents {@code java.lang.Object}, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code ClassType} instance represents {@code java.lang.Object}, {@code false} otherwise
	 */
	public boolean isObject() {
		return getName().equals("java.lang.Object");
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code ClassType} instance is public, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code ClassType} instance is public, {@code false} otherwise
	 */
	public boolean isPublic() {
		return this.classFile.isPublic();
	}
	
	/**
	 * Returns a hash code for this {@code ClassType} instance.
	 * 
	 * @return a hash code for this {@code ClassType} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.classFile);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code ClassType} instance that represents {@code clazz}.
	 * <p>
	 * If {@code clazz} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If either {@code clazz.isAnnotation()}, {@code clazz.isArray()}, {@code clazz.isEnum()}, {@code clazz.isInterface()} or {@code clazz.isPrimitive()} returns {@code true}, a {@code TypeException} will be thrown.
	 * <p>
	 * This method will cache all {@code ClassType} instances.
	 * 
	 * @param clazz a {@code Class} instance
	 * @return a {@code ClassType} instance that represents {@code clazz}
	 * @throws NullPointerException thrown if, and only if, {@code clazz} is {@code null}
	 * @throws TypeException thrown if, and only if, either {@code clazz.isAnnotation()}, {@code clazz.isArray()}, {@code clazz.isEnum()}, {@code clazz.isInterface()} or {@code clazz.isPrimitive()} returns {@code true}
	 */
	public static ClassType valueOf(final Class<?> clazz) {
		Objects.requireNonNull(clazz, "clazz == null");
		
		if(clazz.isAnnotation()) {
			throw new TypeException(String.format("A ClassType must refer to a class type, not an annotation type: %s", clazz));
		} else if(clazz.isArray()) {
			throw new TypeException(String.format("A ClassType must refer to a class type, not an array type: %s", clazz));
		} else if(clazz.isEnum()) {
			throw new TypeException(String.format("A ClassType must refer to a class type, not an enum type: %s", clazz));
		} else if(clazz.isInterface()) {
			throw new TypeException(String.format("A ClassType must refer to a class type, not an interface type: %s", clazz));
		} else if(clazz.isPrimitive()) {
			throw new TypeException(String.format("A ClassType must refer to a class type, not a primitive type: %s", clazz));
		} else {
			try {
				synchronized(CLASS_TYPES) {
					final
					ClassType classType = CLASS_TYPES.computeIfAbsent(clazz.getName(), name -> new ClassType(CLASS_FILES.computeIfAbsent(name, key -> new ClassFileReader().read(clazz))));
					classType.doInitialize();
					
					return classType;
				}
			} catch(final NodeFormatException e) {
				throw new TypeException(e);
			}
		}
	}
	
	/**
	 * Returns a {@code ClassType} instance that represents {@code Class.forName(className)}.
	 * <p>
	 * If {@code className} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code Class.forName(className)} fails or {@code Class.forName(className)} is not referring to a class type, a {@code TypeException} will be thrown.
	 * <p>
	 * This method will cache all {@code ArrayType} instances.
	 * 
	 * @param className the fully qualified name of the desired class
	 * @return a {@code ClassType} instance that represents {@code Class.forName(className)}
	 * @throws NullPointerException thrown if, and only if, {@code className} is {@code null}
	 * @throws TypeException thrown if, and only if, {@code Class.forName(className)} fails or {@code Class.forName(className)} is not referring to a class type
	 */
	public static ClassType valueOf(final String className) {
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
		synchronized(CLASS_TYPES) {
			CLASS_FILES.clear();
			CLASS_TYPES.clear();
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<Type> doGetImportableTypes() {
		final Set<Type> importableTypes = new LinkedHashSet<>();
		
		this.constructors.forEach(constructor -> constructor.getParameterList().getParameters().forEach(parameter -> doAddImportableTypeIfNecessary(parameter.getType(), importableTypes)));
		this.fields.forEach(field -> doAddImportableTypeIfNecessary(field.getType(), importableTypes));
		this.interfaceTypes.forEach(interfaceType -> doAddImportableTypeIfNecessary(interfaceType, importableTypes));
		this.methods.forEach(method -> method.getImportableTypes().forEach(type -> doAddImportableTypeIfNecessary(type, importableTypes)));
		this.optionalTypeParameters.ifPresent(typeParameters -> typeParameters.collectNames().forEach(name -> doAddImportableTypeIfNecessary(Type.valueOf(name), importableTypes)));
		
		return new ArrayList<>(importableTypes);
	}
	
	private List<Type> doGetImportableTypesSorted() {
		final List<Type> importableTypes = doGetImportableTypes();
		
		Collections.sort(importableTypes, (a, b) -> a.getName().compareTo(b.getName()));
		
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
		
		final String packageNameThis = getPackageName();
		final String packageNameType = type.getPackageName();
		
		if(packageNameType.equals("java.lang")) {
			return;
		}
		
		if(packageNameType.equals(packageNameThis)) {
			return;
		}
		
		importableTypes.add(type);
	}
	
	private void doInitialize() {
		if(this.hasInitialized.compareAndSet(false, true)) {
			doInitializeConstructors();
			doInitializeFields();
			doInitializeInnerTypes();
			doInitializeInterfaceTypes();
			doInitializeMethods();
			doInitializeModifiers();
			doInitializeSuperInterfaceSignatures();
		}
	}
	
	private void doInitializeConstructors() {
		MethodInfos.findConstructors(this.classFile).forEach(methodInfo -> this.constructors.add(new Constructor(this.classFile, methodInfo, this)));
	}
	
	private void doInitializeFields() {
		this.classFile.getFieldInfos().stream().filter(fieldInfo -> !fieldInfo.isEnum()).forEach(fieldInfo -> this.fields.add(new Field(this.classFile, fieldInfo, this)));
	}
	
	private void doInitializeInnerTypes() {
		final ClassFile classFile = this.classFile;
		
		final List<InnerType> innerTypes = this.innerTypes;
		
		InnerClassesAttribute.find(classFile).ifPresent(innerClassesAttribute -> {
			for(final InnerClass innerClass : innerClassesAttribute.getInnerClasses()) {
				if(innerClass.getInnerNameIndex() != 0) {
					final InnerType innerType = new InnerType(classFile, innerClass);
					
					final Type type = innerType.getType();
					
					if(!type.equals(this)) {
						innerTypes.add(innerType);
					}
				}
			}
		});
	}
	
	private void doInitializeInterfaceTypes() {
		final ClassFile classFile = this.classFile;
		
		final List<Integer> interfaceIndices = this.classFile.getInterfaces();
		final List<InterfaceType> interfaceTypes = this.interfaceTypes;
		
		for(final int interfaceIndex : interfaceIndices) {
			final String interfaceNameInternalForm = ConstantUTF8Info.findByNameIndex(classFile, classFile.getCPInfo(interfaceIndex, ConstantClassInfo.class)).getStringValue();
			final String interfaceNameExternalForm = ClassName.parseClassName(interfaceNameInternalForm).toExternalForm();
			
			interfaceTypes.add(InterfaceType.valueOf(interfaceNameExternalForm));
		}
	}
	
	private void doInitializeMethods() {
		MethodInfos.findMethods(this.classFile).forEach(methodInfo -> this.methods.add(new Method(this.classFile, methodInfo, this)));
	}
	
	private void doInitializeModifiers() {
		final List<Modifier> modifiers = this.modifiers;
		
		if(isPublic()) {
			modifiers.add(Modifier.PUBLIC);
		}
		
		if(isAbstract()) {
			modifiers.add(Modifier.ABSTRACT);
		} else if(isFinal()) {
			modifiers.add(Modifier.FINAL);
		}
	}
	
	private void doInitializeSuperInterfaceSignatures() {
		this.optionalClassSignature.ifPresent(classSignature -> this.superInterfaceSignatures.addAll(classSignature.getSuperInterfaceSignatures()));
	}
}