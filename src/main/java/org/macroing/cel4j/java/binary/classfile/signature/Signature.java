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

import java.util.ArrayList;
import java.util.List;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.SignatureAttribute;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code Signature} denotes a Signature as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface Signature extends Node {
	/**
	 * Returns a {@code String} representation of this {@code Signature} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code Signature} instance in external form
	 */
	String toExternalForm();
	
	/**
	 * Returns a {@code String} representation of this {@code Signature} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code Signature} instance in internal form
	 */
	String toInternalForm();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Parses all {@code Signature} instances from all {@link SignatureAttribute} instances in {@code classFile}.
	 * <p>
	 * Returns a {@code List} with all {@code Signature} instances that were parsed from all {@code SignatureAttribute} instances in {@code classFile}.
	 * <p>
	 * If {@code classFile} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If, for any {@code SignatureAttribute} {@code signatureAttribute} in {@code classFile}, the {@link CPInfo} on the index {@code signatureAttribute.getSignatureIndex()} is not a {@link ConstantUTF8Info} instance, or the {@code getString()} method
	 * of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If, for any {@code SignatureAttribute} {@code signatureAttribute} in {@code classFile}, {@code signatureAttribute.getSignatureIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an
	 * {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @return a {@code List} with all {@code Signature} instances that were parsed from all {@code SignatureAttribute} instances in {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, for any {@code SignatureAttribute} {@code signatureAttribute} in {@code classFile}, the {@code CPInfo} on the index {@code signatureAttribute.getSignatureIndex()} is not a
	 *                                  {@code ConstantUTF8Info} instance, or the {@code getString()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed
	 * @throws IndexOutOfBoundsException thrown if, and only if, for any {@code SignatureAttribute} {@code signatureAttribute} in {@code classFile}, {@code signatureAttribute.getSignatureIndex()} is less than {@code 0}, or greater than or equal to
	 *                                   {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, {@code classFile} is {@code null}
	 */
	public static List<Signature> parseSignatures(final ClassFile classFile) {
		return NodeFilter.filter(classFile, node -> node instanceof SignatureAttribute, SignatureAttribute.class).stream().map(signatureAttribute -> parseSignature(classFile, signatureAttribute)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Parses the {@code Signature} of {@code signatureAttribute} in {@code classFile}.
	 * <p>
	 * Returns a {@code Signature} instance.
	 * <p>
	 * If either {@code classFile} or {@code signatureAttribute} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain an {@link AttributeInfo} instance that is equal to {@code signatureAttribute}, the {@link CPInfo} on the index {@code signatureAttribute.getSignatureIndex()} is not a {@link ConstantUTF8Info} instance, or
	 * the {@code getString()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code signatureAttribute.getSignatureIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @param signatureAttribute a {@code SignatureAttribute} instance
	 * @return a {@code Signature} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain an {@code AttributeInfo} instance that is equal to {@code signatureAttribute}, the {@code CPInfo} on the index
	 *                                  {@code signatureAttribute.getSignatureIndex()} is not a {@code ConstantUTF8Info} instance, or the {@code getString()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code signatureAttribute.getSignatureIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code signatureAttribute} are {@code null}
	 */
	public static Signature parseSignature(final ClassFile classFile, final SignatureAttribute signatureAttribute) {
		return parseSignature(classFile.getCPInfo(classFile.getAttributeInfo(signatureAttribute, SignatureAttribute.class).getSignatureIndex(), ConstantUTF8Info.class).getString());
	}
	
	/**
	 * Parses {@code string} into a {@code Signature} instance.
	 * <p>
	 * Returns a {@code Signature} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code Signature} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static Signature parseSignature(final String string) {
		return Parsers.parseSignature(new TextScanner(string));
	}
}