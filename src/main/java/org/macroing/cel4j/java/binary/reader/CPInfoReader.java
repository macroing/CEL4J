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

/**
 * A means to read {@link CPInfo}s.
 * <p>
 * It's also used as a Service Provider Interface (SPI) for the same reason.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
/**
 * A {@code CPInfoReader} is used for reading sequences of bytes into instances of the {@link CPInfo} interface.
 * <p>
 * This interface is implemented and used internally by the API. But it is exposed to the public in order to act as a Service Provider Interface (SPI). This allows the user of this API to implement their own {@code CPInfo} and {@code CPInfoReader}
 * implementations. It is even possible to override existing implementations, but it is not recommended.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface CPInfoReader {
	/**
	 * Reads the byte sequence provided by {@code dataInput} into a {@link CPInfo} instance.
	 * <p>
	 * Returns a {@code CPInfo} instance.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code tag} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If the {@code CPInfo} cannot be read, a {@code CPInfoReaderException} will be thrown.
	 * 
	 * @param dataInput the {@code DataInput} to read a {@code CPInfo} instance from
	 * @param tag the tag of the {@code CPInfo}
	 * @return a {@code CPInfo} instance
	 * @throws CPInfoReaderException thrown if, and only if, the {@code CPInfo} cannot be read
	 * @throws IllegalArgumentException thrown if, and only if, {@code tag} is less than {@code 0}
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 */
	CPInfo read(final DataInput dataInput, final int tag);
	
	/**
	 * Returns {@code true} if, and only if, this {@code CPInfoReader} supports reading a {@link CPInfo} given an {@code int} denoting a tag, {@code false} otherwise.
	 * <p>
	 * If {@code tag} is less than {@code 0}, an {@code IllegalArgumentException} may be thrown. But no guarantees can be made.
	 * 
	 * @param tag an {@code int} denoting the tag for the {@code CPInfo} we are eventually about to read
	 * @return {@code true} if, and only if, this {@code CPInfoReader} supports reading a {@code CPInfo} given an {@code int} denoting a tag, {@code false} otherwise
	 * @throws IllegalArgumentException thrown if, and only if, {@code tag} is less than {@code 0}
	 */
	/**
	 * Returns {@code true} if, and only if, this {@code CPInfoReader} supports reading {@link CPInfo} instances with a tag of {@code tag}, {@code false} otherwise.
	 * <p>
	 * If {@code tag} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param tag the tag of the {@code CPInfo} instance to read
	 * @return {@code true} if, and only if, this {@code CPInfoReader} supports reading {@code CPInfo} instances with a tag of {@code tag}, {@code false} otherwise
	 * @throws IllegalArgumentException thrown if, and only if, {@code tag} is less than {@code 0}
	 */
	boolean isSupported(final int tag);
}