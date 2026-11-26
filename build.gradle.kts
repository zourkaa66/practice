import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(ktorLibs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.detekt)
}

group = "com.zainab.library"
version = "1.0.0-SNAPSHOT"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.netty)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.serialization.kotlinx.json)
    implementation(libs.logback.classic)
    implementation(libs.kotlin.logging)
    implementation(ktorLibs.server.callId)
    implementation(ktorLibs.server.callLogging)

    testImplementation(kotlin("test"))
    testImplementation(ktorLibs.server.testHost)
}

tasks.test {
    useJUnitPlatform()
}

kotlinter {
    ignoreFormatFailures = false
    ignoreLintFailures = false
    reporters = arrayOf("checkstyle", "plain")
}

detekt {
    buildUponDefaultConfig = true
    config.setFrom(file("detekt.yml"))
}