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
package org.macroing.cel4j.java.binary.classfile.string;

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.ArrayList;
import java.util.List;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.SignatureAttribute;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.scanner.TextScanner;

//TODO: Add Javadocs!
public interface Signature extends Node {
//	TODO: Add Javadocs!
	String toExternalForm();
	
//	TODO: Add Javadocs!
	String toInternalForm();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static List<Signature> parseSignatures(final ClassFile classFile) {
		return NodeFilter.filter(classFile, node -> node instanceof SignatureAttribute, SignatureAttribute.class).stream().map(signatureAttribute -> parseSignature(classFile, signatureAttribute)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
//	TODO: Add Javadocs!
	public static Signature parseSignature(final ClassFile classFile, final SignatureAttribute signatureAttribute) {
		return parseSignature(classFile.getCPInfo(signatureAttribute.getSignatureIndex(), ConstantUTF8Info.class).getString());
	}
	
//	TODO: Add Javadocs!
	public static Signature parseSignature(final String string) {
		return Parsers.parseSignature(new TextScanner(string));
	}
}