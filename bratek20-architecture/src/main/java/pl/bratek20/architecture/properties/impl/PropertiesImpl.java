package pl.bratek20.architecture.properties.impl;

import pl.bratek20.architecture.properties.api.PropertiesApi;
import pl.bratek20.architecture.properties.api.PropertiesSource;

import java.util.List;

public class PropertiesImpl implements PropertiesApi {
    private final List<PropertiesSource> sources;

    public PropertiesImpl(List<PropertiesSource> sources) {
        this.sources = sources;
    }

    @Override
    public <T> T get(String propertyName, Class<T> propertyType) {
        return sources.get(0).get(propertyName, propertyType);
    }

    @Override
    public <T> List<T> getList(String propertyName, Class<T> propertyType) {
        return sources.get(0).getList(propertyName, propertyType);
    }
}
