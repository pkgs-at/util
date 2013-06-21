/*
 * Copyright (c) 2009-2013, Architector Inc., Japan
 * All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package at.pkgs.util;

public class Exceptor <ExceptionType extends Exception> implements AutoCloseable {

	private ExceptionType throwable;

	public void add(ExceptionType throwable) {
		if (this.throwable == null) {
			this.throwable = throwable;
		}
		else {
			this.throwable.addSuppressed(throwable);
		}
	}

	@SuppressWarnings("unchecked")
	public void add(ExceptionType throwable, boolean unwrap) {
		if (unwrap) {
			this.add((ExceptionType)throwable.getCause());
		}
		else {
			this.add(throwable);
		}
	}

	public void finish() throws ExceptionType {
		if (this.throwable != null) throw this.throwable;
	}

	@Override
	public void close() throws ExceptionType {
		this.finish();
	}

	public static <ExceptionType extends Exception> Exceptor<ExceptionType> create() {
		return new Exceptor<ExceptionType>();
	}

}
