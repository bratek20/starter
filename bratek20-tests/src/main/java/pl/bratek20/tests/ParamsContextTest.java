package pl.bratek20.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class ParamsContextTest<P, C> {
    protected abstract P defaultParams();
    protected abstract C createContext(P params);
    protected abstract void applyContext(C context);

    @BeforeEach
    void beforeEach() {
        var params = defaultParams();
        var context = createContext(params);

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
