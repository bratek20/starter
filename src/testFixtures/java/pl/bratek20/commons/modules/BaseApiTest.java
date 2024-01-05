package pl.bratek20.commons.modules;

import org.junit.jupiter.api.BeforeEach;

public abstract class BaseApiTest<T> {
    protected abstract T createApi();

    protected T api;

    @BeforeEach
    void beforeEach() {
        api = createApi();
        setup();
    }

    protected void setup() {
    }
}
