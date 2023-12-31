/**
 * Copyright 2009 - 2024 J&#246;rgen Lundgren
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

import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptException;

/**
 * {@code ArtifactScript} is an implementation of {@code CompiledScript} and is extended by each script class that is evaluated by this Artifact {@code ScriptEngine} implementation.
 * <p>
 * The purpose with this class, is to supply all script writers with a simple API they can use when writing their scripts.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class ArtifactScript extends CompiledScript {
	/**
	 * Constructs a new {@code ArtifactScript} instance.
	 */
	protected ArtifactScript() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Executes {@code script} as script.
	 * <p>
	 * Returns the return value of the script.
	 * <p>
	 * If {@code script} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an error occurs in the script, a {@code ScriptException} will be thrown.
	 * 
	 * @param script the script to execute
	 * @return the return value of the script
	 * @throws NullPointerException thrown if, and only if, {@code script} is {@code null}
	 * @throws ScriptException thrown if, and only if, an error occurs in the script
	 */
	public final Object eval(final String script) throws ScriptException {
		try {
			return getEngine().eval(script);
		} catch(final Exception e) {
			throw new ScriptException(e);
		}
	}
	
	/**
	 * Returns the value assigned to {@code key}, or {@code null} if no value is assigned to {@code key}.
	 * <p>
	 * If {@code key} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code key} is an empty {@code String}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param key a {@code String}
	 * @return the value assigned to {@code key}, or {@code null} if no value is assigned to {@code key}
	 * @throws IllegalArgumentException thrown if, and only if, {@code key} is an empty {@code String}
	 * @throws NullPointerException thrown if, and only if, {@code key} is {@code null}
	 */
	public final Object get(final String key) {
		return getEngine().getBindings(ScriptContext.ENGINE_SCOPE).get(key);
	}
	
	/**
	 * Sets the value {@code value} for the key {@code key}.
	 * <p>
	 * If {@code key} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code key} is an empty {@code String}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param key the key
	 * @param value the value
	 * @throws IllegalArgumentException thrown if, and only if, {@code key} is an empty {@code String}
	 * @throws NullPointerException thrown if, and only if, {@code key} is {@code null}
	 */
	public final void set(final String key, final Object value) {
		getEngine().getBindings(ScriptContext.ENGINE_SCOPE).put(key, value);
	}
}