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
package org.macroing.cel4j.java.binary.reader;

import java.io.DataInput;

import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.node.NodeFormatException;

/**
 * A means to read {@link CPInfo}s.
 * <p>
 * It's also used as a Service Provider Interface (SPI) for the same reason.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface CPInfoReader {
	/**
	 * Returns a {@link CPInfo} based on the data provided by {@code dataInput}.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
	 * <p>
	 * If {@code tag} is less than {@code 0}, an {@code IllegalArgumentException} may be thrown. But no guarantees can be made.
	 * <p>
	 * If the {@code CPInfo} cannot be read, a {@code NodeFormatException} should be thrown.
	 * 
	 * @param dataInput the {@code DataInput} to read the data from
	 * @param tag an {@code int} denoting the tag for the {@code CPInfo} we are about to read
	 * @return a {@code CPInfo} based on the data provided by {@code dataInput}
	 * @throws IllegalArgumentException thrown if, and only if, {@code tag} is less than {@code 0}
	 * @throws NodeFormatException thrown if, and only if, the {@code CPInfo} cannot be read
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 */
	CPInfo readCPInfo(final DataInput dataInput, final int tag);
	
	/**
	 * Returns {@code true} if, and only if, this {@code CPInfoReader} supports reading a {@link CPInfo} given an {@code int} denoting a tag, {@code false} otherwise.
	 * <p>
	 * If {@code tag} is less than {@code 0}, an {@code IllegalArgumentException} may be thrown. But no guarantees can be made.
	 * 
	 * @param tag an {@code int} denoting the tag for the {@code CPInfo} we are eventually about to read
	 * @return {@code true} if, and only if, this {@code CPInfoReader} supports reading a {@code CPInfo} given an {@code int} denoting a tag, {@code false} otherwise
	 * @throws IllegalArgumentException thrown if, and only if, {@code tag} is less than {@code 0}
	 */
	boolean isCPInfoReadingSupportedFor(final int tag);
}