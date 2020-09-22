import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
}
group = "dev.danielfreer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test-junit5"))
    testImplementation("io.kotest:kotest-runner-junit5:4.2.5")
    testImplementation("io.kotest:kotest-assertions-core:4.2.5")
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "13"
}
tasks.withType<Test> {
    useJUnitPlatform()
}
application {
    mainClassName = "MainKt"
}
