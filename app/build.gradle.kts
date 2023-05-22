import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    kotlin("android")
}

android {
    compileSdk = 33

    defaultConfig {
        versionCode = 2
        versionName = "1.1"
        applicationId = "com.example.fri"

        minSdk = 21
        targetSdk = 33
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.20")
    implementation("androidx.fragment:fragment:1.5.7")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Import the Firebase BoM (see: https://firebase.google.com/docs/android/learn-more#bom)
    implementation(platform("com.google.firebase:firebase-bom:32.0.0"))

    // Firebase Cloud Messaging (Kotlin)
    implementation("com.google.firebase:firebase-messaging-ktx")

    add("alphaImplementation",(project(":lib-alpha")))
    add("betaImplementation",(project(":lib-beta")))
}
