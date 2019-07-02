/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */

package com.github.xiaozhukk.spock.internel;

public class MockException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MockException(String message, Throwable t) {
        super(message, t);
    }

    public MockException(String message) {
        super(message);
    }
}
