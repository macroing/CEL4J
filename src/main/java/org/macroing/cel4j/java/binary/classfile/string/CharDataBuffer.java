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

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Files;
import java.util.Objects;

final class CharDataBuffer implements DataBuffer {
	private final CharBuffer charBuffer;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private CharDataBuffer(final CharBuffer charBuffer) {
		this.charBuffer = charBuffer;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		return this.charBuffer.toString();
	}
	
	public char getChar(final int index) {
		return this.charBuffer.get(index);
	}
	
	@Override
	public int size() {
		return this.charBuffer.capacity();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static CharDataBuffer from(final File file) {
		try {
			return new CharDataBuffer(doDecode(ByteBuffer.wrap(Files.readAllBytes(file.toPath()))));
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	public static CharDataBuffer from(final String string) {
		return new CharDataBuffer(CharBuffer.wrap(doConvertUnicodeCharactersToString(Objects.requireNonNull(string, "string == null"))));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static CharBuffer doDecode(final ByteBuffer byteBuffer) {
		try {
			final Charset charset = Charset.defaultCharset();
			
			final CharsetDecoder charsetDecoder = charset.newDecoder();
			
			final CharBuffer charBuffer = charsetDecoder.decode(byteBuffer);
			
			final String string = doConvertUnicodeCharactersToString(charBuffer.array());
			
			return CharBuffer.wrap(string);
		} catch(final CharacterCodingException e) {
			return CharBuffer.wrap(new char[0]);
		}
	}
	
	private static String doConvertUnicodeCharactersToString(final char[] characters) {
		return doConvertUnicodeCharactersToString(characters, 0, characters.length, new char[characters.length]);
	}
	
	private static String doConvertUnicodeCharactersToString(final String string) {
		return doConvertUnicodeCharactersToString(string.toCharArray(), 0, string.length(), new char[string.length()]);
	}
	
	private static String doConvertUnicodeCharactersToString(final char[] oldCharacters, final int offset, final int length, final char[] newCharacters) {
		int offset0 = offset;
		int length0 = length;
		
		char[] newCharacters0 = newCharacters;
		
		if(newCharacters0.length < length0) {
			int newLength = length0 * 2;
			
			if(newLength < 0) {
				newLength = Integer.MAX_VALUE;
			}
			
			newCharacters0 = new char[newLength];
		}
		
		char character;
		
		int end = offset0 + length0;
		
		length0 = 0;
		
		while(offset0 < end) {
			character = oldCharacters[offset0++];
			
			if(character == '\\') {
				character = oldCharacters[offset0++];
				
				if(character == 'u') {
					int value = 0;
					
					for(int i = 0; i < 4; i++) {
						character = oldCharacters[offset0++];
						
						switch(character) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								value = (value << 4) + character - '0';
								
								break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
								value = (value << 4) + 10 + character - 'a';
								
								break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
								value = (value << 4) + 10 + character - 'A';
								
								break;
							default:
								throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
						}
					}
					
					newCharacters0[length0++] = (char)(value);
				} else {
					if(character == 't') {
						character = '\t';
					} else if(character == 'r') {
						character = '\r';
					} else if(character == 'n') {
						character = '\n';
					} else if(character == 'f') {
						character = '\f';
					} else {
						newCharacters0[length0++] = '\\';
					}
					
					newCharacters0[length0++] = character;
				}
			} else {
				newCharacters0[length0++] = character;
			}
		}
		
		return new String(newCharacters, 0, length0);
	}
}