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
package org.macroing.cel4j.java.source;

import org.macroing.cel4j.node.Node;

/**
 * A {@code JavaNode} is a {@link Node} that provides a method to retrieve Java source code.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface JavaNode extends Node {
	/**
	 * Returns the source code of this {@code JavaNode} instance.
	 * 
	 * @return the source code of this {@code JavaNode} instance
	 */
	String getSourceCode();
}