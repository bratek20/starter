package pl.bratek20.architecture.properties.api;

import java.util.List;

public interface PropertiesSource {
    PropertiesSourceId getId();

    <T> T get(String name, Class<T> type);

    <T> List<T> getList(String name, Class<T> type);

    <T> boolean hasOfType(String name, Class<T> type);
}
