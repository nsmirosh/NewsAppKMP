import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.serialization)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    id("org.kodein.mock.mockmp") version "2.0.0"
    alias(libs.plugins.buildkonfig)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            //Specifying the target JVM version to avoid compilation errors
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        jvmToolchain(17)
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.androidx.ui.android)
            implementation(libs.androidx.core.i18n)
            implementation(libs.play.services.location)
            implementation(libs.accompanist.swiperefresh)

            implementation(libs.androidx.material3.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(libs.kotlinx.datetime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)
            implementation(libs.coil.test)
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kermit)
            api(libs.ktor.serialization)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.negotiation)
            api(libs.compose.webview.multiplatform)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.koin)
            implementation(libs.voyager.screenModel)
            implementation(libs.voyager.tabNavigator)
            implementation(libs.room.runtime)
            implementation(libs.room.sqlite)
            implementation(libs.androidx.datastore.preferences)
            implementation(libs.ktor.resources)


        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

buildkonfig {
    packageName = "nick.mirosh.newsappkmp"

    defaultConfigs {
        buildConfigField(STRING, "API_KEY", "release")
    }

    defaultConfigs("dev") {
        buildConfigField(STRING, "API_KEY", "develop")
    }
}

mockmp {
    onTest {
        withHelper()
    }
}

android {
    namespace = "nick.mirosh.newsappkmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "nick.mirosh.newsappkmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // compose multiplatform
    commonMainApi(libs.moko.permissions)
    ksp(libs.room.compiler)
    debugImplementation(compose.uiTooling)
}
