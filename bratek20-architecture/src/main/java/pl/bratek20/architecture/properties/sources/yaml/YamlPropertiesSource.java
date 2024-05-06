package pl.bratek20.architecture.properties.sources.yaml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import pl.bratek20.architecture.properties.api.PropertiesSource;
import pl.bratek20.architecture.properties.api.PropertiesSourceName;
import pl.bratek20.architecture.properties.api.PropertyKey;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class YamlPropertiesSource implements PropertiesSource {
    @SneakyThrows
    public static <T> T parseYamlSection(String resourcePath, String yamlPath, TypeReference<T> typeReference) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File file = new File(YamlPropertiesSource.class.getClassLoader().getResource(resourcePath).getFile());
        JsonNode rootNode = mapper.readTree(file);

        // Navigate to the specified YAML path
        String[] pathElements = yamlPath.split("\\.");
        JsonNode targetNode = rootNode;
        for (String element : pathElements) {
            targetNode = targetNode.path(element);
        }

        // Deserialize the target node into the specified type
        return mapper.convertValue(targetNode, typeReference);
    }

    @Override
    public PropertiesSourceName getName() {
        return new PropertiesSourceName("yaml");
    }

    @Override
    public <T> T get(PropertyKey key, Class<T> type) {
        var name = key.getValue();
        return parseYamlSection("application.yaml", name, new TypeReference<T>() {
            @Override
            public Type getType() {
                return type;
            }
        });
    }

    public static <T> TypeReference<List<T>> listTypeRef(Class<T> clazz) {
        return new TypeReference<>() {
            @Override
            public Type getType() {
                return TypeFactory.defaultInstance().constructCollectionType(List.class, clazz);
            }
        };
    }

    @Override
    public <T> List<T> getList(PropertyKey key, Class<T> clazz) {
        var name = key.getValue();
        return parseYamlSection("application.yaml", name, listTypeRef(clazz));
    }

    @Override
    public <T> boolean hasOfType(PropertyKey key, Class<T> type) {
        var name = key.getValue();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            // Load the YAML file
            File file = new File(getClass().getClassLoader().getResource("application.yaml").getFile());
            JsonNode rootNode = mapper.readTree(file);

            // Navigate to the specified YAML path
            String[] pathElements = name.split("\\.");
            JsonNode targetNode = rootNode;
            for (String element : pathElements) {
                targetNode = targetNode.get(element);
                if (targetNode == null) {
                    return false; // Path does not exist
                }
            }

            // Attempt to parse the node into the specified type
            T parsedObject = mapper.treeToValue(targetNode, type);
            return parsedObject != null; // If parsing succeeds, the path exists and is of the specified type
        } catch (IOException | IllegalArgumentException e) {
            return false; // Parsing failed or path does not exist
        }
    }

}
