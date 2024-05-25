package pl.bratek20.architecture.properties.sources

import pl.bratek20.architecture.context.stableContextBuilder
import pl.bratek20.architecture.properties.PropertiesSourceTest
import pl.bratek20.architecture.properties.api.PropertiesSource
import pl.bratek20.architecture.properties.sources.yaml.YamlPropertiesSource
import pl.bratek20.architecture.properties.sources.yaml.YamlPropertiesSourceImpl

internal class YamlPropertiesSourceTest : PropertiesSourceTest() {
    override fun createAndSetupSource(): PropertiesSource {
        val source = stableContextBuilder()
            .withModules(
                YamlPropertiesSourceImpl()
            )
            .get(YamlPropertiesSource::class.java)

        source.propertiesPath = "src/test/resources/someName.yaml"

        return source
    }

    override fun expectedName(): String {
        return "yaml"
    }
}