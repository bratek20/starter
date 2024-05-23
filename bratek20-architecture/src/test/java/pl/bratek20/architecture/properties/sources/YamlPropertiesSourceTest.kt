package pl.bratek20.architecture.properties.sources

import pl.bratek20.architecture.context.stableContextBuilder
import pl.bratek20.architecture.properties.PropertiesSourceTest
import pl.bratek20.architecture.properties.api.PropertiesSource
import pl.bratek20.architecture.properties.sources.yaml.YamlPropertiesSourceImpl

internal class YamlPropertiesSourceTest : PropertiesSourceTest() {
    override fun createAndSetupSource(): PropertiesSource {
        return stableContextBuilder()
            .withModules(
                YamlPropertiesSourceImpl("someName.yaml")
            )
            .get(PropertiesSource::class.java)
    }

    override fun expectedName(): String {
        return "yaml"
    }
}