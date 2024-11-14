
repositories {
    google()
    mavenCentral() // Add this if it's missing
    // any other repositories you need
}
plugins {
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}