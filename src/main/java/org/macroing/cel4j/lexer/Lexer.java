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
package org.macroing.cel4j.lexer;

import java.io.File;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.macroing.cel4j.util.ParameterArguments;
import org.macroing.cel4j.util.Strings;

/**
 * A {@code Lexer} is an abstract base class for building your own lexers.
 * <p>
 * To use it, consider the following example:
 * <pre>
 * <code>
 * String input = "public class HelloWorld {public static void main(String[] args) {System.out.println(\"Hello, World!\");}}";
 * 
 * Lexer lexer = Lexer.newJavaLexer();
 * 
 * List&lt;Token&gt; tokens = lexer.lex(input);
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Lexer {
	/**
	 * Constructs a new {@code Lexer} instance.
	 */
	protected Lexer() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Performs the lexing process.
	 * <p>
	 * Returns a {@code List} with {@link Token} instances.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs while reading the contents of the file represented by {@code file}, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * If an error occurs while performing the lexing process, a {@link LexerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * lexer.lex(file, false);
	 * }
	 * </pre>
	 * 
	 * @param file a {@code File} representing the file to read the input from
	 * @return a {@code List} with {@code Token} instances
	 * @throws LexerException thrown if, and only if, an error occurs while performing the lexing process
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs while reading the contents of the file represented by {@code file}
	 */
	public final List<Token> lex(final File file) {
		return lex(file, false);
	}
	
	/**
	 * Performs the lexing process.
	 * <p>
	 * Returns a {@code List} with {@link Token} instances.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs while reading the contents of the file represented by {@code file}, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * If an error occurs while performing the lexing process, a {@link LexerException} will be thrown.
	 * 
	 * @param file a {@code File} representing the file to read the input from
	 * @param isSkippingSkippables {@code true} if, and only if, all {@code Token} instances that have an {@code isSkippable()} method that returns {@code true} should be skipped, {@code false} otherwise
	 * @return a {@code List} with {@code Token} instances
	 * @throws LexerException thrown if, and only if, an error occurs while performing the lexing process
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs while reading the contents of the file represented by {@code file}
	 */
	public final List<Token> lex(final File file, final boolean isSkippingSkippables) {
		return lex(Strings.toString(file), isSkippingSkippables);
	}
	
	/**
	 * Performs the lexing process.
	 * <p>
	 * Returns a {@code List} with {@link Token} instances.
	 * <p>
	 * If {@code input} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an error occurs while performing the lexing process, a {@link LexerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * lexer.lex(input, false);
	 * }
	 * </pre>
	 * 
	 * @param input the input
	 * @return a {@code List} with {@code Token} instances
	 * @throws LexerException thrown if, and only if, an error occurs while performing the lexing process
	 * @throws NullPointerException thrown if, and only if, {@code input} is {@code null}
	 */
	public final List<Token> lex(final String input) {
		return lex(input, false);
	}
	
	/**
	 * Performs the lexing process.
	 * <p>
	 * Returns a {@code List} with {@link Token} instances.
	 * <p>
	 * If {@code input} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an error occurs while performing the lexing process, a {@link LexerException} will be thrown.
	 * <p>
	 * If {@code isSkippingSkippables} is {@code true}, all {@code Token} instances that have an {@code isSkippable()} method that returns {@code true} will be skipped. That is, they will not be added to the returned {@code List}.
	 * 
	 * @param input the input
	 * @param isSkippingSkippables {@code true} if, and only if, all {@code Token} instances that have an {@code isSkippable()} method that returns {@code true} should be skipped, {@code false} otherwise
	 * @return a {@code List} with {@code Token} instances
	 * @throws LexerException thrown if, and only if, an error occurs while performing the lexing process
	 * @throws NullPointerException thrown if, and only if, {@code input} is {@code null}
	 */
	public final List<Token> lex(final String input, final boolean isSkippingSkippables) {
		final List<Token> tokens = new ArrayList<>();
		
		final Pattern pattern = pattern();
		
		final Matcher matcher = pattern.matcher(Objects.requireNonNull(input, "input == null"));
		
		final Map<String, Function<Match, Token>> functions = functions();
		
		int index = 0;
		
		while(matcher.lookingAt()) {
			for(final Entry<String, Function<Match, Token>> entry : functions.entrySet()) {
				final String name = entry.getKey();
				final String group = matcher.group(name);
				
				if(group != null) {
					final Function<Match, Token> function = entry.getValue();
					
					final
					Match match = new Match(matcher);
					match.setUsable(true);
					
					try {
						final Token token = function.apply(match);
						
						if(token == null) {
							throw new LexerException(String.format("Token at index %s may no be null: '%s'", Integer.toString(index), input.substring(index, index + 1)));
						}
						
						if(!isSkippingSkippables || !token.isSkippable()) {
							tokens.add(token);
						}
						
						break;
					} catch(final IllegalArgumentException | NullPointerException e) {
						throw new LexerException(e);
					} finally {
						match.setUsable(false);
					}
				}
			}
			
			index = matcher.end();
			
			matcher.region(matcher.end(), matcher.regionEnd());
		}
		
		if(!matcher.hitEnd()) {
			throw new LexerException(String.format("Illegal input found at index %s: '%s'", Integer.toString(index), input.substring(index, index + 1)));
		}
		
		return tokens;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Lexer} instance that performs lexing on Java source code.
	 * 
	 * @return a {@code Lexer} instance that performs lexing on Java source code
	 */
	public static Lexer newJavaLexer() {
		return new JavaLexer();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@code Map} of {@code Function} instances that maps {@link Match} instances to {@link Token} instances.
	 * 
	 * @return the {@code Map} of {@code Function} instances that maps {@code Match} instances to {@code Token} instances
	 */
	protected abstract Map<String, Function<Match, Token>> functions();
	
	/**
	 * Returns the {@code Pattern} that should be used in the lexing process.
	 * 
	 * @return the {@code Pattern} that should be used in the lexing process
	 */
	protected abstract Pattern pattern();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * A {@code Match} denotes a match in a {@code Matcher}.
	 * <p>
	 * This class is only of interest to those of you who create your own {@link Lexer} implementations.
	 * 
	 * @since 1.0.0
	 * @author J&#246;rgen Lundgren
	 */
	protected static final class Match {
		private final AtomicBoolean isUsable = new AtomicBoolean();
		private final Lock lock = new ReentrantLock();
		private final Matcher matcher;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		Match(final Matcher matcher) {
			this.matcher = Objects.requireNonNull(matcher, "matcher == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/**
		 * Returns {@code true} if, and only if, this {@code Match} instance is usable, {@code false} otherwise.
		 * <p>
		 * A {@code Match} is only usable for a short period of time. This time period is restricted to a single call to {@code function.apply(match)}. Therefore, you should never store a {@code Match} instance anywhere.
		 * 
		 * @return {@code true} if, and only if, this {@code Match} instance is usable, {@code false} otherwise
		 */
		public boolean isUsable() {
			return this.isUsable.get();
		}
		
		/**
		 * Returns the input subsequence matched by the previous match.
		 * <p>
		 * If {@code isUsable()} returns {@code false}, an {@code IllegalStateException} will be thrown.
		 * 
		 * @return the input subsequence matched by the previous match
		 * @throws IllegalStateException thrown if, and only if, {@code isUsable()} returns {@code false}
		 */
		public String group() {
			this.lock.lock();
			
			try {
				if(isUsable()) {
					return this.matcher.group();
				}
				
				throw new IllegalStateException("isUsable() == false");
			} finally {
				this.lock.unlock();
			}
		}
		
		/**
		 * Returns the input subsequence captured by the given named-capturing group during the previous match operation.
		 * <p>
		 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
		 * <p>
		 * If there is no capturing group in the pattern with the given name, an {@code IllegalArgumentException} will be thrown.
		 * <p>
		 * If {@code isUsable()} returns {@code false}, an {@code IllegalStateException} will be thrown.
		 * 
		 * @param name the name of a named-capturing group
		 * @return the input subsequence captured by the given named-capturing group during the previous match operation
		 * @throws IllegalArgumentException thrown if, and only if, there is no capturing group in the pattern with the given name
		 * @throws IllegalStateException thrown if, and only if, {@code isUsable()} returns {@code false}
		 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
		 */
		public String group(final String name) {
			this.lock.lock();
			
			try {
				if(isUsable()) {
					return this.matcher.group(Objects.requireNonNull(name, "name == null"));
				}
				
				throw new IllegalStateException("isUsable() == false");
			} finally {
				this.lock.unlock();
			}
		}
		
		/**
		 * Returns the input subsequence captured by the given group during the previous match operation.
		 * <p>
		 * If {@code index} is less than {@code 0} or the group does not exist, an {@code IllegalArgumentException} will be thrown.
		 * <p>
		 * If {@code isUsable()} returns {@code false}, an {@code IllegalStateException} will be thrown.
		 * 
		 * @param index the index of a group
		 * @return the input subsequence captured by the given group during the previous match operation
		 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or the group does not exist
		 * @throws IllegalStateException thrown if, and only if, {@code isUsable()} returns {@code false}
		 */
		public String group(final int index) {
			this.lock.lock();
			
			try {
				if(isUsable()) {
					return this.matcher.group(ParameterArguments.requireRange(index, 0, Integer.MAX_VALUE, "index"));
				}
				
				throw new IllegalStateException("isUsable() == false");
			} finally {
				this.lock.unlock();
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		void setUsable(final boolean isUsable) {
			this.lock.lock();
			
			try {
				this.isUsable.set(isUsable);
			} finally {
				this.lock.unlock();
			}
		}
	}
}