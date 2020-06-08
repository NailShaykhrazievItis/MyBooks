val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project

plugins {
    application
    kotlin("jvm") version "1.3.71"
}

group = "com.example"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")

    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("io.ktor:ktor-server-core:$ktor_version")
    compile("io.ktor:ktor-html-builder:$ktor_version")
    compile("io.ktor:ktor-auth:$ktor_version")
    compile("io.ktor:ktor-gson:$ktor_version")
    compile("io.ktor:ktor-client-core:$ktor_version")
    compile("io.ktor:ktor-client-core-jvm:$ktor_version")
    compile("io.ktor:ktor-auth-jwt:$ktor_version")

    compile("ch.qos.logback:logback-classic:$logback_version")

//    compile 'org.litote.kmongo:kmongo:4.0.1'
//    compile("org.mongodb:mongodb-driver:3.12.2")

    compile("org.jetbrains.exposed", "exposed-core", exposed_version)
    compile("org.jetbrains.exposed", "exposed-dao", exposed_version)
    compile("org.jetbrains.exposed", "exposed-jdbc", exposed_version)
    compile("org.jetbrains.exposed", "exposed-jodatime", exposed_version)
    compile("org.postgresql:postgresql:42.2.8")

    compile("org.koin:koin-ktor:2.1.5")

    compile("ch.qos.logback:logback-classic:1.2.3")

    testCompile("io.ktor:ktor-server-tests:$ktor_version")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")
