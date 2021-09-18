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

import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.descriptor.FieldDescriptor;
import org.macroing.cel4j.java.binary.classfile.descriptor.ParameterDescriptor;
import org.macroing.cel4j.java.binary.classfile.descriptor.ReturnDescriptor;

abstract class Type {
	protected Type() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public abstract String getName();
	
	public final String getPackageName() {
		final String fullyQualifiedTypeName = getName();
		final String packageName = fullyQualifiedTypeName.lastIndexOf(".") >= 0 ? fullyQualifiedTypeName.substring(0, fullyQualifiedTypeName.lastIndexOf(".")) : "";
		
		return packageName;
	}
	
	public final String getSimpleName() {
		final String fullyQualifiedTypeName = getName();
		final String simpleName0 = fullyQualifiedTypeName.lastIndexOf(".") >= 0 ? fullyQualifiedTypeName.substring(fullyQualifiedTypeName.lastIndexOf(".") + 1) : fullyQualifiedTypeName;
		final String simpleName1 = isInnerType() && simpleName0.lastIndexOf('$') >= 0 ? simpleName0.substring(simpleName0.lastIndexOf('$') + 1) : simpleName0;
		
		return simpleName1;
	}
	
	@SuppressWarnings("static-method")
	public boolean hasMethod(final Method jMethod) {
		Objects.requireNonNull(jMethod, "jMethod == null");
		
		return false;
	}
	
	@SuppressWarnings("static-method")
	public boolean hasMethodInherited(final Method jMethod) {
		Objects.requireNonNull(jMethod, "jMethod == null");
		
		return false;
	}
	
	public final boolean hasMethodOverridden(final Method jMethod) {
		return hasMethod(jMethod) && hasMethodInherited(jMethod);
	}
	
	public abstract boolean isInnerType();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Type valueOf(final Class<?> clazz) {
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
	
	public static Type valueOf(final FieldDescriptor fieldDescriptor) {
		try {
			return valueOf(fieldDescriptor.toClass());
		} catch(final ClassNotFoundException | LinkageError e) {
			throw new TypeException(e);
		}
	}
	
	public static Type valueOf(final ParameterDescriptor parameterDescriptor) {
		try {
			return valueOf(parameterDescriptor.toClass());
		} catch(final ClassNotFoundException | LinkageError e) {
			throw new TypeException(e);
		}
	}
	
	public static Type valueOf(final ReturnDescriptor returnDescriptor) {
		try {
			return valueOf(returnDescriptor.toClass());
		} catch(final ClassNotFoundException | LinkageError e) {
			throw new TypeException(e);
		}
	}
	
	public static Type valueOf(final String name) {
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