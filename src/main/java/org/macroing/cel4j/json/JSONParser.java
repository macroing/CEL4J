/**
 * Copyright 2009 - 2024 J&#246;rgen Lundgren
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
package org.macroing.cel4j.json;

import java.util.List;

/**
 * A {@code JSONParser} is a JSON parser.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class JSONParser {
	private static final String NAME_BINARY_INTEGER_LITERAL = "BinaryIntegerLiteral";
	private static final String NAME_BOOLEAN_LITERAL = "BooleanLiteral";
	private static final String NAME_DECIMAL_FLOATING_POINT_LITERAL = "DecimalFloatingPointLiteral";
	private static final String NAME_DECIMAL_INTEGER_LITERAL = "DecimalIntegerLiteral";
	private static final String NAME_HEXADECIMAL_FLOATING_POINT_LITERAL = "HexadecimalFloatingPointLiteral";
	private static final String NAME_HEX_INTEGER_LITERAL = "HexIntegerLiteral";
	private static final String NAME_NULL_LITERAL = "NullLiteral";
	private static final String NAME_OCTAL_INTEGER_LITERAL = "OctalIntegerLiteral";
	private static final String NAME_SEPARATOR = "Separator";
	private static final String NAME_STRING_LITERAL = "StringLiteral";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final JSONLexer jSONLexer;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code JSONParser} instance.
	 */
	public JSONParser() {
		this.jSONLexer = new JSONLexer();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Performs the parsing process.
	 * <p>
	 * Returns a {@link JSONType}.
	 * <p>
	 * If {@code input} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an error occurs while performing the parsing process, a {@link JSONParserException} will be thrown.
	 * 
	 * @param input the input
	 * @return a {@code JSONType}
	 * @throws JSONParserException thrown if, and only if, an error occurs while performing the parsing process
	 * @throws NullPointerException thrown if, and only if, {@code input} is {@code null}
	 */
	public JSONType parse(final String input) {
		final List<JSONToken> jSONTokens = this.jSONLexer.lex(input, true);
		
		final JSONParserContext jSONParserContext = new JSONParserContext(jSONTokens);
		
		final JSONArray jSONArray = doParseJSONArray(jSONParserContext, jSONParserContext.mark());
		
		if(jSONArray != null) {
			return jSONArray;
		}
		
		final JSONObject jSONObject = doParseJSONObject(jSONParserContext, jSONParserContext.mark());
		
		if(jSONObject != null) {
			return jSONObject;
		}
		
		throw new JSONParserException();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static JSONArray doParseJSONArray(final JSONParserContext jSONParserContext, final int index) {
		if(jSONParserContext.hasNextJSONToken()) {
			final JSONToken jSONToken = jSONParserContext.getNextJSONToken();
			
			final String name = jSONToken.getName();
			final String text = jSONToken.getText();
			
			if(name.equals(NAME_SEPARATOR) && text.equals("[")) {
				final JSONArray jSONArray = new JSONArray();
				
				while(true) {
					final JSONType jSONType = doParseJSONType(jSONParserContext);
					
					if(jSONType == null) {
						break;
					}
					
					jSONArray.addValue(jSONType);
					
					final int index0 = jSONParserContext.mark();
					
					if(jSONParserContext.hasNextJSONToken()) {
						final JSONToken jSONToken0 = jSONParserContext.getNextJSONToken();
						
						final String name0 = jSONToken0.getName();
						final String text0 = jSONToken0.getText();
						
						if(!(name0.equals(NAME_SEPARATOR) && text0.equals(","))) {
							jSONParserContext.rewind(index0);
							
							break;
						}
					}
				}
				
				if(jSONParserContext.hasNextJSONToken()) {
					final JSONToken jSONToken0 = jSONParserContext.getNextJSONToken();
					
					final String name0 = jSONToken0.getName();
					final String text0 = jSONToken0.getText();
					
					if(name0.equals(NAME_SEPARATOR) && text0.equals("]")) {
						return jSONArray;
					}
				}
			}
		}
		
		jSONParserContext.rewind(index);
		
		return null;
	}
	
	private static JSONBoolean doParseJSONBoolean(final JSONParserContext jSONParserContext, final int index) {
		if(jSONParserContext.hasNextJSONToken()) {
			final JSONToken jSONToken = jSONParserContext.getNextJSONToken();
			
			final String name = jSONToken.getName();
			final String text = jSONToken.getText();
			
			if(name.equals(NAME_BOOLEAN_LITERAL)) {
				if(text.equals("false")) {
					return new JSONBoolean(false);
				} else if(text.equals("true")) {
					return new JSONBoolean(true);
				}
			}
		}
		
		jSONParserContext.rewind(index);
		
		return null;
	}
	
	private static JSONNull doParseJSONNull(final JSONParserContext jSONParserContext, final int index) {
		if(jSONParserContext.hasNextJSONToken()) {
			final JSONToken jSONToken = jSONParserContext.getNextJSONToken();
			
			final String name = jSONToken.getName();
			final String text = jSONToken.getText();
			
			if(name.equals(NAME_NULL_LITERAL) && text.equals("null")) {
				return new JSONNull();
			}
		}
		
		jSONParserContext.rewind(index);
		
		return null;
	}
	
	private static JSONNumber doParseJSONNumber(final JSONParserContext jSONParserContext, final int index) {
		if(jSONParserContext.hasNextJSONToken()) {
			final JSONToken jSONToken = jSONParserContext.getNextJSONToken();
			
			final String name = jSONToken.getName();
			final String text = jSONToken.getText();
			
			switch(name) {
				case NAME_BINARY_INTEGER_LITERAL:
					return new JSONNumber(Integer.parseInt(text));
				case NAME_DECIMAL_FLOATING_POINT_LITERAL:
					return new JSONNumber(Double.parseDouble(text));
				case NAME_DECIMAL_INTEGER_LITERAL:
					return new JSONNumber(Double.parseDouble(text));
				case NAME_HEXADECIMAL_FLOATING_POINT_LITERAL:
					return new JSONNumber(Double.parseDouble(text));
				case NAME_HEX_INTEGER_LITERAL:
					return new JSONNumber(Integer.parseInt(text));
				case NAME_OCTAL_INTEGER_LITERAL:
					return new JSONNumber(Integer.parseInt(text));
				default:
					break;
			}
		}
		
		jSONParserContext.rewind(index);
		
		return null;
	}
	
	private static JSONObject doParseJSONObject(final JSONParserContext jSONParserContext, final int index) {
		if(jSONParserContext.hasNextJSONToken()) {
			final JSONToken jSONToken = jSONParserContext.getNextJSONToken();
			
			final String name = jSONToken.getName();
			final String text = jSONToken.getText();
			
			if(name.equals(NAME_SEPARATOR) && text.equals("{")) {
				final JSONObject jSONObject = new JSONObject();
				
				while(true) {
					final int index0 = jSONParserContext.mark();
					
					final JSONString jSONString = doParseJSONString(jSONParserContext, index0);
					
					if(jSONString == null) {
						break;
					}
					
					if(jSONParserContext.hasNextJSONToken()) {
						final JSONToken jSONToken0 = jSONParserContext.getNextJSONToken();
						
						final String name0 = jSONToken0.getName();
						final String text0 = jSONToken0.getText();
						
						if(name0.equals(NAME_SEPARATOR) && text0.equals(":")) {
							final JSONType jSONType = doParseJSONType(jSONParserContext);
							
							if(jSONType == null) {
								break;
							}
							
							jSONObject.addProperty(new JSONObject.Property(jSONString.getValue(), jSONType));
							
							final int index1 = jSONParserContext.mark();
							
							if(jSONParserContext.hasNextJSONToken()) {
								final JSONToken jSONToken1 = jSONParserContext.getNextJSONToken();
								
								final String name1 = jSONToken1.getName();
								final String text1 = jSONToken1.getText();
								
								if(!(name1.equals(NAME_SEPARATOR) && text1.equals(","))) {
									jSONParserContext.rewind(index1);
									
									break;
								}
							}
						}
					}
				}
				
				if(jSONParserContext.hasNextJSONToken()) {
					final JSONToken jSONToken0 = jSONParserContext.getNextJSONToken();
					
					final String name0 = jSONToken0.getName();
					final String text0 = jSONToken0.getText();
					
					if(name0.equals(NAME_SEPARATOR) && text0.equals("}")) {
						return jSONObject;
					}
				}
			}
		}
		
		jSONParserContext.rewind(index);
		
		return null;
	}
	
	private static JSONString doParseJSONString(final JSONParserContext jSONParserContext, final int index) {
		if(jSONParserContext.hasNextJSONToken()) {
			final JSONToken jSONToken = jSONParserContext.getNextJSONToken();
			
			final String name = jSONToken.getName();
			final String text = jSONToken.getText();
			
			if(name.equals(NAME_STRING_LITERAL)) {
				String string = text;
				string = string.startsWith("\"") ? string.substring(1) : string;
				string = string.endsWith("\"") ? string.substring(0, string.length() - 1) : string;
				
				return new JSONString(string);
			}
		}
		
		jSONParserContext.rewind(index);
		
		return null;
	}
	
	private static JSONType doParseJSONType(final JSONParserContext jSONParserContext) {
		final JSONArray jSONArray = doParseJSONArray(jSONParserContext, jSONParserContext.mark());
		
		if(jSONArray != null) {
			return jSONArray;
		}
		
		final JSONBoolean jSONBoolean = doParseJSONBoolean(jSONParserContext, jSONParserContext.mark());
		
		if(jSONBoolean != null) {
			return jSONBoolean;
		}
		
		final JSONNull jSONNull = doParseJSONNull(jSONParserContext, jSONParserContext.mark());
		
		if(jSONNull != null) {
			return jSONNull;
		}
		
		final JSONNumber jSONNumber = doParseJSONNumber(jSONParserContext, jSONParserContext.mark());
		
		if(jSONNumber != null) {
			return jSONNumber;
		}
		
		final JSONObject jSONObject = doParseJSONObject(jSONParserContext, jSONParserContext.mark());
		
		if(jSONObject != null) {
			return jSONObject;
		}
		
		final JSONString jSONString = doParseJSONString(jSONParserContext, jSONParserContext.mark());
		
		if(jSONString != null) {
			return jSONString;
		}
		
		return null;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class JSONParserContext {
		private final List<JSONToken> jSONTokens;
		private int index;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public JSONParserContext(final List<JSONToken> jSONTokens) {
			this.jSONTokens = jSONTokens;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public JSONToken getNextJSONToken() {
			return this.index >= 0 && this.index < this.jSONTokens.size() ? this.jSONTokens.get(this.index++) : null;
		}
		
		public boolean hasNextJSONToken() {
			return this.index >= 0 && this.index < this.jSONTokens.size();
		}
		
		public int mark() {
			return this.index;
		}
		
		public void rewind(final int index) {
			if(index >= 0 && index < this.index) {
				this.index = index;
			}
		}
	}
}