import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "2.3.10"

  // new 2.x plugin
  id("org.jetbrains.intellij.platform") version "2.11.0"
}

group = "org.cheek"
version = "0.1.4"

repositories {
  mavenCentral()
  intellijPlatform {
    defaultRepositories()
  }
}

dependencies {
  intellijPlatform {
    intellijIdeaCommunity("2023.3")
    bundledPlugins("Git4Idea")
    testFramework(TestFrameworkType.Platform)
  }
}

intellijPlatform {
  projectName = project.name

  pluginVerification {
    ides {
      create(IntelliJPlatformType.IntellijIdeaCommunity, "2023.3")
      create(IntelliJPlatformType.IntellijIdeaCommunity, "2024.3")
      create(IntelliJPlatformType.IntellijIdea, "2025.3") // no more community because unified
//      select {
//        types = listOf(IntelliJPlatformType.IntellijIdea)
//        channels = listOf(ProductRelease.Channel.RELEASE)
//        sinceBuild = "232"
//      }
    }
  }

  signing {
    certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    privateKey.set(System.getenv("PRIVATE_KEY"))
    password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishing {
    token.set(System.getenv("PUBLISH_TOKEN"))
  }
}
