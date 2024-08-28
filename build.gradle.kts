plugins {
    id("java")
}

val jUnitVersion = "5.11.0-M2"

group = "ru.clevertec"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor ("org.projectlombok:lombok:1.18.34")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.platform:junit-platform-suite-api")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$jUnitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
