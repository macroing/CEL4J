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
import java.util.regex.Pattern;

import org.macroing.cel4j.scanner.TextScanner;

final class Parsers {
	private static final Pattern ARRAY_TYPE_PATTERN = Pattern.compile("\\[+([BCDFIJSZ]|L\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*(/\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*;)");
	private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*(/\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*");
	private static final Pattern METHOD_DESCRIPTOR_PATTERN = Pattern.compile("\\(((\\[*([BCDFIJSVZ]|L\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*(/\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*;))*)\\)\\[*([BCDFIJSVZ]|L\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*(/\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*;)");
	private static final Pattern OBJECT_TYPE_PATTERN = Pattern.compile("L\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*(/\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*;");
	private static final String[] BASE_TYPE_TERMS_AND_TYPES = {Constants.BOOLEAN_TERM, Constants.BOOLEAN_TYPE, Constants.BYTE_TERM, Constants.BYTE_TYPE, Constants.CHAR_TERM, Constants.CHAR_TYPE, Constants.DOUBLE_TERM, Constants.DOUBLE_TYPE, Constants.FLOAT_TERM, Constants.FLOAT_TYPE, Constants.INT_TERM, Constants.INT_TYPE, Constants.LONG_TERM, Constants.LONG_TYPE, Constants.SHORT_TERM, Constants.SHORT_TYPE};
	private static final String[] VOID_DESCRIPTOR_TERMS_AND_TYPES = {Constants.VOID_TERM, Constants.VOID_TYPE};
	
	private Parsers() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static ArrayType parseArrayType(final TextScanner textScanner) {
		if(canParseArrayType(textScanner) && textScanner.nextCharacter('[') && textScanner.consume()) {
			if(canParseArrayType(textScanner)) {
				return ArrayType.valueOf(parseArrayType(textScanner));
			} else if(canParseBaseType(textScanner)) {
				return ArrayType.valueOf(parseBaseType(textScanner));
			} else if(canParseObjectType(textScanner)) {
				return ArrayType.valueOf(parseObjectType(textScanner));
			}
		}
		
		throw new IllegalArgumentException(String.format("Illegal ArrayType: %s", textScanner));
	}
	
	public static BaseType parseBaseType(final TextScanner textScanner) {
		if(canParseBaseType(textScanner) && textScanner.nextStringAlternation(BASE_TYPE_TERMS_AND_TYPES)) {
			try {
				switch(textScanner.consumption()) {
					case Constants.BOOLEAN_TERM:
					case Constants.BOOLEAN_TYPE:
						return BaseType.BOOLEAN;
					case Constants.BYTE_TERM:
					case Constants.BYTE_TYPE:
						return BaseType.BYTE;
					case Constants.CHAR_TERM:
					case Constants.CHAR_TYPE:
						return BaseType.CHAR;
					case Constants.DOUBLE_TERM:
					case Constants.DOUBLE_TYPE:
						return BaseType.DOUBLE;
					case Constants.FLOAT_TERM:
					case Constants.FLOAT_TYPE:
						return BaseType.FLOAT;
					case Constants.INT_TERM:
					case Constants.INT_TYPE:
						return BaseType.INT;
					case Constants.LONG_TERM:
					case Constants.LONG_TYPE:
						return BaseType.LONG;
					case Constants.SHORT_TERM:
					case Constants.SHORT_TYPE:
						return BaseType.SHORT;
					default:
						throw new IllegalArgumentException(String.format("Illegal BaseType: %s", textScanner));
				}
			} finally {
				textScanner.consume();
			}
		}
		
		throw new IllegalArgumentException(String.format("Illegal BaseType: %s", textScanner));
	}
	
	public static ClassName parseClassName(final TextScanner textScanner) {
		if(canParseClassName(textScanner) && textScanner.nextRegex(CLASS_NAME_PATTERN)) {
			try {
				return new ClassName(textScanner.consumption());
			} finally {
				textScanner.consume();
			}
		}
		
		throw new IllegalArgumentException(String.format("Illegal ClassName: %s", textScanner));
	}
	
	public static ComponentType parseComponentType(final TextScanner textScanner) {
		if(canParseFieldType(textScanner)) {
			return parseFieldType(textScanner);
		}
		
		throw new IllegalArgumentException(String.format("Illegal ComponentType: %s", textScanner));
	}
	
	public static FieldDescriptor parseFieldDescriptor(final TextScanner textScanner) {
		if(canParseFieldType(textScanner)) {
			return parseFieldType(textScanner);
		}
		
		throw new IllegalArgumentException(String.format("Illegal FieldDescriptor: %s", textScanner));
	}
	
	public static FieldType parseFieldType(final TextScanner textScanner) {
		if(canParseBaseType(textScanner)) {
			return parseBaseType(textScanner);
		} else if(canParseObjectType(textScanner)) {
			return parseObjectType(textScanner);
		} else if(canParseArrayType(textScanner)) {
			return parseArrayType(textScanner);
		}
		
		throw new IllegalArgumentException(String.format("Illegal FieldType: %s", textScanner));
	}
	
	public static MethodDescriptor parseMethodDescriptor(final TextScanner textScanner) {
		if(canParseMethodDescriptor(textScanner) && textScanner.nextCharacter() && textScanner.consume()) {
			final List<ParameterDescriptor> parameterDescriptors = new ArrayList<>();
			
			while(canParseParameterDescriptor(textScanner)) {
				parameterDescriptors.add(parseParameterDescriptor(textScanner));
			}
			
			textScanner.nextCharacter();
			textScanner.consume();
			
			if(canParseReturnDescriptor(textScanner)) {
				return new MethodDescriptor(parseReturnDescriptor(textScanner), parameterDescriptors);
			}
		}
		
		throw new IllegalArgumentException(String.format("Illegal MethodDescriptor: %s", textScanner));
	}
	
	public static ObjectType parseObjectType(final TextScanner textScanner) {
		if(canParseObjectType(textScanner) && textScanner.nextCharacter() && textScanner.consume()) {
			try {
				return new ObjectType(parseClassName(textScanner));
			} finally {
				textScanner.nextCharacter();
				textScanner.consume();
			}
		}
		
		throw new IllegalArgumentException(String.format("Illegal ObjectType: %s", textScanner));
	}
	
	public static ParameterDescriptor parseParameterDescriptor(final TextScanner textScanner) {
		if(canParseFieldType(textScanner)) {
			return parseFieldType(textScanner);
		}
		
		throw new IllegalArgumentException(String.format("Illegal ParameterDescriptor: %s", textScanner));
	}
	
	public static ReturnDescriptor parseReturnDescriptor(final TextScanner textScanner) {
		if(canParseFieldType(textScanner)) {
			return parseFieldType(textScanner);
		} else if(canParseVoidDescriptor(textScanner)) {
			return parseVoidDescriptor(textScanner);
		}
		
		throw new IllegalArgumentException(String.format("Illegal ReturnDescriptor: %s", textScanner));
	}
	
	public static VoidDescriptor parseVoidDescriptor(final TextScanner textScanner) {
		if(canParseVoidDescriptor(textScanner) && textScanner.nextStringAlternation(VOID_DESCRIPTOR_TERMS_AND_TYPES) && textScanner.consume()) {
			return VoidDescriptor.VOID;
		}
		
		throw new IllegalArgumentException(String.format("Illegal VoidDescriptor: %s", textScanner));
	}
	
	public static boolean canParseArrayType(final TextScanner textScanner) {
		return textScanner.testNextRegex(ARRAY_TYPE_PATTERN);
	}
	
	public static boolean canParseBaseType(final TextScanner textScanner) {
		return textScanner.testNextStringAlternation(BASE_TYPE_TERMS_AND_TYPES);
	}
	
	public static boolean canParseClassName(final TextScanner textScanner) {
		return textScanner.testNextRegex(CLASS_NAME_PATTERN);
	}
	
	public static boolean canParseComponentType(final TextScanner textScanner) {
		return canParseFieldType(textScanner);
	}
	
	public static boolean canParseFieldDescriptor(final TextScanner textScanner) {
		return canParseFieldType(textScanner);
	}
	
	public static boolean canParseFieldType(final TextScanner textScanner) {
		return canParseBaseType(textScanner) || canParseObjectType(textScanner) || canParseArrayType(textScanner);
	}
	
	public static boolean canParseMethodDescriptor(final TextScanner textScanner) {
		return textScanner.testNextRegex(METHOD_DESCRIPTOR_PATTERN);
	}
	
	public static boolean canParseObjectType(final TextScanner textScanner) {
		return textScanner.testNextRegex(OBJECT_TYPE_PATTERN);
	}
	
	public static boolean canParseParameterDescriptor(final TextScanner textScanner) {
		return canParseFieldType(textScanner);
	}
	
	public static boolean canParseReturnDescriptor(final TextScanner textScanner) {
		return canParseFieldType(textScanner) || canParseVoidDescriptor(textScanner);
	}
	
	public static boolean canParseVoidDescriptor(final TextScanner textScanner) {
		return textScanner.testNextStringAlternation(VOID_DESCRIPTOR_TERMS_AND_TYPES);
	}
}