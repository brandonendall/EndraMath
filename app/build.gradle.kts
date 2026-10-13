plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }
android {
    namespace = "com.endramath.app"
    compileSdk = 35
    useLibrary("android.test.runner")
    useLibrary("android.test.base")
    defaultConfig {
        applicationId = "com.endramath.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "1.0-checkpoint-a-theme"
        testInstrumentationRunner = "android.test.InstrumentationTestRunner"
    }
    signingConfigs.getByName("debug") {
        storeFile = rootProject.file("config/endramath-debug.keystore")
        storePassword = "android"
        keyAlias = "androiddebugkey"
        keyPassword = "android"
    }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
    kotlinOptions { jvmTarget = "17" }
}
dependencies { testImplementation("junit:junit:4.13.2") }
