plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id ("kotlin-parcelize")
    id ("kotlin-kapt")
    id("com.google.gms.google-services")
}
android {
    namespace = "com.example.musicapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.musicapp"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        //noinspection DataBindingWithoutKapt
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //noinspection UseTomlInstead
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    //noinspection UseTomlInstead
    implementation ("androidx.preference:preference-ktx:1.2.1")
    //noinspection UseTomlInstead
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    //noinspection UseTomlInstead
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //noinspection UseTomlInstead
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //noinspection UseTomlInstead
    implementation ("com.google.code.gson:gson:2.10.1")
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    //noinspection UseTomlInstead
    implementation ("io.insert-koin:koin-android:3.5.6")
    //noinspection UseTomlInstead
//    kapt ("com.android.databinding:compiler:3.1.4")

}