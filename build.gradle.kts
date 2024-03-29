@file:Suppress("PropertyName")

import net.minecraftforge.gradle.common.util.MavenArtifactDownloader
import net.minecraftforge.gradle.mcp.ChannelProvider
import net.minecraftforge.gradle.mcp.ChannelProvidersExtension
import net.minecraftforge.gradle.mcp.MCPRepo
import org.gradle.internal.impldep.org.joda.time.DateTime
import org.gradle.internal.impldep.org.joda.time.LocalDateTime
import java.text.DateFormat
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*


buildscript {
    repositories {
        mavenCentral()
        maven {
            name = "sponge"
            setUrl("https://repo.spongepowered.org/maven")
        }
    }
    dependencies {

        classpath(enforcedPlatform("com.google.code.gson:gson:2.10"))
        classpath ("org.ow2.asm:asm-tree:9.5")
        classpath ("org.spongepowered:mixingradle:0.7-SNAPSHOT"){
            exclude("org.ow2")
            exclude("com.google.code.gson")
        }

    }
    configurations.all{
        //classpath{
            resolutionStrategy{
                force("com.google.code.gson:gson:2.10.1")
            }
        //}
    }
}

plugins {
    //id ("eclipse")
    id ("idea")
    id ("maven-publish")
    id ("net.minecraftforge.gradle") version "[6.0,6.2)"
    id ("org.spongepowered.mixin") version "0.7-SNAPSHOT"
    id ("org.parchmentmc.librarian.forgegradle") version "1.+"


    // Adds the Kotlin Gradle plugin
    id ("org.jetbrains.kotlin.jvm") version "1.9.22"
    // OPTIONAL Kotlin Serialization plugin
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
}

val mod_version: String by properties
val mod_group_id: String by properties
val mod_id: String by properties

version = mod_version
group = mod_group_id

base {
    //archivesName = mod_id
}


// Mojang ships Java 17 to end users in 1.18+, so your mod should target Java 17.
//java.toolchain.languageVersion = JavaLanguageVersion.of(17)

val mapping_channel: String by properties
val mapping_version: String by properties

/*object YarnChannelProvider: ChannelProvider {
    override fun getChannels(): MutableSet<String> {
        println("channels")
        return mutableSetOf("yarn")
    }

    override fun getMappingsFile(mcpRepo: MCPRepo, project: Project, channel: String, version: String): File? {
        val desc = "net.fabricmc:yarn:$version:v2@zip"
        println("get$desc")
        return MavenArtifactDownloader.manual(project, desc, false);
    }

}*/
project.extensions.getByType(ChannelProvidersExtension::class.java).run {
    //addProvider(YarnChannelProvider)
}

println ("Java: ${System.getProperty ("java.version")}, JVM: ${System.getProperty ("java.vm.version")} (${System.getProperty ("java.vendor")}), Arch: ${System.getProperty ("os.arch")}")
minecraft {
    // The mappings can be changed at any time and must be in the following format.
    // Channel:   Version:
    // official   MCVersion             Official field/method names from Mojang mapping files
    // parchment  YYYY.MM.DD-MCVersion  Open community-sourced parameter names and javadocs layered on top of official
    //
    // You must be aware of the Mojang license when using the "official" or "parchment" mappings.
    // See more information here: https://github.com/MinecraftForge/MCPConfig/blob/master/Mojang.md
    //
    // Parchment is an unofficial project maintained by ParchmentMC, separate from MinecraftForge
    // Additional setup is needed to use their mappings: https://parchmentmc.org/docs/getting-started
    //
    // Use non-default mappings at your own risk. They may not always work.
    // Simply re-run your setup task after changing the mappings to update your workspace.


    //println("Yarn channel provider: ${channelProviders.getProvider("yarn")}")


    mappings (mapping_channel, mapping_version)

    // When true, this property will have all Eclipse/IntelliJ IDEA run configurations run the "prepareX" task for the given run configuration before launching the game.
    // In most cases, it is not necessary to enable.
    // enableEclipsePrepareRuns = true
    // enableIdeaPrepareRuns = true

    // This property allows configuring Gradle"s ProcessResources task(s) to run on IDE output locations before launching the game.
    // It is REQUIRED to be set to true for this template to function.
    // See https://docs.gradle.org/current/dsl/org.gradle.language.jvm.tasks.ProcessResources.html
    copyIdeResources.set(true)

    // When true, this property will add the folder name of all declared run configurations to generated IDE run configurations.
    // The folder name can be set on a run configuration using the "folderName" property.
    // By default, the folder name of a run configuration is the name of the Gradle project containing it.
    // generateRunFolders = true

    // This property enables access transformers for use in development.
    // They will be applied to the Minecraft artifact.
    // The access transformer file can be anywhere in the project.
    // However, it must be at "META-INF/accesstransformer.cfg" in the final mod jar to be loaded by Forge.
    // This default location is a best practice to automatically put the file in the right place in the final jar.
    // See https://docs.minecraftforge.net/en/latest/advanced/accesstransformers/ for more information.
    // accessTransformer = file("src/main/resources/META-INF/accesstransformer.cfg")

    // Default run configurations.
    // These can be tweaked, removed, or duplicated as needed.
    runs {
        // applies to all the run configs below
        configureEach {
            workingDirectory (project.file("run"))

            // Recommended logging data for a userdev environment
            // The markers can be added/remove as needed separated by commas.
            // "SCAN": For mods scan.
            // "REGISTRIES": For firing of registry events.
            // "REGISTRYDUMP": For getting the contents of all registries.
            property ("forge.logging.markers", "REGISTRIES")

            // Recommended logging level for the console
            // You can set various levels here.
            // Please read: https://stackoverflow.com/questions/2031163/when-to-use-the-different-log-levels
            property ("forge.logging.console.level", "debug")

            mods {
                create(mod_id) {

                    source (sourceSets.main.get())
                }
            }
        }

        create("client") {
            // Comma-separated list of namespaces to load gametests from. Empty = all namespaces.

            property ("forge.enabledGameTestNamespaces", mod_id)
        }

        create("server") {
            property ("forge.enabledGameTestNamespaces", mod_id)
            args ("--nogui")
        }

        // This run config launches GameTestServer and runs all registered gametests, then exits.
        // By default, the server will crash when no gametests are provided.
        // The gametest system is also enabled by default for other run configs under the /test command.
        create("gameTestServer") {
            property ("forge.enabledGameTestNamespaces", mod_id)
        }

        create("data") {
            // example of overriding the workingDirectory set in configureEach above
            //workingDirectory (project.file("run-data"))

            // Specify the modid for data generation, where to output the resulting resource, and where to look for existing resources.
            args ("--mod", mod_id, "--all", "--output", file("src/generated/resources/"), "--existing", file("src/main/resources/"))
        }
    }
    //println("Yarn channel provider: ${channelProviders.getProvider("yarn")}")
}

// Include resources generated by data generators.
//sourceSets.main.resources { srcDir ("src/generated/resources") }

repositories {
    // Put repositories for dependencies here
    // ForgeGradle automatically adds the Forge maven and Maven Central for you

    // If you have mod jar dependencies in ./libs, you can declare them as a repository like so.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html#sub:flat_dir_resolver
    // flatDir {
    //     dir "libs"
    // }
    flatDir { dir ("libs") }


    maven {
        name = "Sinytra"
        setUrl("https://maven.su5ed.dev/releases")
    }


    maven {
        name = "Kotlin for Forge"
        setUrl("https://thedarkcolour.github.io/KotlinForForge/")
        content { includeGroup ("thedarkcolour") }
    }

    maven{setUrl("https://maven.shedaniel.me/")}
    maven{setUrl("https://maven.terraformersmc.com/")}
    maven{setUrl("https://maven.blamejared.com")}
    maven{setUrl("https://api.modrinth.com/maven")}
    maven{setUrl("https://mvn.devos.one/snapshots/")}
    maven{setUrl("https://maven.cafeteria.dev/releases")}
    maven{setUrl("https://jitpack.io/")}
    //maven{setUrl("https://maven.jamieswhiteshirt.com/libs-release")}
    maven{setUrl("https://cursemaven.com")}
    maven{setUrl("https://maven.cafeteria.dev/releases")}
    maven{setUrl("https://maven.tterrag.com/");content { includeGroup("com.jozufozu.flywheel") }}
    maven{setUrl("https://maven.ladysnake.org/releases")}
    maven{
        setUrl("https://maven.fabricmc.net/")
        content {includeGroup("me.zeroeightsix")}
    }
    maven { setUrl("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/") } // GeckoLib
}

val minecraft_version by properties
val forge_version by properties
val connector_version by properties
val ffapi_version by properties
val connector_extras_version by properties
val geckolib_version by properties
val matchbooks_version by properties
val revelationary_version by properties
val additional_entity_attributes_version by properties
val arrowhead_version by properties
val fractal_version by properties
val dimensional_reverb_version by properties
val spectrum_version by properties
val cloth_config_version by properties
val cardinal_components_version by properties
val patchouli_version by properties
val modmenu_version by properties
val architectury_version by properties
val trinkets_version by properties

dependencies {
    // Specify the version of Minecraft to use.
    // Any artifact can be supplied so long as it has a "userdev" classifier artifact and is a compatible patcher artifact.
    // The "userdev" classifier will be requested and setup by ForgeGradle.
    // If the group id is "net.minecraft" and the artifact id is one of ["client", "server", "joined"],
    // then special handling is done to allow a setup of a vanilla dependency without the use of an external repository.
    minecraft ("net.minecraftforge:forge:${minecraft_version}-${forge_version}")

    // Example mod dependency with JEI - using fg.deobf() ensures the dependency is remapped to your development mappings
    // The JEI API is declared for compile time use, while the full JEI artifact is used at runtime
    // compileOnly fg.deobf("mezz.jei:jei-${mc_version}-common-api:${jei_version}")
    // compileOnly fg.deobf("mezz.jei:jei-${mc_version}-forge-api:${jei_version}")
    // runtimeOnly fg.deobf("mezz.jei:jei-${mc_version}-forge:${jei_version}")

    // Example mod dependency using a mod jar from ./libs with a flat dir repository
    // This maps to ./libs/coolmod-${mc_version}-${coolmod_version}.jar
    // The group id is ignored when searching -- in this case, it is "blank"
    // implementation fg.deobf("blank:coolmod-${mc_version}:${coolmod_version}")

    // For more info:
    // http://www.gradle.org/docs/current/userguide/artifact_dependencies_tutorial.html
    // http://www.gradle.org/docs/current/userguide/dependency_management.html

    minecraftLibrary (fg.deobf("dev.su5ed.sinytra:Connector:${connector_version}"))
    // Add FFAPI dependency (if required)
    //implementation ("dev.su5ed.sinytra.fabric-api:fabric-api:${ffapi_version}")
    compileOnly ("dev.su5ed.sinytra.fabric-api:fabric-api:${ffapi_version}")
    runtimeOnly (fg.deobf("dev.su5ed.sinytra.fabric-api:fabric-api:${ffapi_version}"))

    implementation (fg.deobf("maven.modrinth:connector-extras:${connector_extras_version}"))


    // Dragon Survival
    //implementation fg.deobf("libs:dragonsurvival:${dragonsurvival_version}")
    // Fine then. I'll do it this way.
    implementation (fg.deobf("software.bernie.geckolib:geckolib-forge-${minecraft_version}:${geckolib_version}"))
    implementation (fg.deobf("libs:DragonSurvival:1.20.1-09.01.2024"))
    //fg.deobf(name: "DragonSurvival-1.20.1-09.01.2024")//fileTree(dir: "libs", include: "*.jar"))

    // Install desired Fabric mods
    //implementation ("some.fabric:mod:<version>")
    implementation ("thedarkcolour:kotlinforforge:4.10.0")

    implementation("com.github.Noaaan:Matchbooks:${matchbooks_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
    compileOnly(project(path = ":intermediary-deobf", configuration = "modRuntimeClasspathMainMapped")){exclude("com.google.code.gson")}
    //compileOnly (fg.deobf("maven.modrinth:Revelationary:${revelationary_version}")) {
        //exclude(group = "net.fabricmc", module = null)
        //exclude(group = "net.fabricmc.fabric-api")
        //exclude(group = "com.jamieswhiteshirt")

    //}
    runtimeOnly("maven.modrinth:Revelationary:${revelationary_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
    implementation("maven.modrinth:AdditionalEntityAttributes:${additional_entity_attributes_version}"){exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
    implementation("com.github.DaFuqs:Arrowhead:${arrowhead_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
    implementation("maven.modrinth:fractal-lib:${fractal_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
    implementation("com.github.DaFuqs:DimensionalReverb:${dimensional_reverb_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}

    runtimeOnly("maven.modrinth:Spectrum:${spectrum_version}") {exclude("com.jamieswhiteshirt")}

    implementation("me.shedaniel.cloth:cloth-config-fabric:${cloth_config_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}

    implementation("dev.onyxstudios.cardinal-components-api:cardinal-components-base:${cardinal_components_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
    implementation("dev.onyxstudios.cardinal-components-api:cardinal-components-scoreboard:${cardinal_components_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
    implementation("dev.onyxstudios.cardinal-components-api:cardinal-components-level:${cardinal_components_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}

    implementation("vazkii.patchouli:Patchouli:${patchouli_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
    implementation("com.terraformersmc:modmenu:${modmenu_version}"){exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}
    //implementation("dev.architectury:architectury-forge:${architectury_version}") //{exclude(group: "net.fabricmc");exclude(group: "net.fabricmc.fabric-api");exclude(group: "com.jamieswhiteshirt")}
    implementation("dev.emi:trinkets:${trinkets_version}") {exclude("net.fabricmc");exclude("net.fabricmc.fabric-api");exclude("com.jamieswhiteshirt")}

    //implementation(project(path = ":intermediary-deobf", configuration = "modRuntimeClasspathMainMapped"))

}

tasks{
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
        kotlinOptions.freeCompilerArgs+="-Xcontext-receivers"
    }
    compileJava{
        targetCompatibility = "17"
        sourceCompatibility = "17"

        options.encoding = "UTF-8"
    }
}

mixin{

}

afterEvaluate {
    val cleanArtifactJar: File = MavenArtifactDownloader.generate(project, "net.minecraft:joined:${project.properties["MCP_VERSION"]}:srg", true)?: throw NullPointerException("Cannot find clean minecraft artifact")
    //def cleanArtifactJar = Objects.requireNonNull(MavenArtifactDownloader.generate(project, "net.minecraft:joined:${project.MCP_VERSION}:srg", true), "Cannot find clean minecraft artifact")
    minecraft.runs.configureEach {
        property("connector.clean.path", cleanArtifactJar)
    }
}

// This block of code expands all declared replace properties in the specified resource targets.
// A missing property will result in an error. Properties are expanded using ${} Groovy notation.
// When "copyIdeResources" is enabled, this will also run before the game launches in IDE environments.
// See https://docs.gradle.org/current/dsl/org.gradle.language.jvm.tasks.ProcessResources.html
val minecraft_version_range by properties
val forge_version_range by properties
val loader_version_range by properties
val mod_name by properties
val mod_license by properties
val mod_authors by properties
val mod_description by properties
tasks.named<ProcessResources>("processResources").configure {
    var replaceProperties = mutableMapOf(
            "minecraft_version" to minecraft_version, "minecraft_version_range" to minecraft_version_range,
            "forge_version" to forge_version, "forge_version_range" to forge_version_range,
            "loader_version_range" to loader_version_range,
            "mod_id" to mod_id, "mod_name" to mod_name, "mod_license" to mod_license, "mod_version" to mod_version,
            "mod_authors" to mod_authors, "mod_description" to mod_description,
    )
    inputs.properties (replaceProperties)

    filesMatching(listOf("META-INF/mods.toml", "pack.mcmeta")) {
        expand (replaceProperties + mapOf("project" to project))
    }
}

// Example for how to get properties into the manifest for reading at runtime.
tasks.named<Jar>("jar").configure {
    manifest {
        attributes(mutableMapOf(
                "Specification-Title"      to mod_id,
                "Specification-Vendor"     to mod_authors,
                "Specification-Version"    to "1", // We are version 1 of ourselves
                "Implementation-Title"     to project.name,
                "Implementation-Version"   to project.version,
                "Implementation-Vendor"    to mod_authors,
                "Implementation-Timestamp" to DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ").format(ZonedDateTime.now())
        ))
    }

    // This is the preferred method to reobfuscate your jar file
    finalizedBy ("reobfJar")
}

tasks.register<Copy>("fetchLibs"){
    from(fileTree("intermediary-deobf/build/loom-cache/remapped_working"))
    include("*.jar")

    exclude("*-sources.jar")
    rename {
        println(it)
        it.slice(it.indexOf('-')+1 until it.length).let{n->
            (if(n.startsWith("components-api-")) n.slice("components-api-".length until n.length)
            else n)
        }
    }
    into(project.file("libs"))
}

// However if you are in a multi-project build, dev time needs unobfed jar files, so you can delay the obfuscation until publishing by doing:
// tasks.named("publish").configure {
//     dependsOn "reobfJar"
// }

// Example configuration to allow publishing using the maven-publish plugin
publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            //artifact (jar)
        }
    }
    repositories {
        maven {
            setUrl("file://${project.projectDir}/mcmodsrepo")
        }
    }
}
/*
tasks.withType(JavaCompile).configureEach {
    options.encoding = "UTF-8" // Use the UTF-8 charset for Java compilation
}
*/


java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}