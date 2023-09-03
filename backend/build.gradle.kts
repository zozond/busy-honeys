import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.3"
	id ("org.jetbrains.kotlin.plugin.allopen") version "1.8.22"
	id ("org.jetbrains.kotlin.plugin.noarg") version "1.8.22"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.22"
}

noArg {
	annotation("javax.persistence.Entity")
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

group = "com.busy.honey.stock"
version = "1.0.0"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}


repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")

	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-devtools")

	// https://mvnrepository.com/artifact/com.h2database/h2
	implementation("com.h2database:h2:2.2.220")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.13.5")

	implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter-jakarta:2.2.1.RELEASE")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
