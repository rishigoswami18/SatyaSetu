buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://maven.mappls.com/repository/mappls/") }
    }
    dependencies {
        classpath("com.mappls.services:mappls-services:1.0.0")
    }
}

plugins {
    id("com.android.application") version "8.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22" apply false
}
