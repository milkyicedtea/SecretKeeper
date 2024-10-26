plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "1.9.25"
  id("org.jetbrains.intellij") version "1.17.4"
}

group = "org.cheek"

repositories {
  mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
  version.set("2022.1")
  // Target IDE Platforms
//  type.set("IC")  // IntelliJ IDEA Community Edition
//  type.set("IU")  // IntelliJ IDEA Ultimate Edition
//  type.set("CL")  // CLion
//  type.set("PY")  // PyCharm Professional Edition
//  type.set("PC")  // PyCharm Community Edition
//  type.set("PS")  // PhpStorm
//  type.set("RD")  // Rider
//  type.set("GO")  // GoLand
//  type.set("AI")  // Android Studio
//  type.set("RR")  // Rust Rover

  plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
  }

  patchPluginXml {
    sinceBuild.set("221")
    untilBuild.set("242.*")
  }

  signPlugin {
    certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    privateKey.set(System.getenv("PRIVATE_KEY"))
    password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishPlugin {
    token.set(System.getenv("PUBLISH_TOKEN"))
  }
}
