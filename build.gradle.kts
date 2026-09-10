val groupAll = "com.github.bratek20"

plugins {
    alias(common.plugins.kotlin.spring) apply false
}

group = groupAll

subprojects {
    group = groupAll
}