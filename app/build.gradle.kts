import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
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

    signingConfigs {
        create("example") {
            storeFile = file("keystore.jks")
            keyAlias = "key"
            keyPassword = "example"
            storePassword = "example"
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("example")
            isMinifyEnabled = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    flavorDimensions += "distribution"
    productFlavors {
        create("alpha") {
            dimension = "distribution"
        }
        create("beta") {
            dimension = "distribution"
        }
    }
}

dependencies {
    implementation(project(":lib-api"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.21")
    implementation("androidx.fragment:fragment:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")

    // Import the Firebase BoM (see: https://firebase.google.com/docs/android/learn-more#bom)
    implementation(platform("com.google.firebase:firebase-bom:30.0.1"))

    // Firebase Cloud Messaging (Kotlin)
    implementation("com.google.firebase:firebase-messaging-ktx")

    add("alphaImplementation",(project(":lib-alpha")))
    add("betaImplementation",(project(":lib-beta")))
}
