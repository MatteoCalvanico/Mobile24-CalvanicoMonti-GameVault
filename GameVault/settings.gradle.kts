pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io" ) } //Need to use Jitpack and use GitHub libraries
    }
}

rootProject.name = "Mobile24-CalvanicoMonti-GameVault"
include(":app")
