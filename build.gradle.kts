plugins {
	java
	war
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.clevertec"
version = "0.0.1-SNAPSHOT"

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
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-database-postgresql")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")

	implementation("org.mapstruct:mapstruct:1.6.2")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.2")
	implementation("org.projectlombok:lombok-mapstruct-binding:0.2.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")


}

tasks.withType<Test> {
	useJUnitPlatform()
}
