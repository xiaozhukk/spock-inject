package com.github.xiaozhukk.spock.internel;

/**
 * Represents the strategy used to resolve actual instances
 * to be given to a constructor given the argument types.
 */
public interface ConstructorArgumentResolver {

    /**
     * Try to resolve instances from types.
     *
     * <p>
     * Checks on the real argument type or on the correct argument number
     * will happen during the field initialization {@link FieldInitializer#initialize()}.
     * I.e the only responsibility of this method, is to provide instances <strong>if possible</strong>.
     * </p>
     *
     * @param argTypes Constructor argument types, should not be null.
     * @return The argument instances to be given to the constructor, should not be null.
     */
    Object[] resolveTypeInstances(Class<?>... argTypes);
}
