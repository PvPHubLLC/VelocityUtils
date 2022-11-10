import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("kapt") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    `maven-publish`
}

group = "co.pvphub"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://nexus.velocitypowered.com/repository/maven-public/")
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
}

dependencies {
    testImplementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")
    compileOnly("com.velocitypowered:velocity-api:3.1.0")
    kapt("com.velocitypowered:velocity-api:3.1.0")
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
    archiveBaseName.set("twoauthmc")
    mergeServiceFiles()
}