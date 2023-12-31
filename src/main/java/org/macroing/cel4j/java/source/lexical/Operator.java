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
package org.macroing.cel4j.java.source.lexical;

/**
 * An {@code Operator} denotes the nonterminal symbol Operator, as defined by the Java Language Specification.
 * <p>
 * If the Java Language Specification is updated in a way that affects any operators, this enum may be updated to reflect those changes. This means that new elements could be added. This happened between Java 7 and Java 8.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public enum Operator implements Token {
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "+"}.
	 */
	ADDITION(Constants.OPERATOR_ADDITION),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "+="}.
	 */
	ADDITION_ASSIGNMENT(Constants.OPERATOR_ADDITION_ASSIGNMENT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "->"}.
	 */
	ARROW(Constants.OPERATOR_ARROW),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "="}.
	 */
	ASSIGNMENT(Constants.OPERATOR_ASSIGNMENT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "&"}.
	 */
	BITWISE_AND(Constants.OPERATOR_BITWISE_AND),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "&="}.
	 */
	BITWISE_AND_ASSIGNMENT(Constants.OPERATOR_BITWISE_AND_ASSIGNMENT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "^"}.
	 */
	BITWISE_EXCLUSIVE_OR(Constants.OPERATOR_BITWISE_EXCLUSIVE_OR),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "^="}.
	 */
	BITWISE_EXCLUSIVE_OR_ASSIGNMENT(Constants.OPERATOR_BITWISE_EXCLUSIVE_OR_ASSIGNMENT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "|"}.
	 */
	BITWISE_INCLUSIVE_OR(Constants.OPERATOR_BITWISE_INCLUSIVE_OR),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "|="}.
	 */
	BITWISE_INCLUSIVE_OR_ASSIGNMENT(Constants.OPERATOR_BITWISE_INCLUSIVE_OR_ASSIGNMENT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "&&"}.
	 */
	CONDITIONAL_AND(Constants.OPERATOR_CONDITIONAL_AND),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "||"}.
	 */
	CONDITIONAL_OR(Constants.OPERATOR_CONDITIONAL_OR),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "--"}.
	 */
	DECREMENT(Constants.OPERATOR_DECREMENT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "/"}.
	 */
	DIVISION(Constants.OPERATOR_DIVISION),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "/="}.
	 */
	DIVISION_ASSIGNMENT(Constants.OPERATOR_DIVISION_ASSIGNMENT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "=="}.
	 */
	EQUAL_TO(Constants.OPERATOR_EQUAL_TO),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code ">"}.
	 */
	GREATER_THAN(Constants.OPERATOR_GREATER_THAN),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code ">="}.
	 */
	GREATER_THAN_OR_EQUAL_TO(Constants.OPERATOR_GREATER_THAN_OR_EQUAL_TO),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "++"}.
	 */
	INCREMENT(Constants.OPERATOR_INCREMENT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "<"}.
	 */
	LESS_THAN(Constants.OPERATOR_LESS_THAN),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "<="}.
	 */
	LESS_THAN_OR_EQUAL_TO(Constants.OPERATOR_LESS_THAN_OR_EQUAL_TO),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "!"}.
	 */
	LOGICAL_COMPLEMENT(Constants.OPERATOR_LOGICAL_COMPLEMENT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "*"}.
	 */
	MULTIPLICATION(Constants.OPERATOR_MULTIPLICATION),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "*="}.
	 */
	MULTIPLICATION_ASSIGNMENT(Constants.OPERATOR_MULTIPLICATION_ASSIGNMENT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "!="}.
	 */
	NOT_EQUAL_TO(Constants.OPERATOR_NOT_EQUAL_TO),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "%"}.
	 */
	REMAINDER(Constants.OPERATOR_REMAINDER),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "%="}.
	 */
	REMAINDER_ASSIGNMENT(Constants.OPERATOR_REMAINDER_ASSIGNMENT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "<<"}.
	 */
	SIGNED_LEFT_SHIFT(Constants.OPERATOR_SIGNED_LEFT_SHIFT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "<<="}.
	 */
	SIGNED_LEFT_SHIFT_ASSIGNMENT(Constants.OPERATOR_SIGNED_LEFT_SHIFT_ASSIGNMENT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code ">>"}.
	 */
	SIGNED_RIGHT_SHIFT(Constants.OPERATOR_SIGNED_RIGHT_SHIFT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code ">>="}.
	 */
	SIGNED_RIGHT_SHIFT_ASSIGNMENT(Constants.OPERATOR_SIGNED_RIGHT_SHIFT_ASSIGNMENT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "-"}.
	 */
	SUBTRACTION(Constants.OPERATOR_SUBTRACTION),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "-="}.
	 */
	SUBTRACTION_ASSIGNMENT(Constants.OPERATOR_SUBTRACTION_ASSIGNMENT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code ":"}.
	 */
	TERNARY_COLON(Constants.OPERATOR_TERNARY_COLON),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "?"}.
	 */
	TERNARY_QUESTION_MARK(Constants.OPERATOR_TERNARY_QUESTION_MARK),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code "~"}.
	 */
	UNARY_BITWISE_COMPLEMENT(Constants.OPERATOR_UNARY_BITWISE_COMPLEMENT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code ">>>"}.
	 */
	UNSIGNED_RIGHT_SHIFT(Constants.OPERATOR_UNSIGNED_RIGHT_SHIFT),
	
	/**
	 * An {@code Operator} with a {@code String} representation that equals the {@code String} literal {@code ">>>="}.
	 */
	UNSIGNED_RIGHT_SHIFT_ASSIGNMENT(Constants.OPERATOR_UNSIGNED_RIGHT_SHIFT_ASSIGNMENT);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String sourceCode;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Operator(final String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the source code of this {@code Operator} instance.
	 * 
	 * @return the source code of this {@code Operator} instance
	 */
	@Override
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Operator} instance.
	 * 
	 * @return a {@code String} representation of this {@code Operator} instance
	 */
	@Override
	public String toString() {
		return String.format("Operator: [SourceCode=%s]", getSourceCode());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static Operator of(final String sourceCode) {
		switch(sourceCode) {
			case Constants.OPERATOR_ADDITION:
				return ADDITION;
			case Constants.OPERATOR_ADDITION_ASSIGNMENT:
				return ADDITION_ASSIGNMENT;
			case Constants.OPERATOR_ARROW:
				return ARROW;
			case Constants.OPERATOR_ASSIGNMENT:
				return ASSIGNMENT;
			case Constants.OPERATOR_BITWISE_AND:
				return BITWISE_AND;
			case Constants.OPERATOR_BITWISE_AND_ASSIGNMENT:
				return BITWISE_AND_ASSIGNMENT;
			case Constants.OPERATOR_BITWISE_EXCLUSIVE_OR:
				return BITWISE_EXCLUSIVE_OR;
			case Constants.OPERATOR_BITWISE_EXCLUSIVE_OR_ASSIGNMENT:
				return BITWISE_EXCLUSIVE_OR_ASSIGNMENT;
			case Constants.OPERATOR_BITWISE_INCLUSIVE_OR:
				return BITWISE_INCLUSIVE_OR;
			case Constants.OPERATOR_BITWISE_INCLUSIVE_OR_ASSIGNMENT:
				return BITWISE_INCLUSIVE_OR_ASSIGNMENT;
			case Constants.OPERATOR_CONDITIONAL_AND:
				return CONDITIONAL_AND;
			case Constants.OPERATOR_CONDITIONAL_OR:
				return CONDITIONAL_OR;
			case Constants.OPERATOR_DECREMENT:
				return DECREMENT;
			case Constants.OPERATOR_DIVISION:
				return DIVISION;
			case Constants.OPERATOR_DIVISION_ASSIGNMENT:
				return DIVISION_ASSIGNMENT;
			case Constants.OPERATOR_EQUAL_TO:
				return EQUAL_TO;
			case Constants.OPERATOR_GREATER_THAN:
				return GREATER_THAN;
			case Constants.OPERATOR_GREATER_THAN_OR_EQUAL_TO:
				return GREATER_THAN_OR_EQUAL_TO;
			case Constants.OPERATOR_INCREMENT:
				return INCREMENT;
			case Constants.OPERATOR_LESS_THAN:
				return LESS_THAN;
			case Constants.OPERATOR_LESS_THAN_OR_EQUAL_TO:
				return LESS_THAN_OR_EQUAL_TO;
			case Constants.OPERATOR_LOGICAL_COMPLEMENT:
				return LOGICAL_COMPLEMENT;
			case Constants.OPERATOR_MULTIPLICATION:
				return MULTIPLICATION;
			case Constants.OPERATOR_MULTIPLICATION_ASSIGNMENT:
				return MULTIPLICATION_ASSIGNMENT;
			case Constants.OPERATOR_NOT_EQUAL_TO:
				return NOT_EQUAL_TO;
			case Constants.OPERATOR_REMAINDER:
				return REMAINDER;
			case Constants.OPERATOR_REMAINDER_ASSIGNMENT:
				return REMAINDER_ASSIGNMENT;
			case Constants.OPERATOR_SIGNED_LEFT_SHIFT:
				return SIGNED_LEFT_SHIFT;
			case Constants.OPERATOR_SIGNED_LEFT_SHIFT_ASSIGNMENT:
				return SIGNED_LEFT_SHIFT_ASSIGNMENT;
			case Constants.OPERATOR_SIGNED_RIGHT_SHIFT:
				return SIGNED_RIGHT_SHIFT;
			case Constants.OPERATOR_SIGNED_RIGHT_SHIFT_ASSIGNMENT:
				return SIGNED_RIGHT_SHIFT_ASSIGNMENT;
			case Constants.OPERATOR_SUBTRACTION:
				return SUBTRACTION;
			case Constants.OPERATOR_SUBTRACTION_ASSIGNMENT:
				return SUBTRACTION_ASSIGNMENT;
			case Constants.OPERATOR_TERNARY_COLON:
				return TERNARY_COLON;
			case Constants.OPERATOR_TERNARY_QUESTION_MARK:
				return TERNARY_QUESTION_MARK;
			case Constants.OPERATOR_UNARY_BITWISE_COMPLEMENT:
				return UNARY_BITWISE_COMPLEMENT;
			case Constants.OPERATOR_UNSIGNED_RIGHT_SHIFT:
				return UNSIGNED_RIGHT_SHIFT;
			case Constants.OPERATOR_UNSIGNED_RIGHT_SHIFT_ASSIGNMENT:
				return UNSIGNED_RIGHT_SHIFT_ASSIGNMENT;
			default:
				throw new IllegalArgumentException(String.format("Illegal Operator: %s", sourceCode));
		}
	}
}