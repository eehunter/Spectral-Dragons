
buildscript {
    repositories {
        mavenCentral()
        maven {
            name = "Architectury"
            url = uri("https://maven.architectury.dev")
        }
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
    }

    dependencies {
        classpath(enforcedPlatform("com.google.code.gson:gson:2.10"))
    //    classpath("com.google.code.gson:gson:2.10.1")
    }
}

plugins {
    id ("dev.architectury.loom") version "1.5-SNAPSHOT"
    //id("fabric-loom") version "1.5-SNAPSHOT"
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

val cloth_config_version: String by project.rootProject
val cardinal_components_version: String by project.rootProject

val patchouli_version: String by project.rootProject
val modmenu_version: String by project.rootProject
val trinkets_version: String by project.rootProject

val revelationary_version: String by project.rootProject
val matchbooks_version: String by project.rootProject
val additional_entity_attributes_version: String by project.rootProject
val arrowhead_version: String by project.rootProject
val fractal_version: String by project.rootProject
val dimensional_reverb_version: String by project.rootProject

val spectrum_version: String by project.rootProject

val minecraft_version: String by project.rootProject
val parchment_version: String by project

dependencies{
    //minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    implementation("com.google.code.gson:gson:2.10")

    minecraft("com.mojang:minecraft:$minecraft_version")
    //mappings("net.fabricmc:yarn:1.20.1+build.10:v2")//loom.officialMojangMappings())//("org.parchmentmc.data:parchment-1.20.1:2023.09.03@zip"))
    mappings (loom.layered {
        //mappings("net.fabricmc:yarn:1.20.1+build.10:v2")
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-$minecraft_version:${parchment_version}@zip")
    })
    //forge(group = "net.minecraftforge", name = "forge", version = "1.20.1-47.2.20")

    modImplementation("maven.modrinth:Revelationary:${revelationary_version}"){}

    modImplementation("com.github.Noaaan:Matchbooks:${matchbooks_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}

    modImplementation("maven.modrinth:AdditionalEntityAttributes:${additional_entity_attributes_version}"){exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
    modImplementation("com.github.DaFuqs:Arrowhead:${arrowhead_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
    modImplementation("maven.modrinth:fractal-lib:${fractal_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
    modImplementation("com.github.DaFuqs:DimensionalReverb:${dimensional_reverb_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}

    modImplementation("me.shedaniel.cloth:cloth-config-fabric:${cloth_config_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}

    modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-base:${cardinal_components_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
    modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-scoreboard:${cardinal_components_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
    modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-level:${cardinal_components_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}

    modImplementation("vazkii.patchouli:Patchouli:${patchouli_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
    modImplementation("com.terraformersmc:modmenu:${modmenu_version}"){exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}

    modImplementation("dev.emi:trinkets:${trinkets_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}

    modImplementation("maven.modrinth:Spectrum:${spectrum_version}"){exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(19))
}



