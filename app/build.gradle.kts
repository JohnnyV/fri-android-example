import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 31

    defaultConfig {
        versionCode = 1
        versionName = "1.0"
        applicationId = "com.example.fri"

        minSdk = 21
        targetSdk = 31
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
    implementation("androidx.fragment:fragment:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
}
