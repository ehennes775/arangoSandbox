import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") version "1.5.31"
    application
}

group = "com.github.ehennes775"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    
    sourceSets {
        jvm()

        val commonMain by getting {
            dependencies {
                implementation("com.arangodb:arangodb-java-driver:6.14.0")
                implementation("com.arangodb:jackson-dataformat-velocypack:2.0.0")
                implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.5")
                implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
                implementation("org.slf4j:slf4j-api:1.7.32")
                implementation("org.slf4j:slf4j-simple:1.7.32")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val jvmMain by getting {
            dependsOn(commonMain)
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}