package pl.bratek20.architecture.properties;

import pl.bratek20.architecture.properties.api.PropertiesApi;
import pl.bratek20.architecture.properties.impl.PropertiesImpl;
import pl.bratek20.architecture.properties.sources.inmemory.InMemoryPropertiesSource;

import java.util.List;

public class PropertiesApiMock implements PropertiesApi {
    private final InMemoryPropertiesSource source;
    private final PropertiesApi api;

    public PropertiesApiMock() {
        source = new InMemoryPropertiesSource("mock");
        api = new PropertiesImpl(List.of(source));
    }

    @Override
    public <T> T get(String propertyName, Class<T> propertyType) {
        return api.get(propertyName, propertyType);
    }

    @Override
    public <T> List<T> getList(String propertyName, Class<T> propertyType) {
        return api.getList(propertyName, propertyType);
    }

    public void setProperty(String propertyName, Object value) {
        source.set(propertyName, value);
    }
}
