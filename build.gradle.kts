import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.gradle.api.tasks.bundling.Zip


plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.intellij.platform") version "2.7.1"
}

group = "com.cortlet.bpls"
version = "1.0.0" // SNAPSHOT dropped, we goin' LIVE 💥

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        create("IC", "2025.1.4.1")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)
        // Add: bundledPlugin("com.intellij.java") if needed
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "251"
        }

        changeNotes = """
            🌟 Initial production release of BPLS Plugin
            🧠 Case-sensitive command checking
            ❗ Unknown command detection with typo suggestions
        """.trimIndent()
    }
}

tasks {
    // DRIP Java config
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }

    // DRIP Kotlin config
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "21"
    }

    // Build production-ready zip
    // 💼 Build production-ready plugin zip in /build/distributions/
    // 💼 Build production-ready plugin zip in /build/distributions/


    extensions.configure<KotlinJvmProjectExtension>("kotlin") {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    // 🔐 Optional signing
    signPlugin {
        certificateChain.set(System.getenv("CERT_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    // 🚀 Optional publish (if you want to upload from CLI)
    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}

// 💼 Build production-ready plugin zip in /build/distributions/
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "21"
    }
}

tasks.register<Zip>("buildPluginZip") {
    dependsOn("buildPlugin")

    from("$buildDir/distributions") {
        include("*.zip")
    }

    destinationDirectory.set(layout.buildDirectory.dir("final-zips"))
    archiveFileName.set("YourPlugin.zip")

    doLast {
        println("✅ ZIPPED → ${destinationDirectory.get().asFile.absolutePath}\\${archiveFileName.get()}")
    }
}



