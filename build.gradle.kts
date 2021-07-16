plugins {
    id("com.github.johnrengelman.shadow") version "7.0.0"
    java
    application
    kotlin("jvm") version "1.5.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    implementation(project("ngrok4j"))
}

application {
    mainClass.set("Main")
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = "Main"
    }
}