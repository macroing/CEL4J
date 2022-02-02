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
package org.macroing.cel4j.java.binary.classfile.support;

import java.util.ArrayList;
import java.util.List;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;

/**
 * A class that consists exclusively of static methods that returns or performs various operations on {@link MethodInfo} instances.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class MethodInfos {
	private MethodInfos() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} that contains all {@link MethodInfo} instances in {@code classFile} that are constructors.
	 * <p>
	 * If {@code classFile} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @return a {@code List} that contains all {@code MethodInfo} instances in {@code classFile} that are constructors
	 * @throws IllegalArgumentException thrown if, and only if, {@link ConstantUTF8Info#findByNameIndex(ClassFile, MethodInfo)} throws it
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code ConstantUTF8Info.findByNameIndex(ClassFile, MethodInfo)} throws it
	 */
	public static List<MethodInfo> findConstructors(final ClassFile classFile) {
		return classFile.getMethodInfos().stream().filter(methodInfo -> doFilterConstructor(classFile, methodInfo)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Returns a {@code List} that contains all {@link MethodInfo} instances in {@code classFile} that are methods.
	 * <p>
	 * If {@code classFile} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @return a {@code List} that contains all {@code MethodInfo} instances in {@code classFile} that are methods
	 * @throws IllegalArgumentException thrown if, and only if, {@link ConstantUTF8Info#findByNameIndex(ClassFile, MethodInfo)} throws it
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code ConstantUTF8Info.findByNameIndex(ClassFile, MethodInfo)} throws it
	 */
	public static List<MethodInfo> findMethods(final ClassFile classFile) {
		return classFile.getMethodInfos().stream().filter(methodInfo -> doFilterMethod(classFile, methodInfo)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static boolean doFilterConstructor(final ClassFile classFile, final MethodInfo methodInfo) {
		final String name = ConstantUTF8Info.findByNameIndex(classFile, methodInfo).getStringValue();
		
		final boolean isConstructor = name.equals("<init>");
		
		return isConstructor;
	}
	
	private static boolean doFilterMethod(final ClassFile classFile, final MethodInfo methodInfo) {
		final String name = ConstantUTF8Info.findByNameIndex(classFile, methodInfo).getStringValue();
		
		final boolean isMethod = !name.equals("<clinit>") && !name.equals("<init>");
		
		return isMethod;
	}
}