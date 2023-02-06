import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    `java-library`
    kotlin("jvm") version "1.8.10"
    id("io.papermc.paperweight.userdev") version "1.4.1"
    id("xyz.jpenilla.run-paper") version "2.0.1" // Adds runServer and runMojangMappedServer tasks for testing
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2" // Generates plugin.yml
}

group = "io.papermc.paperweight"
version = "1.0.0-SNAPSHOT"
description = "Test plugin for paperweight-userdev"

java {
    // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
    api("com.mmorrell:solanaj:1.14")
    api("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    paperDevBundle("1.19.3-R0.1-SNAPSHOT")
}

tasks {
    // Configure reobfJar to run when invoking the build task
    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
}

// Configure plugin.yml generation
bukkit {
    load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
    main = "io.papermc.paperweight.testplugin.TestPlugin"
    apiVersion = "1.19"
    authors = listOf("Author")
    libraries = listOf(
        "org.jetbrains.kotlin:kotlin-stdlib:1.8.10",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4",
        "com.mmorrell:solanaj:1.14"
    )
}
