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
package org.macroing.cel4j.scanner;

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
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.macroing.cel4j.util.ParameterArguments;
import org.macroing.cel4j.util.Strings;

/**
 * A {@code TextScanner} is a {@link Scanner} implementation that can scan text.
 * <p>
 * In this context text refers to the data types {@code char} and {@code String}.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class TextScanner extends Scanner<StringBuilder, String> {
	private static final char EOF = (char)(0);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final CharBuffer charBuffer;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code TextScanner} instance with {@code char} data read from the file {@code file}.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the {@code char} data cannot be read, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * If the {@code char} data contains an illegal Unicode escape sequence, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * This constructor performs Unicode escape sequence conversions using {@link Strings#convertUnicodeEscapeSequences(char[])}.
	 * 
	 * @param file a {@code File}
	 * @throws IllegalArgumentException thrown if, and only if, the {@code char} data contains an illegal Unicode escape sequence
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, the {@code char} data cannot be read
	 */
	public TextScanner(final File file) {
		try {
			this.charBuffer = doDecode(ByteBuffer.wrap(Files.readAllBytes(Objects.requireNonNull(file, "file == null").toPath())));
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Constructs a new {@code TextScanner} instance with {@code char} data read from the {@code String} {@code string}.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the {@code char} data contains an illegal Unicode escape sequence, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * This method performs Unicode escape sequence conversions using {@link Strings#convertUnicodeEscapeSequences(String)}.
	 * 
	 * @param string a {@code String}
	 * @throws IllegalArgumentException thrown if, and only if, the {@code char} data contains an illegal Unicode escape sequence
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public TextScanner(final String string) {
		this.charBuffer = CharBuffer.wrap(Strings.convertUnicodeEscapeSequences(Objects.requireNonNull(string, "string == null")));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} that contains the consumption between {@code indexAtBeginningInclusive} (inclusive) and {@code indexAtEndExclusive} (exclusive).
	 * <p>
	 * If {@code indexAtBeginningInclusive} is less than {@code 0} or greater than {@code indexAtEndExclusive}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexAtBeginningInclusive the index at the beginning of the consumption, which is inclusive
	 * @param indexAtEndExclusive the index at the end of the consumption, which is exclusive
	 * @return a {@code String} that contains the consumption between {@code indexAtBeginningInclusive} (inclusive) and {@code indexAtEndExclusive} (exclusive)
	 * @throws IllegalArgumentException thrown if, and only if, {@code indexAtBeginningInclusive} is less than {@code 0} or greater than {@code indexAtEndExclusive}
	 */
	@Override
	public String consumption(final int indexAtBeginningInclusive, final int indexAtEndExclusive) {
		ParameterArguments.requireRange(indexAtBeginningInclusive, 0, indexAtEndExclusive, "indexAtBeginningInclusive");
		ParameterArguments.requireRange(indexAtEndExclusive, indexAtBeginningInclusive, Integer.MAX_VALUE, "indexAtEndExclusive");//Might not be necessary?
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(int index = indexAtBeginningInclusive; index < indexAtEndExclusive; index++) {
			if(doHasDataAt(index)) {
				stringBuilder.append(doGetCharAt(index));
			} else {
				break;
			}
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * Reads the next data element.
	 * <p>
	 * Returns {@code true} if, and only if, the next data element was read, {@code false} otherwise.
	 * <p>
	 * This method essentially increments the index at the end of the current consumption.
	 * <p>
	 * If this method returns {@code true}, the {@link #consume()} and {@link #consume(Object)} methods can be successfully called.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * textScanner.nextCharacter();
	 * }
	 * </pre>
	 * 
	 * @return {@code true} if, and only if, the next data element was read, {@code false} otherwise
	 */
	@Override
	public boolean next() {
		return nextCharacter();
	}
	
	/**
	 * Reads the next {@code char}.
	 * <p>
	 * Returns {@code true} if, and only if, the next {@code char} was read, {@code false} otherwise.
	 * <p>
	 * This method essentially increments the index at the end of the current consumption.
	 * <p>
	 * If this method returns {@code true}, the {@link #consume()} and {@link #consume(Object)} methods can be successfully called.
	 * 
	 * @return {@code true} if, and only if, the next {@code char} was read, {@code false} otherwise
	 */
	public boolean nextCharacter() {
		final int oldIndexAtEndExclusive = getIndexAtEndExclusive();
		final int newIndexAtEndExclusive = oldIndexAtEndExclusive + 1;
		
		if(doHasDataAt(oldIndexAtEndExclusive)) {
			setIndexAtEndExclusive(newIndexAtEndExclusive);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Reads the next {@code char}, but only if it is equal to {@code character}.
	 * <p>
	 * Returns {@code true} if, and only if, the next {@code char} was read, {@code false} otherwise.
	 * <p>
	 * This method essentially increments the index at the end of the current consumption.
	 * <p>
	 * If this method returns {@code true}, the {@link #consume()} and {@link #consume(Object)} methods can be successfully called.
	 * 
	 * @param character the {@code char} to read
	 * @return {@code true} if, and only if, the next {@code char} was read, {@code false} otherwise
	 */
	public boolean nextCharacter(final char character) {
		final int oldIndexAtEndExclusive = getIndexAtEndExclusive();
		final int newIndexAtEndExclusive = oldIndexAtEndExclusive + 1;
		
		if(doHasDataAt(oldIndexAtEndExclusive) && doGetCharAt(oldIndexAtEndExclusive) == character) {
			setIndexAtEndExclusive(newIndexAtEndExclusive);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Reads the next {@code char}, but only if it is equal to at least one of the {@code char} instances in {@code characters}.
	 * <p>
	 * Returns {@code true} if, and only if, the next {@code char} was read, {@code false} otherwise.
	 * <p>
	 * If {@code characters} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method essentially increments the index at the end of the current consumption.
	 * <p>
	 * If this method returns {@code true}, the {@link #consume()} and {@link #consume(Object)} methods can be successfully called.
	 * 
	 * @param characters an array of {@code char} instances to read
	 * @return {@code true} if, and only if, the next {@code char} was read, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code characters} is {@code null}
	 */
	public boolean nextCharacterAlternation(final char... characters) {
		Objects.requireNonNull(characters, "characters == null");
		
		final Key key = stateSave();
		
		for(final char character : characters) {
			if(nextCharacter(character)) {
				stateDelete(key);
				
				return true;
			}
		}
		
		stateLoad(key);
		stateDelete(key);
		
		return false;
	}
	
	/**
	 * Reads the next {@code char} instances, but only if they are equal to all of the {@code char} instances in {@code characters}.
	 * <p>
	 * Returns {@code true} if, and only if, the next {@code char} instances were read, {@code false} otherwise.
	 * <p>
	 * If {@code characters} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method essentially increments the index at the end of the current consumption.
	 * <p>
	 * If this method returns {@code true}, the {@link #consume()} and {@link #consume(Object)} methods can be successfully called.
	 * 
	 * @param characters an array of {@code char} instances to read
	 * @return {@code true} if, and only if, the next {@code char} instances were read, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code characters} is {@code null}
	 */
	public boolean nextCharacterConcatenation(final char... characters) {
		Objects.requireNonNull(characters, "characters == null");
		
		final Key key = stateSave();
		
		for(final char character : characters) {
			if(!nextCharacter(character)) {
				stateLoad(key);
				stateDelete(key);
				
				return false;
			}
		}
		
		stateDelete(key);
		
		return true;
	}
	
	/**
	 * Reads the next {@code String}, but only if it matches {@code pattern}.
	 * <p>
	 * Returns {@code true} if, and only if, the next {@code String} was read, {@code false} otherwise.
	 * <p>
	 * If {@code pattern} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method essentially increments the index at the end of the current consumption.
	 * <p>
	 * If this method returns {@code true}, the {@link #consume()} and {@link #consume(Object)} methods can be successfully called.
	 * 
	 * @param pattern the {@code Pattern} to match against
	 * @return {@code true} if, and only if, the next {@code String} was read, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code pattern} is {@code null}
	 */
	public boolean nextRegex(final Pattern pattern) {
		return nextRegex(pattern, matchResult -> {});
	}
	
	/**
	 * Reads the next {@code String}, but only if it matches {@code pattern}.
	 * <p>
	 * Returns {@code true} if, and only if, the next {@code String} was read, {@code false} otherwise.
	 * <p>
	 * If either {@code pattern} or {@code matchResultConsumer} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method essentially increments the index at the end of the current consumption.
	 * <p>
	 * If this method returns {@code true}, the {@link #consume()} and {@link #consume(Object)} methods can be successfully called.
	 * 
	 * @param pattern the {@code Pattern} to match against
	 * @param matchResultConsumer a {@code Consumer} that will consume all {@code MatchResult} instances
	 * @return {@code true} if, and only if, the next {@code String} was read, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code pattern} or {@code matchResultConsumer} are {@code null}
	 */
	public boolean nextRegex(final Pattern pattern, final Consumer<MatchResult> matchResultConsumer) {
		Objects.requireNonNull(pattern, "pattern == null");
		Objects.requireNonNull(matchResultConsumer, "matchResultConsumer == null");
		
		final int oldIndexAtEndExclusive = getIndexAtEndExclusive();
		
		final CharBuffer charBuffer = this.charBuffer;
		
		final
		Matcher matcher = pattern.matcher(charBuffer.toString());
		matcher.region(oldIndexAtEndExclusive, charBuffer.capacity());
		
		if(matcher.lookingAt()) {
			final MatchResult matchResult = matcher.toMatchResult();
			
			matchResultConsumer.accept(matchResult);
			
			final int newIndexAtEndExclusive = oldIndexAtEndExclusive + matcher.group().length();
			
			setIndexAtEndExclusive(newIndexAtEndExclusive);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Reads the next {@code String}, but only if it matches at least one of the {@code Pattern} instances in {@code patterns}.
	 * <p>
	 * Returns {@code true} if, and only if, the next {@code String} was read, {@code false} otherwise.
	 * <p>
	 * If either {@code patterns} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method essentially increments the index at the end of the current consumption.
	 * <p>
	 * If this method returns {@code true}, the {@link #consume()} and {@link #consume(Object)} methods can be successfully called.
	 * 
	 * @param patterns an array of {@code Pattern} instances to match against
	 * @return {@code true} if, and only if, the next {@code String} was read, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code patterns} or any of its elements are {@code null}
	 */
	public boolean nextRegexAlternation(final Pattern... patterns) {
		Objects.requireNonNull(patterns, "patterns == null");
		
		final Key key = stateSave();
		
		for(final Pattern pattern : patterns) {
			if(nextRegex(pattern)) {
				stateDelete(key);
				
				return true;
			}
		}
		
		stateLoad(key);
		stateDelete(key);
		
		return false;
	}
	
	/**
	 * Reads the next {@code String} instances, but only if they matches all of the {@code Pattern} instances in {@code patterns}.
	 * <p>
	 * Returns {@code true} if, and only if, the next {@code String} instances were read, {@code false} otherwise.
	 * <p>
	 * If either {@code patterns} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method essentially increments the index at the end of the current consumption.
	 * <p>
	 * If this method returns {@code true}, the {@link #consume()} and {@link #consume(Object)} methods can be successfully called.
	 * 
	 * @param patterns an array of {@code Pattern} instances to match against
	 * @return {@code true} if, and only if, the next {@code String} instances were read, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code patterns} or any of its elements are {@code null}
	 */
	public boolean nextRegexConcatenation(final Pattern... patterns) {
		Objects.requireNonNull(patterns, "patterns == null");
		
		final Key key = stateSave();
		
		for(final Pattern pattern : patterns) {
			if(!nextRegex(pattern)) {
				stateLoad(key);
				stateDelete(key);
				
				return false;
			}
		}
		
		stateDelete(key);
		
		return true;
	}
	
	/**
	 * Reads the next {@code String}, but only if it is equal to {@code string}.
	 * <p>
	 * Returns {@code true} if, and only if, the next {@code String} was read, {@code false} otherwise.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method essentially increments the index at the end of the current consumption.
	 * <p>
	 * If this method returns {@code true}, the {@link #consume()} and {@link #consume(Object)} methods can be successfully called.
	 * 
	 * @param string the {@code String} to read
	 * @return {@code true} if, and only if, the next {@code String} was read, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public boolean nextString(final String string) {
		return nextCharacterConcatenation(Objects.requireNonNull(string, "string == null").toCharArray());
	}
	
	/**
	 * Reads the next {@code String}, but only if it is equal to at least one of the {@code String} instances in {@code strings}.
	 * <p>
	 * Returns {@code true} if, and only if, the next {@code String} was read, {@code false} otherwise.
	 * <p>
	 * If either {@code strings} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method essentially increments the index at the end of the current consumption.
	 * <p>
	 * If this method returns {@code true}, the {@link #consume()} and {@link #consume(Object)} methods can be successfully called.
	 * 
	 * @param strings an array of {@code String} instances to read
	 * @return {@code true} if, and only if, the next {@code String} was read, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code strings} or any of its elements are {@code null}
	 */
	public boolean nextStringAlternation(final String... strings) {
		Objects.requireNonNull(strings, "strings == null");
		
		final Key key = stateSave();
		
		for(final String string : strings) {
			if(nextString(string)) {
				stateDelete(key);
				
				return true;
			}
		}
		
		stateLoad(key);
		stateDelete(key);
		
		return false;
	}
	
	/**
	 * Reads the next {@code String} instances, but only if they are equal to all of the {@code String} instances in {@code strings}.
	 * <p>
	 * Returns {@code true} if, and only if, the next {@code String} instances were read, {@code false} otherwise.
	 * <p>
	 * If either {@code strings} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method essentially increments the index at the end of the current consumption.
	 * <p>
	 * If this method returns {@code true}, the {@link #consume()} and {@link #consume(Object)} methods can be successfully called.
	 * 
	 * @param strings an array of {@code String} instances to read
	 * @return {@code true} if, and only if, the next {@code String} instances were read, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code strings} or any of its elements are {@code null}
	 */
	public boolean nextStringConcatenation(final String... strings) {
		Objects.requireNonNull(strings, "strings == null");
		
		final Key key = stateSave();
		
		for(final String string : strings) {
			if(!nextString(string)) {
				stateLoad(key);
				stateDelete(key);
				
				return false;
			}
		}
		
		stateDelete(key);
		
		return true;
	}
	
	/**
	 * Tests whether {@link #nextCharacter()} can be called successfully.
	 * <p>
	 * Returns {@code true} if, and only if, {@code nextCharacter()} returns {@code true}, {@code false} otherwise.
	 * <p>
	 * This method calls {@link #stateSave()} prior to {@code nextCharacter()} and {@link #stateLoad(Key)} as well as {@link #stateDelete(Key)} afterwards, which means that the result of {@code nextCharacter()} cannot be consumed.
	 * 
	 * @return {@code true} if, and only if, {@code nextCharacter()} returns {@code true}, {@code false} otherwise
	 */
	public boolean testNextCharacter() {
		return doTestNext(() -> nextCharacter());
	}
	
	/**
	 * Tests whether {@link #nextCharacter(char)} can be called successfully.
	 * <p>
	 * Returns {@code true} if, and only if, {@code nextCharacter(char)} returns {@code true}, {@code false} otherwise.
	 * <p>
	 * This method calls {@link #stateSave()} prior to {@code nextCharacter(char)} and {@link #stateLoad(Key)} as well as {@link #stateDelete(Key)} afterwards, which means that the result of {@code nextCharacter(char)} cannot be consumed.
	 * 
	 * @param character the {@code char} to read
	 * @return {@code true} if, and only if, {@code nextCharacter(char)} returns {@code true}, {@code false} otherwise
	 */
	public boolean testNextCharacter(final char character) {
		return doTestNext(() -> nextCharacter(character));
	}
	
	/**
	 * Tests whether {@link #nextCharacterAlternation(char[])} can be called successfully.
	 * <p>
	 * Returns {@code true} if, and only if, {@code nextCharacterAlternation(char[])} returns {@code true}, {@code false} otherwise.
	 * <p>
	 * If {@code characters} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@link #stateSave()} prior to {@code nextCharacterAlternation(char[])} and {@link #stateLoad(Key)} as well as {@link #stateDelete(Key)} afterwards, which means that the result of {@code nextCharacterAlternation(char[])} cannot
	 * be consumed.
	 * 
	 * @param characters an array of {@code char} instances to read
	 * @return {@code true} if, and only if, {@code nextCharacterAlternation(char[])} returns {@code true}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code characters} is {@code null}
	 */
	public boolean testNextCharacterAlternation(final char... characters) {
		return doTestNext(() -> nextCharacterAlternation(characters));
	}
	
	/**
	 * Tests whether {@link #nextCharacterConcatenation(char[])} can be called successfully.
	 * <p>
	 * Returns {@code true} if, and only if, {@code nextCharacterConcatenation(char[])} returns {@code true}, {@code false} otherwise.
	 * <p>
	 * If {@code characters} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@link #stateSave()} prior to {@code nextCharacterConcatenation(char[])} and {@link #stateLoad(Key)} as well as {@link #stateDelete(Key)} afterwards, which means that the result of {@code nextCharacterConcatenation(char[])}
	 * cannot be consumed.
	 * 
	 * @param characters an array of {@code char} instances to read
	 * @return {@code true} if, and only if, {@code nextCharacterConcatenation(char[])} returns {@code true}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code characters} is {@code null}
	 */
	public boolean testNextCharacterConcatenation(final char... characters) {
		return doTestNext(() -> nextCharacterConcatenation(characters));
	}
	
	/**
	 * Tests whether {@link #nextRegex(Pattern)} can be called successfully.
	 * <p>
	 * Returns {@code true} if, and only if, {@code nextRegex(Pattern)} returns {@code true}, {@code false} otherwise.
	 * <p>
	 * If {@code pattern} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@link #stateSave()} prior to {@code nextRegex(Pattern)} and {@link #stateLoad(Key)} as well as {@link #stateDelete(Key)} afterwards, which means that the result of {@code nextRegex(Pattern)} cannot be consumed.
	 * 
	 * @param pattern the {@code Pattern} to match against
	 * @return {@code true} if, and only if, {@code nextRegex(Pattern)} returns {@code true}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code pattern} is {@code null}
	 */
	public boolean testNextRegex(final Pattern pattern) {
		return doTestNext(() -> nextRegex(pattern));
	}
	
	/**
	 * Tests whether {@link #nextRegex(Pattern, Consumer)} can be called successfully.
	 * <p>
	 * Returns {@code true} if, and only if, {@code nextRegex(Pattern, Consumer)} returns {@code true}, {@code false} otherwise.
	 * <p>
	 * If either {@code pattern} or {@code matchResultConsumer} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@link #stateSave()} prior to {@code nextRegex(Pattern, Consumer)} and {@link #stateLoad(Key)} as well as {@link #stateDelete(Key)} afterwards, which means that the result of {@code nextRegex(Pattern, Consumer)} cannot be
	 * consumed.
	 * 
	 * @param pattern the {@code Pattern} to match against
	 * @param matchResultConsumer a {@code Consumer} that will consume all {@code MatchResult} instances
	 * @return {@code true} if, and only if, {@code nextRegex(Pattern, Consumer)} returns {@code true}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code pattern} or {@code matchResultConsumer} are {@code null}
	 */
	public boolean testNextRegex(final Pattern pattern, final Consumer<MatchResult> matchResultConsumer) {
		return doTestNext(() -> nextRegex(pattern, matchResultConsumer));
	}
	
	/**
	 * Tests whether {@link #nextRegexAlternation(Pattern[])} can be called successfully.
	 * <p>
	 * Returns {@code true} if, and only if, {@code nextRegexAlternation(Pattern[])} returns {@code true}, {@code false} otherwise.
	 * <p>
	 * If either {@code patterns} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@link #stateSave()} prior to {@code nextRegexAlternation(Pattern[])} and {@link #stateLoad(Key)} as well as {@link #stateDelete(Key)} afterwards, which means that the result of {@code nextRegexAlternation(Pattern[])} cannot be
	 * consumed.
	 * 
	 * @param patterns an array of {@code Pattern} instances to match against
	 * @return {@code true} if, and only if, {@code nextRegexAlternation(Pattern[])} returns {@code true}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code patterns} or any of its elements are {@code null}
	 */
	public boolean testNextRegexAlternation(final Pattern... patterns) {
		return doTestNext(() -> nextRegexAlternation(patterns));
	}
	
	/**
	 * Tests whether {@link #nextRegexConcatenation(Pattern[])} can be called successfully.
	 * <p>
	 * Returns {@code true} if, and only if, {@code nextRegexConcatenation(Pattern[])} returns {@code true}, {@code false} otherwise.
	 * <p>
	 * If either {@code patterns} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@link #stateSave()} prior to {@code nextRegexConcatenation(Pattern[])} and {@link #stateLoad(Key)} as well as {@link #stateDelete(Key)} afterwards, which means that the result of {@code nextRegexConcatenation(Pattern[])}
	 * cannot be consumed.
	 * 
	 * @param patterns an array of {@code Pattern} instances to match against
	 * @return {@code true} if, and only if, {@code nextRegexConcatenation(Pattern[])} returns {@code true}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code patterns} or any of its elements are {@code null}
	 */
	public boolean testNextRegexConcatenation(final Pattern... patterns) {
		return doTestNext(() -> nextRegexConcatenation(patterns));
	}
	
	/**
	 * Tests whether {@link #nextString(String)} can be called successfully.
	 * <p>
	 * Returns {@code true} if, and only if, {@code nextString(String)} returns {@code true}, {@code false} otherwise.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@link #stateSave()} prior to {@code nextString(String)} and {@link #stateLoad(Key)} as well as {@link #stateDelete(Key)} afterwards, which means that the result of {@code nextString(String)} cannot be consumed.
	 * 
	 * @param string the {@code String} to read
	 * @return {@code true} if, and only if, {@code nextString(String)} returns {@code true}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public boolean testNextString(final String string) {
		return doTestNext(() -> nextString(string));
	}
	
	/**
	 * Tests whether {@link #nextStringAlternation(String[])} can be called successfully.
	 * <p>
	 * Returns {@code true} if, and only if, {@code nextStringAlternation(String[])} returns {@code true}, {@code false} otherwise.
	 * <p>
	 * If either {@code strings} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@link #stateSave()} prior to {@code nextStringAlternation(String[])} and {@link #stateLoad(Key)} as well as {@link #stateDelete(Key)} afterwards, which means that the result of {@code nextStringAlternation(String[])} cannot be
	 * consumed.
	 * 
	 * @param strings an array of {@code String} instances to read
	 * @return {@code true} if, and only if, {@code nextStringAlternation(String[])} returns {@code true}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code strings} or any of its elements are {@code null}
	 */
	public boolean testNextStringAlternation(final String... strings) {
		return doTestNext(() -> nextStringAlternation(strings));
	}
	
	/**
	 * Tests whether {@link #nextStringConcatenation(String[])} can be called successfully.
	 * <p>
	 * Returns {@code true} if, and only if, {@code nextStringConcatenation(String[])} returns {@code true}, {@code false} otherwise.
	 * <p>
	 * If either {@code strings} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method calls {@link #stateSave()} prior to {@code nextStringConcatenation(String[])} and {@link #stateLoad(Key)} as well as {@link #stateDelete(Key)} afterwards, which means that the result of {@code nextStringConcatenation(String[])}
	 * cannot be consumed.
	 * 
	 * @param strings an array of {@code String} instances to read
	 * @return {@code true} if, and only if, {@code nextStringConcatenation(String[])} returns {@code true}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code strings} or any of its elements are {@code null}
	 */
	public boolean testNextStringConcatenation(final String... strings) {
		return doTestNext(() -> nextStringConcatenation(strings));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Called by {@link #consume()} when data can be consumed.
	 * <p>
	 * All parameter arguments passed to this method should be valid.
	 * 
	 * @param indexAtBeginningInclusive the index at the beginning of the consumption, which is inclusive
	 * @param indexAtEndExclusive the index at the end of the consumption, which is exclusive
	 */
	@Override
	protected void consume(final int indexAtBeginningInclusive, final int indexAtEndExclusive) {
		consume(indexAtBeginningInclusive, indexAtEndExclusive, new StringBuilder());
	}
	
	/**
	 * Called by {@link #consume(Object)} when data can be consumed.
	 * <p>
	 * All parameter arguments passed to this method should be valid.
	 * 
	 * @param indexAtBeginningInclusive the index at the beginning of the consumption, which is inclusive
	 * @param indexAtEndExclusive the index at the end of the consumption, which is exclusive
	 * @param stringBuilder the {@code StringBuilder} that consumes the data
	 */
	@Override
	protected void consume(final int indexAtBeginningInclusive, final int indexAtEndExclusive, final StringBuilder stringBuilder) {
		stringBuilder.append(consumption(indexAtBeginningInclusive, indexAtEndExclusive));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean doTestNext(final BooleanSupplier booleanSupplier) {
		final Key key = stateSave();
		
		try {
			return booleanSupplier.getAsBoolean();
		} finally {
			stateLoad(key);
			stateDelete(key);
		}
	}
	
	private boolean doHasDataAt(final int index) {
		return doHasDataAt(index, 1);
	}
	
	private boolean doHasDataAt(final int index, final int count) {
		ParameterArguments.requireRange(index, 0, Integer.MAX_VALUE, "index");
		ParameterArguments.requireRange(count, 1, Integer.MAX_VALUE, "count");
		
		return index + count <= this.charBuffer.limit();
	}
	
	private char doGetCharAt(final int index) {
		ParameterArguments.requireRange(index, 0, Integer.MAX_VALUE, "index");
		
		return doHasDataAt(index) ? this.charBuffer.charAt(index) : EOF;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static CharBuffer doDecode(final ByteBuffer byteBuffer) throws CharacterCodingException {
		final Charset charset = Charset.defaultCharset();
		
		final CharsetDecoder charsetDecoder = charset.newDecoder();
		
		final CharBuffer charBuffer = charsetDecoder.decode(byteBuffer);
		
		final String string = Strings.convertUnicodeEscapeSequences(charBuffer.array());
		
		return CharBuffer.wrap(string);
	}
}