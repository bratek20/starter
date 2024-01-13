package pl.bratek20.commons.modules;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseApiTest<T> {
    protected abstract T createApi();

    protected T api;

    @BeforeEach
    void beforeEach() {
        api = createApi();
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
