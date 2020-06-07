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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.macroing.cel4j.java.binary.classfile.string.MethodSignature.Builder;

final class Parsers {
	private static final Pattern ARRAY_TYPE_PATTERN = Pattern.compile("\\[+([BCDFIJSZ]|L\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*(/\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*;)");
	private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*(/\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*");
	private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("[^\\.;\\[/<>:]+");
	private static final Pattern METHOD_DESCRIPTOR_PATTERN = Pattern.compile("\\(((\\[*([BCDFIJSVZ]|L\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*(/\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*;))*)\\)\\[*([BCDFIJSVZ]|L\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*(/\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*;)");
	private static final Pattern OBJECT_TYPE_PATTERN = Pattern.compile("L\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*(/\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*;");
	private static final Pattern PACKAGE_SPECIFIER_PATTERN = Pattern.compile("([^\\.;\\[/<>:]+/)+");
	private static final Pattern TYPE_VARIABLE_SIGNATURE_PATTERN = Pattern.compile("T[^\\.;\\[/<>:]+;");
	private static final String[] BASE_TYPE_TERMS_AND_TYPES = {Constants.BOOLEAN_TERM, Constants.BOOLEAN_TYPE, Constants.BYTE_TERM, Constants.BYTE_TYPE, Constants.CHAR_TERM, Constants.CHAR_TYPE, Constants.DOUBLE_TERM, Constants.DOUBLE_TYPE, Constants.FLOAT_TERM, Constants.FLOAT_TYPE, Constants.INT_TERM, Constants.INT_TYPE, Constants.LONG_TERM, Constants.LONG_TYPE, Constants.SHORT_TERM, Constants.SHORT_TYPE};
	private static final String[] VOID_DESCRIPTOR_TERMS_AND_TYPES = {Constants.VOID_TERM, Constants.VOID_TYPE};
	private static final String[] WILDCARD_INDICATOR_BOUNDS = {Constants.WILDCARD_INDICATOR_LOWER_BOUND, Constants.WILDCARD_INDICATOR_UPPER_BOUND};
	
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
	
	public static ArrayTypeSignature parseArrayTypeSignature(final TextScanner textScanner) {
		if(textScanner.nextCharacter('[') && textScanner.consume() && canParseJavaTypeSignature(textScanner)) {
			return ArrayTypeSignature.valueOf(parseJavaTypeSignature(textScanner));
		}
		
		throw new IllegalArgumentException(String.format("Illegal ArrayTypeSignature: %s", textScanner));
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
	
	public static ClassBound parseClassBound(final TextScanner textScanner) {
		if(canParseClassBound(textScanner) && textScanner.nextCharacter(':') && textScanner.consume()) {
			if(canParseReferenceTypeSignature(textScanner)) {
				return ClassBound.valueOf(parseReferenceTypeSignature(textScanner));
			}
			
			return ClassBound.EMPTY;
		}
		
		throw new IllegalArgumentException(String.format("Illegal ClassBound: %s", textScanner));
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
	
	public static ClassSignature parseClassSignature(final TextScanner textScanner) {
		if(canParseClassSignature(textScanner)) {
			final TypeParameters typeParameters = canParseTypeParameters(textScanner) ? parseTypeParameters(textScanner) : null;
			
			final SuperClassSignature superClassSignature = parseSuperClassSignature(textScanner);
			
			final List<SuperInterfaceSignature> superInterfaceSignatures = new ArrayList<>();
			
			while(canParseSuperInterfaceSignature(textScanner)) {
				superInterfaceSignatures.add(parseSuperInterfaceSignature(textScanner));
			}
			
			return new ClassSignature(superClassSignature, Optional.ofNullable(typeParameters), superInterfaceSignatures);
		}
		
		throw new IllegalArgumentException(String.format("Illegal ClassSignature: %s", textScanner));
	}
	
	public static ClassTypeSignature parseClassTypeSignature(final TextScanner textScanner) {
		if(canParseClassTypeSignature(textScanner) && textScanner.nextCharacter('L') && textScanner.consume()) {
			final PackageSpecifier packageSpecifier = canParsePackageSpecifier(textScanner) ? parsePackageSpecifier(textScanner) : null;
			
			final SimpleClassTypeSignature simpleClassTypeSignature = parseSimpleClassTypeSignature(textScanner);
			
			final List<ClassTypeSignatureSuffix> classTypeSignatureSuffixes = new ArrayList<>();
			
			while(canParseClassTypeSignatureSuffix(textScanner)) {
				classTypeSignatureSuffixes.add(parseClassTypeSignatureSuffix(textScanner));
			}
			
			textScanner.nextCharacter(';');
			textScanner.consume();
			
			return new ClassTypeSignature(simpleClassTypeSignature, Optional.ofNullable(packageSpecifier), classTypeSignatureSuffixes);
		}
		
		throw new IllegalArgumentException(String.format("Illegal ClassTypeSignature: %s", textScanner));
	}
	
	public static ClassTypeSignatureSuffix parseClassTypeSignatureSuffix(final TextScanner textScanner) {
		if(canParseClassTypeSignatureSuffix(textScanner) && textScanner.nextCharacter('.') && textScanner.consume()) {
			return ClassTypeSignatureSuffix.valueOf(parseSimpleClassTypeSignature(textScanner));
		}
		
		throw new IllegalArgumentException(String.format("Illegal ClassTypeSignatureSuffix: %s", textScanner));
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
	
	public static FieldSignature parseFieldSignature(final TextScanner textScanner) {
		if(canParseReferenceTypeSignature(textScanner)) {
			return parseReferenceTypeSignature(textScanner);
		}
		
		throw new IllegalArgumentException(String.format("Illegal FieldSignature: %s", textScanner));
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
	
	public static Identifier parseIdentifier(final TextScanner textScanner) {
		if(canParseIdentifier(textScanner) && textScanner.nextRegex(IDENTIFIER_PATTERN)) {
			try {
				return new Identifier(textScanner.consumption());
			} finally {
				textScanner.consume();
			}
		}
		
		throw new IllegalArgumentException(String.format("Illegal Identifier: %s", textScanner));
	}
	
	public static InterfaceBound parseInterfaceBound(final TextScanner textScanner) {
		if(canParseInterfaceBound(textScanner) && textScanner.nextCharacter(':') && textScanner.consume()) {
			return InterfaceBound.valueOf(parseReferenceTypeSignature(textScanner));
		}
		
		throw new IllegalArgumentException(String.format("Illegal InterfaceBound: %s", textScanner));
	}
	
	public static JavaTypeSignature parseJavaTypeSignature(final TextScanner textScanner) {
		if(canParseReferenceTypeSignature(textScanner)) {
			return parseReferenceTypeSignature(textScanner);
		} else if(canParseBaseType(textScanner)) {
			return parseBaseType(textScanner);
		}
		
		throw new IllegalArgumentException(String.format("Illegal JavaTypeSignature: %s", textScanner));
	}
	
	public static MethodDescriptor parseMethodDescriptor(final TextScanner textScanner) {
		if(canParseMethodDescriptor(textScanner) && textScanner.next() && textScanner.consume()) {
			final List<ParameterDescriptor> parameterDescriptors = new ArrayList<>();
			
			while(canParseParameterDescriptor(textScanner)) {
				parameterDescriptors.add(parseParameterDescriptor(textScanner));
			}
			
			textScanner.next();
			textScanner.consume();
			
			if(canParseReturnDescriptor(textScanner)) {
				return new MethodDescriptor(parseReturnDescriptor(textScanner), parameterDescriptors);
			}
		}
		
		throw new IllegalArgumentException(String.format("Illegal MethodDescriptor: %s", textScanner));
	}
	
	public static MethodSignature parseMethodSignature(final TextScanner textScanner) {
		if(canParseMethodSignature(textScanner)) {
			final TypeParameters typeParameters = canParseTypeParameters(textScanner) ? parseTypeParameters(textScanner) : null;
			
			if(textScanner.nextCharacter('(') && textScanner.consume()) {
				final List<JavaTypeSignature> javaTypeSignatures = new ArrayList<>();
				
				while(canParseJavaTypeSignature(textScanner)) {
					javaTypeSignatures.add(parseJavaTypeSignature(textScanner));
				}
				
				if(textScanner.nextCharacter(')') && textScanner.consume()) {
					final Result result = parseResult(textScanner);
					
					final Builder builder = typeParameters != null ? Builder.newInstance(result, typeParameters) : Builder.newInstance(result);
					
					for(final JavaTypeSignature javaTypeSignature : javaTypeSignatures) {
						builder.addJavaTypeSignature(javaTypeSignature);
					}
					
					while(canParseThrowsSignature(textScanner)) {
						builder.addThrowsSignature(parseThrowsSignature(textScanner));
					}
					
					return builder.build();
				}
			}
		}
		
		throw new IllegalArgumentException(String.format("Illegal MethodSignature: %s", textScanner));
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
	
	public static PackageSpecifier parsePackageSpecifier(final TextScanner textScanner) {
		if(canParsePackageSpecifier(textScanner) && textScanner.nextRegex(PACKAGE_SPECIFIER_PATTERN)) {
			try {
				final String[] strings = textScanner.consumption().split("/");
				
				final List<Identifier> identifiers = new ArrayList<>();
				
				for(final String string : strings) {
					identifiers.add(Identifier.parseIdentifier(string));
				}
				
				if(identifiers.size() > 0) {
					return new PackageSpecifier(identifiers);
				}
			} finally {
				textScanner.consume();
			}
		}
		
		throw new IllegalArgumentException(String.format("Illegal PackageSpecifier: %s", textScanner));
	}
	
	public static ParameterDescriptor parseParameterDescriptor(final TextScanner textScanner) {
		if(canParseFieldType(textScanner)) {
			return parseFieldType(textScanner);
		}
		
		throw new IllegalArgumentException(String.format("Illegal ParameterDescriptor: %s", textScanner));
	}
	
	public static ReferenceTypeSignature parseReferenceTypeSignature(final TextScanner textScanner) {
		if(canParseClassTypeSignature(textScanner)) {
			return parseClassTypeSignature(textScanner);
		} else if(canParseTypeVariableSignature(textScanner)) {
			return parseTypeVariableSignature(textScanner);
		} else if(canParseArrayTypeSignature(textScanner)) {
			return parseArrayTypeSignature(textScanner);
		}
		
		throw new IllegalArgumentException(String.format("Illegal ReferenceTypeSignature: %s", textScanner));
	}
	
	public static Result parseResult(final TextScanner textScanner) {
		if(canParseJavaTypeSignature(textScanner)) {
			return parseJavaTypeSignature(textScanner);
		} else if(canParseVoidDescriptor(textScanner)) {
			return parseVoidDescriptor(textScanner);
		}
		
		throw new IllegalArgumentException(String.format("Illegal Result: %s", textScanner));
	}
	
	public static ReturnDescriptor parseReturnDescriptor(final TextScanner textScanner) {
		if(canParseFieldType(textScanner)) {
			return parseFieldType(textScanner);
		} else if(canParseVoidDescriptor(textScanner)) {
			return parseVoidDescriptor(textScanner);
		}
		
		throw new IllegalArgumentException(String.format("Illegal ReturnDescriptor: %s", textScanner));
	}
	
	public static Signature parseSignature(final TextScanner textScanner) {
		if(canParseClassSignature(textScanner)) {
			return parseClassSignature(textScanner);
		} else if(canParseFieldSignature(textScanner)) {
			return parseFieldSignature(textScanner);
		} else if(canParseMethodSignature(textScanner)) {
			return parseMethodSignature(textScanner);
		}
		
		throw new IllegalArgumentException(String.format("Illegal Signature: %s", textScanner));
	}
	
	public static SimpleClassTypeSignature parseSimpleClassTypeSignature(final TextScanner textScanner) {
		if(canParseSimpleClassTypeSignature(textScanner)) {
			final Identifier identifier = parseIdentifier(textScanner);
			
			if(canParseTypeArguments(textScanner)) {
				return SimpleClassTypeSignature.valueOf(identifier, parseTypeArguments(textScanner));
			}
			
			return SimpleClassTypeSignature.valueOf(identifier);
		}
		
		throw new IllegalArgumentException(String.format("Illegal SimpleClassTypeSignature: %s", textScanner));
	}
	
	public static SuperClassSignature parseSuperClassSignature(final TextScanner textScanner) {
		if(canParseClassTypeSignature(textScanner)) {
			return parseClassTypeSignature(textScanner);
		}
		
		throw new IllegalArgumentException(String.format("Illegal SuperClassSignature: %s", textScanner));
	}
	
	public static SuperInterfaceSignature parseSuperInterfaceSignature(final TextScanner textScanner) {
		if(canParseClassTypeSignature(textScanner)) {
			return parseClassTypeSignature(textScanner);
		}
		
		throw new IllegalArgumentException(String.format("Illegal SuperInterfaceSignature: %s", textScanner));
	}
	
	public static ThrowsSignature parseThrowsSignature(final TextScanner textScanner) {
		if(canParseThrowsSignature(textScanner) && textScanner.nextCharacter('^') && textScanner.consume()) {
			if(canParseClassTypeSignature(textScanner)) {
				return ThrowsSignature.valueOf(parseClassTypeSignature(textScanner));
			}
			
			return ThrowsSignature.valueOf(parseTypeVariableSignature(textScanner));
		}
		
		throw new IllegalArgumentException(String.format("Illegal ThrowsSignature: %s", textScanner));
	}
	
	public static TypeArgument parseTypeArgument(final TextScanner textScanner) {
		if(canParseTypeArgument(textScanner)) {
			if(textScanner.nextString(Constants.TYPE_ARGUMENT_UNKNOWN) && textScanner.consume()) {
				return TypeArgument.UNKNOWN;
			} else if(canParseReferenceTypeSignature(textScanner)) {
				return TypeArgument.valueOf(parseReferenceTypeSignature(textScanner));
			} else if(textScanner.nextStringAlternation("+", "-")) {
				final String consumption = textScanner.consumption();
				
				textScanner.consume();
				
				return TypeArgument.valueOf(parseReferenceTypeSignature(textScanner), WildcardIndicator.parseWildcardIndicator(consumption));
			}
		}
		
		throw new IllegalArgumentException(String.format("Illegal TypeArgument: %s", textScanner));
	}
	
	public static TypeArguments parseTypeArguments(final TextScanner textScanner) {
		if(canParseTypeArguments(textScanner)) {
			if(textScanner.nextCharacter('<') && textScanner.consume()) {
				final List<TypeArgument> typeArguments = new ArrayList<>();
				
				while(canParseTypeArgument(textScanner)) {
					typeArguments.add(parseTypeArgument(textScanner));
				}
				
				if(textScanner.nextCharacter('>') && textScanner.consume()) {
					return new TypeArguments(typeArguments);
				}
			}
		}
		
		throw new IllegalArgumentException(String.format("Illegal TypeArguments: %s", textScanner));
	}
	
	public static TypeParameter parseTypeParameter(final TextScanner textScanner) {
		if(canParseTypeParameter(textScanner)) {
			final Identifier identifier = parseIdentifier(textScanner);
			
			final ClassBound classBound = parseClassBound(textScanner);
			
			final List<InterfaceBound> interfaceBounds = new ArrayList<>();
			
			while(canParseInterfaceBound(textScanner)) {
				interfaceBounds.add(parseInterfaceBound(textScanner));
			}
			
			return new TypeParameter(identifier, classBound, interfaceBounds);
		}
		
		throw new IllegalArgumentException(String.format("Illegal TypeParameter: %s", textScanner));
	}
	
	public static TypeParameters parseTypeParameters(final TextScanner textScanner) {
		if(canParseTypeParameters(textScanner)) {
			if(textScanner.nextCharacter('<') && textScanner.consume()) {
				final List<TypeParameter> typeParameters = new ArrayList<>();
				
				while(canParseTypeParameter(textScanner)) {
					typeParameters.add(parseTypeParameter(textScanner));
				}
				
				if(textScanner.nextCharacter('>') && textScanner.consume()) {
					return new TypeParameters(typeParameters);
				}
			}
		}
		
		throw new IllegalArgumentException(String.format("Illegal TypeParameters: %s", textScanner));
	}
	
	public static TypeVariableSignature parseTypeVariableSignature(final TextScanner textScanner) {
		if(canParseTypeVariableSignature(textScanner) && textScanner.nextCharacter('T') && textScanner.consume()) {
			try {
				return new TypeVariableSignature(parseIdentifier(textScanner));
			} finally {
				textScanner.next();
				textScanner.consume();
			}
		}
		
		throw new IllegalArgumentException(String.format("Invalid TypeVariableSignature': %s", textScanner));
	}
	
	public static VoidDescriptor parseVoidDescriptor(final TextScanner textScanner) {
		if(canParseVoidDescriptor(textScanner) && textScanner.nextStringAlternation(VOID_DESCRIPTOR_TERMS_AND_TYPES) && textScanner.consume()) {
			return VoidDescriptor.VOID;
		}
		
		throw new IllegalArgumentException(String.format("Illegal VoidDescriptor: %s", textScanner));
	}
	
	public static WildcardIndicator parseWildcardIndicator(final TextScanner textScanner) {
		if(canParseWildcardIndicator(textScanner) && textScanner.nextStringAlternation(WILDCARD_INDICATOR_BOUNDS)) {
			try {
				switch(textScanner.consumption()) {
					case Constants.WILDCARD_INDICATOR_LOWER_BOUND:
						return WildcardIndicator.LOWER_BOUND;
					case Constants.WILDCARD_INDICATOR_UPPER_BOUND:
						return WildcardIndicator.UPPER_BOUND;
					default:
						throw new IllegalArgumentException(String.format("Illegal WildcardIndicator: %s", textScanner));
				}
			} finally {
				textScanner.consume();
			}
		}
		
		throw new IllegalArgumentException(String.format("Illegal WildcardIndicator: %s", textScanner));
	}
	
	public static boolean canParseArrayType(final TextScanner textScanner) {
		return textScanner.testNextRegex(ARRAY_TYPE_PATTERN);
	}
	
	public static boolean canParseArrayTypeSignature(final TextScanner textScanner) {
		final int key = textScanner.mark();
		
		try {
			return textScanner.nextCharacter('[') && textScanner.consume() && canParseJavaTypeSignature(textScanner);
		} finally {
			textScanner.rewind(key);
		}
	}
	
	public static boolean canParseBaseType(final TextScanner textScanner) {
		return textScanner.testNextStringAlternation(BASE_TYPE_TERMS_AND_TYPES);
	}
	
	public static boolean canParseClassBound(final TextScanner textScanner) {
		final int key = textScanner.mark();
		
		try {
			return textScanner.nextCharacter(':');
		} finally {
			textScanner.rewind(key);
		}
	}
	
	public static boolean canParseClassName(final TextScanner textScanner) {
		return textScanner.testNextRegex(CLASS_NAME_PATTERN);
	}
	
	public static boolean canParseClassSignature(final TextScanner textScanner) {
		final int key = textScanner.mark();
		
		try {
			if(canParseTypeParameters(textScanner)) {
				parseTypeParameters(textScanner);
			}
			
			return canParseSuperClassSignature(textScanner);
		} finally {
			textScanner.rewind(key);
		}
	}
	
	public static boolean canParseClassTypeSignature(final TextScanner textScanner) {
		final int key = textScanner.mark();
		
		try {
			if(textScanner.nextCharacter('L') && textScanner.consume()) {
				if(canParsePackageSpecifier(textScanner)) {
					parsePackageSpecifier(textScanner);
				}
				
				if(canParseSimpleClassTypeSignature(textScanner)) {
					parseSimpleClassTypeSignature(textScanner);
					
					while(canParseClassTypeSignatureSuffix(textScanner)) {
						parseClassTypeSignatureSuffix(textScanner);
					}
					
					return textScanner.nextCharacter(';');
				}
			}
			
			return false;
		} finally {
			textScanner.rewind(key);
		}
	}
	
	public static boolean canParseClassTypeSignatureSuffix(final TextScanner textScanner) {
		final int key = textScanner.mark();
		
		try {
			return textScanner.nextCharacter('.') && textScanner.consume() && canParseSimpleClassTypeSignature(textScanner);
		} finally {
			textScanner.rewind(key);
		}
	}
	
	public static boolean canParseComponentType(final TextScanner textScanner) {
		return canParseFieldType(textScanner);
	}
	
	public static boolean canParseFieldDescriptor(final TextScanner textScanner) {
		return canParseFieldType(textScanner);
	}
	
	public static boolean canParseFieldSignature(final TextScanner textScanner) {
		return canParseReferenceTypeSignature(textScanner);
	}
	
	public static boolean canParseFieldType(final TextScanner textScanner) {
		return canParseBaseType(textScanner) || canParseObjectType(textScanner) || canParseArrayType(textScanner);
	}
	
	public static boolean canParseIdentifier(final TextScanner textScanner) {
		return textScanner.testNextRegex(IDENTIFIER_PATTERN);
	}
	
	public static boolean canParseInterfaceBound(final TextScanner textScanner) {
		final int key = textScanner.mark();
		
		try {
			return textScanner.nextCharacter(':') && textScanner.consume() && canParseReferenceTypeSignature(textScanner);
		} finally {
			textScanner.rewind(key);
		}
	}
	
	public static boolean canParseJavaTypeSignature(final TextScanner textScanner) {
		return canParseReferenceTypeSignature(textScanner) || canParseBaseType(textScanner);
	}
	
	public static boolean canParseMethodDescriptor(final TextScanner textScanner) {
		return textScanner.testNextRegex(METHOD_DESCRIPTOR_PATTERN);
	}
	
	public static boolean canParseMethodSignature(final TextScanner textScanner) {
		final int key = textScanner.mark();
		
		try {
			if(canParseTypeParameters(textScanner)) {
				parseTypeParameters(textScanner);
			}
			
			if(textScanner.nextCharacter('(') && textScanner.consume()) {
				while(canParseJavaTypeSignature(textScanner)) {
					parseJavaTypeSignature(textScanner);
				}
				
				if(textScanner.nextCharacter(')') && textScanner.consume()) {
					return canParseResult(textScanner);
				}
			}
			
			return false;
		} finally {
			textScanner.rewind(key);
		}
	}
	
	public static boolean canParseObjectType(final TextScanner textScanner) {
		return textScanner.testNextRegex(OBJECT_TYPE_PATTERN);
	}
	
	public static boolean canParsePackageSpecifier(final TextScanner textScanner) {
		return textScanner.testNextRegex(PACKAGE_SPECIFIER_PATTERN);
	}
	
	public static boolean canParseParameterDescriptor(final TextScanner textScanner) {
		return canParseFieldType(textScanner);
	}
	
	public static boolean canParseReferenceTypeSignature(final TextScanner textScanner) {
		return canParseClassTypeSignature(textScanner) || canParseTypeVariableSignature(textScanner) || canParseArrayTypeSignature(textScanner);
	}
	
	public static boolean canParseResult(final TextScanner textScanner) {
		return canParseJavaTypeSignature(textScanner) || canParseVoidDescriptor(textScanner);
	}
	
	public static boolean canParseReturnDescriptor(final TextScanner textScanner) {
		return canParseFieldType(textScanner) || canParseVoidDescriptor(textScanner);
	}
	
	public static boolean canParseSignature(final TextScanner textScanner) {
		return canParseClassSignature(textScanner) || canParseFieldSignature(textScanner) || canParseMethodSignature(textScanner);
	}
	
	public static boolean canParseSimpleClassTypeSignature(final TextScanner textScanner) {
		return canParseIdentifier(textScanner);
	}
	
	public static boolean canParseSuperClassSignature(final TextScanner textScanner) {
		return canParseClassTypeSignature(textScanner);
	}
	
	public static boolean canParseSuperInterfaceSignature(final TextScanner textScanner) {
		return canParseClassTypeSignature(textScanner);
	}
	
	public static boolean canParseThrowsSignature(final TextScanner textScanner) {
		final int key = textScanner.mark();
		
		try {
			return textScanner.nextCharacter('^') && textScanner.consume() && (canParseClassTypeSignature(textScanner) || canParseTypeVariableSignature(textScanner));
		} finally {
			textScanner.rewind(key);
		}
	}
	
	public static boolean canParseTypeArgument(final TextScanner textScanner) {
		final int key = textScanner.mark();
		
		try {
			return textScanner.testNextString(Constants.TYPE_ARGUMENT_UNKNOWN) || canParseReferenceTypeSignature(textScanner) || textScanner.testNextStringAlternation("+", "-") && textScanner.nextStringAlternation("+", "-") && textScanner.consume() && canParseReferenceTypeSignature(textScanner);
		} finally {
			textScanner.rewind(key);
		}
	}
	
	public static boolean canParseTypeArguments(final TextScanner textScanner) {
		final int key = textScanner.mark();
		
		try {
			if(textScanner.nextCharacter('<') && textScanner.consume()) {
				int count = 0;
				
				while(canParseTypeArgument(textScanner)) {
					parseTypeArgument(textScanner);
					
					count++;
				}
				
				if(count > 0 && textScanner.nextCharacter('>')) {
					return true;
				}
			}
			
			return false;
		} finally {
			textScanner.rewind(key);
		}
	}
	
	public static boolean canParseTypeParameter(final TextScanner textScanner) {
		final int key = textScanner.mark();
		
		try {
			if(canParseIdentifier(textScanner)) {
				parseIdentifier(textScanner);
				
				if(canParseClassBound(textScanner)) {
					parseClassBound(textScanner);
					
					return true;
				}
			}
			
			return false;
		} finally {
			textScanner.rewind(key);
		}
	}
	
	public static boolean canParseTypeParameters(final TextScanner textScanner) {
		final int key = textScanner.mark();
		
		try {
			if(textScanner.nextCharacter('<') && textScanner.consume()) {
				int count = 0;
				
				while(canParseTypeParameter(textScanner)) {
					parseTypeParameter(textScanner);
					
					count++;
				}
				
				if(count > 0 && textScanner.nextCharacter('>')) {
					return true;
				}
			}
			
			return false;
		} finally {
			textScanner.rewind(key);
		}
	}
	
	public static boolean canParseTypeVariableSignature(final TextScanner textScanner) {
		return textScanner.testNextRegex(TYPE_VARIABLE_SIGNATURE_PATTERN);
	}
	
	public static boolean canParseVoidDescriptor(final TextScanner textScanner) {
		return textScanner.testNextStringAlternation(VOID_DESCRIPTOR_TERMS_AND_TYPES);
	}
	
	public static boolean canParseWildcardIndicator(final TextScanner textScanner) {
		return textScanner.testNextStringAlternation(WILDCARD_INDICATOR_BOUNDS);
	}
}