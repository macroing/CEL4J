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
package org.macroing.cel4j.artifact;

/**
 * This class is the entry-point for the CLI- and GUI-program.
 * <p>
 * By default it will start with the CLI-program, using the Artifact scripting language for evaluating the source code. This means it's like setting the {@code -e} flag to either {@code java} or {@code .java}.
 * <p>
 * The following is a walk-through of all flags currently supported.
 * <ul>
 * <li>
 * {@code -e} - Use this flag to set the filename extension of the scripting language you want to use. If not set, Artifact will be used. That is, the {@code -e} flag is set to {@code java}. This flag requires one parameter argument. So, to use it, here
 * is an example {@code -e java}.
 * </li>
 * <li>
 * {@code -g} - Use this flag to start the GUI-program. By default the CLI-program will be used. This flag takes no parameter arguments. So, to use it, simply write {@code -g}.
 * </li>
 * </ul>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Main {
	private Main() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Starts this program.
	 * 
	 * @param args the parameter arguments supplied
	 */
	public static void main(final String[] args) {
		boolean isUsingGUI = false;
		
		String extension = "java";
		
		if(args != null) {
			for(int i = 0; i < args.length; i++) {
				switch(args[i]) {
					case "-e":
						if(i + 1 < args.length) {
							extension = args[++i];
						}
						
						break;
					case "-g":
						isUsingGUI = true;
						
						break;
					default:
						break;
				}
			}
		}
		
		if(isUsingGUI) {
			GUI.start(extension);
		} else {
			CLI.start(extension);
		}
	}
}