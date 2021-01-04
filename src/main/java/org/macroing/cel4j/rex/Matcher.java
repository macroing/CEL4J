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
package org.macroing.cel4j.rex;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.util.Documentable;

/**
 * A {@code Matcher} matches a {@code String} and produces a {@link MatchResult} with the result of the match.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface Matcher extends Documentable, Node {
	/**
	 * Matches {@code input}.
	 * <p>
	 * Returns a {@link MatchResult} with the result of the match.
	 * <p>
	 * If {@code input} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * matcher.match(input, 0);
	 * }
	 * </pre>
	 * 
	 * @param input the {@code String} to match
	 * @return a {@code MatchResult} with the result of the match
	 * @throws NullPointerException thrown if, and only if, {@code input} is {@code null}
	 */
	MatchResult match(final String input);
	
	/**
	 * Matches {@code input}.
	 * <p>
	 * Returns a {@link MatchResult} with the result of the match.
	 * <p>
	 * If {@code input} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than or equal to {@code input.length()}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param input the {@code String} to match
	 * @param index the index in {@code input} to match from
	 * @return a {@code MatchResult} with the result of the match
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than or equal to {@code input.length()}
	 * @throws NullPointerException thrown if, and only if, {@code input} is {@code null}
	 */
	MatchResult match(final String input, final int index);
	
	/**
	 * Returns the source code associated with this {@code Matcher} instance.
	 * 
	 * @return the source code associated with this {@code Matcher} instance
	 */
	String getSourceCode();
}