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
package org.macroing.cel4j.java.binary.reader;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.FieldInfo;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.UnimplementedAttribute;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.node.NodeFormatException;

/**
 * A {@code ClassFileReader} is used for reading {@link ClassFile}s from binary data.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ClassFileReader {
	private final FieldInfoReader fieldInfoReader;
	private final MethodInfoReader methodInfoReader;
	private final ServiceLoader<AttributeInfoReader> attributeInfoReaders;
	private final ServiceLoader<CPInfoReader> cPInfoReaders;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ClassFileReader} instance.
	 */
	public ClassFileReader() {
		this.fieldInfoReader = new FieldInfoReader();
		this.methodInfoReader = new MethodInfoReader();
		this.attributeInfoReaders = ServiceLoader.load(AttributeInfoReader.class);
		this.cPInfoReaders = ServiceLoader.load(CPInfoReader.class);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@link ClassFile} representation of {@code clazz}, by reading its binary data.
	 * <p>
	 * If {@code clazz} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the read binary data is invalid for some reason, a {@code NodeFormatException} will be thrown.
	 * 
	 * @param clazz the {@code Class} from which to read binary data into a {@code ClassFile} instance
	 * @return a {@code ClassFile} representation of {@code clazz}, by reading its binary data
	 * @throws NodeFormatException thrown if, and only if, the read binary data is invalid
	 * @throws NullPointerException thrown if, and only if, {@code clazz} is {@code null}
	 */
	public ClassFile readClassFile(final Class<?> clazz) {
		doReloadAttributeInfoReaders();
		doReloadCPInfoReaders();
		
		return doReadClassFile(Objects.requireNonNull(clazz, "clazz == null"));
	}
	
	/**
	 * Returns a {@link ClassFile}, by reading some binary data from {@code dataInput}.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the read binary data is invalid for some reason, a {@code NodeFormatException} will be thrown.
	 * 
	 * @param dataInput the {@code DataInput} from which to read binary data into a {@code ClassFile} instance
	 * @return a {@code ClassFile}, by reading some binary data from {@code dataInput}
	 * @throws NodeFormatException thrown if, and only if, the read binary data is invalid
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 */
	public ClassFile readClassFile(final DataInput dataInput) {
		doReloadAttributeInfoReaders();
		doReloadCPInfoReaders();
		
		return doReadClassFile(Objects.requireNonNull(dataInput, "dataInput == null"), "?");
	}
	
	/**
	 * Returns a {@link ClassFile}, by reading some binary data given a {@code File}.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the read binary data is invalid for some reason, a {@code NodeFormatException} will be thrown.
	 * 
	 * @param file the {@code File} from which to read binary data into a {@code ClassFile} instance
	 * @return a {@code ClassFile}, by reading some binary data given a {@code File}
	 * @throws NodeFormatException thrown if, and only if, the read binary data is invalid
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 */
	public ClassFile readClassFile(final File file) {
		doReloadAttributeInfoReaders();
		doReloadCPInfoReaders();
		
		return doReadClassFile(Objects.requireNonNull(file, "file == null"));
	}
	
	/**
	 * Returns a {@link ClassFile} representation of the {@code Class} that can be obtained by {@code Class.forName(className)}, by reading its binary data.
	 * <p>
	 * If {@code className} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the read binary data is invalid for some reason, or no such {@code Class} exists, a {@code NodeFormatException} will be thrown.
	 * 
	 * @param className the name of the {@code Class} from which to read binary data into a {@code ClassFile} instance
	 * @return a {@code ClassFile} representation of the {@code Class} that can be obtained by {@code Class.forName(className)}, by reading its binary data
	 * @throws NodeFormatException thrown if, and only if, the read binary data is invalid, or no such {@code Class} exists
	 * @throws NullPointerException thrown if, and only if, {@code className} is {@code null}
	 */
	public ClassFile readClassFile(final String className) {
		doReloadAttributeInfoReaders();
		doReloadCPInfoReaders();
		
		return doReadClassFile(Objects.requireNonNull(className, "className == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code ClassFileReader} instance.
	 * 
	 * @return a new {@code ClassFileReader} instance
	 */
	public static ClassFileReader newInstance() {
		return new ClassFileReader();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private AttributeInfo doReadAttributeInfo(final DataInput dataInput, final ClassFile classFile) {
		final int attributeNameIndex = doReadU2(dataInput);
		final int attributeLength = doReadU4(dataInput);
		
		final List<CPInfo> constantPool = classFile.getCPInfos();
		
		final String name = doGetName(attributeNameIndex, constantPool);
		
		for(final AttributeInfoReader attributeInfoReader : this.attributeInfoReaders) {
			if(attributeInfoReader.isAttributeInfoReadingSupportedFor(name)) {
				return attributeInfoReader.readAttributeInfo(dataInput, attributeNameIndex, classFile.getCPInfos());
			}
		}
		
		final AttributeInfoReader attributeInfoReader = new AttributeInfoReaderImpl();
		
		if(attributeInfoReader.isAttributeInfoReadingSupportedFor(name)) {
			return attributeInfoReader.readAttributeInfo(dataInput, attributeNameIndex, classFile.getCPInfos());
		}
		
		try {
			final byte[] info = new byte[attributeLength];
			
			dataInput.readFully(info);
			
			return UnimplementedAttribute.newInstance(name, attributeNameIndex, info);
		} catch(final IOException e) {
			throw new NodeFormatException("Unable to read attribute_info: name = " + name);
		}
	}
	
	private CPInfo doReadCPInfo(final DataInput dataInput) {
		final int tag = doReadU1(dataInput);
		
		for(final CPInfoReader cPInfoReader : this.cPInfoReaders) {
			if(cPInfoReader.isCPInfoReadingSupportedFor(tag)) {
				return cPInfoReader.readCPInfo(dataInput, tag);
			}
		}
		
		final CPInfoReader cPInfoReader = new CPInfoReaderImpl();
		
		if(cPInfoReader.isCPInfoReadingSupportedFor(tag)) {
			return cPInfoReader.readCPInfo(dataInput, tag);
		}
		
		throw new NodeFormatException(String.format("Unable to read cp_info: tag = %s", Integer.toString(tag)));
	}
	
	private ClassFile doReadClassFile(final Class<?> clazz) {
		return doReadClassFile(new DataInputStream(clazz.getResourceAsStream(doGetSimpleNameOf(clazz) + ".class")), clazz.getName() + " (" + doGetSimpleNameOf(clazz) + ".class)");
	}
	
	private ClassFile doReadClassFile(final DataInput dataInput, final String string) {
		try {
			final ClassFile classFile = ClassFile.newInstance();
			
			doReadMagic(dataInput);
			doReadMinorVersion(dataInput, classFile);
			doReadMajorVersion(dataInput, classFile);
			doReadConstantPool(dataInput, classFile);
			doReadAccessFlags(dataInput, classFile);
			doReadThisClass(dataInput, classFile);
			doReadSuperClass(dataInput, classFile);
			doReadInterfaces(dataInput, classFile);
			doReadFields(dataInput, classFile);
			doReadMethods(dataInput, classFile);
			doReadAttributes(dataInput, classFile);
			
			return classFile;
		} catch(final IllegalArgumentException | NullPointerException e) {
			throw new NodeFormatException(String.format("Unable to read ClassFile: %s", string), e);
		}
	}
	
	private ClassFile doReadClassFile(final File file) {
		try(final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))) {
			return doReadClassFile(dataInputStream, file.getName());
		} catch(final IOException e) {
			throw new NodeFormatException(String.format("Unable to read ClassFile: %s", file), e);
		}
	}
	
	private ClassFile doReadClassFile(final String className) {
		try {
			return doReadClassFile(Class.forName(className));
		} catch(final ClassNotFoundException e) {
			throw new NodeFormatException(e);
		}
	}
	
	private void doReadAttributes(final DataInput dataInput, final ClassFile classFile) {
		final int attributesCount = doReadU2(dataInput);
		
		for(int i = 0; i < attributesCount; i++) {
			final AttributeInfo attributeInfo = doReadAttributeInfo(dataInput, classFile);
			
			if(attributeInfo == null) {
				throw new NullPointerException("attributes[" + i + "] == null");
			}
			
			classFile.addAttributeInfo(attributeInfo);
		}
	}
	
	private void doReadConstantPool(final DataInput dataInput, final ClassFile classFile) {
		final int constantPoolCount = doReadU2(dataInput);
		
		for(int i = 1; i < constantPoolCount; i++) {
			final CPInfo cPInfo = doReadCPInfo(dataInput);
			
			if(cPInfo == null) {
				throw new NullPointerException("constantPool[" + i + "] == null");
			}
			
			final int constantPoolEntryCount = cPInfo.getConstantPoolEntryCount();
			
			i += constantPoolEntryCount - 1;
			
			classFile.addCPInfo(cPInfo);
		}
	}
	
	private void doReadFields(final DataInput dataInput, final ClassFile classFile) {
		final int fieldsCount = doReadU2(dataInput);
		
		for(int i = 0; i < fieldsCount; i++) {
			final FieldInfo fieldInfo = this.fieldInfoReader.readFieldInfo(dataInput, classFile);
			
			classFile.addFieldInfo(fieldInfo);
		}
	}
	
	private void doReadMethods(final DataInput dataInput, final ClassFile classFile) {
		final int methodsCount = doReadU2(dataInput);
		
		for(int i = 0; i < methodsCount; i++) {
			final MethodInfo methodInfo = this.methodInfoReader.readMethodInfo(dataInput, classFile);
			
			classFile.addMethodInfo(methodInfo);
		}
	}
	
	private void doReloadAttributeInfoReaders() {
		this.attributeInfoReaders.reload();
	}
	
	private void doReloadCPInfoReaders() {
		this.cPInfoReaders.reload();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static String doGetName(final int attributeNameIndex, final List<CPInfo> constantPool) {
		String name = "";
		
		if(attributeNameIndex >= 0 && attributeNameIndex < constantPool.size()) {
			final CPInfo cPInfo = constantPool.get(attributeNameIndex);
			
			if(cPInfo instanceof ConstantUTF8Info) {
				name = cPInfo.toString();
			}
		}
		
		return name;
	}
	
	private static String doGetSimpleNameOf(Class<?> clazz) {
		final StringBuilder stringBuilder = new StringBuilder();
		
		final int lastIndexOf$ = clazz.getName().lastIndexOf('$');
		
		if(lastIndexOf$ != -1) {
			stringBuilder.append(clazz.getName().substring(lastIndexOf$ + 1));
			
			while((clazz = clazz.getEnclosingClass()) != null) {
				stringBuilder.insert(0, clazz.getSimpleName() + "$");
			}
		} else {
			stringBuilder.append(clazz.getSimpleName());
		}
		
		return stringBuilder.toString();
	}
	
	private static int doReadU1(final DataInput dataInput) {
		try {
			return dataInput.readUnsignedByte();
		} catch(final IOException e) {
			throw new NodeFormatException(e);
		}
	}
	
	private static int doReadU2(final DataInput dataInput) {
		try {
			return dataInput.readUnsignedShort();
		} catch(final IOException e) {
			throw new NodeFormatException(e);
		}
	}
	
	private static int doReadU4(final DataInput dataInput) {
		try {
			return dataInput.readInt();
		} catch(final IOException e) {
			throw new NodeFormatException(e);
		}
	}
	
	private static void doAssertU4IsValidMagic(final int u4) {
		if(u4 != 0xCAFEBABE) {
			throw new IllegalArgumentException("u4 != 0xCAFEBABE (" + 0xCAFEBABE + "): u4 = " + u4);
		}
	}
	
	private static void doReadAccessFlags(final DataInput dataInput, final ClassFile classFile) {
		final int accessFlags = doReadU2(dataInput);
		
		classFile.setAbstract((accessFlags & ClassFile.ACC_ABSTRACT) != 0);
		classFile.setAnnotation((accessFlags & ClassFile.ACC_ANNOTATION) != 0);
		classFile.setEnum((accessFlags & ClassFile.ACC_ENUM) != 0);
		classFile.setFinal((accessFlags & ClassFile.ACC_FINAL) != 0);
		classFile.setInterface((accessFlags & ClassFile.ACC_INTERFACE) != 0);
		classFile.setPublic((accessFlags & ClassFile.ACC_PUBLIC) != 0);
		classFile.setSuper((accessFlags & ClassFile.ACC_SUPER) != 0);
		classFile.setSynthetic((accessFlags & ClassFile.ACC_SYNTHETIC) != 0);
	}
	
	private static void doReadInterfaces(final DataInput dataInput, final ClassFile classFile) {
		final int interfacesCount = doReadU2(dataInput);
		
		for(int i = 0; i < interfacesCount; i++) {
			classFile.addInterface(doReadU2(dataInput));
		}
	}
	
	private static void doReadMagic(final DataInput dataInput) {
		doAssertU4IsValidMagic(doReadU4(dataInput));
	}
	
	private static void doReadMajorVersion(final DataInput dataInput, final ClassFile classFile) {
		final int majorVersion = doReadU2(dataInput);
		
		classFile.setMajorVersion(majorVersion);
	}
	
	private static void doReadMinorVersion(final DataInput dataInput, final ClassFile classFile) {
		final int minorVersion = doReadU2(dataInput);
		
		classFile.setMinorVersion(minorVersion);
	}
	
	private static void doReadSuperClass(final DataInput dataInput, final ClassFile classFile) {
		final int superClass = doReadU2(dataInput);
		
		classFile.setSuperClass(superClass);
	}
	
	private static void doReadThisClass(final DataInput dataInput, final ClassFile classFile) {
		final int thisClass = doReadU2(dataInput);
		
		classFile.setThisClass(thisClass);
	}
}