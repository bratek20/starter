package pl.bratek20.architecture.properties.sources.inmemory;

import lombok.RequiredArgsConstructor;
import pl.bratek20.architecture.properties.api.PropertiesSource;
import pl.bratek20.architecture.properties.api.PropertiesSourceId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class InMemoryPropertiesSource implements PropertiesSource {
    private final String name;
    private final Map<String, Object> properties = new HashMap<>();

    @Override
    public PropertiesSourceId getId() {
        return new PropertiesSourceId(name);
    }

    @Override
    public <T> T get(String name, Class<T> type) {
        return type.cast(properties.get(name));
    }

    @Override
    public <T> List<T> getList(String name, Class<T> type) {
        var property = properties.get(name);
        return (List<T>) property;
    }

    @Override
    public <T> boolean hasOfType(String name, Class<T> type) {
        var property = properties.get(name);
        return type.isInstance(property);
    }

    public void set(String name, Object property) {
        properties.put(name, property);
    }
}
