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
package org.macroing.cel4j.java.source.lexical;

/**
 * A {@code Keyword} denotes the nonterminal symbol Keyword, as defined by the Java Language Specification.
 * <p>
 * If the Java Language Specification is updated in a way that affects any keywords, this enum may be updated to reflect those changes. This means that new elements could be added. But it's highly unlikely.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public enum Keyword implements Token {
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "abstract"}.
	 */
	ABSTRACT(Constants.KEYWORD_ABSTRACT),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "assert"}.
	 */
	ASSERT(Constants.KEYWORD_ASSERT),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "boolean"}.
	 */
	BOOLEAN(Constants.KEYWORD_BOOLEAN),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "break"}.
	 */
	BREAK(Constants.KEYWORD_BREAK),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "byte"}.
	 */
	BYTE(Constants.KEYWORD_BYTE),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "case"}.
	 */
	CASE(Constants.KEYWORD_CASE),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "catch"}.
	 */
	CATCH(Constants.KEYWORD_CATCH),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "char"}.
	 */
	CHAR(Constants.KEYWORD_CHAR),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "class"}.
	 */
	CLASS(Constants.KEYWORD_CLASS),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "const"}.
	 */
	CONST(Constants.KEYWORD_CONST),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "continue"}.
	 */
	CONTINUE(Constants.KEYWORD_CONTINUE),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "default"}.
	 */
	DEFAULT(Constants.KEYWORD_DEFAULT),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "do"}.
	 */
	DO(Constants.KEYWORD_DO),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "double"}.
	 */
	DOUBLE(Constants.KEYWORD_DOUBLE),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "else"}.
	 */
	ELSE(Constants.KEYWORD_ELSE),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "enum"}.
	 */
	ENUM(Constants.KEYWORD_ENUM),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "extends"}.
	 */
	EXTENDS(Constants.KEYWORD_EXTENDS),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "final"}.
	 */
	FINAL(Constants.KEYWORD_FINAL),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "finally"}.
	 */
	FINALLY(Constants.KEYWORD_FINALLY),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "float"}.
	 */
	FLOAT(Constants.KEYWORD_FLOAT),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "for"}.
	 */
	FOR(Constants.KEYWORD_FOR),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "goto"}.
	 */
	GO_TO(Constants.KEYWORD_GO_TO),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "if"}.
	 */
	IF(Constants.KEYWORD_IF),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "implements"}.
	 */
	IMPLEMENTS(Constants.KEYWORD_IMPLEMENTS),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "import"}.
	 */
	IMPORT(Constants.KEYWORD_IMPORT),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "instanceof"}.
	 */
	INSTANCE_OF(Constants.KEYWORD_INSTANCE_OF),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "int"}.
	 */
	INT(Constants.KEYWORD_INT),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "interface"}.
	 */
	INTERFACE(Constants.KEYWORD_INTERFACE),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "long"}.
	 */
	LONG(Constants.KEYWORD_LONG),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "native"}.
	 */
	NATIVE(Constants.KEYWORD_NATIVE),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "new"}.
	 */
	NEW(Constants.KEYWORD_NEW),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "package"}.
	 */
	PACKAGE(Constants.KEYWORD_PACKAGE),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "private"}.
	 */
	PRIVATE(Constants.KEYWORD_PRIVATE),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "protected"}.
	 */
	PROTECTED(Constants.KEYWORD_PROTECTED),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "public"}.
	 */
	PUBLIC(Constants.KEYWORD_PUBLIC),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "return"}.
	 */
	RETURN(Constants.KEYWORD_RETURN),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "short"}.
	 */
	SHORT(Constants.KEYWORD_SHORT),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "static"}.
	 */
	STATIC(Constants.KEYWORD_STATIC),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "strictfp"}.
	 */
	STRICT_F_P(Constants.KEYWORD_STRICT_F_P),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "super"}.
	 */
	SUPER(Constants.KEYWORD_SUPER),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "switch"}.
	 */
	SWITCH(Constants.KEYWORD_SWITCH),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "synchronized"}.
	 */
	SYNCHRONIZED(Constants.KEYWORD_SYNCHRONIZED),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "this"}.
	 */
	THIS(Constants.KEYWORD_THIS),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "throw"}.
	 */
	THROW(Constants.KEYWORD_THROW),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "throws"}.
	 */
	THROWS(Constants.KEYWORD_THROWS),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "transient"}.
	 */
	TRANSIENT(Constants.KEYWORD_TRANSIENT),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "try"}.
	 */
	TRY(Constants.KEYWORD_TRY),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "void"}.
	 */
	VOID(Constants.KEYWORD_VOID),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "volatile"}.
	 */
	VOLATILE(Constants.KEYWORD_VOLATILE),
	
	/**
	 * A {@code Keyword} with a {@code String} representation that equals the {@code String} literal {@code "while"}.
	 */
	WHILE(Constants.KEYWORD_WHILE);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Keyword(final String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the source code of this {@code Keyword} instance.
	 * 
	 * @return the source code of this {@code Keyword} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Keyword} instance.
	 * 
	 * @return a {@code String} representation of this {@code Keyword} instance
	 */
	@Override
	public String toString() {
		return String.format("Keyword: [SourceCode=%s]", getSourceCode());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static Keyword of(final String sourceCode) {
		switch(sourceCode) {
			case Constants.KEYWORD_ABSTRACT:
				return ABSTRACT;
			case Constants.KEYWORD_ASSERT:
				return ASSERT;
			case Constants.KEYWORD_BOOLEAN:
				return BOOLEAN;
			case Constants.KEYWORD_BREAK:
				return BREAK;
			case Constants.KEYWORD_BYTE:
				return BYTE;
			case Constants.KEYWORD_CASE:
				return CASE;
			case Constants.KEYWORD_CATCH:
				return CATCH;
			case Constants.KEYWORD_CHAR:
				return CHAR;
			case Constants.KEYWORD_CLASS:
				return CLASS;
			case Constants.KEYWORD_CONST:
				return CONST;
			case Constants.KEYWORD_CONTINUE:
				return CONTINUE;
			case Constants.KEYWORD_DEFAULT:
				return DEFAULT;
			case Constants.KEYWORD_DO:
				return DO;
			case Constants.KEYWORD_DOUBLE:
				return DOUBLE;
			case Constants.KEYWORD_ELSE:
				return ELSE;
			case Constants.KEYWORD_ENUM:
				return ENUM;
			case Constants.KEYWORD_EXTENDS:
				return EXTENDS;
			case Constants.KEYWORD_FINAL:
				return FINAL;
			case Constants.KEYWORD_FINALLY:
				return FINALLY;
			case Constants.KEYWORD_FLOAT:
				return FLOAT;
			case Constants.KEYWORD_FOR:
				return FOR;
			case Constants.KEYWORD_GO_TO:
				return GO_TO;
			case Constants.KEYWORD_IF:
				return IF;
			case Constants.KEYWORD_IMPLEMENTS:
				return IMPLEMENTS;
			case Constants.KEYWORD_IMPORT:
				return IMPORT;
			case Constants.KEYWORD_INSTANCE_OF:
				return INSTANCE_OF;
			case Constants.KEYWORD_INT:
				return INT;
			case Constants.KEYWORD_INTERFACE:
				return INTERFACE;
			case Constants.KEYWORD_LONG:
				return LONG;
			case Constants.KEYWORD_NATIVE:
				return NATIVE;
			case Constants.KEYWORD_NEW:
				return NEW;
			case Constants.KEYWORD_PACKAGE:
				return PACKAGE;
			case Constants.KEYWORD_PRIVATE:
				return PRIVATE;
			case Constants.KEYWORD_PROTECTED:
				return PROTECTED;
			case Constants.KEYWORD_PUBLIC:
				return PUBLIC;
			case Constants.KEYWORD_RETURN:
				return RETURN;
			case Constants.KEYWORD_SHORT:
				return SHORT;
			case Constants.KEYWORD_STATIC:
				return STATIC;
			case Constants.KEYWORD_STRICT_F_P:
				return STRICT_F_P;
			case Constants.KEYWORD_SUPER:
				return SUPER;
			case Constants.KEYWORD_SWITCH:
				return SWITCH;
			case Constants.KEYWORD_SYNCHRONIZED:
				return SYNCHRONIZED;
			case Constants.KEYWORD_THIS:
				return THIS;
			case Constants.KEYWORD_THROW:
				return THROW;
			case Constants.KEYWORD_THROWS:
				return THROWS;
			case Constants.KEYWORD_TRANSIENT:
				return TRANSIENT;
			case Constants.KEYWORD_TRY:
				return TRY;
			case Constants.KEYWORD_VOID:
				return VOID;
			case Constants.KEYWORD_VOLATILE:
				return VOLATILE;
			case Constants.KEYWORD_WHILE:
				return WHILE;
			default:
				throw new IllegalArgumentException(String.format("Illegal Keyword: %s", sourceCode));
		}
	}
}