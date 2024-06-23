package pl.bratek20.spring.properties

import com.github.bratek20.architecture.context.spring.SpringContextBuilder
import org.junit.jupiter.api.Test
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.PropertySource
import pl.bratek20.spring.app.App
import java.util.function.Consumer


class SpringEnvironmentSourceTest {
    @Test
    fun test() {
        val env = App.run(null, emptyArray())
            .get(ConfigurableEnvironment::class.java)

        env.propertySources.forEach(Consumer { propertySource: PropertySource<*>? ->
            if (propertySource is MapPropertySource) {
                val map = propertySource.source
                map.forEach { (key: String, value: Any) ->
                    println("$key: $value")
                }
            }
        })
    }
}