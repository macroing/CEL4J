/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
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
package org.macroing.cel4j.java.binary.classfile.descriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

final class Filters {
	private static final Pattern PACKAGE_NAME_PATTERN = Pattern.compile("\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*((/|\\.)\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Filters() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static ArrayType excludePackageName(final String packageName, final ArrayType arrayType) {
		final ComponentType oldComponentType = arrayType.getComponentType();
		final ComponentType newComponentType = excludePackageName(packageName, oldComponentType);
		
		if(oldComponentType == newComponentType) {
			return arrayType;
		}
		
		return ArrayType.valueOf(newComponentType);
	}
	
	public static ClassName excludePackageName(final String packageName, final ClassName className) {
		if(PACKAGE_NAME_PATTERN.matcher(packageName).matches()) {
			final String internalForm = className.toInternalForm();
			
			final String[] internalFormParts = internalForm.split("/");
			final String[] packageNameParts = packageName.split("\\.|/");
			
			if(packageNameParts.length + 1 == internalFormParts.length) {
				for(int i = 0; i < packageNameParts.length; i++) {
					if(!packageNameParts[i].equals(internalFormParts[i])) {
						return className;
					}
				}
				
				return new ClassName(internalFormParts[internalFormParts.length - 1]);
			}
		}
		
		return className;
	}
	
	public static ComponentType excludePackageName(final String packageName, final ComponentType componentType) {
		return componentType instanceof FieldType ? excludePackageName(packageName, FieldType.class.cast(componentType)) : componentType;
	}
	
	public static FieldDescriptor excludePackageName(final String packageName, final FieldDescriptor fieldDescriptor) {
		return fieldDescriptor instanceof FieldType ? excludePackageName(packageName, FieldType.class.cast(fieldDescriptor)) : fieldDescriptor;
	}
	
	public static FieldType excludePackageName(final String packageName, final FieldType fieldType) {
		if(fieldType instanceof ArrayType) {
			return excludePackageName(packageName, ArrayType.class.cast(fieldType));
		} else if(fieldType instanceof ObjectType) {
			return excludePackageName(packageName, ObjectType.class.cast(fieldType));
		} else {
			return fieldType;
		}
	}
	
	public static MethodDescriptor excludePackageName(final String packageName, final MethodDescriptor methodDescriptor) {
		final ReturnDescriptor oldReturnDescriptor = methodDescriptor.getReturnDescriptor();
		final ReturnDescriptor newReturnDescriptor = excludePackageName(packageName, oldReturnDescriptor);
		
		final List<ParameterDescriptor> oldParameterDescriptors = methodDescriptor.getParameterDescriptors();
		final List<ParameterDescriptor> newParameterDescriptors = new ArrayList<>();
		
		for(final ParameterDescriptor parameterDescriptor : oldParameterDescriptors) {
			newParameterDescriptors.add(excludePackageName(packageName, parameterDescriptor));
		}
		
		if(oldReturnDescriptor.equals(newReturnDescriptor) && Objects.equals(oldParameterDescriptors, newParameterDescriptors)) {
			return methodDescriptor;
		}
		
		return MethodDescriptor.valueOf(newReturnDescriptor, newParameterDescriptors.toArray(new ParameterDescriptor[newParameterDescriptors.size()]));
	}
	
	public static ObjectType excludePackageName(final String packageName, final ObjectType objectType) {
		final ClassName oldClassName = objectType.getClassName();
		final ClassName newClassName = excludePackageName(packageName, oldClassName);
		
		if(oldClassName == newClassName) {
			return objectType;
		}
		
		return new ObjectType(newClassName);
	}
	
	public static ParameterDescriptor excludePackageName(final String packageName, final ParameterDescriptor parameterDescriptor) {
		return parameterDescriptor instanceof FieldType ? excludePackageName(packageName, FieldType.class.cast(parameterDescriptor)) : parameterDescriptor;
	}
	
	public static ReturnDescriptor excludePackageName(final String packageName, final ReturnDescriptor returnDescriptor) {
		return returnDescriptor instanceof FieldType ? excludePackageName(packageName, FieldType.class.cast(returnDescriptor)) : returnDescriptor;
	}
}