/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package com.github.xiaozhukk.spock.internel;


import java.lang.reflect.Field;

public class FieldReader {

    final Object target;
    final Field field;
    final AccessibilityChanger changer = new AccessibilityChanger();

    public FieldReader(Object target, Field field) {
        this.target = target;
        this.field = field;
        changer.enableAccess(field);
    }

    public boolean isNull() {
            return read() == null;
    }

    public Object read() {
        try {
            return field.get(target);
        } catch (Exception e) {
            throw new MockException("Cannot read state from field: " + field + ", on instance: " + target);
        }
    }
}
