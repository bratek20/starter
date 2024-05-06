package pl.bratek20.architecture.properties;

import org.junit.jupiter.api.Test;
import pl.bratek20.architecture.properties.api.Properties;
import pl.bratek20.architecture.properties.api.PropertyKey;
import pl.bratek20.architecture.properties.impl.PropertiesModule;
import pl.bratek20.architecture.properties.sources.inmemory.InMemoryPropertiesSource;
import pl.bratek20.architecture.properties.sources.inmemory.InMemoryPropertiesSourceModule;
import pl.bratek20.tests.InterfaceParamsTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.bratek20.architecture.context.HelpersKt.someContextBuilder;

public class PropertiesTest {
    record MyProperty(String value) {}

    @Test
    void shouldGetProperty() {
        var c = someContextBuilder()
            .withModules(
                new PropertiesModule(),
                new InMemoryPropertiesSourceModule()
            )
            .build();

        var api = c.get(Properties.class);
        var source = c.get(InMemoryPropertiesSource.class);

        var prop = new MyProperty("x");
        source.set(new PropertyKey("mine"), prop);

        var x = api.get(InMemoryPropertiesSource.Companion.getName(), new PropertyKey("mine"), MyProperty.class);

        assertEquals(prop, x);
    }
}