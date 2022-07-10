import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
}

group = "demo.kx"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val testcontainersVersion = "1.15.3"
val postgresVersion="42.2.14"

repositories {
    mavenCentral()
}

springBoot {
    mainClass.set("demo.DemoApplicationKt")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    //postgreSQL
    implementation("org.postgresql:postgresql:$postgresVersion")

    // integration test
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // mocks
    testImplementation("io.mockk:mockk:1.10.4")
    testImplementation("com.ninja-squad:springmockk:3.0.1")

    //test containers
    testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")

    // web client
    implementation("com.konghq:unirest-java:3.13.6")
    implementation("com.konghq:unirest-mocks:3.13.6")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
