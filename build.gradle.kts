plugins {
    kotlin("jvm") version "2.0.10"
    `java-gradle-plugin`
    `maven-publish`
}

group = "ru.clevertec"
version = "0.0.4"
repositories {
    mavenCentral()
}

gradlePlugin {
    val gitagcreator by plugins.creating {
        id = "ru.clevertec.gitagcreator"
        implementationClass = "ru.clevertec.GitTagVersionCreatorPlugin"
    }
}

kotlin {
    jvmToolchain(21)
}