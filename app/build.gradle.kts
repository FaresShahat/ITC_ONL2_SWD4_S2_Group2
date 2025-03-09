plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("kotlin-kapt")
}

android {
    namespace = "event.countdown"
    compileSdk = 35

    defaultConfig {
        applicationId = "event.countdown"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
dependencies {
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.1-alpha")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation (libs.androidx.runtime)
    implementation (libs.androidx.runtime.livedata)
    implementation (libs.androidx.navigation.compose)
    implementation (libs.androidx.hilt.navigation.compose)
    implementation (libs.androidx.hilt.navigation.fragment)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

        // Room Database
        implementation (libs.androidx.room.runtime)

        // لتشغيل Coroutines مع Room
        implementation (libs.androidx.room.ktx)

        // ViewModel و LiveData لدعم الـ UI
        implementation (libs.androidx.lifecycle.viewmodel.ktx)
        implementation (libs.androidx.lifecycle.livedata.ktx)

        // دعم Kotlin Coroutines
        implementation (libs.kotlinx.coroutines.android)
        implementation (libs.jetbrains.kotlinx.coroutines.core)

    //The calender
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    //The calender V2
    implementation("com.google.accompanist:accompanist-pager:0.28.0")

    implementation ("androidx.compose.material3:material3:1.1.2") // For Material 3
    implementation ("androidx.compose.material:material:1.5.4") // For Material 2
}
