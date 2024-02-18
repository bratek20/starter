package pl.bratek20.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class ContextTest<T> {
    protected abstract T createContext();
    protected abstract void applyContext(T context);

    @BeforeEach
    void beforeEach() {
        var context = createContext();

        applyContext(context);

        setup();
    }

    @AfterEach
    void afterEach() {
        clean();
    }

    protected void setup() {}

    protected void clean() {}
}
