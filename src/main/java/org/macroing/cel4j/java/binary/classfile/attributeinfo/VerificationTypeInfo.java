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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.UncheckedIOException;

import org.macroing.cel4j.node.Node;

/**
 * A {@code VerificationTypeInfo} represents a {@code verification_type_info} {@code union} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * A {@code verification_type_info} {@code union} is part of the structures {@code append_frame}, {@code full_frame}, {@code same_locals_1_stack_item_frame} and {@code same_locals_1_stack_item_frame_extended}. The structures mentioned and some others,
 * are part of the {@code StackMapTable_attribute} structure.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface VerificationTypeInfo extends Node {
	/**
	 * The value of the {@code tag} item called {@code ITEM_Double}.
	 */
	int ITEM_DOUBLE = 3;
	
	/**
	 * The value of the {@code tag} item called {@code ITEM_Float}.
	 */
	int ITEM_FLOAT = 2;
	
	/**
	 * The value of the {@code tag} item called {@code ITEM_Integer}.
	 */
	int ITEM_INTEGER = 1;
	
	/**
	 * The value of the {@code tag} item called {@code ITEM_Long}.
	 */
	int ITEM_LONG = 4;
	
	/**
	 * The value of the {@code tag} item called {@code ITEM_Null}.
	 */
	int ITEM_NULL = 5;
	
	/**
	 * The value of the {@code tag} item called {@code ITEM_Object}.
	 */
	int ITEM_OBJECT = 7;
	
	/**
	 * The value of the {@code tag} item called {@code ITEM_Top}.
	 */
	int ITEM_TOP = 0;
	
	/**
	 * The value of the {@code tag} item called {@code ITEM_Uninitialized}.
	 */
	int ITEM_UNINITIALIZED = 8;
	
	/**
	 * The value of the {@code tag} item called {@code ITEM_UninitializedThis}.
	 */
	int ITEM_UNINITIALIZED_THIS = 6;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code VerificationTypeInfo} instance.
	 * 
	 * @return a copy of this {@code VerificationTypeInfo} instance
	 */
	VerificationTypeInfo copy();
	
	/**
	 * Returns the length of this {@code VerificationTypeInfo} instance.
	 * 
	 * @return the length of this {@code VerificationTypeInfo} instance
	 */
	int getLength();
	
	/**
	 * Returns the value of the {@code tag} item associated with this {@code VerificationTypeInfo} instance.
	 * 
	 * @return the value of the {@code tag} item associated with this {@code VerificationTypeInfo} instance
	 */
	int getTag();
	
	/**
	 * Writes this {@code VerificationTypeInfo} to {@code dataOutput}.
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
	void write(final DataOutput dataOutput);
}