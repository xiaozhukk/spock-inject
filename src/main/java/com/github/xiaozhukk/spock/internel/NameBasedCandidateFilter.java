/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package com.github.xiaozhukk.spock.internel;


import java.lang.reflect.Field;
import java.util.Collection;

public class NameBasedCandidateFilter implements MockCandidateFilter {
    private final MockCandidateFilter next;


    public NameBasedCandidateFilter(MockCandidateFilter next) {
        this.next = next;
    }

    public OngoingInjecter filterCandidate(Collection<Object> mocks, Field field, Object fieldInstance) {
        return next.filterCandidate(mocks, field, fieldInstance);
    }
}
