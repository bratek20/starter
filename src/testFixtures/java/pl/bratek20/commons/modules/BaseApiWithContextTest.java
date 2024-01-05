package pl.bratek20.commons.modules;

import org.junit.jupiter.api.BeforeEach;

public abstract class BaseApiWithContextTest<T> {
    protected abstract T createContext();
    protected abstract void applyContext(T context);

    protected T context;

    @BeforeEach
    void beforeEach() {
        context = createContext();

        applyContext(context);

        setup();
    }

    protected void setup() {
    }
}
