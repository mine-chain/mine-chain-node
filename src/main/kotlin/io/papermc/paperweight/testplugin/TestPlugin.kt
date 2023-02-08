package io.papermc.paperweight.testplugin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.plugin.java.JavaPlugin
import kotlin.time.Duration.Companion.seconds

class TestPlugin : JavaPlugin(), Listener, CoroutineScope {
    override val coroutineContext = MinecraftCoroutineDispatcher(this)

    override fun onEnable() {
        logger.info("TestPlugin enabled!")

        server.pluginManager.registerEvents(this, this)

        launch {
            logger.info("Creating world_empty")
            emptyWorld = WorldCreator("world_empty")
                .generator(EmptyChunkGenerator)
                .createWorld() ?: throw IllegalStateException("Failed to create world_empty")
            emptyWorld.spawnLocation = emptyWorld.getHighestBlockAt(0, 0).location
            logger.info("Created world_empty")
            server.onlinePlayers.forEach { it.teleport(emptyWorld.spawnLocation) }
        }

        launch {
            while (true) {
                delay(30.seconds)
                server.dispatchCommand(server.consoleSender, ZOMBIE_SPAWN_COMMAND)
            }
        }
    }

    @EventHandler
    fun onLogin(event: PlayerJoinEvent) {
        logger.info("Player joined: ${event.player.name}")
        event.player.teleport(emptyWorld.spawnLocation)
        event.player.sendMessage("Welcome to the empty world!")
    }

    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
      event.respawnLocation = emptyWorld.spawnLocation
    }

    companion object {
        lateinit var emptyWorld: World
        val ZOMBIE_SPAWN_COMMAND
            get() = "execute in minecraft:${emptyWorld.name} run summon zombie -1.5 1 -1.5 {PersistenceRequired:1,ArmorItems:[{},{},{},{Count:1,id:golden_helmet}],CustomName:\"\\\"DAY_MOZGI\\\"\"}"
    }
}
