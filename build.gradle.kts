plugins {
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
    testImplementation("junit:junit:4.13")
    implementation(project("ngrok4j"))
}

application {
    mainClass.set("Main")
}

tasks.getByName<Test>("test") {
    useJUnit()
}

