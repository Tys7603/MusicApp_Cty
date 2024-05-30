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

    // Import the BoM for the Firebase platform
    //noinspection UseTomlInstead
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-auth")

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
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    //noinspection UseTomlInstead
    implementation ("io.insert-koin:koin-android:3.5.6")
    //noinspection UseTomlInstead
//    kapt ("com.android.databinding:compiler:3.1.4")
    //noinspection UseTomlInstead
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
    //noinspection UseTomlInstead
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:custom-ui:12.1.0")
    //noinspection UseTomlInstead
    implementation ("com.makeramen:roundedimageview:2.3.0")
    //noinspection UseTomlInstead
    implementation ("com.google.code.gson:gson:2.10.1")
    //noinspection UseTomlInstead
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.28")
    //noinspection UseTomlInstead
    implementation ("androidx.mediarouter:mediarouter:1.7.0")
    //noinspection UseTomlInstead
    implementation ("com.google.android.gms:play-services-cast-framework:21.4.0")
    //noinspection UseTomlInstead
    implementation("com.google.android.gms:play-services-auth:21.2.0")
}