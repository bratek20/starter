package pl.bratek20.architecture.properties.api;

import java.util.List;

public interface PropertiesApi {
    <T> T get(String propertyName, Class<T> propertyType);
    <T> List<T> getList(String propertyName, Class<T> propertyType);
}
