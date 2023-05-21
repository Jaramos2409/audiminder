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

dependencies {
    // AndroidX
    implementation(libs.androidx.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation(libs.browser)

    // Material
    implementation(libs.material)

    // Navigation
    implementation(libs.navigationFragment)
    implementation(libs.navigationUi)
    implementation(libs.navigationTesting)

    // Unit Testing
    testImplementation(libs.junitExt)
    testImplementation(libs.kotlinxCoroutinesTest)
    testImplementation(libs.turbine)
    testImplementation(libs.mockitoCore)
    testImplementation(libs.mockitoKotlin)
    debugImplementation(libs.fragmentTesting)

    // Instrumented Testing
    androidTestImplementation(libs.espessoCore)
    androidTestImplementation(libs.mockitoAndroid)
    androidTestImplementation(libs.testCore)
    androidTestImplementation(libs.testRunner)
    androidTestImplementation(libs.testRules)
    androidTestImplementation(libs.testExtJunit)
    androidTestImplementation(libs.truth)

    // Spotify
    implementation(libs.spotifyWebApiKotlin)

    // Timber
    implementation(libs.timber)

    // Coroutines
    implementation(libs.coroutinesAndroid)

    // Hilt (Dagger)
    implementation(libs.hiltAndroid)
    kapt(libs.hiltCompiler)
    androidTestImplementation(libs.hiltAndroidTesting)
    kaptAndroidTest(libs.hiltCompiler)

    // Room
    implementation(libs.roomRuntime)
    implementation(libs.roomKtx)
    ksp(libs.roomCompiler)
    testImplementation(libs.roomTesting)

    // Coil
    implementation(libs.coil)
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