plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }

    namespace = "com.example.fri.lib.beta"
}

dependencies {
    implementation(project(":lib-api"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.20")
}
