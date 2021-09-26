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
package org.macroing.cel4j.java.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.CodeAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.Instruction;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantClassInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.java.binary.classfile.descriptor.ClassName;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.DeprecatedAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.ExceptionsAttribute;
import org.macroing.cel4j.java.binary.classfile.signature.MethodSignature;

/**
 * A {@code Constructor} represents a constructor.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Constructor implements Comparable<Constructor> {
	private final AtomicBoolean hasInitializedExceptionTypes;
	private final AtomicBoolean hasInitializedImportableTypes;
	private final AtomicBoolean hasInitializedModifiers;
	private final ClassFile classFile;
	private final List<Modifier> modifiers;
	private final List<Type> exceptionTypes;
	private final List<Type> importableTypes;
	private final MethodInfo methodInfo;
	private final Optional<CodeAttribute> optionalCodeAttribute;
	private final Optional<ExceptionsAttribute> optionalExceptionsAttribute;
	private final ParameterList parameterList;
	private final Type enclosingType;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	Constructor(final ClassFile classFile, final MethodInfo methodInfo, final Type enclosingType) {
		this.classFile = Objects.requireNonNull(classFile, "classFile == null");
		this.methodInfo = Objects.requireNonNull(methodInfo, "methodInfo == null");
		this.enclosingType = Objects.requireNonNull(enclosingType, "enclosingType == null");
		this.hasInitializedExceptionTypes = new AtomicBoolean();
		this.hasInitializedImportableTypes = new AtomicBoolean();
		this.hasInitializedModifiers = new AtomicBoolean();
		this.modifiers = new ArrayList<>();
		this.exceptionTypes = new ArrayList<>();
		this.importableTypes = new ArrayList<>();
		this.optionalCodeAttribute = CodeAttribute.find(this.methodInfo);
		this.optionalExceptionsAttribute = ExceptionsAttribute.find(this.methodInfo);
		this.parameterList = ParameterList.load(classFile, methodInfo);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link ClassFile} instance associated with this {@code Constructor} instance.
	 * 
	 * @return the {@code ClassFile} instance associated with this {@code Constructor} instance
	 */
	public ClassFile getClassFile() {
		return this.classFile;
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
		if(this.optionalCodeAttribute.isPresent()) {
			final CodeAttribute codeAttribute = this.optionalCodeAttribute.get();
			
			return codeAttribute.getInstructions();
		}
		
		return new ArrayList<>();
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Modifier} instances associated with this {@code Constructor} instance.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code Constructor} instance.
	 * 
	 * @return a {@code List} that contains all {@code Modifier} instances associated with this {@code Constructor} instance
	 */
	public List<Modifier> getModifiers() {
		doInitializeModifiers();
		
		return new ArrayList<>(this.modifiers);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Type} instances associated with this {@code Constructor} instance that are exceptions.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code Constructor} instance.
	 * 
	 * @return a {@code List} that contains all {@code Type} instances associated with this {@code Constructor} instance that are exceptions
	 */
	public List<Type> getExceptionTypes() {
		doInitializeExceptionTypes();
		
		return new ArrayList<>(this.exceptionTypes);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link Type} instances associated with this {@code Constructor} instance that are importable.
	 * <p>
	 * Modifications to the returned {@code List} will not affect this {@code Constructor} instance.
	 * 
	 * @return a {@code List} that contains all {@code Type} instances associated with this {@code Constructor} instance that are importable
	 */
	public List<Type> getImportableTypes() {
		doInitializeImportableTypes();
		
		return new ArrayList<>(this.importableTypes);
	}
	
	/**
	 * Returns the optional {@link CodeAttribute} instance associated with this {@code Constructor} instance.
	 * 
	 * @return the optional {@code CodeAttribute} instance associated with this {@code Constructor} instance
	 */
	public Optional<CodeAttribute> getOptionalCodeAttribute() {
		return this.optionalCodeAttribute;
	}
	
	/**
	 * Returns the optional {@link ExceptionsAttribute} instance associated with this {@code Constructor} instance.
	 * 
	 * @return the optional {@code ExceptionsAttribute} instance associated with this {@code Constructor} instance
	 */
	public Optional<ExceptionsAttribute> getOptionalExceptionsAttribute() {
		return this.optionalExceptionsAttribute;
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
	 * Returns the external name of this {@code Constructor} instance.
	 * 
	 * @return the external name of this {@code Constructor} instance
	 */
	public String getExternalName() {
		return getEnclosingType().getExternalName();
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
	 * Compares this {@code Constructor} instance with {@code constructor} for order.
	 * <p>
	 * Returns a negative integer, zero or a positive integer as this {@code Constructor} instance is less than, equal to or greater than {@code constructor}.
	 * <p>
	 * If {@code constructor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param constructor a {@code Constructor} instance
	 * @return a negative integer, zero or a positive integer as this {@code Constructor} instance is less than, equal to or greater than {@code constructor}
	 * @throws NullPointerException thrown if, and only if, {@code constructor} is {@code null}
	 */
	@Override
	public int compareTo(final Constructor constructor) {
		final Constructor constructorThis = this;
		final Constructor constructorThat = constructor;
		
		final boolean isPublicThis = constructorThis.isPublic();
		final boolean isPublicThat = constructorThat.isPublic();
		
		if(isPublicThis != isPublicThat) {
			return isPublicThis ? -1 : 1;
		}
		
		final boolean isProtectedThis = constructorThis.isProtected();
		final boolean isProtectedThat = constructorThat.isProtected();
		
		if(isProtectedThis != isProtectedThat) {
			return isProtectedThis ? -1 : 1;
		}
		
		final boolean isPackageProtectedThis = constructorThis.isPackageProtected();
		final boolean isPackageProtectedThat = constructorThat.isPackageProtected();
		
		if(isPackageProtectedThis != isPackageProtectedThat) {
			return isPackageProtectedThis ? -1 : 1;
		}
		
		final boolean isPrivateThis = constructorThis.isPrivate();
		final boolean isPrivateThat = constructorThat.isPrivate();
		
		if(isPrivateThis != isPrivateThat) {
			return isPrivateThis ? -1 : 1;
		}
		
		return constructorThis.getParameterList().compareTo(constructorThat.getParameterList());
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
	
	/**
	 * Returns {@code true} if, and only if, {@code constructorA} and {@code constructorB} are in different groups, {@code false} otherwise.
	 * <p>
	 * If either {@code constructorA} or {@code constructorB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param constructorA a {@code Constructor} instance
	 * @param constructorB a {@code Constructor} instance
	 * @return {@code true} if, and only if, {@code constructorA} and {@code constructorB} are in different groups, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code constructorA} or {@code constructorB} are {@code null}
	 */
	public static boolean inDifferentGroups(final Constructor constructorA, final Constructor constructorB) {
		if(constructorA.isPublic() != constructorB.isPublic()) {
			return true;
		}
		
		if(constructorA.isProtected() != constructorB.isProtected()) {
			return true;
		}
		
		if(constructorA.isPackageProtected() != constructorB.isPackageProtected()) {
			return true;
		}
		
		if(constructorA.isPrivate() != constructorB.isPrivate()) {
			return true;
		}
		
		return false;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<Type> doGetImportableTypes() {
		final Set<Type> importableTypes = new LinkedHashSet<>();
		
		getExceptionTypes().forEach(exceptionType -> doAddImportableTypeIfNecessary(exceptionType, importableTypes));
		getOptionalCodeAttribute().ifPresent(codeAttribute -> Instructions.findTypeNames(this.classFile, codeAttribute).forEach(typeName -> doAddImportableTypeIfNecessary(Type.valueOf(typeName), importableTypes)));
		getParameterList().getParameters().forEach(parameter -> doAddImportableTypeIfNecessary(parameter.getType(), importableTypes));
		
		return new ArrayList<>(importableTypes);
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
		
		final String externalPackageNameThis = getEnclosingType().getExternalPackageName();
		final String externalPackageNameType = type.getExternalPackageName();
		
		if(externalPackageNameType.equals("java.lang")) {
			return;
		}
		
		if(externalPackageNameType.equals(externalPackageNameThis)) {
			return;
		}
		
		importableTypes.add(type);
	}
	
	private void doInitializeExceptionTypes() {
		if(this.hasInitializedExceptionTypes.compareAndSet(false, true)) {
			getOptionalExceptionsAttribute().ifPresent(exceptionsAttribute -> {
				exceptionsAttribute.getExceptionIndexTable().forEach(exceptionIndex -> {
					this.exceptionTypes.add(Type.valueOf(ClassName.parseClassName(this.classFile.getCPInfo(this.classFile.getCPInfo(exceptionIndex.intValue(), ConstantClassInfo.class).getNameIndex(), ConstantUTF8Info.class).getStringValue()).toExternalForm()));
				});
			});
		}
	}
	
	private void doInitializeImportableTypes() {
		if(this.hasInitializedImportableTypes.compareAndSet(false, true)) {
			this.importableTypes.addAll(doGetImportableTypes());
		}
	}
	
	private void doInitializeModifiers() {
		if(this.hasInitializedModifiers.compareAndSet(false, true)) {
			if(isPrivate()) {
				this.modifiers.add(Modifier.PRIVATE);
			} else if(isProtected()) {
				this.modifiers.add(Modifier.PROTECTED);
			} else if(isPublic()) {
				this.modifiers.add(Modifier.PUBLIC);
			}
			
			if(isStrict()) {
				this.modifiers.add(Modifier.STRICT_F_P);
			}
		}
	}
}