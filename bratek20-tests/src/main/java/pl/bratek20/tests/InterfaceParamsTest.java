package pl.bratek20.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class InterfaceParamsTest<T, P> {
    protected abstract P defaultParams();
    protected abstract T createInstance(P params);

    protected T instance;

    @BeforeEach
    void beforeEach() {
        instance = createInstance(defaultParams());
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
