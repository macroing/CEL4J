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

import java.util.Objects;
import java.util.Scanner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

final class CLI {
	private static final ScriptEngineManager DEFAULT_SCRIPT_ENGINE_MANAGER = new ScriptEngineManager();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private CLI() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void start(final String extension) {
		try(final Scanner scanner = new Scanner(System.in)) {
			final ScriptEngineManager scriptEngineManager = DEFAULT_SCRIPT_ENGINE_MANAGER;
			
			final ScriptEngine scriptEngine = scriptEngineManager.getEngineByExtension(Objects.requireNonNull(extension, "extension == null"));
			
			while(true) {
				System.out.print("Artifact: ");
				
				final String script = scanner.nextLine();
				
				try {
					final Object object = scriptEngine.eval(script);
					
					System.out.println("Artifact: " + object + System.getProperty("line.separator"));
				} catch(final ScriptException e) {
					System.out.println("Artifact: " + e.toString() + System.getProperty("line.separator"));
				}
			}
		}
	}
}