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
package org.macroing.cel4j.java.binary.classfile.signature;

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Optional;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.FieldInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.SignatureAttribute;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code FieldSignature} denotes a FieldSignature as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface FieldSignature extends Signature {
//	TODO: Add Javadocs!
	static FieldSignature parseFieldSignature(final ClassFile classFile, final SignatureAttribute signatureAttribute) {
		return parseFieldSignature(classFile.getCPInfo(signatureAttribute.getSignatureIndex(), ConstantUTF8Info.class).getString());
	}
	
//	TODO: Add Javadocs!
	static FieldSignature parseFieldSignature(final String string) {
		return Parsers.parseFieldSignature(new TextScanner(string));
	}
	
//	TODO: Add Javadocs!
	static Optional<FieldSignature> parseFieldSignatureOptionally(final ClassFile classFile, final FieldInfo fieldInfo) {
		return SignatureAttribute.find(classFile.getFieldInfo(fieldInfo)).map(signatureAttribute -> parseFieldSignature(classFile, signatureAttribute));
	}
}