import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode.BITCODE
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
//import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.RELEASE

plugins {
    kotlin("multiplatform") version "1.6.10"
    kotlin("native.cocoapods") version "1.6.10"
    id("com.android.library") version "7.0.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
    id ("com.squareup.sqldelight") version "1.5.3"
}

group = "com.kb"
version = "1.0"
repositories {
    mavenCentral()
    google()
    maven("../fluttermodule/build/host/outputs/repo")
    maven {
        setUrl("https://storage.googleapis.com/download.flutter.io")
    }
    maven("https://jitpack.io")
}

kotlin {
    android(){
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    val iosTarget: (String, org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.() -> Unit) -> org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        System.getenv("NATIVE_ARCH")?.startsWith("arm") == true -> ::iosSimulatorArm64
        else -> ::iosX64
    }

    iosTarget("ios") {

        compilations {

            val main by getting {
                // dependsOn(sourceSets.commonMain.get())

                val flutternative by cinterops.creating {

                    val flutterPath = "${project.projectDir.parent}/ios/Flutter"
                    includeDirs(
                        "${project.projectDir.parent}/shared/src/nativeinterop/sources/Flutter.framework/Headers"
                    )
                }
               /* kotlinOptions.freeCompilerArgs += arrayOf<String>(
                    // "-Xg0",
                    // "-Xadd-light-debug=enable",
                    "-linker-options",
                    "-F${project.projectDir.parent}/ios/Flutter/Flutter.Framework -framework Flutter"
                )*/
            }
        }
    }
    //iosSimulatorArm64() sure all ios dependencies support this target

    cocoapods {

        framework {
            // Configure fields required by CocoaPods.
            summary = "Some description for a Kotlin/Native module"
            homepage = "Link to a Kotlin/Native module homepage"
            // Framework name configuration. Use this property instead of deprecated 'frameworkName'
            //baseName = "MyFramework"
            // (Optional) Dynamic framework support
            isStatic = false
            // (Optional) Dependency export
            //export(project(":anotherKMMModule"))
            transitiveExport = true
            // (Optional) Bitcode embedding
            embedBitcode(BITCODE)
        }

        // Maps custom Xcode configuration to NativeBuildType
        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = NativeBuildType.RELEASE
    }

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework> {
            linkerOpts.apply {
                add("-framework")
                add("Flutter")
                add("-F${project.projectDir.parent}/shared/src/nativeinterop/sources")
            }
        }
    }

    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.3.1")
                implementation("com.squareup.sqldelight:coroutines-extensions:1.5.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("com.fluttermodule.fluttermodule:flutter_debug:1.0")
                implementation("com.squareup.sqldelight:native-driver:1.5.3")

                api("com.github.streem.pbandk:pbandk-runtime-jvm:v0.8.1") {
                    exclude(
                        group = "org.jetbrains.kotlinx",
                        module = "kotlinx-serialization-runtime"
                    )
                }
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:native-driver:1.5.3")
            }
        }
        val iosTest by getting {
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }

    compileOptions {
        sourceCompatibility = org.gradle.api.JavaVersion.VERSION_1_8
        targetCompatibility = org.gradle.api.JavaVersion.VERSION_1_8
    }

    packagingOptions {
        resources.excludes.add("io/flutter/plugins/GeneratedPluginRegistrant.class")
        // exclude("src/androidTest")
    }
}


sqldelight {
    database("Database"){ // This will be the name of the generated database class.
        packageName = "com.kb"
        schemaOutputDirectory = file("src/main/sqldelight/databases")
        verifyMigrations = true
    }
}
