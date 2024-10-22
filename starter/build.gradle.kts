plugins {
    id("java-library")
    id("maven-publish")
}

group = "ru.clevertec"
version = "0.0.1-SNAPSHOT"

val springBootVersion = "3.3.4"
val jacksonVersion = "2.18.0"
val lombokVersion = "1.18.34"
val wiremockVersion = "3.3.1"

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
    implementation("org.springframework.boot:spring-boot-starter:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-web:${springBootVersion}")
    implementation("com.fasterxml.jackson.core:jackson-core:${jacksonVersion}")

    compileOnly("org.projectlombok:lombok:${lombokVersion}")

    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")

    testImplementation("org.springframework.boot:spring-boot-starter-test:${springBootVersion}")
    testImplementation("org.wiremock:wiremock:$wiremockVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}