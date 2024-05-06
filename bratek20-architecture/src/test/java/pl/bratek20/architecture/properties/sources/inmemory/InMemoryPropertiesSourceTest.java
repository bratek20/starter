package pl.bratek20.architecture.properties.sources.inmemory;

import pl.bratek20.architecture.properties.api.PropertiesSource;
import pl.bratek20.architecture.properties.api.PropertiesSourceTest;

class InMemoryPropertiesSourceTest extends PropertiesSourceTest {

    @Override
    protected PropertiesSource createAndSetupSource() {
        var source = new InMemoryPropertiesSource();
        source.set(SOME_PROPERTY_NAME, EXPECTED_SOME_PROPERTY);
        source.set(SOME_PROPERTY_LIST_NAME, EXPECTED_SOME_PROPERTY_LIST);
        return source;
    }

    @Override
    protected String expectedName() {
        return "inMemory";
    }
}