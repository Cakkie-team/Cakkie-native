@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.cakkie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cakkie"
        minSdk = 24
        targetSdk = 34
        versionCode = 17
        versionName = "1.03.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.ui.viewbinding)
    implementation(libs.shimmer)

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform("androidx.compose:compose-bom:2023.06.01"))
    implementation(libs.compose.animation)
    implementation(libs.compose.material)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.kotlin.stdlib.jdk7)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
//    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // Glide
    implementation(libs.glide.compose)
    //accompanist navigation animation\
//    implementation(libs.accompanist.navigation.animation)

    // Timber
    implementation(libs.timber)

    // dataStore
    implementation(libs.datastore.preferences)

    // Koin
    implementation(libs.koin)
    implementation(libs.koin.worker)

    // livedata
    implementation(libs.livedata)
    implementation(libs.livedata.runtime)

    // viewModel
    implementation(libs.viewmodel.compose)

    // coil
    implementation(libs.coil.compose)

    // room
    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // kotlinx.serialization
    implementation(libs.serialization)

    //destination
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.destinations.core)
    ksp(libs.destinations.ksp)
    implementation(libs.accompanist.pager.indicator)

    //Fuel-core
    implementation(libs.fuel)

    //Fuel-packages
    implementation(libs.fuel.android)
    implementation(libs.fuel.livedata)
    implementation(libs.fuel.coroutines)
    implementation(libs.fuel.kotlinx.serialization)

    //gson type converter
    implementation(libs.gson)

    // file upload
    implementation(libs.uploadservice)

    //location
    implementation(libs.play.services.location)

    //navigation
    implementation(libs.accompanist.navigation.material)

    //video
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.exoplayer.hls)

    //coil
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    //collapsing toolbar
    implementation(libs.toolbar.compose)

    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))

    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)


    //file
    implementation(libs.commons.io)

    //permission
    implementation(libs.accompanist.permissions)

    //glide with shimmer effect
    implementation(libs.landscapist.glide)

    //socket.io
    implementation("io.socket:socket.io-client:2.0.0") {
        exclude("org.json", "json")
    }
//    implementation(libs.engine.io.client)

    //shimmer
    implementation(libs.accompanist.placeholder.material)

    //admob
    implementation(libs.play.services.ads)

    //referrer
    implementation(libs.installreferrer)

}