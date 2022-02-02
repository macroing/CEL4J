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
package org.macroing.cel4j.java.source.lexical;

import java.io.File;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.macroing.cel4j.java.source.JavaNode;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.Strings;

/**
 * An {@code Input} denotes the nonterminal symbol Input, as defined by the Java Language Specification.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Input implements Iterable<InputElement>, JavaNode {
	private static final Map<String, Function<Matcher, InputElement>> FUNCTIONS = doCreateFunctions();
	private static final Pattern PATTERN = Pattern.compile(Constants.REGEX_INPUT_ELEMENT);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final AtomicBoolean hasSubAtEnd = new AtomicBoolean();
	private final List<InputElement> inputElements = new ArrayList<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Input() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code Iterator} over all currently added {@link InputElement}s.
	 * <p>
	 * Removing an {@code InputElement} via the {@code Iterator} will not affect the internal state of this {@code Input} instance.
	 * 
	 * @return an {@code Iterator} over all currently added {@link InputElement}s
	 */
	@Override
	public Iterator<InputElement> iterator() {
		return getInputElements().iterator();
	}
	
	/**
	 * Returns a {@code List} with all currently added {@link InputElement}s.
	 * <p>
	 * Modifications made to the returned {@code List} will not affect the internal state of this {@code Input} instance.
	 * 
	 * @return a {@code List} with all currently added {@code InputElement}s
	 */
	public List<InputElement> getInputElements() {
		return new ArrayList<>(this.inputElements);
	}
	
	/**
	 * Returns the source code of this {@code Input} instance.
	 * 
	 * @return the source code of this {@code Input} instance
	 */
	@Override
	public String getSourceCode() {
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(final InputElement inputElement : this.inputElements) {
			stringBuilder.append(inputElement.getSourceCode());
		}
		
		stringBuilder.append(hasSubAtEnd() ? "\u001A" : "");
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Input} instance.
	 * 
	 * @return a {@code String} representation of this {@code Input} instance
	 */
	@Override
	public String toString() {
		return String.format("Input: [SourceCode=%s]", getSourceCode());
	}
	
	/**
	 * Accepts a {@link NodeHierarchicalVisitor}.
	 * <p>
	 * Returns the result of {@code nodeHierarchicalVisitor.visitLeave(this)}.
	 * <p>
	 * If {@code nodeHierarchicalVisitor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}, a {@code NodeTraversalException} will be thrown with the {@code RuntimeException} wrapped.
	 * <p>
	 * This implementation will:
	 * <ul>
	 * <li>throw a {@code NullPointerException} if {@code nodeHierarchicalVisitor} is {@code null}.</li>
	 * <li>throw a {@code NodeTraversalException} if {@code nodeHierarchicalVisitor} throws a {@code RuntimeException}.</li>
	 * <li>traverse its child {@code Node}s, if it has any.</li>
	 * </ul>
	 * 
	 * @param nodeHierarchicalVisitor the {@code NodeHierarchicalVisitor} to accept
	 * @return the result of {@code nodeHierarchicalVisitor.visitLeave(this)}
	 * @throws NodeTraversalException thrown if, and only if, a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code nodeHierarchicalVisitor} is {@code null}
	 */
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				for(final InputElement inputElement : this.inputElements) {
					if(!inputElement.accept(nodeHierarchicalVisitor)) {
						break;
					}
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Input}, and that {@code Input} instance is equal to this {@code Input} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Input}, and that {@code Input} instance is equal to this {@code Input} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Input)) {
			return false;
		} else if(Input.class.cast(object).hasSubAtEnd.get() != this.hasSubAtEnd.get()) {
			return false;
		} else if(!Objects.equals(Input.class.cast(object).inputElements, this.inputElements)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, a Sub or Control-Z has been added to the end, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, a Sub or Control-Z has been added to the end, {@code false} otherwise
	 */
	public boolean hasSubAtEnd() {
		return this.hasSubAtEnd.get();
	}
	
	/**
	 * Returns a hash-code for this {@code Input} instance.
	 * 
	 * @return a hash-code for this {@code Input} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Boolean.valueOf(this.hasSubAtEnd.get()), this.inputElements);
	}
	
	/**
	 * Adds an {@link InputElement} to this {@code Input} instance.
	 * <p>
	 * If {@code inputElement} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param inputElement the {@code InputElement} to add
	 * @throws NullPointerException thrown if, and only if, {@code inputElement} is {@code null}
	 */
	public void addInputElement(final InputElement inputElement) {
		this.inputElements.add(Objects.requireNonNull(inputElement, "inputElement == null"));
	}
	
	/**
	 * Removes an {@link InputElement} from this {@code Input} instance.
	 * <p>
	 * If {@code inputElement} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param inputElement the {@code InputElement} to remove
	 * @throws NullPointerException thrown if, and only if, {@code inputElement} is {@code null}
	 */
	public void removeInputElement(final InputElement inputElement) {
		this.inputElements.remove(Objects.requireNonNull(inputElement, "inputElement == null"));
	}
	
	/**
	 * Sets the state of the Sub or Control-Z at the end of this {@code Input} instance.
	 * 
	 * @param hasSubAtEnd {@code true} for Sub or Control-Z at the end
	 */
	public void setSubAtEnd(final boolean hasSubAtEnd) {
		this.hasSubAtEnd.set(hasSubAtEnd);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new empty {@code Input} instance.
	 * 
	 * @return a new empty {@code Input} instance
	 */
	public static Input newInstance() {
		return new Input();
	}
	
	/**
	 * Parses the content of a file as an {@code Input} instance.
	 * <p>
	 * Returns an {@code Input} instance.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the file cannot be read, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param file a {@code File} denoting the file from which to parse
	 * @return an {@code Input} instance
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, the file cannot be read
	 */
	public static Input parseInput(final File file) {
		return parseInput(Strings.toString(file));
	}
	
	/**
	 * Parses the content of the suppled {@code String} as an {@code Input} instance.
	 * <p>
	 * Returns an {@code Input} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param string the {@code String} with the content to parse
	 * @return an {@code Input} instance
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static Input parseInput(final String string) {
		final Input input = newInstance();
		
		final Matcher matcher = PATTERN.matcher(Objects.requireNonNull(string, "string == null"));
		
		while(matcher.find()) {
			for(final Entry<String, Function<Matcher, InputElement>> entry : FUNCTIONS.entrySet()) {
				final String name = entry.getKey();
				final String group = matcher.group(name);
				
				if(group != null) {
					final Function<Matcher, InputElement> function = entry.getValue();
					
					final InputElement inputElement = function.apply(matcher);
					
					input.addInputElement(inputElement);
				}
			}
		}
		
		return input;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Map<String, Function<Matcher, InputElement>> doCreateFunctions() {
		final Map<String, Function<Matcher, InputElement>> functions = new LinkedHashMap<>();
		
		functions.put(Constants.NAME_BINARY_INTEGER_LITERAL, matcher -> BinaryIntegerLiteral.valueOf(matcher.group(Constants.NAME_BINARY_INTEGER_LITERAL_BINARY_NUMERAL), matcher.group(Constants.NAME_BINARY_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX)));
		functions.put(Constants.NAME_BOOLEAN_LITERAL, matcher -> BooleanLiteral.of(matcher.group()));
		functions.put(Constants.NAME_CHARACTER_LITERAL, matcher -> CharacterLiteral.valueOf(matcher.group()));
		functions.put(Constants.NAME_DECIMAL_FLOATING_POINT_LITERAL, matcher -> DecimalFloatingPointLiteral.valueOf(matcher.group()));
		functions.put(Constants.NAME_DECIMAL_INTEGER_LITERAL, matcher -> DecimalIntegerLiteral.valueOf(matcher.group(Constants.NAME_DECIMAL_INTEGER_LITERAL_DECIMAL_NUMERAL), matcher.group(Constants.NAME_DECIMAL_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX)));
		functions.put(Constants.NAME_END_OF_LINE_COMMENT, matcher -> EndOfLineComment.valueOf(matcher.group()));
		functions.put(Constants.NAME_HEXADECIMAL_FLOATING_POINT_LITERAL, matcher -> HexadecimalFloatingPointLiteral.valueOf(matcher.group(Constants.NAME_HEXADECIMAL_FLOATING_POINT_LITERAL_HEX_SIGNIFICAND), matcher.group(Constants.NAME_HEXADECIMAL_FLOATING_POINT_LITERAL_BINARY_EXPONENT), matcher.group(Constants.NAME_HEXADECIMAL_FLOATING_POINT_LITERAL_FLOAT_TYPE_SUFFIX)));
		functions.put(Constants.NAME_HEX_INTEGER_LITERAL, matcher -> HexIntegerLiteral.valueOf(matcher.group(Constants.NAME_HEX_INTEGER_LITERAL_HEX_NUMERAL), matcher.group(Constants.NAME_HEX_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX)));
		functions.put(Constants.NAME_IDENTIFIER, matcher -> Identifier.valueOf(matcher.group()));
		functions.put(Constants.NAME_KEYWORD, matcher -> Keyword.of(matcher.group()));
		functions.put(Constants.NAME_NULL_LITERAL, matcher -> NullLiteral.of(matcher.group()));
		functions.put(Constants.NAME_OCTAL_INTEGER_LITERAL, matcher -> OctalIntegerLiteral.valueOf(matcher.group(Constants.NAME_OCTAL_INTEGER_LITERAL_OCTAL_NUMERAL), matcher.group(Constants.NAME_OCTAL_INTEGER_LITERAL_INTEGER_TYPE_SUFFIX)));
		functions.put(Constants.NAME_OPERATOR, matcher -> Operator.of(matcher.group()));
		functions.put(Constants.NAME_SEPARATOR, matcher -> Separator.of(matcher.group()));
		functions.put(Constants.NAME_STRING_LITERAL, matcher -> StringLiteral.valueOf(matcher.group()));
		functions.put(Constants.NAME_TRADITIONAL_COMMENT, matcher -> TraditionalComment.valueOf(matcher.group()));
		functions.put(Constants.NAME_WHITE_SPACE, matcher -> WhiteSpace.of(matcher.group()));
		
		return functions;
	}
}