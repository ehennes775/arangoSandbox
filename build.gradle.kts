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

        val commonMain by getting

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