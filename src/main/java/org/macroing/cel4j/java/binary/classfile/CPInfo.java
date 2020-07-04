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
package org.macroing.cel4j.java.binary.classfile;

import java.io.DataOutput;
import java.io.UncheckedIOException;
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.util.Documentable;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code CPInfo} represents a {@code cp_info} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code cp_info} structure has the following format:
 * <pre>
 * <code>
 * cp_info {
 *     u1 tag;
 *     u1[] info;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class CPInfo implements Documentable, Node {
	private final String name;
	private final int constantPoolEntryCount;
	private final int tag;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code CPInfo} instance.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If either {@code tag} or {@code constantPoolEntryCount} are less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param name the name associated with this {@code CPInfo} instance
	 * @param tag the value for the {@code tag} item associated with this {@code CPInfo} instance
	 * @param constantPoolEntryCount the entry count of the {@code constant_pool} table associated with this {@code CPInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code tag} or {@code constantPoolEntryCount} are less than {@code 0}
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	protected CPInfo(final String name, final int tag, final int constantPoolEntryCount) {
		this.name = Objects.requireNonNull(name, "name == null");
		this.tag = ParameterArguments.requireRange(tag, 0, Integer.MAX_VALUE);
		this.constantPoolEntryCount = ParameterArguments.requireRange(constantPoolEntryCount, 0, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code CPInfo} instance.
	 * 
	 * @return a copy of this {@code CPInfo} instance
	 */
	public abstract CPInfo copy();
	
	/**
	 * Returns the name associated with the this {@code CPInfo} instance.
	 * 
	 * @return the name associated with the this {@code CPInfo} instance
	 */
	public final String getName() {
		return this.name;
	}
	
	/**
	 * Returns the entry count of the {@code constant_pool} table associated with this {@code CPInfo} instance.
	 * 
	 * @return the entry count of the {@code constant_pool} table associated with this {@code CPInfo} instance
	 */
	public final int getConstantPoolEntryCount() {
		return this.constantPoolEntryCount;
	}
	
	/**
	 * Returns the value for the {@code tag} item associated with this {@code CPInfo} instance.
	 * 
	 * @return the value for the {@code tag} item associated with this {@code CPInfo} instance
	 */
	public final int getTag() {
		return this.tag;
	}
	
	/**
	 * Writes this {@code CPInfo} to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an {@code IOException} is caught, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * This method does not close {@code dataOutput}.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an {@code IOException} is caught
	 */
	public abstract void write(final DataOutput dataOutput);
}