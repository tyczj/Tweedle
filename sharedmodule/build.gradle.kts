plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-android-extensions")
    id("kotlinx-serialization")
    id("maven-publish")
}

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}

group = "com.tycz"
version = "0.2.0"

kotlin {
    android{
        publishLibraryVariants("release")
    }
    ios {
        binaries {
            framework {
                baseName = "tweedle"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:1.5.1")
                implementation("io.ktor:ktor-client-json:1.5.1")
                implementation("io.ktor:ktor-client-serialization:1.5.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.core:core-ktx:1.3.2")
                implementation("io.ktor:ktor-client-android:1.5.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.12")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:1.5.1")
//                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2-native-mt"){
//                    version {
//                        strictly("1.4.2-native-mt")
//                    }
//                }
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    packagingOptions {
        excludes.add("META-INF/*.kotlin_module")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

afterEvaluate {
    publishing {
        repositories {
            maven {
                url = uri("https://maven.pkg.jetbrains.space/tyczj/p/vqi18/tweedle")
                credentials {
                    username = "tyczj359"
                    password = "eyJhbGciOiJSUzUxMiJ9.eyJzdWIiOiIxZnF3YkkwOGhFZ1UiLCJhdWQiOiJjaXJjbGV0LXdlYi11aSIsIm9yZ0RvbWFpbiI6InR5Y3pqIiwibmFtZSI6InR5Y3pqMzU5IiwiaXNzIjoiaHR0cHM6XC9cL2pldGJyYWlucy5zcGFjZSIsInBlcm1fdG9rZW4iOiIzVEtOOWg0NTltREwiLCJwcmluY2lwYWxfdHlwZSI6IlVTRVIiLCJpYXQiOjE2MTI4MzUzNjh9.olf8LQkB_U6ZfcFbLAIkvwbMmprBPkAT3uqsai1tRoqIiOEScbLkOO_rVBdzTw-IThjmK9zaLHW9V00aSsR1U7pGzILElHCdrTJn00hFJxjvQwjLlJ36Tbckdqwg-sE3PPZjvz25qHQ-T5chgtSUnLFZm0fVPVR5ZR7SZ14qW4U"
//                    username = System.getenv("USERNAME")
//                    password = System.getenv("PASSWORD")
                }
            }
        }
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework =
        kotlin.targets.getByName<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>(
            targetName
        ).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}
tasks.getByName("build").dependsOn(packForXcode)