import sun.tools.jar.resources.jar

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(ktorLibs.plugins.ktor)

}

group = "ir.andy"
version = "1.0.0-SNAPSHOT"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
    jvmToolchain(21)
}


tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = "io.ktor.server.netty.EngineMain"
    }
    archiveFileName.set("app.jar")
}


dependencies {
    implementation(ktorLibs.server.config.yaml)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.netty)
    implementation(libs.logback.classic)
    implementation("com.github.bbottema:java-socks-proxy-server:4.2.0")
    testImplementation(kotlin("test"))
    testImplementation(ktorLibs.server.testHost)
}
