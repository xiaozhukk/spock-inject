package com.github.xiaozhukk.spock.inject;

import com.github.xiaozhukk.spock.internel.MockInjection;
import org.spockframework.mock.MockUtil;
import spock.lang.Specification;
import spock.lang.Subject;
import spock.mock.DetachedMockFactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * help Mock and inject beans for Spock unit test framework.
 *
 * some framework(eg: Spring) help manage beans relations on the runtime, and probability
 * have no setter method for dependent bean. it is hard to Mock the dependent beans.
 *
 * this extension will make it easy to achieve this purpose by only several Annotations.
 *
 * {@link Subject} indicate the Object to be tested. this annotation is primitive provided
 * by Spock framework, and we just reuse it.
 * {@link Mock} indicate Objects/beans are required by the Object which to be tested
 *
 * run SpockAnnotation.init(this) in the setup method, to make all run
 *
 * eg:
 *
 * class Foo {
 *     @Resource
 *     private Bar bar;
 *
 *     public void methodToBeTested() {
 *         bar.method1();
 *         // ...
 *     }
 * }
 *
 * class Bar {
 *     public boolean method1() {
 *         // ...
 *     }
 * }
 *
 * class FooTest extends Specification{
 *     @Subject
 *     Foo foo
 *     @Mock
 *     Bar bar
 *
 *     void setup() {
 *         SpockAnnotation.init(this)
 *     }
 *
 *     def "foo test"() {
 *         given:
 *         bar.method1() >> true
 *
 *         when:
 *         foo.mehodToBeTested()
 *
 *         then:
 *         1 * bar.method1()
 *     }
 * }
 *
 */
public class SpockAnnotation {

    private Specification specification;
    private DetachedMockFactory factory;
    private Set<Object> mocks;
    private Field subject;
    private MockUtil util;

    private SpockAnnotation(Specification spec) {
        this.specification = spec;
        factory = new DetachedMockFactory();
        mocks = new HashSet<>();
        util = new MockUtil();
    }

    public static void init(Specification spec) {
        if (spec == null) {
            throw new RuntimeException("spec could not be null");
        }

        new SpockAnnotation(spec).inject();
    }

    private void inject() {
        Class<?> classContext = specification.getClass();
        while (classContext != Specification.class) {
            process(classContext);
            classContext = classContext.getSuperclass();
        }

        if (subject == null) {
            throw new RuntimeException("no annotation of Subject");
        }

        Set<Field> set = new HashSet<>();
        set.add(subject);

        MockInjection.onFields(set, specification)
                .withMocks(mocks)
                .tryConstructorInjection()
                .tryPropertyOrFieldInjection()
                .apply();
    }

    private void process(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Mock.class)) {
                try {
                    Object mock = createMockFor(field);
                    setField(specification, field, mock);
                } catch (Exception e) {
                    throw new RuntimeException("problems setting field " + field.getName(), e);
                }
            }

            if (field.isAnnotationPresent(Subject.class)) {
                if(this.subject == null) {
                    this.subject = field;
                }else {
                    throw new RuntimeException("only one Subject annotation is supported");
                }
            }

        }
    }

    private Object createMockFor(Field field) {
        Object mock = factory.Mock(field.getType());
        mocks.add(mock);

        util.attachMock(mock, specification);
        return mock;
    }

    private void setField(Specification testInstance, Field field, Object mock) {
        field.setAccessible(true);
        try {
            field.set(testInstance, mock);
        } catch (Exception e) {
            throw new RuntimeException("inject issue on field " + field, e);
        }
    }

}
