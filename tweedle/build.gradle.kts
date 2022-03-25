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
version = "0.5.1"

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
                implementation("io.ktor:ktor-client-core:1.6.7")
                implementation("io.ktor:ktor-client-json:1.6.7")
                implementation("io.ktor:ktor-client-serialization:1.6.7")
//                implementation("io.ktor:ktor-client-logging:1.5.1")
//                implementation("ch.qos.logback:logback-classic:1.2.3")
//                implementation("com.squareup.okio:okio:2.10.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt"){
                    version {
                        strictly("1.6.0-native-mt")
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
                implementation("androidx.core:core-ktx:1.7.0")
                implementation("io.ktor:ktor-client-android:1.6.7")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
//                implementation("com.github.scribejava:scribejava-apis:8.3.0")
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
                implementation("io.ktor:ktor-client-ios:1.6.7")
            }
        }
//        val iosTest by getting
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 26
        targetSdk = 31
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    packagingOptions {
        resources.excludes.add("META-INF/*.kotlin_module")
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
                    val ossrhUsername = System.getenv("OSSRH_USERNAME") ?: rootProject.ext["ossrhUsername"]?.toString()
                    val ossrhPassword = System.getenv("OSSRH_PASSWORD") ?: rootProject.ext["ossrhPassword"]?.toString()
                    username = ossrhUsername
                    password = ossrhPassword
                }
            }
        }

        publications.withType<MavenPublication> {

            artifact(javadocJar.get())

            pom{
                group = "io.github.tyczj"
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

signing {
    val signingKeyId = System.getenv("SIGNING_KEYID") ?: rootProject.ext["signing.keyId"]?.toString()
    val signingPassword = System.getenv("SIGNING_PASSWORD") ?: rootProject.ext["signing.password"]?.toString()
    val gpgPrivateKey = System.getenv("GPG_PRIVATE_KEY")
    if(gpgPrivateKey != null){
        val signingKey: String? by project
        val signingPassword: String? by project
        useInMemoryPgpKeys(signingKey,signingPassword)
    }else{
        ext["signing.keyId"] = signingKeyId
        ext["signing.password"] = signingPassword
        ext["signing.secretKeyRingFile"] = rootProject.ext["signing.secretKeyRingFile"]?.toString()
    }

    sign(publishing.publications)
}