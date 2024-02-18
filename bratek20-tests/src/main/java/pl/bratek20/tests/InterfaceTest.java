package pl.bratek20.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class InterfaceTest<T> {
    protected abstract T createInstance();

    protected T instance;

    @BeforeEach
    void beforeEach() {
        instance = createInstance();
        setup();
    }

    @AfterEach
    void afterEach() {
        clean();
    }

    protected void setup() {
    }

    protected void clean() {
    }
}
