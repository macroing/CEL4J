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
package org.macroing.cel4j.html.model;

import java.util.List;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.util.Documentable;

/**
 * A {@code Content} represents the content contained in a {@link ContentElement} instance.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface Content<T extends Element> extends Documentable, Node {
	/**
	 * Returns the {@link Display} associated with this {@code Content} instance.
	 * 
	 * @return the {@code Display} associated with this {@code Content} instance
	 */
	Display getDisplay();
	
	/**
	 * Returns a {@code List} with all {@link Element} instances currently added to this {@code Content} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code Content} instance.
	 * 
	 * @return a {@code List} with all {@code Element} instances currently added to this {@code Content} instance
	 */
	List<T> getElements();
}