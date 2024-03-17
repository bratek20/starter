package pl.bratek20.architecture.properties.api;

import org.junit.jupiter.api.Test;
import pl.bratek20.architecture.properties.sources.inmemory.InMemoryPropertiesSource;
import pl.bratek20.tests.InterfaceParamsTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class PropertiesApiTest extends InterfaceParamsTest<PropertiesApi, List<PropertiesSource>> {
    private final InMemoryPropertiesSource source = new InMemoryPropertiesSource("mock");

    @Override
    protected List<PropertiesSource> defaultParams() {
        return List.of(
            source
        );
    }

    private PropertiesApi api;

    @Override
    protected void setup() {
        super.setup();
        api = instance;
    }

    record MyProperty(String value) {}

    @Test
    void shouldGetProperty() {
        var prop = new MyProperty("x");
        source.set("mine", prop);

        var x = api.get("mine", MyProperty.class);

        assertEquals(prop, x);
    }
}