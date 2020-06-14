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
package org.macroing.cel4j.java.binary.classfile;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code ClassFile} denotes a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ClassFile implements Node {
	/**
	 * This field represents ACC_ABSTRACT in the access_flags element of a ClassFile structure.
	 */
	public static final int ACC_ABSTRACT = 0x0400;
	
	/**
	 * This field represents ACC_ANNOTATION in the access_flags element of a ClassFile structure.
	 */
	public static final int ACC_ANNOTATION = 0x2000;
	
	/**
	 * This field represents ACC_ENUM in the access_flags element of a ClassFile structure.
	 */
	public static final int ACC_ENUM = 0x4000;
	
	/**
	 * This field represents ACC_FINAL in the access_flags element of a ClassFile structure.
	 */
	public static final int ACC_FINAL = 0x0010;
	
	/**
	 * This field represents ACC_INTERFACE in the access_flags element of a ClassFile structure.
	 */
	public static final int ACC_INTERFACE = 0x0200;
	
	/**
	 * This field represents ACC_MODULE in the access_flags element of a ClassFile structure.
	 */
	public static final int ACC_MODULE = 0x8000;
	
	/**
	 * This field represents ACC_PUBLIC in the access_flags element of a ClassFile structure.
	 */
	public static final int ACC_PUBLIC = 0x0001;
	
	/**
	 * This field represents ACC_SUPER in the access_flags element of a ClassFile structure.
	 */
	public static final int ACC_SUPER = 0x0020;
	
	/**
	 * This field represents ACC_SYNTHETIC in the access_flags element of a ClassFile structure.
	 */
	public static final int ACC_SYNTHETIC = 0x1000;
	
	/**
	 * The major_version for JDK 1.0.
	 */
	public static final int MAJOR_VERSION_1_0 = 45;
	
	/**
	 * The major_version for JDK 1.1.
	 */
	public static final int MAJOR_VERSION_1_1 = 45;
	
	/**
	 * The major_version for JDK 1.2.
	 */
	public static final int MAJOR_VERSION_1_2 = 46;
	
	/**
	 * The major_version for JDK 1.3.
	 */
	public static final int MAJOR_VERSION_1_3 = 47;
	
	/**
	 * The major_version for JDK 1.4.
	 */
	public static final int MAJOR_VERSION_1_4 = 48;
	
	/**
	 * The major_version for J2SE 10.0.
	 */
	public static final int MAJOR_VERSION_10_0 = 54;
	
	/**
	 * The major_version for J2SE 11.0.
	 */
	public static final int MAJOR_VERSION_11_0 = 55;
	
	/**
	 * The major_version for J2SE 5.0.
	 */
	public static final int MAJOR_VERSION_5_0 = 49;
	
	/**
	 * The major_version for J2SE 6.0.
	 */
	public static final int MAJOR_VERSION_6_0 = 50;
	
	/**
	 * The major_version for J2SE 7.0.
	 */
	public static final int MAJOR_VERSION_7_0 = 51;
	
	/**
	 * The major_version for J2SE 8.0.
	 */
	public static final int MAJOR_VERSION_8_0 = 52;
	
	/**
	 * The major_version for J2SE 9.0.
	 */
	public static final int MAJOR_VERSION_9_0 = 53;
	
	/**
	 * The minor_version for JDK 1.0.
	 */
	public static final int MINOR_VERSION_1_0 = 3;
	
	/**
	 * The minor_version for JDK 1.1.
	 */
	public static final int MINOR_VERSION_1_1 = 3;
	
	/**
	 * The minor_version for JDK 1.2.
	 */
	public static final int MINOR_VERSION_1_2 = 0;
	
	/**
	 * The minor_version for JDK 1.3.
	 */
	public static final int MINOR_VERSION_1_3 = 0;
	
	/**
	 * The minor_version for JDK 1.4.
	 */
	public static final int MINOR_VERSION_1_4 = 0;
	
	/**
	 * The minor_version for JDK 10.0.
	 */
	public static final int MINOR_VERSION_10_0 = 0;
	
	/**
	 * The minor_version for JDK 11.0.
	 */
	public static final int MINOR_VERSION_11_0 = 0;
	
	/**
	 * The minor_version for J2SE 5.0.
	 */
	public static final int MINOR_VERSION_5_0 = 0;
	
	/**
	 * The minor_version for J2SE 6.0.
	 */
	public static final int MINOR_VERSION_6_0 = 0;
	
	/**
	 * The minor_version for J2SE 7.0.
	 */
	public static final int MINOR_VERSION_7_0 = 0;
	
	/**
	 * The minor_version for J2SE 8.0.
	 */
	public static final int MINOR_VERSION_8_0 = 0;
	
	/**
	 * The minor_version for J2SE 9.0.
	 */
	public static final int MINOR_VERSION_9_0 = 0;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final CopyOnWriteArrayList<AttributeInfo> attributeInfos;
	private final CopyOnWriteArrayList<CPInfo> cPInfos;
	private final CopyOnWriteArrayList<FieldInfo> fieldInfos;
	private final CopyOnWriteArrayList<Integer> interfaces;
	private final CopyOnWriteArrayList<MethodInfo> methodInfos;
	private int accessFlags;
	private int majorVersion;
	private int minorVersion;
	private int superClass;
	private int thisClass;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new empty {@code ClassFile} instance.
	 */
	public ClassFile() {
		this.attributeInfos = new CopyOnWriteArrayList<>();
		this.cPInfos = new CopyOnWriteArrayList<>();
		this.cPInfos.add(ConstantUnreachableInfo.newInstance());
		this.fieldInfos = new CopyOnWriteArrayList<>();
		this.interfaces = new CopyOnWriteArrayList<>();
		this.methodInfos = new CopyOnWriteArrayList<>();
		this.accessFlags = ACC_PUBLIC;
		this.majorVersion = MAJOR_VERSION_8_0;
		this.minorVersion = MINOR_VERSION_8_0;
		this.superClass = 3;
		this.thisClass = 1;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link AttributeInfo} instance of this {@code ClassFile} instance that is equal to {@code attributeInfo}.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code ClassFile} instance does not contain an {@code AttributeInfo} instance that is equal to {@code attributeInfo}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeInfo the {@code AttributeInfo} instance to test for equality against
	 * @return the {@code AttributeInfo} instance of this {@code ClassFile} instance that is equal to {@code attributeInfo}
	 * @throws IllegalArgumentException thrown if, and only if, this {@code ClassFile} instance does not contain an {@code AttributeInfo} instance that is equal to {@code attributeInfo}
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	public AttributeInfo getAttributeInfo(final AttributeInfo attributeInfo) {
		Objects.requireNonNull(attributeInfo, "attributeInfo == null");
		
		for(final AttributeInfo currentAttributeInfo : getAttributeInfos()) {
			if(currentAttributeInfo.equals(attributeInfo)) {
				return currentAttributeInfo;
			}
		}
		
		throw new IllegalArgumentException("This ClassFile does not contain the provided attribute_info.");
	}
	
	/**
	 * Returns an {@link AttributeInfo} given its index.
	 * <p>
	 * If {@code index} is less than {@code 0}, or greater than or equal to {@link #getAttributeInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param index the index of the {@code AttributeInfo}
	 * @return an {@code AttributeInfo} given its index
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is less than {@code 0}, or greater than or equal to {@code getAttributeInfoCount()}
	 */
	public AttributeInfo getAttributeInfo(final int index) {
		return this.attributeInfos.get(index);
	}
	
	/**
	 * Returns the {@link CPInfo} instance of this {@code ClassFile} instance that is equal to {@code cPInfo}.
	 * <p>
	 * If {@code cPInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code ClassFile} instance does not contain a {@code CPInfo} instance that is equal to {@code cPInfo}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param cPInfo the {@code CPInfo} instance to test for equality against
	 * @return the {@code CPInfo} instance of this {@code ClassFile} instance that is equal to {@code cPInfo}
	 * @throws IllegalArgumentException thrown if, and only if, this {@code ClassFile} instance does not contain a {@code CPInfo} instance that is equal to {@code cPInfo}
	 * @throws NullPointerException thrown if, and only if, {@code cPInfo} is {@code null}
	 */
	public CPInfo getCPInfo(final CPInfo cPInfo) {
		Objects.requireNonNull(cPInfo, "cPInfo == null");
		
		for(final CPInfo currentCPInfo : getCPInfos()) {
			if(currentCPInfo.equals(cPInfo)) {
				return currentCPInfo;
			}
		}
		
		throw new IllegalArgumentException("This ClassFile does not contain the provided cp_info.");
	}
	
	/**
	 * Returns a {@link CPInfo} given its index.
	 * <p>
	 * If {@code index} is less than {@code 0}, or greater than or equal to {@link #getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param index the index of the {@code CPInfo}
	 * @return a {@code CPInfo} given its index
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is less than {@code 0}, or greater than or equal to {@code getCPInfoCount()}
	 */
	public CPInfo getCPInfo(final int index) {
		return this.cPInfos.get(index);
	}
	
	/**
	 * Returns a copy of this {@code ClassFile} instance.
	 * 
	 * @return a copy of this {@code ClassFile} instance
	 */
	public ClassFile copy() {
		final
		ClassFile classFile = new ClassFile();
		classFile.accessFlags = this.accessFlags;
		classFile.majorVersion = this.majorVersion;
		classFile.minorVersion = this.minorVersion;
		classFile.superClass = this.superClass;
		classFile.thisClass = this.thisClass;
		
		this.attributeInfos.forEach(attributeInfo -> classFile.addAttributeInfo(attributeInfo.copy()));
		
		this.cPInfos.forEach(cPInfo -> {
			if(!(cPInfo instanceof ConstantUnreachableInfo)) {
				classFile.addCPInfo(cPInfo.copy());
			}
		});
		
		this.fieldInfos.forEach(fieldInfo -> classFile.addFieldInfo(fieldInfo.copy()));
		
		this.interfaces.forEach(index -> classFile.addInterface(index.intValue()));
		
		this.methodInfos.forEach(methodInfo -> classFile.addMethodInfo(methodInfo.copy()));
		
		return classFile;
	}
	
	/**
	 * Writes this {@code ClassFile} to {@code dataOutput}.
	 * <p>
	 * Returns {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an {@code IOException} is caught, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * This method does not close {@code dataOutput}.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @return {@code dataOutput}
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, some {@code IOException} is caught
	 */
	public DataOutput write(final DataOutput dataOutput) {
		try {
			dataOutput.writeInt(0xCAFEBABE);
			dataOutput.writeShort(this.minorVersion);
			dataOutput.writeShort(this.majorVersion);
			dataOutput.writeShort(this.cPInfos.size());
			
			this.cPInfos.forEach(cPInfo -> cPInfo.write(dataOutput));
			
			dataOutput.writeShort(this.accessFlags);
			dataOutput.writeShort(this.thisClass);
			dataOutput.writeShort(this.superClass);
			dataOutput.writeShort(this.interfaces.size());
			
			for(final int interfaceIndex : this.interfaces) {
				dataOutput.writeShort(interfaceIndex);
			}
			
			dataOutput.writeShort(this.fieldInfos.size());
			
			this.fieldInfos.forEach(fieldInfo -> fieldInfo.write(dataOutput));
			
			dataOutput.writeShort(this.methodInfos.size());
			
			this.methodInfos.forEach(methodInfo -> methodInfo.write(dataOutput));
			
			dataOutput.writeShort(this.attributeInfos.size());
			
			this.attributeInfos.forEach(attributeInfo -> attributeInfo.write(dataOutput));
			
			return dataOutput;
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Writes this {@code ClassFile} to {@code file}.
	 * <p>
	 * Returns the {@code DataOutput} that was written to.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an {@code IOException} is caught, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * The {@code DataOutput} returned will get closed.
	 * 
	 * @param file the {@code File} to write to
	 * @return the {@code DataOutput} that was written to
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an {@code IOException} is caught
	 */
	public DataOutput write(final File file) {
		final File parentFile = file.getParentFile();
		
		if(!parentFile.exists()) {
			parentFile.mkdirs();
		}
		
		try(final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(Objects.requireNonNull(file, "file == null")))) {
			return write(dataOutputStream);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Writes this {@code ClassFile} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * classFile.write(new Document());
	 * }
	 * </pre>
	 * 
	 * @return the {@code Document}
	 */
	public Document write() {
		return write(new Document());
	}
	
	/**
	 * Writes this {@code ClassFile} to {@code document}.
	 * <p>
	 * Returns {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param document the {@link Document} to write to
	 * @return {@code document}
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	public Document write(final Document document) {
		document.linef("ClassFile = {");
		document.indent();
		document.linef("u4 magic = %s;", Integer.toString(getMagic()));
		document.linef("u2 minor_version = %s;", Integer.toString(getMinorVersion()));
		document.linef("u2 major_version = %s;", Integer.toString(getMajorVersion()));
		document.linef("u2 constant_pool_count = %s;", Integer.toString(getCPInfoCount()));
		document.linef("cp_info[%s] constant_pool = {", Integer.toString(getCPInfoCount() - 1));
		document.indent();
		
		for(final CPInfo cPInfo : getCPInfos()) {
			cPInfo.write(document);
		}
		
		document.outdent();
		document.linef("};");
		document.linef("u2 access_flags = %s;", Integer.toString(getAccessFlags()));
		document.linef("u2 this_class = %s;", Integer.toString(getThisClass()));
		document.linef("u2 super_class = %s;", Integer.toString(getSuperClass()));
		document.linef("u2 interfaces_count = %s;", Integer.toString(getInterfaceCount()));
		document.linef("u2[%s] interfaces = {", Integer.toString(getInterfaceCount()));
		document.indent();
		
		for(final int interfaceIndex : getInterfaces()) {
			document.linef("u2 interface = %s;", Integer.toString(interfaceIndex));
		}
		
		document.outdent();
		document.linef("};");
		document.linef("u2 fields_count = %s;", Integer.toString(getFieldInfoCount()));
		document.linef("field_info[%s] fields = {", Integer.toString(getFieldInfoCount()));
		document.indent();
		
		for(final FieldInfo fieldInfo : getFieldInfos()) {
			fieldInfo.write(document);
		}
		
		document.outdent();
		document.linef("};");
		document.linef("u2 methods_count = %s;", Integer.toString(getMethodInfoCount()));
		document.linef("method_info[%s] methods = {", Integer.toString(getMethodInfoCount()));
		document.indent();
		
		for(final MethodInfo methodInfo : getMethodInfos()) {
			methodInfo.write(document);
		}
		
		document.outdent();
		document.linef("};");
		document.linef("u2 attributes_count = %s;", Integer.toString(getAttributeInfoCount()));
		document.linef("attribute_info[%s] attributes = {", Integer.toString(getAttributeInfoCount()));
		document.indent();
		document.outdent();
		document.linef("};");
		document.outdent();
		document.linef("};");
		
		return document;
	}
	
	/**
	 * Returns the {@link FieldInfo} instance of this {@code ClassFile} instance that is equal to {@code fieldInfo}.
	 * <p>
	 * If {@code fieldInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code ClassFile} instance does not contain a {@code FieldInfo} instance that is equal to {@code fieldInfo}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param fieldInfo the {@code FieldInfo} instance to test for equality against
	 * @return the {@code FieldInfo} instance of this {@code ClassFile} instance that is equal to {@code fieldInfo}
	 * @throws IllegalArgumentException thrown if, and only if, this {@code ClassFile} instance does not contain a {@code FieldInfo} instance that is equal to {@code fieldInfo}
	 * @throws NullPointerException thrown if, and only if, {@code fieldInfo} is {@code null}
	 */
	public FieldInfo getFieldInfo(final FieldInfo fieldInfo) {
		Objects.requireNonNull(fieldInfo, "fieldInfo == null");
		
		for(final FieldInfo currentFieldInfo : getFieldInfos()) {
			if(currentFieldInfo.equals(fieldInfo)) {
				return currentFieldInfo;
			}
		}
		
		throw new IllegalArgumentException("This ClassFile does not contain the provided field_info.");
	}
	
	/**
	 * Returns a {@link FieldInfo} given its index.
	 * <p>
	 * If {@code index} is less than {@code 0}, or greater than or equal to {@link #getFieldInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param index the index of the {@code FieldInfo}
	 * @return a {@code FieldInfo} given its index
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is less than {@code 0}, or greater than or equal to {@code getFieldInfoCount()}
	 */
	public FieldInfo getFieldInfo(final int index) {
		return this.fieldInfos.get(index);
	}
	
	/**
	 * Returns a {@code List} with all currently added {@link AttributeInfo}s.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code ClassFile} instance.
	 * 
	 * @return a {@code List} with all currently added {@code AttributeInfo}s
	 */
	public List<AttributeInfo> getAttributeInfos() {
		return new ArrayList<>(this.attributeInfos);
	}
	
	/**
	 * Returns a {@code List} with all currently added {@link CPInfo}s.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code ClassFile} instance.
	 * 
	 * @return a {@code List} with all currently added {@code CPInfo}s
	 */
	public List<CPInfo> getCPInfos() {
		return new ArrayList<>(this.cPInfos);
	}
	
	/**
	 * Returns a {@code List} with all currently added {@link FieldInfo}s.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code ClassFile} instance.
	 * 
	 * @return a {@code List} with all currently added {@code FieldInfo}s
	 */
	public List<FieldInfo> getFieldInfos() {
		return new ArrayList<>(this.fieldInfos);
	}
	
	/**
	 * Returns a {@code List} with all currently added interfaces.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code ClassFile} instance.
	 * 
	 * @return a {@code List} with all currently added interfaces
	 */
	public List<Integer> getInterfaces() {
		return new ArrayList<>(this.interfaces);
	}
	
	/**
	 * Returns a {@code List} with all currently added {@link MethodInfo}s.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code ClassFile} instance.
	 * 
	 * @return a {@code List} with all currently added {@code MethodInfo}s
	 */
	public List<MethodInfo> getMethodInfos() {
		return new ArrayList<>(this.methodInfos);
	}
	
	/**
	 * Returns the {@link MethodInfo} instance of this {@code ClassFile} instance that is equal to {@code methodInfo}.
	 * <p>
	 * If {@code methodInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code ClassFile} instance does not contain a {@code MethodInfo} instance that is equal to {@code methodInfo}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param methodInfo the {@code MethodInfo} instance to test for equality against
	 * @return the {@code MethodInfo} instance of this {@code ClassFile} instance that is equal to {@code methodInfo}
	 * @throws IllegalArgumentException thrown if, and only if, this {@code ClassFile} instance does not contain a {@code MethodInfo} instance that is equal to {@code methodInfo}
	 * @throws NullPointerException thrown if, and only if, {@code methodInfo} is {@code null}
	 */
	public MethodInfo getMethodInfo(final MethodInfo methodInfo) {
		Objects.requireNonNull(methodInfo, "methodInfo == null");
		
		for(final MethodInfo currentMethodInfo : getMethodInfos()) {
			if(currentMethodInfo.equals(methodInfo)) {
				return currentMethodInfo;
			}
		}
		
		throw new IllegalArgumentException("This ClassFile does not contain the provided method_info.");
	}
	
	/**
	 * Returns a {@link MethodInfo} given its index.
	 * <p>
	 * If {@code index} is less than {@code 0}, or greater than or equal to {@link #getMethodInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param index the index of the {@code MethodInfo}
	 * @return a {@code MethodInfo} given its index
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is less than {@code 0}, or greater than or equal to {@code getMethodInfoCount()}
	 */
	public MethodInfo getMethodInfo(final int index) {
		return this.methodInfos.get(index);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassFile} instance.
	 * 
	 * @return a {@code String} representation of this {@code ClassFile} instance
	 */
	@Override
	public String toString() {
		return write().toString();
	}
	
	/**
	 * Returns the {@link AttributeInfo} instance of this {@code ClassFile} instance that is equal to {@code attributeInfo}.
	 * <p>
	 * If either {@code attributeInfo} or {@code clazz} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code ClassFile} instance does not contain an {@code AttributeInfo} instance that is equal to {@code attributeInfo}, or {@code clazz} is not assignable from the {@code AttributeInfo} instance, an {@code IllegalArgumentException} will
	 * be thrown.
	 * 
	 * @param <T> the type of the {@code AttributeInfo} to return
	 * @param attributeInfo the {@code AttributeInfo} instance to test for equality against
	 * @param clazz the {@code Class} of the {@code AttributeInfo} to return
	 * @return the {@code AttributeInfo} instance of this {@code ClassFile} instance that is equal to {@code attributeInfo}
	 * @throws IllegalArgumentException thrown if, and only if, this {@code ClassFile} instance does not contain an {@code AttributeInfo} instance that is equal to {@code attributeInfo}, or {@code clazz} is not assignable from the
	 *                                  {@code AttributeInfo} instance
	 * @throws NullPointerException thrown if, and only if, either {@code attributeInfo} or {@code clazz} are {@code null}
	 */
	public <T extends AttributeInfo> T getAttributeInfo(final T attributeInfo, final Class<T> clazz) {
		final AttributeInfo currentAttributeInfo = getAttributeInfo(attributeInfo);
		
		if(!clazz.isAssignableFrom(currentAttributeInfo.getClass())) {
			throw new IllegalArgumentException("The attribute_info is of wrong type.");
		}
		
		return clazz.cast(currentAttributeInfo);
	}
	
	/**
	 * Returns an {@link AttributeInfo} cast to {@code T} given its index.
	 * <p>
	 * If {@code index} is less than {@code 0}, or greater than or equal to {@link #getAttributeInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * <p>
	 * If {@code clazz} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code clazz} is not assignable from the {@code AttributeInfo} instance located at {@code index}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param <T> the type of the {@code AttributeInfo} to return
	 * @param index the index of the {@code AttributeInfo}
	 * @param clazz the {@code Class} of the {@code AttributeInfo} to return
	 * @return an {@code AttributeInfo} cast to {@code T} given its index
	 * @throws IllegalArgumentException thrown if, and only if, {@code clazz} is not assignable from the {@code AttributeInfo} instance located at {@code index}
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is less than {@code 0}, or greater than or equal to {@code getAttributeInfoCount()}
	 * @throws NullPointerException thrown if, and only if, {@code clazz} is {@code null}
	 */
	public <T extends AttributeInfo> T getAttributeInfo(final int index, final Class<T> clazz) {
		final AttributeInfo attributeInfo = getAttributeInfo(index);
		
		if(!clazz.isAssignableFrom(attributeInfo.getClass())) {
			throw new IllegalArgumentException(String.format("The attribute_info refered to by index %s is of wrong type.", Integer.toString(index)));
		}
		
		return clazz.cast(attributeInfo);
	}
	
	/**
	 * Returns the {@link CPInfo} instance of this {@code ClassFile} instance that is equal to {@code cPInfo}.
	 * <p>
	 * If either {@code cPInfo} or {@code clazz} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code ClassFile} instance does not contain a {@code CPInfo} instance that is equal to {@code cPInfo}, or {@code clazz} is not assignable from the {@code CPInfo} instance, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param <T> the type of the {@code CPInfo} to return
	 * @param cPInfo the {@code CPInfo} instance to test for equality against
	 * @param clazz the {@code Class} of the {@code CPInfo} to return
	 * @return the {@code CPInfo} instance of this {@code ClassFile} instance that is equal to {@code cPInfo}
	 * @throws IllegalArgumentException thrown if, and only if, this {@code ClassFile} instance does not contain a {@code CPInfo} instance that is equal to {@code cPInfo}, or {@code clazz} is not assignable from the {@code CPInfo} instance
	 * @throws NullPointerException thrown if, and only if, either {@code cPInfo} or {@code clazz} are {@code null}
	 */
	public <T extends CPInfo> T getCPInfo(final T cPInfo, final Class<T> clazz) {
		final CPInfo currentCPInfo = getCPInfo(cPInfo);
		
		if(!clazz.isAssignableFrom(currentCPInfo.getClass())) {
			throw new IllegalArgumentException("The cp_info is of wrong type.");
		}
		
		return clazz.cast(currentCPInfo);
	}
	
	/**
	 * Returns a {@link CPInfo} cast to {@code T} given its index.
	 * <p>
	 * If {@code index} is less than {@code 0}, or greater than or equal to {@link #getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * <p>
	 * If {@code clazz} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code clazz} is not assignable from the {@code CPInfo} instance located at {@code index}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param <T> the type of the {@code CPInfo} to return
	 * @param index the index of the {@code CPInfo}
	 * @param clazz the {@code Class} of the {@code CPInfo} to return
	 * @return a {@code CPInfo} cast to {@code T} given its index
	 * @throws IllegalArgumentException thrown if, and only if, {@code clazz} is not assignable from the {@code CPInfo} instance located at {@code index}
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is less than {@code 0}, or greater than or equal to {@code getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, {@code clazz} is {@code null}
	 */
	public <T extends CPInfo> T getCPInfo(final int index, final Class<T> clazz) {
		final CPInfo cPInfo = getCPInfo(index);
		
		if(!clazz.isAssignableFrom(cPInfo.getClass())) {
			throw new IllegalArgumentException(String.format("The cp_info refered to by index %s is of wrong type.", Integer.toString(index)));
		}
		
		return clazz.cast(cPInfo);
	}
	
	/**
	 * Accepts a {@link NodeHierarchicalVisitor}.
	 * <p>
	 * Returns the result of {@code nodeHierarchicalVisitor.visitLeave(this)}.
	 * <p>
	 * If {@code nodeHierarchicalVisitor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}, a {@code NodeTraversalException} will be thrown with the {@code RuntimeException} wrapped.
	 * <p>
	 * This implementation will:
	 * <ul>
	 * <li>throw a {@code NullPointerException} if {@code nodeHierarchicalVisitor} is {@code null}.</li>
	 * <li>throw a {@code NodeTraversalException} if {@code nodeHierarchicalVisitor} throws a {@code RuntimeException}.</li>
	 * <li>traverse its child {@code Node}s, if it has any.</li>
	 * </ul>
	 * 
	 * @param nodeHierarchicalVisitor the {@code NodeHierarchicalVisitor} to accept
	 * @return the result of {@code nodeHierarchicalVisitor.visitLeave(this)}
	 * @throws NodeTraversalException thrown if, and only if, a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code nodeHierarchicalVisitor} is {@code null}
	 */
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				for(final CPInfo cPInfo : this.cPInfos) {
					if(!cPInfo.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				for(final FieldInfo fieldInfo : this.fieldInfos) {
					if(!fieldInfo.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				for(final MethodInfo methodInfo : this.methodInfos) {
					if(!methodInfo.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				for(final AttributeInfo attributeInfo : this.attributeInfos) {
					if(!attributeInfo.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Adds {@code attributeInfo} to this {@code ClassFile} instance, if absent.
	 * <p>
	 * Returns {@code true} if, and only if, {@code attributeInfo} was added, {@code false} otherwise.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attributeInfo the {@link AttributeInfo} to add
	 * @return {@code true} if, and only if, {@code attributeInfo} was added, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	public boolean addAttributeInfo(final AttributeInfo attributeInfo) {
		return this.attributeInfos.addIfAbsent(Objects.requireNonNull(attributeInfo, "attributeInfo == null"));
	}
	
	/**
	 * Adds {@code cPInfo} to this {@code ClassFile} instance.
	 * <p>
	 * Returns {@code true} if, and only if, {@code cPInfo} was added, {@code false} otherwise.
	 * <p>
	 * If {@code cPInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param cPInfo the {@link CPInfo} to add
	 * @return {@code true} if, and only if, {@code cPInfo} was added, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code cPInfo} is {@code null}
	 */
	public boolean addCPInfo(final CPInfo cPInfo) {
		if(this.cPInfos.add(Objects.requireNonNull(cPInfo, "cPInfo == null"))) {
			for(int i = 1; i < cPInfo.getConstantPoolEntryCount(); i++) {
				this.cPInfos.add(ConstantUnreachableInfo.newInstance());
			}
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Adds {@code fieldInfo} to this {@code ClassFile} instance, if absent.
	 * <p>
	 * Returns {@code true} if, and only if, {@code fieldInfo} was added, {@code false} otherwise.
	 * <p>
	 * If {@code fieldInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param fieldInfo the {@link FieldInfo} to add
	 * @return {@code true} if, and only if, {@code fieldInfo} was added, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code fieldInfo} is {@code null}
	 */
	public boolean addFieldInfo(final FieldInfo fieldInfo) {
		return this.fieldInfos.addIfAbsent(Objects.requireNonNull(fieldInfo, "fieldInfo == null"));
	}
	
	/**
	 * Adds an interface index to this {@code ClassFile} instance, if absent.
	 * <p>
	 * Returns {@code true} if, and only if, the interface index was added, {@code false} otherwise.
	 * <p>
	 * If {@code index} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index the interface index to add
	 * @return {@code true} if, and only if, the interface index was added, {@code false} otherwise
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than or equal to {@code 0}
	 */
	public boolean addInterface(final int index) {
		return this.interfaces.addIfAbsent(Integer.valueOf(ParameterArguments.requireRange(index, 1, Integer.MAX_VALUE)));
	}
	
	/**
	 * Adds {@code methodInfo} to this {@code ClassFile} instance, if absent.
	 * <p>
	 * Returns {@code true} if, and only if, {@code methodInfo} was added, {@code false} otherwise.
	 * <p>
	 * If {@code methodInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param methodInfo the {@link MethodInfo} to add
	 * @return {@code true} if, and only if, {@code methodInfo} was added, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code methodInfo} is {@code null}
	 */
	public boolean addMethodInfo(final MethodInfo methodInfo) {
		return this.methodInfos.addIfAbsent(Objects.requireNonNull(methodInfo, "methodInfo"));
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code ClassFile} instance contains {@code attributeInfo}, {@code false} otherwise.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attributeInfo the {@link AttributeInfo} to check
	 * @return {@code true} if, and only if, this {@code ClassFile} instance contains {@code attributeInfo}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	public boolean containsAttributeInfo(final AttributeInfo attributeInfo) {
		Objects.requireNonNull(attributeInfo, "attributeInfo == null");
		
		for(final AttributeInfo currentAttributeInfo : getAttributeInfos()) {
			if(currentAttributeInfo.equals(attributeInfo)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code ClassFile} instance contains {@code cPInfo}, {@code false} otherwise.
	 * <p>
	 * If {@code cPInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param cPInfo the {@link CPInfo} to check
	 * @return {@code true} if, and only if, this {@code ClassFile} instance contains {@code cPInfo}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code cPInfo} is {@code null}
	 */
	public boolean containsCPInfo(final CPInfo cPInfo) {
		Objects.requireNonNull(cPInfo, "cPInfo == null");
		
		for(final CPInfo currentCPInfo : getCPInfos()) {
			if(currentCPInfo.equals(cPInfo)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code ClassFile} instance contains {@code fieldInfo}, {@code false} otherwise.
	 * <p>
	 * If {@code fieldInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param fieldInfo the {@link FieldInfo} to check
	 * @return {@code true} if, and only if, this {@code ClassFile} instance contains {@code fieldInfo}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code fieldInfo} is {@code null}
	 */
	public boolean containsFieldInfo(final FieldInfo fieldInfo) {
		Objects.requireNonNull(fieldInfo, "fieldInfo == null");
		
		for(final FieldInfo currentFieldInfo : getFieldInfos()) {
			if(currentFieldInfo.equals(fieldInfo)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code ClassFile} instance contains {@code methodInfo}, {@code false} otherwise.
	 * <p>
	 * If {@code methodInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param methodInfo the {@link MethodInfo} to check
	 * @return {@code true} if, and only if, this {@code ClassFile} instance contains {@code methodInfo}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code methodInfo} is {@code null}
	 */
	public boolean containsMethodInfo(final MethodInfo methodInfo) {
		Objects.requireNonNull(methodInfo, "methodInfo == null");
		
		for(final MethodInfo currentMethodInfo : getMethodInfos()) {
			if(currentMethodInfo.equals(methodInfo)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Compares {@code object} to this {@code ClassFile} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ClassFile}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ClassFile} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ClassFile}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ClassFile)) {
			return false;
		} else if(ClassFile.class.cast(object).minorVersion != this.minorVersion) {
			return false;
		} else if(ClassFile.class.cast(object).majorVersion != this.majorVersion) {
			return false;
		} else if(!Objects.equals(ClassFile.class.cast(object).cPInfos, this.cPInfos)) {
			return false;
		} else if(ClassFile.class.cast(object).accessFlags != this.accessFlags) {
			return false;
		} else if(ClassFile.class.cast(object).thisClass != this.thisClass) {
			return false;
		} else if(ClassFile.class.cast(object).superClass != this.superClass) {
			return false;
		} else if(!Objects.equals(ClassFile.class.cast(object).interfaces, this.interfaces)) {
			return false;
		} else if(!Objects.equals(ClassFile.class.cast(object).fieldInfos, this.fieldInfos)) {
			return false;
		} else if(!Objects.equals(ClassFile.class.cast(object).methodInfos, this.methodInfos)) {
			return false;
		} else if(!Objects.equals(ClassFile.class.cast(object).attributeInfos, this.attributeInfos)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_ABSTRACT is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_ABSTRACT is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise
	 */
	public boolean isAbstract() {
		return (this.accessFlags & ACC_ABSTRACT) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_ANNOTATION is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_ANNOTATION is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise
	 */
	public boolean isAnnotation() {
		return (this.accessFlags & ACC_ANNOTATION) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_ENUM is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_ENUM is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise
	 */
	public boolean isEnum() {
		return (this.accessFlags & ACC_ENUM) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_FINAL is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_FINAL is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise
	 */
	public boolean isFinal() {
		return (this.accessFlags & ACC_FINAL) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_INTERFACE is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_INTERFACE is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise
	 */
	public boolean isInterface() {
		return (this.accessFlags & ACC_INTERFACE) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_MODULE is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_MODULE is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise
	 */
	public boolean isModule() {
		return (this.accessFlags & ACC_MODULE) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_PUBLIC is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_PUBLIC is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise
	 */
	public boolean isPublic() {
		return (this.accessFlags & ACC_PUBLIC) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_SUPER is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_SUPER is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise
	 */
	public boolean isSuper() {
		return (this.accessFlags & ACC_SUPER) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_SYNTHETIC is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_SYNTHETIC is set in the access_flags item of this {@code ClassFile} instance, {@code false} otherwise
	 */
	public boolean isSynthetic() {
		return (this.accessFlags & ACC_SYNTHETIC) != 0;
	}
	
	/**
	 * Removes {@code attributeInfo} from this {@code ClassFile} instance, if present.
	 * <p>
	 * Returns {@code true} if, and only if, {@code attributeInfo} was removed, {@code false} otherwise.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attributeInfo the {@link AttributeInfo} to remove
	 * @return {@code true} if, and only if, {@code attributeInfo} was removed, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	public boolean removeAttributeInfo(final AttributeInfo attributeInfo) {
		return this.attributeInfos.remove(Objects.requireNonNull(attributeInfo, "attributeInfo"));
	}
	
	/**
	 * Removes {@code cPInfo} from this {@code ClassFile} instance, if present.
	 * <p>
	 * Returns {@code true} if, and only if, {@code cPInfo} was removed, {@code false} otherwise.
	 * <p>
	 * If {@code cPInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param cPInfo the {@link CPInfo} to remove
	 * @return {@code true} if, and only if, {@code cPInfo} was removed, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code cPInfo} is {@code null}
	 */
	public boolean removeCPInfo(CPInfo cPInfo) {
		final int index = this.cPInfos.indexOf(Objects.requireNonNull(cPInfo, "cPInfo"));
		
		if(index != -1) {
			boolean isRemoved = this.cPInfos.remove(index) == cPInfo;
			
			if(index < this.cPInfos.size()) {
				cPInfo = this.cPInfos.get(index);
				
				if(cPInfo instanceof ConstantUnreachableInfo) {
					isRemoved &= this.cPInfos.remove(index) == cPInfo;
				}
			}
			
			return isRemoved;
		}
		
		return false;
	}
	
	/**
	 * Removes {@code fieldInfo} from this {@code ClassFile} instance, if present.
	 * <p>
	 * Returns {@code true} if, and only if, {@code fieldInfo} was removed, {@code false} otherwise.
	 * <p>
	 * If {@code fieldInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param fieldInfo the {@link FieldInfo} to remove
	 * @return {@code true} if, and only if, {@code fieldInfo} was removed, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code fieldInfo} is {@code null}
	 */
	public boolean removeFieldInfo(final FieldInfo fieldInfo) {
		return this.fieldInfos.remove(Objects.requireNonNull(fieldInfo, "fieldInfo == null"));
	}
	
	/**
	 * Removes the interface index {@code index} from this {@code ClassFile} instance, if present.
	 * <p>
	 * Returns {@code true} if, and only if, the interface index was removed, {@code false} otherwise.
	 * <p>
	 * If {@code index} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index the interface index to remove
	 * @return {@code true} if, and only if, the interface index was removed, {@code false} otherwise
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than or equal to {@code 0}
	 */
	public boolean removeInterface(final int index) {
		return this.interfaces.remove(Integer.valueOf(ParameterArguments.requireRange(index, 1, Integer.MAX_VALUE)));
	}
	
	/**
	 * Removes {@code methodInfo} from this {@code ClassFile} instance, if present.
	 * <p>
	 * Returns {@code true} if, and only if, {@code methodInfo} was removed, {@code false} otherwise.
	 * <p>
	 * If {@code methodInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param methodInfo the {@link MethodInfo} to remove
	 * @return {@code true} if, and only if, {@code methodInfo} was removed, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code methodInfo} is {@code null}
	 */
	public boolean removeMethodInfo(final MethodInfo methodInfo) {
		return this.methodInfos.remove(Objects.requireNonNull(methodInfo, "methodInfo"));
	}
	
	/**
	 * Sets {@code attributeInfo} as the {@link AttributeInfo} on the given index.
	 * <p>
	 * Returns {@code true} if, and only if, {@code attributeInfo} was set, {@code false} otherwise.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code index} is less than {@code 0}, or greater than or equal to {@link #getAttributeInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param attributeInfo the {@code AttributeInfo} to set
	 * @param index the index of the {@code AttributeInfo}
	 * @return {@code true} if, and only if, {@code attributeInfo} was set, {@code false} otherwise
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is less than {@code 0}, or greater than or equal to {@code getAttributeInfoCount()}
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	public boolean setAttributeInfo(final AttributeInfo attributeInfo, final int index) {
		this.attributeInfos.set(index, Objects.requireNonNull(attributeInfo, "attributeInfo == null"));
		
		return true;
	}
	
	/**
	 * Sets {@code cPInfo} as the {@link CPInfo} on the given index.
	 * <p>
	 * Returns {@code true} if, and only if, {@code cPInfo} is not an instance of {@link ConstantUnreachableInfo} and it was set, {@code false} otherwise.
	 * <p>
	 * If {@code cPInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code index} is less than {@code 0}, or greater than or equal to {@link #getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param cPInfo the {@code CPInfo} to set
	 * @param index the index of the {@code CPInfo}
	 * @return {@code true} if, and only if, {@code cPInfo} is not an instance of {@code ConstantUnreachableInfo} and it was set, {@code false} otherwise
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is less than {@code 0}, or greater than or equal to {@code getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, {@code cPInfo} is {@code null}
	 */
	public boolean setCPInfo(final CPInfo cPInfo, final int index) {
		if(cPInfo instanceof ConstantUnreachableInfo) {
			return false;
		}
		
		final CPInfo cPInfo0 = this.cPInfos.get(index);
		
		if(cPInfo0 instanceof ConstantUnreachableInfo) {
			return false;
		}
		
		final CPInfo cPInfo1 = this.cPInfos.remove(index);
		
		for(int i = 1; i < cPInfo1.getConstantPoolEntryCount(); i++) {
			this.cPInfos.remove(index);
		}
		
		this.cPInfos.add(index, Objects.requireNonNull(cPInfo, "cPInfo == null"));
		
		for(int i = 1; i < cPInfo.getConstantPoolEntryCount(); i++) {
			this.cPInfos.add(index + i, ConstantUnreachableInfo.newInstance());
		}
		
		return true;
	}
	
	/**
	 * Sets {@code fieldInfo} as the {@link FieldInfo} on the given index.
	 * <p>
	 * Returns {@code true} if, and only if, {@code fieldInfo} was set, {@code false} otherwise.
	 * <p>
	 * If {@code fieldInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code index} is less than {@code 0}, or greater than or equal to {@link #getFieldInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param fieldInfo the {@code FieldInfo} to set
	 * @param index the index of the {@code FieldInfo}
	 * @return {@code true} if, and only if, {@code fieldInfo} was set, {@code false} otherwise
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is less than {@code 0}, or greater than or equal to {@code getFieldInfoCount()}
	 * @throws NullPointerException thrown if, and only if, {@code fieldInfo} is {@code null}
	 */
	public boolean setFieldInfo(final FieldInfo fieldInfo, final int index) {
		this.fieldInfos.set(index, Objects.requireNonNull(fieldInfo, "fieldInfo == null"));
		
		return true;
	}
	
	/**
	 * Sets {@code methodInfo} as the {@link MethodInfo} on the given index.
	 * <p>
	 * Returns {@code true} if, and only if, {@code methodInfo} was set, {@code false} otherwise.
	 * <p>
	 * If {@code methodInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code index} is less than {@code 0}, or greater than or equal to {@link #getMethodInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param methodInfo the {@code MethodInfo} to set
	 * @param index the index of the {@code MethodInfo}
	 * @return {@code true} if, and only if, {@code methodInfo} was set, {@code false} otherwise
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is less than {@code 0}, or greater than or equal to {@code getMethodInfoCount()}
	 * @throws NullPointerException thrown if, and only if, {@code methodInfo} is {@code null}
	 */
	public boolean setMethodInfo(final MethodInfo methodInfo, final int index) {
		this.methodInfos.set(index, Objects.requireNonNull(methodInfo, "methodInfo == null"));
		
		return true;
	}
	
	/**
	 * Returns the access_flags of this {@code ClassFile} instance.
	 * 
	 * @return the access_flags of this {@code ClassFile} instance
	 */
	public int getAccessFlags() {
		return this.accessFlags;
	}
	
	/**
	 * Returns the number of {@link AttributeInfo}s currently added.
	 * 
	 * @return the number of {@code AttributeInfo}s currently added
	 */
	public int getAttributeInfoCount() {
		return this.attributeInfos.size();
	}
	
	/**
	 * Returns the number of {@link CPInfo}s currently added.
	 * 
	 * @return the number of {@code CPInfo}s currently added
	 */
	public int getCPInfoCount() {
		return this.cPInfos.size();
	}
	
	/**
	 * Returns the number of {@link FieldInfo}s currently added.
	 * 
	 * @return the number of {@code FieldInfo}s currently added
	 */
	public int getFieldInfoCount() {
		return this.fieldInfos.size();
	}
	
	/**
	 * Returns the number of interfaces currently added.
	 * 
	 * @return the number of interfaces currently added
	 */
	public int getInterfaceCount() {
		return this.interfaces.size();
	}
	
	/**
	 * Returns the magic of this {@code ClassFile} instance.
	 * 
	 * @return the magic of this {@code ClassFile} instance
	 */
	@SuppressWarnings("static-method")
	public int getMagic() {
		return 0xCAFEBABE;
	}
	
	/**
	 * Returns the major_version of this {@code ClassFile} instance.
	 * 
	 * @return the major_version of this {@code ClassFile} instance
	 */
	public int getMajorVersion() {
		return this.majorVersion;
	}
	
	/**
	 * Returns the number of {@link MethodInfo}s currently added.
	 * 
	 * @return the number of {@code MethodInfo}s currently added
	 */
	public int getMethodInfoCount() {
		return this.methodInfos.size();
	}
	
	/**
	 * Returns the minor_version of this {@code ClassFile} instance.
	 * 
	 * @return the minor_version of this {@code ClassFile} instance
	 */
	public int getMinorVersion() {
		return this.minorVersion;
	}
	
	/**
	 * Returns the super_class of this {@code ClassFile} instance.
	 * 
	 * @return the super_class of this {@code ClassFile} instance
	 */
	public int getSuperClass() {
		return this.superClass;
	}
	
	/**
	 * Returns the this_class of this {@code ClassFile} instance.
	 * 
	 * @return the this_class of this {@code ClassFile} instance
	 */
	public int getThisClass() {
		return this.thisClass;
	}
	
	/**
	 * Returns a hash code for this {@code ClassFile} instance.
	 * 
	 * @return a hash code for this {@code ClassFile} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.minorVersion), Integer.valueOf(this.majorVersion), this.cPInfos, Integer.valueOf(this.accessFlags), Integer.valueOf(this.thisClass), Integer.valueOf(this.superClass), this.interfaces, this.fieldInfos, this.methodInfos, this.attributeInfos);
	}
	
	/**
	 * Returns the index of {@code cPInfo} in the constant_pool of this {@code ClassFile} instance, or {@code -1} if it is an instance of {@link ConstantUnreachableInfo} or it does not exist.
	 * <p>
	 * If {@code cPInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param cPInfo the {@link CPInfo} to get the index of in this {@code ClassFile} instance
	 * @return the index of {@code cPInfo} in the constant_pool of this {@code ClassFile} instance, or {@code -1} if it is an instance of {@code ConstantUnreachableInfo} or it does not exist
	 * @throws NullPointerException thrown if, and only if, {@code cPInfo} is {@code null}
	 */
	public int indexOf(final CPInfo cPInfo) {
		Objects.requireNonNull(cPInfo, "cPInfo == null");
		
		if(cPInfo instanceof ConstantUnreachableInfo) {
			return -1;
		}
		
		final List<CPInfo> cPInfos = getCPInfos();
		
		for(int index = 1; index < cPInfos.size();) {
			final CPInfo currentCPInfo = cPInfos.get(index);
			
			if(currentCPInfo.equals(cPInfo)) {
				return index;
			}
			
			index += currentCPInfo.getConstantPoolEntryCount();
		}
		
		return -1;
	}
	
	/**
	 * Adds or removes the access flag ACC_ABSTRACT.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_FINAL</li>
	 * <li>ACC_MODULE</li>
	 * </ul>
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code false}:
	 * <ul>
	 * <li>ACC_ANNOTATION</li>
	 * <li>ACC_INTERFACE</li>
	 * </ul>
	 * 
	 * @param isAbstract {@code true} to add the access flag ACC_ABSTRACT
	 */
	public void setAbstract(final boolean isAbstract) {
		if(isAbstract) {
			this.accessFlags |= ACC_ABSTRACT;
			this.accessFlags &= ~ACC_FINAL;
			this.accessFlags &= ~ACC_MODULE;
		} else {
			this.accessFlags &= ~ACC_ABSTRACT;
			this.accessFlags &= ~ACC_ANNOTATION;
			this.accessFlags &= ~ACC_INTERFACE;
		}
	}
	
	/**
	 * Sets the access_flags for this {@code ClassFile} instance.
	 * 
	 * @param accessFlags the access_flags to set
	 */
	public void setAccessFlags(final int accessFlags) {
		this.accessFlags = accessFlags;
	}
	
	/**
	 * Adds or removes the access flag ACC_ANNOTATION.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_ENUM</li>
	 * <li>ACC_FINAL</li>
	 * <li>ACC_MODULE</li>
	 * <li>ACC_SUPER</li>
	 * </ul>
	 * <p>
	 * This method turns on the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_ABSTRACT</li>
	 * <li>ACC_INTERFACE</li>
	 * </ul>
	 * 
	 * @param isAnnotation {@code true} to add the access flag ACC_ANNOTATION
	 */
	public void setAnnotation(final boolean isAnnotation) {
		if(isAnnotation) {
			this.accessFlags |= ACC_ABSTRACT;
			this.accessFlags |= ACC_ANNOTATION;
			this.accessFlags &= ~ACC_ENUM;
			this.accessFlags &= ~ACC_FINAL;
			this.accessFlags |= ACC_INTERFACE;
			this.accessFlags &= ~ACC_MODULE;
			this.accessFlags &= ~ACC_SUPER;
		} else {
			this.accessFlags &= ~ACC_ANNOTATION;
		}
	}
	
	/**
	 * Adds or removes the access flag ACC_ENUM.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_ANNOTATION</li>
	 * <li>ACC_INTERFACE</li>
	 * <li>ACC_MODULE</li>
	 * </ul>
	 * 
	 * @param isEnum {@code true} to add the access flag ACC_ENUM
	 */
	public void setEnum(final boolean isEnum) {
		if(isEnum) {
			this.accessFlags &= ~ACC_ANNOTATION;
			this.accessFlags |= ACC_ENUM;
			this.accessFlags &= ~ACC_INTERFACE;
			this.accessFlags &= ~ACC_MODULE;
		} else {
			this.accessFlags &= ~ACC_ENUM;
		}
	}
	
	/**
	 * Adds or removes the access flag ACC_FINAL.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_ABSTRACT</li>
	 * <li>ACC_ANNOTATION</li>
	 * <li>ACC_INTERFACE</li>
	 * <li>ACC_MODULE</li>
	 * </ul>
	 * 
	 * @param isFinal {@code true} to add the access flag ACC_FINAL
	 */
	public void setFinal(final boolean isFinal) {
		if(isFinal) {
			this.accessFlags &= ~ACC_ABSTRACT;
			this.accessFlags &= ~ACC_ANNOTATION;
			this.accessFlags |= ACC_FINAL;
			this.accessFlags &= ~ACC_INTERFACE;
			this.accessFlags &= ~ACC_MODULE;
		} else {
			this.accessFlags &= ~ACC_FINAL;
		}
	}
	
	/**
	 * Adds or removes the access flag ACC_INTERFACE.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_ENUM</li>
	 * <li>ACC_FINAL</li>
	 * <li>ACC_MODULE</li>
	 * <li>ACC_SUPER</li>
	 * </ul>
	 * <p>
	 * This method turns on the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_ABSTRACT</li>
	 * </ul>
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code false}:
	 * <ul>
	 * <li>ACC_ANNOTATION</li>
	 * </ul>
	 * 
	 * @param isInterface {@code true} to add the access flag ACC_INTERFACE
	 */
	public void setInterface(final boolean isInterface) {
		if(isInterface) {
			this.accessFlags |= ACC_ABSTRACT;
			this.accessFlags &= ~ACC_ENUM;
			this.accessFlags &= ~ACC_FINAL;
			this.accessFlags |= ACC_INTERFACE;
			this.accessFlags &= ~ACC_MODULE;
			this.accessFlags &= ~ACC_SUPER;
		} else {
			this.accessFlags &= ~ACC_ANNOTATION;
			this.accessFlags &= ~ACC_INTERFACE;
		}
	}
	
	/**
	 * Sets a new major_version for this {@code ClassFile} instance.
	 * <p>
	 * If {@code majorVersion} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param majorVersion the new major_version
	 * @throws IllegalArgumentException thrown if, and only if, {@code majorVersion} is less than {@code 0}
	 */
	public void setMajorVersion(final int majorVersion) {
		this.majorVersion = ParameterArguments.requireRange(majorVersion, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets a new minor_version for this {@code ClassFile} instance.
	 * <p>
	 * If {@code minorVersion} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param minorVersion the new minor_version
	 * @throws IllegalArgumentException thrown if, and only if, {@code minorVersion} is less than {@code 0}
	 */
	public void setMinorVersion(final int minorVersion) {
		this.majorVersion = ParameterArguments.requireRange(minorVersion, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Adds or removes the access flag ACC_MODULE.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_ABSTRACT</li>
	 * <li>ACC_ANNOTATION</li>
	 * <li>ACC_ENUM</li>
	 * <li>ACC_FINAL</li>
	 * <li>ACC_INTERFACE</li>
	 * <li>ACC_PUBLIC</li>
	 * <li>ACC_SUPER</li>
	 * <li>ACC_SYNTHETIC</li>
	 * </ul>
	 * 
	 * @param isModule {@code true} to add the access flag ACC_MODULE
	 */
	public void setModule(final boolean isModule) {
		if(isModule) {
			this.accessFlags &= ~ACC_ABSTRACT;
			this.accessFlags &= ~ACC_ANNOTATION;
			this.accessFlags &= ~ACC_ENUM;
			this.accessFlags &= ~ACC_FINAL;
			this.accessFlags &= ~ACC_INTERFACE;
			this.accessFlags |= ACC_MODULE;
			this.accessFlags &= ~ACC_PUBLIC;
			this.accessFlags &= ~ACC_SUPER;
			this.accessFlags &= ~ACC_SYNTHETIC;
		} else {
			this.accessFlags &= ~ACC_MODULE;
		}
	}
	
	/**
	 * Adds or removes the access flag ACC_PUBLIC.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_MODULE</li>
	 * </ul>
	 * 
	 * @param isPublic {@code true} to add the access flag ACC_PUBLIC
	 */
	public void setPublic(final boolean isPublic) {
		if(isPublic) {
			this.accessFlags &= ~ACC_MODULE;
			this.accessFlags |= ACC_PUBLIC;
		} else {
			this.accessFlags &= ~ACC_PUBLIC;
		}
	}
	
	/**
	 * Adds or removes the access flag ACC_SUPER.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_ANNOTATION</li>
	 * <li>ACC_INTERFACE</li>
	 * <li>ACC_MODULE</li>
	 * </ul>
	 * 
	 * @param isSuper {@code true} to add the access flag ACC_SUPER
	 */
	public void setSuper(final boolean isSuper) {
		if(isSuper) {
			this.accessFlags &= ~ACC_ANNOTATION;
			this.accessFlags &= ~ACC_INTERFACE;
			this.accessFlags &= ~ACC_MODULE;
			this.accessFlags |= ACC_SUPER;
		} else {
			this.accessFlags &= ~ACC_SUPER;
		}
	}
	
	/**
	 * Sets a new super_class for this {@code ClassFile} instance.
	 * <p>
	 * If {@code superClass} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param superClass the new super_class for this {@code ClassFile} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code superClass} is less than {@code 0}
	 */
	public void setSuperClass(final int superClass) {
		this.superClass = ParameterArguments.requireRange(superClass, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Adds or removes the access flag ACC_SYNTHETIC.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_MODULE</li>
	 * </ul>
	 * 
	 * @param isSynthetic {@code true} to add the access flag ACC_SYNTHETIC
	 */
	public void setSynthetic(final boolean isSynthetic) {
		if(isSynthetic) {
			this.accessFlags &= ~ACC_MODULE;
			this.accessFlags |= ACC_SYNTHETIC;
		} else {
			this.accessFlags &= ~ACC_SYNTHETIC;
		}
	}
	
	/**
	 * Sets a new this_class for this {@code ClassFile} instance.
	 * <p>
	 * If {@code thisClass} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param thisClass the new this_class for this {@code ClassFile} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code thisClass} is less than or equal to {@code 0}
	 */
	public void setThisClass(final int thisClass) {
		this.thisClass = ParameterArguments.requireRange(thisClass, 1, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new empty {@code ClassFile} instance.
	 * 
	 * @return a new empty {@code ClassFile} instance
	 */
	public static ClassFile newInstance() {
		return new ClassFile();
	}
}