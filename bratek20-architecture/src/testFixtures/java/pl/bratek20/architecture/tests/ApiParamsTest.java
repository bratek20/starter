package pl.bratek20.architecture.tests;

import pl.bratek20.tests.InterfaceParamsTest;

public abstract class ApiParamsTest<T, P> extends InterfaceParamsTest<T, P> {
    protected T api;

    protected abstract T createApi(P params);

    @Override
    protected T createInstance(P params) {
        return createApi(params);
    }

    @Override
    protected void setup() {
        super.setup();
        api = instance;
    }
}
