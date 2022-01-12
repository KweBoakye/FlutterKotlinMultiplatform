rootProject.name = "shared"

pluginManagement {
    resolutionStrategy {

        repositories {
            google()
            mavenCentral()
            mavenLocal()
            gradlePluginPortal()
        }
        eachPlugin {
            if (requested.id.namespace == "com.android") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }

            when (requested.id.id) {
                "com.squareup.sqldelight" -> {
                    useModule("com.squareup.sqldelight:gradle-plugin:${requested.version}")
                }
                "org.jetbrains.kotlin.plugin.serialization" -> {
                    useModule(
                        "org.jetbrains.kotlin.plugin.serialization:" +
                                "org.jetbrains.kotlin.plugin.serialization.gradle.plugin:" +
                                "${requested.version}"
                    )
                }
            }

        }
    }
}


