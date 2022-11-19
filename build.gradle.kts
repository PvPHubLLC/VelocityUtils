import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("kapt") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    `maven-publish`
}

group = "co.pvphub"
version = "-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "PvPHub"
        url = uri("https://maven.pvphub.me/releases")
        credentials {
            username = System.getenv("PVPHUB_MAVEN_USERNAME")
            password = System.getenv("PVPHUB_MAVEN_SECRET")
        }
    }
    maven {
        url = uri("https://jitpack.io")
    }
    maven {
        url = uri("https://mvn.exceptionflug.de/repository/exceptionflug-public/")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.10")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")
    compileOnly("com.velocitypowered:velocity-api:3.1.2-SNAPSHOT")
    compileOnly("com.velocitypowered:velocity-proxy:3.0.1")
//    kapt("com.velocitypowered:velocity-api:3.1.2-SNAPSHOT")
    implementation("me.carleslc.Simple-YAML:Simple-Yaml:1.7.2")
    implementation("io.netty:netty-buffer:5.0.0.Alpha2")
}
sourceSets["main"].resources.srcDir("src/resources/")

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
publishing {
    repositories {
        maven {
            name = "pvphub-releases"
            url = uri("https://maven.pvphub.me/releases")
            credentials {
                username = System.getenv("PVPHUB_MAVEN_USERNAME")
                password = System.getenv("PVPHUB_MAVEN_SECRET")
            }
        }
    }
    publications {
        create<MavenPublication>("velocityutils") {
            from(components["java"])
        }
    }

}
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveBaseName.set("velocityutils")
    mergeServiceFiles()
}