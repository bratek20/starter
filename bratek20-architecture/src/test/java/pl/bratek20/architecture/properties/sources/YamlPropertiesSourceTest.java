package pl.bratek20.architecture.properties.sources;

import pl.bratek20.architecture.properties.api.PropertiesSource;
import pl.bratek20.architecture.properties.PropertiesSourceTest;
import pl.bratek20.architecture.properties.sources.yaml.YamlPropertiesSource;

class YamlPropertiesSourceTest extends PropertiesSourceTest {

    @Override
    protected PropertiesSource createAndSetupSource() {
        var source = new YamlPropertiesSource();
        source.setPropertiesPath("someName.yaml");
        return source;
    }

    @Override
    protected String expectedName() {
        return "yaml";
    }
}