/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */

package com.github.xiaozhukk.spock.internel;


import java.lang.reflect.Field;
import java.util.Set;

/**
 * Handler for field annotated with &#64;InjectMocks and &#64;Spy.
 *
 * <p>
 * The handler assumes that field initialization AND injection already happened.
 * So if the field is still null, then nothing will happen there.
 * </p>
 */
public class SpyOnInjectedFieldsHandler extends MockInjectionStrategy {

    @Override
    protected boolean processInjection(Field field, Object fieldOwner, Set<Object> mockCandidates) {

        // TODO spy injection
        return false;
    }
}
