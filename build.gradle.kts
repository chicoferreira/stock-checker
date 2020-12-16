import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
}

group = "com.github.chicoferreira"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    jcenter()
}

dependencies {
    implementation("org.jsoup:jsoup:1.11.3")
    implementation("org.fusesource.jansi:jansi:2.0.1")
    implementation("org.jline:jline:3.9.0")
    implementation("org.jline:jline-terminal-jansi:3.17.1")
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testImplementation("io.mockk:mockk:1.10.3-jdk8")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "com.github.chicoferreira.stockchecker.MainKt"
}

tasks.test {
    useJUnitPlatform()
}