package com.github.bratek20.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.attributes.Bundling
import org.gradle.api.attributes.Category
import org.gradle.api.attributes.DocsType
import org.gradle.api.attributes.Usage
import org.gradle.api.component.AdhocComponentWithVariants
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register

class B20Sources : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            // main sources: `-sources.jar` + `sourcesElements` variant, picked up by B20Publish via the `java` component
            extensions.configure<JavaPluginExtension> {
                withSourcesJar()
            }

            val testFixturesSourcesJar = tasks.register<Jar>("testFixturesSourcesJar") {
                archiveClassifier.set("test-fixtures-sources")
                // allSource also covers src/testFixtures/kotlin
                from(sourceSets()["testFixtures"].allSource)
            }

            val sourcesElements = configurations.create("testFixturesSourcesElements") {
                isCanBeResolved = false
                isCanBeConsumed = true
                attributes {
                    attribute(Usage.USAGE_ATTRIBUTE, objects.named<Usage>(Usage.JAVA_RUNTIME))
                    attribute(Category.CATEGORY_ATTRIBUTE, objects.named<Category>(Category.DOCUMENTATION))
                    attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named<Bundling>(Bundling.EXTERNAL))
                    attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named<DocsType>(DocsType.SOURCES))
                }
                outgoing.artifact(testFixturesSourcesJar)
            }

            afterEvaluate {
                // group and version are only known after evaluation,
                // capability must match the one java-test-fixtures puts on testFixtures*Elements
                sourcesElements.outgoing.capability("$group:$name-test-fixtures:$version")

                (components["java"] as AdhocComponentWithVariants)
                    .addVariantsFromConfiguration(sourcesElements) { }
            }
        }
    }
}
