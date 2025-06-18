plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.henrick.stock"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.henrick.stock"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose UI
    implementation(libs.androidx.activity.compose)
    implementation(libs.viewmodel.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.viewmodel)
    implementation(libs.androidx.viewmodel.ktx)
    implementation(libs.androidx.runtime.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // hilt
    implementation(libs.hilt.android)
    testImplementation(libs.hilt.android.testing)
    kapt(libs.hilt.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.codegen)

    // test
    // unit test
    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.mockito.kotlin) // Kotlin friendly Mockito
    testImplementation(libs.coroutines.test)
    testImplementation(libs.core.testing) // For testing LiveData

    // android Test
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.rules)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.compiler)
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(libs.mockito.kotlin) // Kotlin friendly Mockito
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.core.testing) // For testing LiveData

    // Import the Compose BOM for test
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}