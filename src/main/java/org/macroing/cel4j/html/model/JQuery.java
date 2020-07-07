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
package org.macroing.cel4j.html.model;

/**
 * A class that consists exclusively of static methods that returns {@link Element} instances related to JQuery.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class JQuery {
	private JQuery() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@link Script} instance that points to JQuery.
	 * <p>
	 * The current implementation of this method points to JQuery version 3.4.1, minified.
	 * 
	 * @return a {@code Script} instance that points to JQuery
	 */
	public static Script createScript() {
		final
		Script script = new Script();
		script.getAttributeCrossOrigin().setValue("anonymous");
		script.getAttributeSrc().setValue("https://code.jquery.com/jquery-3.4.1.min.js");
		
		return script;
	}
}