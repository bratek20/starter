package pl.bratek20.architecture.tests;

import pl.bratek20.tests.InterfaceTest;

public abstract class ApiTest<T> extends InterfaceTest<T> {
    protected T api;

    protected abstract T createApi();

    @Override
    protected T createInstance() {
        return createApi();
    }

    @Override
    protected void setup() {
        super.setup();
        api = instance;
    }
}
