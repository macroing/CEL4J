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
import java.lang.reflect.Field;//TODO: Update Javadocs!
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code CPInfo} denotes a cp_info structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class CPInfo implements Node {
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
	 * @param name the name of the cp_info structure
	 * @param tag the tag of the cp_info structure
	 * @param constantPoolEntryCount the entry count of the constant_pool table
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
	 * Returns the name of the this {@code CPInfo} instance.
	 * 
	 * @return the name of the this {@code CPInfo} instance
	 */
	public final String getName() {
		return this.name;
	}
	
	/**
	 * Returns the entry count of the constant_pool table.
	 * 
	 * @return the entry count of the constant_pool table
	 */
	public final int getConstantPoolEntryCount() {
		return this.constantPoolEntryCount;
	}
	
	/**
	 * Returns the tag of this {@code CPInfo} instance.
	 * 
	 * @return the tag of this {@code CPInfo} instance
	 */
	public final int getTag() {
		return this.tag;
	}
	
	/**
	 * Writes this {@code CPInfo} to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is an {@code OutputStream} (or any other type of stream), this method will not close it.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * If an I/O-error occurs, an {@code UncheckedIOException} may be thrown. But no guarantees can be made.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O-error occurs
	 */
	public abstract void write(final DataOutput dataOutput);
	
	/**
	 * Writes this {@code CPInfo} to {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * 
	 * @param document the {@link Document} to write to
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	public abstract void write(final Document document);
}