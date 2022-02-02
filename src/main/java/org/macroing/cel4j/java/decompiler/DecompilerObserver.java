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
package org.macroing.cel4j.java.decompiler;

/**
 * A {@code DecompilerObserver} is an observer of decompilation progress.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface DecompilerObserver {
	/**
	 * Called by a {@link Decompiler} instance when it can report progress.
	 * 
	 * @param progress the {@code String} with the progress information
	 */
	void onProgress(final String progress);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code DecompilerObserver} that will print the progress to standard output.
	 * <p>
	 * The progress will be printed by calling {@code System.out.println(String)}.
	 * 
	 * @return a {@code DecompilerObserver} that will print the progress to standard output
	 */
	static DecompilerObserver print() {
		return progress -> System.out.println(progress);
	}
}