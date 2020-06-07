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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.lang.reflect.Field;//TODO: Add Javadocs!

import org.macroing.cel4j.node.Node;

//TODO: Add Javadocs!
public interface VerificationTypeInfo extends Node {
//	TODO: Add Javadocs!
	int ITEM_DOUBLE = 3;
	
//	TODO: Add Javadocs!
	int ITEM_FLOAT = 2;
	
//	TODO: Add Javadocs!
	int ITEM_INTEGER = 1;
	
//	TODO: Add Javadocs!
	int ITEM_LONG = 4;
	
//	TODO: Add Javadocs!
	int ITEM_NULL = 5;
	
//	TODO: Add Javadocs!
	int ITEM_OBJECT = 7;
	
//	TODO: Add Javadocs!
	int ITEM_TOP = 0;
	
//	TODO: Add Javadocs!
	int ITEM_UNINITIALIZED = 8;
	
//	TODO: Add Javadocs!
	int ITEM_UNINITIALIZED_THIS = 6;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	VerificationTypeInfo copy();
	
//	TODO: Add Javadocs!
	int getLength();
	
//	TODO: Add Javadocs!
	int getTag();
	
//	TODO: Add Javadocs!
	void write(final DataOutput dataOutput);
}