plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("com.google.dagger.hilt.android") version "2.55" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23" // o la versión que uses
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    namespace = "com.s10plusArtifacts.coreSdk"

    compileSdk = 35

    defaultConfig {
        minSdk = 29
        targetSdk = 35
        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        debug {
            manifestPlaceholders["appName"] = "Amad-[D]"
            buildConfigField("String", "BASE_URL", "\"https://s10plus.com:8443/wsdemos/rest/action/\"")
            buildConfigField("String", "BASE_URL_SEPOMEX", "\"https://api.s10plus.com/s10-sepomex.php/\"")
        }
        release {
            isMinifyEnabled = false
            manifestPlaceholders["appName"] = "Amad"
            buildConfigField("String", "BASE_URL", "\"https://s10plus.com:8443/ws/rest/action/\"")
            buildConfigField("String", "BASE_URL_SEPOMEX", "\"https://api.s10plus.com/s10-sepomex.php/\"")
        }
        create("qa") {
            initWith(buildTypes.getByName("debug"))
            manifestPlaceholders["appName"] = "Amad-[Q]"
            buildConfigField("String", "BASE_URL", "\"https://s10plus.com:8443/wsqa/rest/action/\"")
            buildConfigField("String", "BASE_URL_SEPOMEX", "\"https://api.s10plus.com/s10-sepomex.php/\"")
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
        buildConfig = true
    }
}
kapt {
    correctErrorTypes = true
}
dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    api("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    api("androidx.activity:activity-compose:1.9.1")
    api(platform("androidx.compose:compose-bom:2024.06.00"))
    api("androidx.compose.ui:ui")
    api("androidx.compose.ui:ui-graphics")
    api("androidx.compose.ui:ui-tooling")
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.google.code.gson:gson:2.10.1")
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    api("com.jakewharton.timber:timber:5.0.1")
    api("com.squareup.okhttp3:okhttp:4.9.2")
    api("com.squareup.okhttp3:logging-interceptor:4.9.2")
    api("androidx.datastore:datastore-preferences:1.1.1")
    api("com.auth0.android:jwtdecode:2.0.2")
    api("androidx.compose.runtime:runtime-livedata:1.6.8")
    api("androidx.navigation:navigation-compose:2.8.0-beta06")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    api("com.airbnb.android:lottie-compose:6.5.0")
    api("com.github.bumptech.glide:compose:1.0.0-beta01")
    api("com.google.accompanist:accompanist-permissions:0.35.2-beta")
    api("com.google.accompanist:accompanist-pager:0.28.0")
    api("com.google.accompanist:accompanist-pager-indicators:0.28.0")
    implementation("com.google.android.gms:play-services-location:21.1.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.compose.material:material-icons-extended:1.7.4")
    implementation("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-android-compiler:2.51")
    api("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.blankj:utilcodex:1.31.1")
    api("io.reactivex.rxjava2:rxandroid:2.1.1")
    api("io.reactivex.rxjava2:rxjava:2.2.21")
    api("io.reactivex.rxjava3:rxjava:3.1.0")

    api ("androidx.media3:media3-exoplayer:1.5.0")
    api ("androidx.media3:media3-ui:1.5.0")
    implementation ("androidx.media3:media3-common:1.5.0")
    api("androidx.compose.foundation:foundation:1.7.5")
    api("com.google.crypto.tink:tink-android:1.8.0")
    api("androidx.security:security-crypto:1.1.0-alpha06")
}

publishing{
    publications {
        create<MavenPublication>("release"){
            groupId= "com.s10plusArtifacts"
            artifactId = "core-sdk-prod"
            version = "1.0.0"

            afterEvaluate{
                from(components["release"])
            }
        }
    }

}


