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
package org.macroing.cel4j.java.binary.classfile.signature;

import java.util.Objects;
import java.util.Optional;

import org.macroing.cel4j.java.binary.classfile.CPInfo;
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
	/**
	 * Returns a {@code FieldSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code fieldSignature}.
	 * <p>
	 * If {@code fieldSignature} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FieldSignature.excludePackageName(fieldSignature, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param fieldSignature a {@code FieldSignature} instance
	 * @return a {@code FieldSignature} instance that excludes all package names that are equal to {@code "java.lang"} from {@code fieldSignature}
	 * @throws NullPointerException thrown if, and only if, {@code fieldSignature} is {@code null}
	 */
	static FieldSignature excludePackageName(final FieldSignature fieldSignature) {
		return excludePackageName(fieldSignature, "java.lang");
	}
	
	/**
	 * Returns a {@code FieldSignature} instance that excludes all package names that are equal to {@code packageName} from {@code fieldSignature}.
	 * <p>
	 * If either {@code fieldSignature} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param fieldSignature a {@code FieldSignature} instance
	 * @param packageName the package name to exclude
	 * @return a {@code FieldSignature} instance that excludes all package names that are equal to {@code packageName} from {@code fieldSignature}
	 * @throws NullPointerException thrown if, and only if, either {@code fieldSignature} or {@code packageName} are {@code null}
	 */
	static FieldSignature excludePackageName(final FieldSignature fieldSignature, final String packageName) {
		Objects.requireNonNull(fieldSignature, "fieldSignature == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, fieldSignature);
	}
	
	/**
	 * Parses the {@code FieldSignature} of {@code signatureAttribute} in {@code classFile}.
	 * <p>
	 * Returns a {@code FieldSignature} instance.
	 * <p>
	 * If either {@code classFile} or {@code signatureAttribute} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the {@link CPInfo} on the index {@code signatureAttribute.getSignatureIndex()} is not a {@link ConstantUTF8Info} instance, or the {@code getStringValue()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is
	 * malformed, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code signatureAttribute.getSignatureIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @param signatureAttribute a {@link SignatureAttribute} instance
	 * @return a {@code FieldSignature} instance
	 * @throws IllegalArgumentException thrown if, and only if, the {@code CPInfo} on the index {@code signatureAttribute.getSignatureIndex()} is not a {@code ConstantUTF8Info} instance, or the {@code getStringValue()} method of the
	 *                                  {@code ConstantUTF8Info} instance returns a {@code String} that is malformed
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code signatureAttribute.getSignatureIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code signatureAttribute} are {@code null}
	 */
	static FieldSignature parseFieldSignature(final ClassFile classFile, final SignatureAttribute signatureAttribute) {
		return parseFieldSignature(classFile.getCPInfo(signatureAttribute.getSignatureIndex(), ConstantUTF8Info.class).getStringValue());
	}
	
	/**
	 * Parses {@code string} into a {@code FieldSignature} instance.
	 * <p>
	 * Returns a {@code FieldSignature} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code FieldSignature} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	static FieldSignature parseFieldSignature(final String string) {
		return Parsers.parseFieldSignature(new TextScanner(string));
	}
	
	/**
	 * Parses the {@code FieldSignature} of {@code fieldInfo} in {@code classFile}, if present.
	 * <p>
	 * Returns an {@code Optional} of type {@code FieldSignature}.
	 * <p>
	 * If either {@code classFile} or {@code fieldInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@link FieldInfo} instance that is equal to {@code fieldInfo}, {@code fieldInfo} contains a {@link SignatureAttribute} {@code signatureAttribute} but the {@link CPInfo} on the index
	 * {@code signatureAttribute.getSignatureIndex()} is not a {@link ConstantUTF8Info} instance, or the {@code getStringValue()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed, an
	 * {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If, for the {@code SignatureAttribute} {@code signatureAttribute} in {@code fieldInfo}, {@code signatureAttribute.getSignatureIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an
	 * {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @param fieldInfo a {@code FieldInfo} instance
	 * @return an {@code Optional} of type {@code FieldSignature}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code FieldInfo} instance that is equal to {@code fieldInfo}, {@code fieldInfo} contains a {@code SignatureAttribute} {@code signatureAttribute} but
	 *                                  the  {@code CPInfo} on the index {@code signatureAttribute.getSignatureIndex()} is not a {@code ConstantUTF8Info} instance, or the {@code getStringValue()} method of the {@code ConstantUTF8Info} instance returns
	 *                                  a {@code String} that is malformed
	 * @throws IndexOutOfBoundsException thrown if, and only if, for the {@code SignatureAttribute} {@code signatureAttribute} in {@code fieldInfo}, {@code signatureAttribute.getSignatureIndex()} is less than {@code 0}, or greater than or equal to
	 *                                   {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code fieldInfo} are {@code null}
	 */
	static Optional<FieldSignature> parseFieldSignatureOptionally(final ClassFile classFile, final FieldInfo fieldInfo) {
		return SignatureAttribute.find(classFile.getFieldInfo(fieldInfo)).map(signatureAttribute -> parseFieldSignature(classFile, signatureAttribute));
	}
}