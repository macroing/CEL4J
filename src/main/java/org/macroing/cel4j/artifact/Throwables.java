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

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Objects;

final class Throwables {
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Throwables() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String getStackTrace(final Throwable throwable) {
		return getStackTrace(throwable, Thread.currentThread());
	}
	
	public static String getStackTrace(final Throwable throwable, final Thread thread) {
		final StringBuilder stringBuilder = new StringBuilder(thread + LINE_SEPARATOR);
		
		Throwable currentThrowable = throwable;
		
		do {
			final Class<?> clazz = currentThrowable.getClass();
			
			final String className = clazz.getName();
			final String message = currentThrowable.getMessage() != null ? ": " + currentThrowable.getMessage() : "";
			
			stringBuilder.append(className);
			stringBuilder.append(" ");
			stringBuilder.append(message);
			stringBuilder.append(LINE_SEPARATOR);
			
			for(final StackTraceElement stackTraceElement : currentThrowable.getStackTrace()) {
				final boolean hasFileName = stackTraceElement.getFileName() != null;
				final boolean hasLineNumber = stackTraceElement.getLineNumber() >= 0;
				
				stringBuilder.append("\t");
				stringBuilder.append("at");
				stringBuilder.append(stackTraceElement.getClassName());
				stringBuilder.append(".");
				stringBuilder.append(stackTraceElement.getMethodName());
				stringBuilder.append("(");
				stringBuilder.append(hasFileName ? stackTraceElement.getFileName() : "");
				stringBuilder.append(hasFileName && hasLineNumber ? ":" : "");
				stringBuilder.append(hasLineNumber ? Integer.toString(stackTraceElement.getLineNumber()) : "");
				stringBuilder.append(!hasFileName && !hasLineNumber ? "Unknown Source" : "");
				stringBuilder.append(")");
				stringBuilder.append(LINE_SEPARATOR);
			}
		} while((currentThrowable = currentThrowable.getCause()) != null);
		
		return stringBuilder.toString();
	}
	
	public static void handleThrowable(final Throwable throwable) {
		Objects.requireNonNull(throwable, "throwable == null");
		
		final UncaughtExceptionHandler uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		
		if(uncaughtExceptionHandler != null) {
			uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), throwable);
		} else {
			throwable.printStackTrace();
		}
		
		/*
		 * LinkageError:
		 * This may happen. But only if we got things like JNI and other linking problems. We should fix them, so why continue?
		 * 
		 * ThreadDeath:
		 * This shouldn't happen. ThreadDeath is only thrown by Thread.stop(), which is deprecated, and thus never used by us. We don't use deprecated things. But someone else might, in which case it's important to handle it anyway.
		 * 
		 * VirtualMachineError:
		 * This may happen. If we are out of memory (OutOfMemoryError is just one kind of VirtualMachineError), we probably have a problem in our code. Then why continue?
		 */
		if(throwable instanceof LinkageError) {
			System.exit(1);
		} else if(throwable instanceof ThreadDeath) {
			throw ThreadDeath.class.cast(throwable);
		} else if(throwable instanceof VirtualMachineError) {
			System.exit(1);
		}
	}
}