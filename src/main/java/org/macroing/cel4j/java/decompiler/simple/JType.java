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
package org.macroing.cel4j.java.decompiler.simple;

import java.util.Objects;

abstract class JType {
	protected JType() {
		
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
	public boolean hasMethod(final JMethod jMethod) {
		Objects.requireNonNull(jMethod, "jMethod == null");
		
		return false;
	}
	
	@SuppressWarnings("static-method")
	public boolean hasMethodInherited(final JMethod jMethod) {
		Objects.requireNonNull(jMethod, "jMethod == null");
		
		return false;
	}
	
	public final boolean hasMethodOverridden(final JMethod jMethod) {
		return hasMethod(jMethod) && hasMethodInherited(jMethod);
	}
	
	public abstract boolean isInnerType();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static JType valueOf(final Class<?> clazz) {
		if(clazz == Void.TYPE) {
			return JVoid.valueOf(clazz);
		} else if(clazz.isAnnotation()) {
			return JAnnotation.valueOf(clazz);
		} else if(clazz.isArray()) {
			return JArray.valueOf(clazz);
		} else if(clazz.isEnum()) {
			return JEnum.valueOf(clazz);
		} else if(clazz.isInterface()) {
			return JInterface.valueOf(clazz);
		} else if(clazz.isPrimitive()) {
			return JPrimitive.valueOf(clazz);
		} else {
			return JClass.valueOf(clazz);
		}
	}
	
	public static JType valueOf(final String name) {
		switch(name) {
			case "boolean":
			case "Z":
			case "B":
			case "byte":
			case "C":
			case "char":
			case "D":
			case "double":
			case "F":
			case "float":
			case "I":
			case "int":
			case "J":
			case "long":
			case "S":
			case "short":
				return JPrimitive.valueOf(name);
			case "V":
			case "void":
				return JVoid.valueOf(name);
			default:
				try {
					return valueOf(Class.forName(name));
				} catch(final ClassNotFoundException | LinkageError e) {
					throw new JTypeException(e);
				}
		}
	}
}