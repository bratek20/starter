package pl.bratek20.architecture.properties.sources.yaml;

import pl.bratek20.architecture.properties.api.PropertiesSource;
import pl.bratek20.architecture.properties.api.PropertiesSourceId;
import pl.bratek20.architecture.properties.api.PropertiesSourceTest;

class YamlPropertiesSourceTest extends PropertiesSourceTest {

    @Override
    protected PropertiesSource createAndSetupSource() {
        return new YamlPropertiesSource();
    }

    @Override
    protected PropertiesSourceId expectedName() {
        return new PropertiesSourceId("yaml");
    }
}