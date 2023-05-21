import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "gg.jrg.audiminder"
    compileSdk = 33

    defaultConfig {
        applicationId = "gg.jrg.audiminder"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "gg.jrg.audiminder.HiltTestRunner"

        val properties = gradleLocalProperties(rootDir)

        buildConfigField(
            "String",
            "SPOTIFY_CLIENT_ID",
            "\"${properties.getProperty("SPOTIFY_CLIENT_ID")}\""
        )
        buildConfigField(
            "String",
            "SPOTIFY_REDIRECT_URI",
            "\"${properties.getProperty("SPOTIFY_REDIRECT_URI")}\""
        )
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            packaging {
                resources.excludes.add("META-INF/*.kotlin_module")
            }
        }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        dataBinding = true
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

val androidxCoreVersion = "1.10.1"
val appCompatVersion = "1.6.1"
val materialVersion = "1.9.0"
val constraintLayoutVersion = "2.1.4"
val navigationVersion = "2.5.3"
val junitExtVersion = "1.1.5"
val testRunnerVersion = "1.5.2"
val lifecycleVersion = "2.6.1"
val securityCryptoVersion = "1.1.0-alpha06"
val junitVersion = "4.13.2"
val coroutinesVersion = "1.6.4"
val espressoVersion = "3.5.1"
val spotifyWebApiKotlinVersion = "3.8.8"
val timberVersion = "5.0.1"
val recyclerviewVersion = "1.3.0"
val coroutinesAndroidVersion = "1.6.4"
val turbineVersion = "0.12.3"
val mockitoCoreVersion = "5.2.0"
val mockitoKotlinVersion = "4.1.0"
val fragmentTestingVersion = "1.6.0-rc01"
val hiltVersion = "2.46"
val testCoreVersion = "1.5.0"
val testRulesVersion = "1.5.0"
val truthVersion = "1.5.0"
val browserVersion = "1.5.0"
val roomVersion = "2.5.1"
val coilVersion = "2.3.0"


dependencies {
    // AndroidX
    implementation("androidx.core:core-ktx:$androidxCoreVersion")
    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    implementation("androidx.recyclerview:recyclerview:$recyclerviewVersion")
    implementation("androidx.browser:browser:$browserVersion")

    // Material
    implementation("com.google.android.material:material:$materialVersion")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-testing:$navigationVersion")

    // Unit Testing
    testImplementation("junit:junit:$junitVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    testImplementation("app.cash.turbine:turbine:$turbineVersion")
    testImplementation("org.mockito:mockito-core:$mockitoCoreVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation("androidx.room:room-testing:$roomVersion")
    debugImplementation("androidx.fragment:fragment-testing:$fragmentTestingVersion")

    // Instrumented Testing
    androidTestImplementation("androidx.test.ext:junit:$junitExtVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
    androidTestImplementation("org.mockito:mockito-android:$mockitoCoreVersion")
    androidTestImplementation("androidx.test:core:$testCoreVersion")
    androidTestImplementation("androidx.test:runner:$testRunnerVersion")
    androidTestImplementation("androidx.test:rules:$testRulesVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitExtVersion")
    androidTestImplementation("androidx.test.ext:truth:$truthVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")
    androidTestImplementation("androidx.test.ext:truth:$truthVersion")

    // Spotify
    implementation("com.adamratzman:spotify-api-kotlin-core:$spotifyWebApiKotlinVersion")

    // Timber
    implementation("com.jakewharton.timber:timber:$timberVersion")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesAndroidVersion")

    // Hilt (Dagger)
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    testImplementation("androidx.room:room-testing:$roomVersion")

    // Coil
    implementation("io.coil-kt:coil:$coilVersion")
}

configurations.all {
    resolutionStrategy {
        force("androidx.security:security-crypto-ktx:$securityCryptoVersion")
    }
}

kapt {
    correctErrorTypes = true
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

allprojects {
    tasks.withType<JavaCompile>().configureEach {
        options.compilerArgs.add("-Xlint:unchecked")
        options.compilerArgs.add("-Xlint:deprecation")
    }
}