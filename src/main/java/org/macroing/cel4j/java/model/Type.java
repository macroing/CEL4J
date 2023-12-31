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
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.descriptor.FieldDescriptor;
import org.macroing.cel4j.java.binary.classfile.descriptor.ParameterDescriptor;
import org.macroing.cel4j.java.binary.classfile.descriptor.ReturnDescriptor;

/**
 * A {@code Type} represents a type.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Type {
	/**
	 * Constructs a new {@code Type} instance.
	 */
	protected Type() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} that contains all {@link Type} instances associated with this {@code Type} instance that are importable.
	 * <p>
	 * The default implementation will return an empty {@code List} instance.
	 * 
	 * @return a {@code List} that contains all {@code Type} instances associated with this {@code Type} instance that are importable
	 */
	@SuppressWarnings("static-method")
	public List<Type> getImportableTypes() {
		return new ArrayList<>();
	}
	
	/**
	 * Returns the external name of this {@code Type} instance.
	 * 
	 * @return the external name of this {@code Type} instance
	 */
	public abstract String getExternalName();
	
	/**
	 * Returns the external package name of this {@code Type} instance.
	 * 
	 * @return the external package name of this {@code Type} instance
	 */
	public final String getExternalPackageName() {
		final String externalName = getExternalName();
		
		final int lastIndexOfPeriod = externalName.lastIndexOf(".");
		
		if(lastIndexOfPeriod >= 0) {
			return externalName.substring(0, lastIndexOfPeriod);
		}
		
		return "";
	}
	
	/**
	 * Returns the external simple name of this {@code Type} instance.
	 * 
	 * @return the external simple name of this {@code Type} instance
	 */
	public final String getExternalSimpleName() {
		final String externalName = getExternalName();
		
		final int lastIndexOfPeriod = externalName.lastIndexOf(".");
		
		if(lastIndexOfPeriod >= 0) {
			final String externalSimpleName = externalName.substring(lastIndexOfPeriod + 1);
			
			if(isInnerType()) {
				final int lastIndexOfDollarSign = externalSimpleName.lastIndexOf("$");
				
				if(lastIndexOfDollarSign >= 0) {
					return externalSimpleName.substring(lastIndexOfDollarSign + 1);
				}
			}
			
			return externalSimpleName;
		}
		
		if(isInnerType()) {
			final int lastIndexOfDollarSign = externalName.lastIndexOf("$");
			
			if(lastIndexOfDollarSign >= 0) {
				return externalName.substring(lastIndexOfDollarSign + 1);
			}
		}
		
		return externalName;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Type} instance has a {@link Method} instance with a signature that is equal to the signature of {@code method}, {@code false} otherwise.
	 * <p>
	 * If {@code method} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The default implementation returns {@code false}.
	 * 
	 * @param method a {@code Method} instance
	 * @return {@code true} if, and only if, this {@code Type} instance has a {@code Method} instance with a signature that is equal to the signature of {@code method}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code method} is {@code null}
	 */
	@SuppressWarnings("static-method")
	public boolean hasMethod(final Method method) {
		Objects.requireNonNull(method, "method == null");
		
		return false;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Type} instance has inherited a {@link Method} instance with a signature that is equal to the signature of {@code method}, {@code false} otherwise.
	 * <p>
	 * If {@code method} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The default implementation returns {@code false}.
	 * 
	 * @param method a {@code Method} instance
	 * @return {@code true} if, and only if, this {@code Type} instance has inherited a {@code Method} instance with a signature that is equal to the signature of {@code method}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code method} is {@code null}
	 */
	@SuppressWarnings("static-method")
	public boolean hasMethodInherited(final Method method) {
		Objects.requireNonNull(method, "method == null");
		
		return false;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Type} instance has an overridden {@code Method} instance with a signature that is equal to the signature of {@code method}, {@code false} otherwise.
	 * <p>
	 * If {@code method} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param method a {@code Method} instance
	 * @return {@code true} if, and only if, this {@code Type} instance has an overridden {@code Method} instance with a signature that is equal to the signature of {@code method}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code method} is {@code null}
	 */
	public final boolean hasMethodOverridden(final Method method) {
		return hasMethod(method) && hasMethodInherited(method);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Type} instance is an inner type, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Type} instance is an inner type, {@code false} otherwise
	 */
	public abstract boolean isInnerType();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Type} instance that represents {@code clazz}.
	 * <p>
	 * If {@code clazz} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param clazz a {@code Class} instance
	 * @return a {@code Type} instance that represents {@code clazz}
	 * @throws NullPointerException thrown if, and only if, {@code clazz} is {@code null}
	 */
	public static Type valueOf(final Class<?> clazz) {
		Objects.requireNonNull(clazz, "clazz == null");
		
		if(clazz == Void.TYPE) {
			return VoidType.valueOf(clazz);
		} else if(clazz.isAnnotation()) {
			return AnnotationType.valueOf(clazz);
		} else if(clazz.isArray()) {
			return ArrayType.valueOf(clazz);
		} else if(clazz.isEnum()) {
			return EnumType.valueOf(clazz);
		} else if(clazz.isInterface()) {
			return InterfaceType.valueOf(clazz);
		} else if(clazz.isPrimitive()) {
			return PrimitiveType.valueOf(clazz);
		} else {
			return ClassType.valueOf(clazz);
		}
	}
	
	/**
	 * Returns a {@code Type} instance given {@code fieldDescriptor}.
	 * <p>
	 * If {@code fieldDescriptor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code fieldDescriptor.toClass()} fails, a {@code TypeException} will be thrown.
	 * 
	 * @param fieldDescriptor a {@link FieldDescriptor} instance
	 * @return a {@code Type} instance given {@code fieldDescriptor}
	 * @throws NullPointerException thrown if, and only if, {@code fieldDescriptor} is {@code null}
	 * @throws TypeException thrown if, and only if, {@code fieldDescriptor.toClass()} fails
	 */
	public static Type valueOf(final FieldDescriptor fieldDescriptor) {
		try {
			return valueOf(fieldDescriptor.toClass());
		} catch(final ClassNotFoundException | LinkageError e) {
			throw new TypeException(e);
		}
	}
	
	/**
	 * Returns a {@code Type} instance given {@code parameterDescriptor}.
	 * <p>
	 * If {@code parameterDescriptor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code parameterDescriptor.toClass()} fails, a {@code TypeException} will be thrown.
	 * 
	 * @param parameterDescriptor a {@link ParameterDescriptor} instance
	 * @return a {@code Type} instance given {@code parameterDescriptor}
	 * @throws NullPointerException thrown if, and only if, {@code parameterDescriptor} is {@code null}
	 * @throws TypeException thrown if, and only if, {@code parameterDescriptor.toClass()} fails
	 */
	public static Type valueOf(final ParameterDescriptor parameterDescriptor) {
		try {
			return valueOf(parameterDescriptor.toClass());
		} catch(final ClassNotFoundException | LinkageError e) {
			throw new TypeException(e);
		}
	}
	
	/**
	 * Returns a {@code Type} instance given {@code returnDescriptor}.
	 * <p>
	 * If {@code returnDescriptor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code returnDescriptor.toClass()} fails, a {@code TypeException} will be thrown.
	 * 
	 * @param returnDescriptor a {@link ReturnDescriptor} instance
	 * @return a {@code Type} instance given {@code returnDescriptor}
	 * @throws NullPointerException thrown if, and only if, {@code returnDescriptor} is {@code null}
	 * @throws TypeException thrown if, and only if, {@code returnDescriptor.toClass()} fails
	 */
	public static Type valueOf(final ReturnDescriptor returnDescriptor) {
		try {
			return valueOf(returnDescriptor.toClass());
		} catch(final ClassNotFoundException | LinkageError e) {
			throw new TypeException(e);
		}
	}
	
	/**
	 * Returns a {@code Type} instance given {@code name} in external or internal format.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code Class.forName(name)} fails, a {@code TypeException} will be thrown.
	 * 
	 * @param name the name in external or internal format
	 * @return a {@code Type} instance given {@code name} in external or internal format
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 * @throws TypeException thrown if, and only if, {@code Class.forName(name)} fails
	 */
	public static Type valueOf(final String name) {
		Objects.requireNonNull(name, "name == null");
		
		switch(name) {
			case PrimitiveType.BOOLEAN_EXTERNAL_NAME:
			case PrimitiveType.BOOLEAN_INTERNAL_NAME:
			case PrimitiveType.BYTE_EXTERNAL_NAME:
			case PrimitiveType.BYTE_INTERNAL_NAME:
			case PrimitiveType.CHAR_EXTERNAL_NAME:
			case PrimitiveType.CHAR_INTERNAL_NAME:
			case PrimitiveType.DOUBLE_EXTERNAL_NAME:
			case PrimitiveType.DOUBLE_INTERNAL_NAME:
			case PrimitiveType.FLOAT_EXTERNAL_NAME:
			case PrimitiveType.FLOAT_INTERNAL_NAME:
			case PrimitiveType.INT_EXTERNAL_NAME:
			case PrimitiveType.INT_INTERNAL_NAME:
			case PrimitiveType.LONG_EXTERNAL_NAME:
			case PrimitiveType.LONG_INTERNAL_NAME:
			case PrimitiveType.SHORT_EXTERNAL_NAME:
			case PrimitiveType.SHORT_INTERNAL_NAME:
				return PrimitiveType.valueOf(name);
			case VoidType.VOID_EXTERNAL_NAME:
			case VoidType.VOID_INTERNAL_NAME:
				return VoidType.valueOf(name);
			default:
				try {
					return valueOf(Class.forName(name));
				} catch(final ClassNotFoundException | LinkageError e) {
					throw new TypeException(e);
				}
		}
	}
}