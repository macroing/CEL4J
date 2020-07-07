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
 * A class that consists exclusively of static methods that returns {@link Element} instances related to Font Awesome.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class FontAwesome {
	private FontAwesome() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@link Script} instance that points to Font Awesome.
	 * 
	 * @return a {@code Script} instance that points to Font Awesome
	 */
	public static Script createScript() {
		final
		Script script = new Script();
		script.getAttributeSrc().setValue("https://kit.fontawesome.com/f3f524ba52.js");
		
		return script;
	}
}