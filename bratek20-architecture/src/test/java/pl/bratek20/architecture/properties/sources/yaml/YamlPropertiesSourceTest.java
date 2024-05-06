package pl.bratek20.architecture.properties.sources.yaml;

import pl.bratek20.architecture.properties.api.PropertiesSource;
import pl.bratek20.architecture.properties.api.PropertiesSourceTest;

class YamlPropertiesSourceTest extends PropertiesSourceTest {

    @Override
    protected PropertiesSource createAndSetupSource() {
        return new YamlPropertiesSource();
    }

    @Override
    protected String expectedName() {
        return "yaml";
    }
}