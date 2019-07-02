# spock-inject
auto inject dependencies for spock framework


help Mock and inject beans for Spock unit test framework.


some framework(eg: Spring) help manage beans relations on the runtime, and probability
have no setter method for dependent bean. it is hard to Mock the dependent beans.


this extension will make it easy to achieve this purpose by only several Annotations.


{@link Subject} indicate the Object to be tested. this annotation is primitive provided
by Spock framework, and we just reuse it.
{@link Mock} indicate Objects/beans are required by the Object which to be tested


run SpockAnnotation.init(this) in the setup method

eg:
```groovy
class Foo {
    @Resource
    private Bar bar;

    public void methodToBeTested() {
        bar.method1();
        // ...
    }
}

class Bar {
    public boolean method1() {
        // ...
    }
}

class FooTest extends Specification{
    @Subject
    Foo foo
    @Mock
    Bar bar

    void setup() {
        SpockAnnotation.init(this)
    }

    def "foo test"() {
        given:
        bar.method1() >> true

        when:
        foo.mehodToBeTested()

        then:
        1 * bar.method1()
    }
}
```


