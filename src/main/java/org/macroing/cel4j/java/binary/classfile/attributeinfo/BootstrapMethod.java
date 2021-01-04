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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code BootstrapMethod} represents an entry in the {@code bootstrap_methods} table item of the {@code BootstrapMethods_attribute} structure.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * Each entry has the following format:
 * <pre>
 * <code>
 * {
 *     u2 bootstrap_method_ref;
 *     u2 num_bootstrap_arguments;
 *     u2[num_bootstrap_arguments] bootstrap_arguments;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class BootstrapMethod implements Node {
	private final List<Integer> bootstrapArguments;
	private int bootstrapMethodRef;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code BootstrapMethod} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new BootstrapMethod(1);
	 * }
	 * </pre>
	 */
	public BootstrapMethod() {
		this(1);
	}
	
	/**
	 * Constructs a new {@code BootstrapMethod} instance that is a copy of {@code bootstrapMethod}.
	 * <p>
	 * If {@code bootstrapMethod} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param bootstrapMethod the {@code BootstrapMethod} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code bootstrapMethod} is {@code null}
	 */
	public BootstrapMethod(final BootstrapMethod bootstrapMethod) {
		this.bootstrapArguments = bootstrapMethod.bootstrapArguments.stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Constructs a new {@code BootstrapMethod} instance.
	 * <p>
	 * If {@code bootstrapMethodRef} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param bootstrapMethodRef the value for the {@code bootstrap_method_ref} item associated with this {@code BootstrapMethod} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code bootstrapMethodRef} is less than {@code 1}
	 */
	public BootstrapMethod(final int bootstrapMethodRef) {
		this.bootstrapArguments = new ArrayList<>();
		this.bootstrapMethodRef = ParameterArguments.requireRange(bootstrapMethodRef, 1, Integer.MAX_VALUE, "bootstrapMethodRef");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code BootstrapMethod} instance.
	 * 
	 * @return a copy of this {@code BootstrapMethod} instance
	 */
	public BootstrapMethod copy() {
		return new BootstrapMethod(this);
	}
	
	/**
	 * Returns a {@code List} that represents the {@code bootstrap_arguments} table item associated with this {@code BootstrapMethod} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code BootstrapMethod} instance.
	 * 
	 * @return a {@code List} that represents the {@code bootstrap_arguments} table item associated with this {@code BootstrapMethod} instance
	 */
	public List<Integer> getBootstrapArguments() {
		return new ArrayList<>(this.bootstrapArguments);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code BootstrapMethod} instance.
	 * 
	 * @return a {@code String} representation of this {@code BootstrapMethod} instance
	 */
	@Override
	public String toString() {
		return String.format("new BootstrapMethod(%)", Integer.toString(getBootstrapMethodRef()));
	}
	
	/**
	 * Compares {@code object} to this {@code BootstrapMethod} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code BootstrapMethod}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code BootstrapMethod} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code BootstrapMethod}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof BootstrapMethod)) {
			return false;
		} else if(getBootstrapMethodRef() != BootstrapMethod.class.cast(object).getBootstrapMethodRef()) {
			return false;
		} else if(getNumBootstrapArguments() != BootstrapMethod.class.cast(object).getNumBootstrapArguments()) {
			return false;
		} else if(!Objects.equals(this.bootstrapArguments, BootstrapMethod.class.cast(object).bootstrapArguments)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value for the {@code bootstrap_method_ref} item associated with this {@code BootstrapMethod} instance.
	 * 
	 * @return the value for the {@code bootstrap_method_ref} item associated with this {@code BootstrapMethod} instance
	 */
	public int getBootstrapMethodRef() {
		return this.bootstrapMethodRef;
	}
	
	/**
	 * Returns the length of this {@code BootstrapMethod} instance.
	 * 
	 * @return the length of this {@code BootstrapMethod} instance
	 */
	public int getLength() {
		return 4 + getNumBootstrapArguments() * 2;
	}
	
	/**
	 * Returns the value for the {@code num_bootstrap_arguments} item associated with this {@code BootstrapMethod} instance.
	 * 
	 * @return the value for the {@code num_bootstrap_arguments} item associated with this {@code BootstrapMethod} instance
	 */
	public int getNumBootstrapArguments() {
		return this.bootstrapArguments.size();
	}
	
	/**
	 * Returns a hash code for this {@code BootstrapMethod} instance.
	 * 
	 * @return a hash code for this {@code BootstrapMethod} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getBootstrapMethodRef()), Integer.valueOf(getNumBootstrapArguments()), this.bootstrapArguments);
	}
	
	/**
	 * Adds {@code bootstrapArgument} to the {@code bootstrap_arguments} table item associated with this {@code BootstrapMethod} instance.
	 * <p>
	 * If {@code bootstrapArgument} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param bootstrapArgument the bootstrap argument to add
	 * @throws IllegalArgumentException thrown if, and only if, {@code bootstrapArgument} is less than {@code 1}
	 */
	public void addBootstrapArgument(final int bootstrapArgument) {
		this.bootstrapArguments.add(Integer.valueOf(ParameterArguments.requireRange(bootstrapArgument, 1, Integer.MAX_VALUE, "bootstrapArgument")));
	}
	
	/**
	 * Removes {@code bootstrapArgument} from the {@code bootstrap_arguments} table item associated with this {@code BootstrapMethod} instance.
	 * <p>
	 * If {@code bootstrapArgument} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param bootstrapArgument the bootstrap argument to remove
	 * @throws IllegalArgumentException thrown if, and only if, {@code bootstrapArgument} is less than {@code 1}
	 */
	public void removeBootstrapArgument(final int bootstrapArgument) {
		this.bootstrapArguments.remove(Integer.valueOf(ParameterArguments.requireRange(bootstrapArgument, 1, Integer.MAX_VALUE, "bootstrapArgument")));
	}
	
	/**
	 * Sets {@code bootstrapMethodRef} as the value for the {@code bootstrap_method_ref} item associated with this {@code BootstrapMethod} instance.
	 * <p>
	 * If {@code bootstrapMethodRef} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param bootstrapMethodRef the value for the {@code bootstrap_method_ref} item associated with this {@code BootstrapMethod} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code bootstrapMethodRef} is less than {@code 1}
	 */
	public void setBootstrapMethodRef(final int bootstrapMethodRef) {
		this.bootstrapMethodRef = ParameterArguments.requireRange(bootstrapMethodRef, 1, Integer.MAX_VALUE, "bootstrapMethodRef");
	}
	
	/**
	 * Writes this {@code BootstrapMethod} to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is an {@code OutputStream} (or any other type of stream), this method will not close it.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O-error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O-error occurs
	 */
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(getBootstrapMethodRef());
			dataOutput.writeShort(getNumBootstrapArguments());
			
			for(final int bootstrapArgument : this.bootstrapArguments) {
				dataOutput.writeShort(bootstrapArgument);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}