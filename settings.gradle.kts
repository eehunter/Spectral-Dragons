pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            name = "MinecraftForge"
            url = uri("https://maven.minecraftforge.net/")
        }
        maven { url = uri("https://maven.parchmentmc.org") }

        maven {
            name = "sponge"
            url = uri("https://repo.spongepowered.org/maven")
        }
        maven {
            name = "Architectury"
            url = uri("https://maven.architectury.dev")
        }
    }

}

plugins {
    id ("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

include("intermediary-deobf")