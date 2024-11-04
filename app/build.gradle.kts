plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.a.acs2"
    compileSdk = 34



    defaultConfig {
        applicationId = "com.a.acs2"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures{
        viewBinding =true
        dataBinding =true
    }

}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.media3.datasource.cronet)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)



    implementation ("androidx.media3:media3-exoplayer:1.3.1")

/*    implementation ("androidx.media3:media3-exoplayer-dash:1.3.1")
    implementation ("androidx.media3:media3-exoplayer-hls:1.3.1")*/
    implementation ("androidx.media3:media3-ui:1.3.1")
    implementation ("androidx.media3:media3-datasource-okhttp:1.3.1")
    implementation ("androidx.media3:media3-datasource:1.3.1")
    implementation ("androidx.media3:media3-datasource-cronet:1.3.1")
    implementation ("org.chromium.net:cronet-common:119.6045.31")
//
    implementation ("com.otaliastudios:zoomlayout:1.9.0")
    implementation ("com.google.android.material:material:1.12.0")
//
//    implementation ("com.google.android.exoplayer:exoplayer:2.19.1")
//    implementation ("com.google.android.exoplayer:exoplayer-core:r2.19.1")
//    implementation ("com.google.android.exoplayer:exoplayer-dash:r2.19.1")
//    implementation ("com.google.android.exoplayer:exoplayer-hls:r2.19.1")




    implementation ("androidx.recyclerview:recyclerview:1.3.2")
//    implementation ("com.mindorks.android:prdownloader:0.6.0")
    implementation ("com.airbnb.android:lottie:3.4.0")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.google.android.material:material:1.12.0")
    implementation ("com.facebook.shimmer:shimmer:0.5.0")
    //implementation ("io.github.krupen:fabulousfilter:0.0.6")
    //implementation (" 'androidx.core:core:1.7.0")



    implementation ("com.github.rubensousa:previewseekbar:3.1.1")
    // Media3 extension that contains a TimeBar.
    implementation ("com.github.rubensousa:previewseekbar-media3:1.1.1.0")
    // (Deprecated) ExoPlayer Extension that contains a TimeBar.
    implementation ("com.github.rubensousa:previewseekbar-exoplayer:2.18.1.0")



    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation ("androidx.fragment:fragment:1.8.2")
    implementation ("androidx.recyclerview:recyclerview-selection:1.1.0")





}