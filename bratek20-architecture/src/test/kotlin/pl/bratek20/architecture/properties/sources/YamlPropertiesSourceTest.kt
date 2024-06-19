package pl.bratek20.architecture.properties.sources

import org.junit.jupiter.api.Test
import pl.bratek20.architecture.context.stableContextBuilder
import pl.bratek20.architecture.exceptions.assertApiExceptionThrown
import pl.bratek20.architecture.properties.PropertiesSourceTest
import pl.bratek20.architecture.properties.api.PropertiesSource
import pl.bratek20.architecture.properties.sources.yaml.YamlPropertiesSource
import pl.bratek20.architecture.properties.sources.yaml.YamlPropertiesSourceImpl
import java.nio.file.Paths

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
        val prefix = Paths.get("").toAbsolutePath().toString()
        val path = Paths.get(prefix, "src", "test", "resources", "someName.yaml").toString()
        return path
    }


    @Test
    fun shouldThrowWhenFilePathDoesNotEndWithYamlExtension() {
        assertApiExceptionThrown(
            {YamlPropertiesSource("src/test/resources/someName.txt")},
            {
                message = "Yaml properties source path should end with .yaml extension"
            }
        )

        val source = createAndSetupSource() as YamlPropertiesSource
        assertApiExceptionThrown(
            {source.propertiesPath = "src/test/resources/someName.txt"},
            {
                message = "Yaml properties source path should end with .yaml extension"
            }
        )
    }
}