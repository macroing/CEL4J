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
package org.macroing.cel4j.java.decompiler;

import java.util.Objects;

enum JModifier {
	ABSTRACT("abstract"),
	DEFAULT("default"),
	FINAL("final"),
	NATIVE("native"),
	PRIVATE("private"),
	PROTECTED("protected"),
	PUBLIC("public"),
	STATIC("static"),
	STRICT_F_P("strictfp"),
	SYNCHRONIZED("synchronized"),
	TRANSIENT("transient"),
	VOLATILE("volatile");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String keyword;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private JModifier(final String keyword) {
		this.keyword = Objects.requireNonNull(keyword, "keyword == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getKeyword() {
		return this.keyword;
	}
}