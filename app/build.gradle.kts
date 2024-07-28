plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.scanner"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.scanner"
        minSdk = 24
        targetSdk = 34
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
}

dependencies {

    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.google.code.gson:gson:2.10")


    implementation ("androidx.appcompat:appcompat:1.2.0")
    implementation ("com.google.android.material:material:1.3.0")

    implementation ("org.apache.httpcomponents:httpclient-android:4.3.5.1")
    implementation ("org.json:json:20210307")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}