import java.net.URI

plugins {
    java
    kotlin("jvm") version "1.5.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(project("ngrok4j"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}