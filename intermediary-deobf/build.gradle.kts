
buildscript {
    repositories {
        maven {
            name = "Architectury"
            url = uri("https://maven.architectury.dev")
        }
    }
}

plugins {
    id ("dev.architectury.loom") version "1.4-SNAPSHOT"
    //id ("org.jetbrains.kotlin.jvm") version "1.9.22"
}

loom{
    //forge{

    //}
}

repositories{
    maven { setUrl("https://maven.parchmentmc.org") }

    maven{setUrl("https://maven.shedaniel.me/")}
    maven{setUrl("https://maven.terraformersmc.com/")}
    maven{setUrl("https://maven.blamejared.com")}
    maven{setUrl("https://api.modrinth.com/maven")}
    maven{setUrl("https://mvn.devos.one/snapshots/")}
    maven{setUrl("https://maven.cafeteria.dev/releases")}
    maven{setUrl("https://jitpack.io/")}
    maven{setUrl("https://maven.jamieswhiteshirt.com/libs-release")}
    maven{setUrl("https://cursemaven.com")}
    maven{setUrl("https://maven.cafeteria.dev/releases")}
    maven{setUrl("https://maven.tterrag.com/");content { includeGroup("com.jozufozu.flywheel") }}
    maven{setUrl("https://maven.ladysnake.org/releases")}
}


val revelationary_version: String by project
val minecraft_version: String by project

dependencies{
    //minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings("net.fabricmc:yarn:1.20.1+build.10:v2")//loom.officialMojangMappings())//("org.parchmentmc.data:parchment-1.20.1:2023.09.03@zip"))
    //forge(group = "net.minecraftforge", name = "forge", version = "1.20.1-47.2.20")

    //modImplementation("maven.modrinth:Revelationary:${revelationary_version}"){}
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(19))
}



