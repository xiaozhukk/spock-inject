/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package com.github.xiaozhukk.spock.internel;

import java.lang.reflect.Field;
import java.util.Collection;

public interface MockCandidateFilter {

    OngoingInjecter filterCandidate(
            Collection<Object> mocks,
            Field fieldToBeInjected,
            Object fieldInstance
    );

}
