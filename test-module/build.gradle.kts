plugins {
    java
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "ru.clevertec"
version = "0.0.1-SNAPSHOT"

val starterVersion = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("ru.clevertec:starter:${starterVersion}")

    annotationProcessor("org.projectlombok:lombok")

    compileOnly("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
