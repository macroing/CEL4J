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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.macroing.cel4j.util.Maps;
import org.macroing.cel4j.util.ParameterArguments;
import org.macroing.cel4j.util.Strings;

final class TextScanner extends Scanner {
	public static final char EOF = (char)(0);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final String REGEX_ANY_CHARACTER_ZERO_TO_ONE_HUNDRED_TIMES = ".{0,100}";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final CharDataBuffer charDataBuffer;
	private final Map<Pattern, Long> regexMatchingTimes;
	private boolean isRecordingRegexMatchingTime;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private TextScanner(final CharDataBuffer charDataBuffer) {
		super(charDataBuffer);
		
		this.charDataBuffer = charDataBuffer;
		this.regexMatchingTimes = new HashMap<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String peekString(final int length) {
		return peekString(0, ParameterArguments.requireRange(length, 0, Integer.MAX_VALUE));
	}
	
	public String peekString(final int relativeIndex, final int length) {
		final int index = ParameterArguments.requireRange(getIndexAtEndOfConsumption() + relativeIndex, 0, Integer.MAX_VALUE);
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		for(int i = index; i < index + ParameterArguments.requireRange(length, 0, Integer.MAX_VALUE) && i < this.charDataBuffer.size(); i++) {
			stringBuilder.append(this.charDataBuffer.getChar(i));
		}
		
		return stringBuilder.toString();
	}
	
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		
		for(int i = getIndexAtEndOfConsumption() - getIndexAtBeginningOfConsumption(); i > 0; i--) {
			stringBuilder.append("'");
			stringBuilder.append(peekCharacter(-i));
			stringBuilder.append("'");
			stringBuilder.append(i > 1 ? "," : "");
		}
		
		int repetition = -1;
		
		stringBuilder.append("]");
		
		for(int i = 0; i < this.charDataBuffer.size(); i++) {
			final char character = peekCharacter(i);
			
			if(character == EOF) {
				stringBuilder.append(",");
				stringBuilder.append("EOF");
			} else {
				stringBuilder.append(",");
				stringBuilder.append("'");
				stringBuilder.append(character);
				stringBuilder.append("'");
			}
			
			if(repetition == -1) {
				repetition = stringBuilder.length() - 2;
			}
			
			if(character == EOF) {
				break;
			}
		}
		
		stringBuilder.append("\n");
		stringBuilder.append(Strings.repeat(" ", repetition));
		stringBuilder.append("^");
		
		return stringBuilder.toString();
	}
	
	@Override
	public String toString(final int absoluteIndex) {
		return Character.toString(this.charDataBuffer.getChar(ParameterArguments.requireRange(absoluteIndex, 0, this.charDataBuffer.size() - 1)));
	}
	
	public boolean isRecordingRegexMatchingTime() {
		return this.isRecordingRegexMatchingTime;
	}
	
	@Override
	public boolean next() {
		return nextCharacter();
	}
	
	public boolean nextCharacter() {
		boolean isSuccessful = false;
		
		if(getIndexAtEndOfConsumption() < this.charDataBuffer.size()) {
			isSuccessful = true;
			
			setIndexAtEndOfConsumption(getIndexAtEndOfConsumption() + 1);
		}
		
		return isSuccessful;
	}
	
	public boolean nextCharacter(final char character) {
		boolean isSuccessful = false;
		
		if(getIndexAtEndOfConsumption() < this.charDataBuffer.size() && this.charDataBuffer.getChar(getIndexAtEndOfConsumption()) == character) {
			isSuccessful = true;
			
			setIndexAtEndOfConsumption(getIndexAtEndOfConsumption() + 1);
		}
		
		return isSuccessful;
	}
	
	public boolean nextCharacterAlternation(final char... characters) {
		boolean isSuccessful = false;
		
		final int key = mark();
		
		for(final char character : characters) {
			if(nextCharacter(character)) {
				isSuccessful = true;
				
				break;
			}
		}
		
		if(isSuccessful) {
			unmark(key);
		} else {
			rewind(key);
		}
		
		return isSuccessful;
	}
	
	public boolean nextCharacterConcatenation(final char... characters) {
		boolean isSuccessful = true;
		
		final int key = mark();
		
		for(final char character : characters) {
			if(!nextCharacter(character)) {
				isSuccessful = false;
				
				break;
			}
		}
		
		if(isSuccessful) {
			unmark(key);
		} else {
			rewind(key);
		}
		
		return isSuccessful;
	}
	
	public boolean nextRegex(final Pattern pattern) {
		return nextRegex(pattern, matchResult -> {});
	}
	
	public boolean nextRegex(final Pattern pattern, final Consumer<MatchResult> matchResultConsumer) {
		boolean isSuccessful = false;
		
		final long startTime = System.nanoTime();
		
		final
		Matcher matcher = pattern.matcher(this.charDataBuffer.toString());
		matcher.region(getIndexAtEndOfConsumption(), this.charDataBuffer.size());
		
		if(matcher.lookingAt()) {
			final MatchResult matchResult = matcher.toMatchResult();
			
			matchResultConsumer.accept(matchResult);
			
			setIndexAtEndOfConsumption(getIndexAtEndOfConsumption() + matcher.group().length());
			
			isSuccessful = true;
		}
		
		final long stopTime = System.nanoTime();
		final long elapsedTime = stopTime - startTime;
		
		if(this.isRecordingRegexMatchingTime) {
			this.regexMatchingTimes.merge(pattern, Long.valueOf(elapsedTime), (a, b) -> Long.valueOf(a.longValue() + b.longValue() < 0L ? Long.MAX_VALUE : a.longValue() + b.longValue()));
		}
		
		return isSuccessful;
	}
	
	public boolean nextRegex(final String regex) {
		return nextRegex(Pattern.compile(Objects.requireNonNull(regex, "regex == null"), Pattern.DOTALL));
	}
	
	public boolean nextRegexAlternation(final Pattern... patterns) {
		boolean isSuccessful = false;
		
		final int key = mark();
		
		for(final Pattern pattern : patterns) {
			if(nextRegex(pattern)) {
				isSuccessful = true;
				
				break;
			}
		}
		
		if(isSuccessful) {
			unmark(key);
		} else {
			rewind(key);
		}
		
		return isSuccessful;
	}
	
	public boolean nextRegexAlternation(final String... regexes) {
		boolean isSuccessful = false;
		
		final int key = mark();
		
		for(final String regex : regexes) {
			if(nextRegex(regex)) {
				isSuccessful = true;
				
				break;
			}
		}
		
		if(isSuccessful) {
			unmark(key);
		} else {
			rewind(key);
		}
		
		return isSuccessful;
	}
	
	public boolean nextRegexConcatenation(final Pattern... patterns) {
		boolean isSuccessful = true;
		
		final int key = mark();
		
		for(final Pattern pattern : patterns) {
			if(!nextRegex(pattern)) {
				isSuccessful = false;
				
				break;
			}
		}
		
		if(isSuccessful) {
			unmark(key);
		} else {
			rewind(key);
		}
		
		return isSuccessful;
	}
	
	public boolean nextRegexConcatenation(final String... regexes) {
		boolean isSuccessful = true;
		
		final int key = mark();
		
		for(final String regex : regexes) {
			if(!nextRegex(regex)) {
				isSuccessful = false;
				
				break;
			}
		}
		
		if(isSuccessful) {
			unmark(key);
		} else {
			rewind(key);
		}
		
		return isSuccessful;
	}
	
	public boolean nextString(final String string) {
		boolean isSuccessful = false;
		
		int count = 0;
		
		final int indexAtEndOfConsumption = getIndexAtEndOfConsumption();
		final int index = indexAtEndOfConsumption + string.length() - 1;
		
		if(index < this.charDataBuffer.size()) {
			for(final char character : string.toCharArray()) {
				if(this.charDataBuffer.getChar(indexAtEndOfConsumption + count) == character) {
					count++;
				} else {
					break;
				}
			}
			
			isSuccessful = count == string.length();
			
			if(isSuccessful) {
				setIndexAtEndOfConsumption(indexAtEndOfConsumption + count);
			}
		}
		
		return isSuccessful;
	}
	
	public boolean nextStringAlternation(final String... strings) {
		boolean isSuccessful = false;
		
		final int key = mark();
		
		for(final String string : strings) {
			if(nextString(string)) {
				isSuccessful = true;
				
				break;
			}
		}
		
		if(isSuccessful) {
			unmark(key);
		} else {
			rewind(key);
		}
		
		return isSuccessful;
	}
	
	public boolean nextStringConcatenation(final String... strings) {
		boolean isSuccessful = true;
		
		final int key = mark();
		
		for(final String string : strings) {
			if(!nextString(string)) {
				isSuccessful = false;
				
				break;
			}
		}
		
		if(isSuccessful) {
			unmark(key);
		} else {
			rewind(key);
		}
		
		return isSuccessful;
	}
	
	public boolean testNextCharacter() {
		return doTestNext(() -> nextCharacter());
	}
	
	public boolean testNextCharacter(final char character) {
		return doTestNext(() -> nextCharacter(character));
	}
	
	public boolean testNextCharacterAlternation(final char... characters) {
		return doTestNext(() -> nextCharacterAlternation(characters));
	}
	
	public boolean testNextCharacterConcatenation(final char... characters) {
		return doTestNext(() -> nextCharacterConcatenation(characters));
	}
	
	public boolean testNextRegex(final Pattern pattern) {
		return doTestNext(() -> nextRegex(pattern));
	}
	
	public boolean testNextRegex(final Pattern pattern, final Consumer<MatchResult> matchResultConsumer) {
		return doTestNext(() -> nextRegex(pattern, matchResultConsumer));
	}
	
	public boolean testNextRegex(final String regex) {
		return doTestNext(() -> nextRegex(regex));
	}
	
	public boolean testNextRegexAlternation(final Pattern... patterns) {
		return doTestNext(() -> nextRegexAlternation(patterns));
	}
	
	public boolean testNextRegexAlternation(final String... regexes) {
		return doTestNext(() -> nextRegexAlternation(regexes));
	}
	
	public boolean testNextRegexConcatenation(final Pattern... patterns) {
		return doTestNext(() -> nextRegexConcatenation(patterns));
	}
	
	public boolean testNextRegexConcatenation(final String... regexes) {
		return doTestNext(() -> nextRegexConcatenation(regexes));
	}
	
	public boolean testNextString(final String string) {
		return doTestNext(() -> nextString(string));
	}
	
	public boolean testNextStringAlternation(final String... strings) {
		return doTestNext(() -> nextStringAlternation(strings));
	}
	
	public boolean testNextStringConcatenation(final String... strings) {
		return doTestNext(() -> nextStringConcatenation(strings));
	}
	
	public char peekCharacter() {
		return peekCharacter(0);
	}
	
	public char peekCharacter(final int relativeIndex) {
		final int index = ParameterArguments.requireRange(getIndexAtEndOfConsumption() + relativeIndex, 0, Integer.MAX_VALUE);
		
		char character = EOF;
		
		if(index < this.charDataBuffer.size()) {
			character = this.charDataBuffer.getChar(index);
		}
		
		return character;
	}
	
	@Override
	public void error() {
		final StringBuilder stringBuilder = new StringBuilder();
		
		nextRegex(REGEX_ANY_CHARACTER_ZERO_TO_ONE_HUNDRED_TIMES);
		consume(stringBuilder);
		error("Unable to parse: " + stringBuilder);
	}
	
	@Override
	public void error(final String message) {
		throw new IllegalArgumentException(Objects.requireNonNull(message, "message == null"));
	}
	
	public void printRegexMatchingTimes() {
		for(final Entry<Pattern, Long> entry : Maps.sortByValue(new LinkedHashMap<>(this.regexMatchingTimes)).entrySet()) {
			System.out.printf("%-10s%s%n", Long.toString(TimeUnit.NANOSECONDS.toMillis(entry.getValue().longValue())), entry.getKey());
		}
	}
	
	public void setRecordingRegexMatchingTime(final boolean isRecordingRegexMatchingTime) {
		this.isRecordingRegexMatchingTime = isRecordingRegexMatchingTime;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static TextScanner newInstance(final File file) {
		return new TextScanner(CharDataBuffer.from(file));
	}
	
	public static TextScanner newInstance(final String string) {
		return new TextScanner(CharDataBuffer.from(string));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private <T> boolean doTestNext(final Predicate predicate) {
		final int key = mark();
		
		try {
			return predicate.test();
		} finally {
			rewind(key);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static interface Predicate {
		boolean test();
	}
}