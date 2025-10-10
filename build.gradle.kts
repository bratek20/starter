val groupAll = "com.github.bratek20"

plugins {
    kotlin("plugin.spring") version "1.9.23" apply false
}

group = groupAll

subprojects {
    group = groupAll
}