import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.jg.citysearch"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jg.citysearch"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val localProperties = Properties()
        localProperties.load(FileInputStream(rootProject.file("local.properties")))
        val weatherApiKey = localProperties.getProperty("weather_api_key") ?: ""
        buildConfigField(
            "String",
            "WEATHER_API_KEY",
            "\"$weatherApiKey\""
        )
        val googleApiKey = localProperties.getProperty("google_maps_key") ?: ""
        manifestPlaceholders["googleMapsApiKey"] = googleApiKey
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)

    // Kotlin coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.gsonConverter)
    implementation(libs.loggingInterceptor)

    // DataStore
    implementation(libs.androidx.data.store)
    implementation(libs.androidx.data.store.preferences)

    // Paging
    implementation(libs.paging.compose)
    testImplementation(libs.paging.testing)

    // Map
    implementation(libs.maps.compose)

    // Play Services
    implementation(libs.play.services.maps)

    // Junit
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    // Espresso
    androidTestImplementation(libs.androidx.espresso.core)
    // Coroutines testing
    testImplementation(libs.kotlinx.coroutines.test)
    // Mockito + MockK
    testImplementation(libs.mockito)
    testImplementation(libs.mockk)
    // Core testing
    testImplementation(libs.core.testing)
    // Thuth
    testImplementation(libs.truth)
    // Compose UI Tests
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(kotlin("test"))
}