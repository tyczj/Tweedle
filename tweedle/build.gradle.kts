import org.gradle.api.tasks.bundling.Jar

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlinx-serialization")
    id("maven-publish")
    id("signing")
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

group = "io.github.tyczj"
version = "0.5.0"

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
//                implementation("io.ktor:ktor-client-logging:1.5.1")
//                implementation("ch.qos.logback:logback-classic:1.2.3")
//                implementation("com.squareup.okio:okio:2.10.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2-native-mt"){
                    version {
                        strictly("1.4.2-native-mt")
                    }
                }
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
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2-native-mt")
                implementation("com.github.scribejava:scribejava-apis:8.3.0")
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
            }
        }
//        val iosTest by getting
    }
}

android {
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(26)
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
                name = "sonatype"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = rootProject.ext["ossrhUsername"]?.toString()
                    password = rootProject.ext["ossrhPassword"]?.toString()
                }
            }
        }

        publications.withType<MavenPublication> {

            artifact(javadocJar.get())

            pom{
                name.set("Tweedle")
                description.set("Tweedle is an Android library built around the Twitter v2 API built fully in Kotlin using Kotlin Coroutines")
                url.set("https://github.com/tyczj/Tweedle")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("tyczj")
                        name.set("Jeff Tycz")
                        email.set("tyczj359@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/tyczj/Tweedle")
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

ext["signing.keyId"] = rootProject.ext["signing.keyId"]?.toString()
ext["signing.password"] = rootProject.ext["signing.password"]?.toString()
ext["signing.secretKeyRingFile"] = rootProject.ext["signing.secretKeyRingFile"]?.toString()

signing {
    sign(publishing.publications)
}
