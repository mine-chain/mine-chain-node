package github.minechain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.plugin.java.JavaPlugin
import kotlin.time.Duration.Companion.seconds

class MinechainNodePlugin : JavaPlugin(), Listener, CoroutineScope {
  override val coroutineContext = MinecraftCoroutineDispatcher(this)
  override fun onLoad() {
    logger.info(String.format("Loaded %s", this.dataFolder.path))
  }

  override fun onEnable() {
    logger.info("Enabled")
    server.pluginManager.registerEvents(this, this)
    server.commandMap.register("", ExampleCommand())
    launch {
      for (name in listOf("lobby", "arena")) {
        logger.info(String.format("Creating world '%s'", name))
        val world = WorldCreator(name)
          .seed(42L)
          .generator(ChunkGenerator)
          .type(WorldType.FLAT)
          .createWorld() ?: throw IllegalStateException(String.format("Failed to create world '%s'", name))
        world.save()
        logger.info(String.format("Created world '%s'", name))
      }
      lobby = server.getWorld("lobby") ?: throw IllegalStateException("Failed getting world 'lobby")
      lobby.spawnLocation = lobby.getHighestBlockAt(0, 0).location
      zombie = lobby.getHighestBlockAt(0, 1).location
      server.onlinePlayers.forEach { it.teleport(lobby.spawnLocation) }
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
    event.player.teleport(lobby.spawnLocation)
    event.player.sendMessage("Welcome to the empty world!")
  }

  @EventHandler
  fun onRespawn(event: PlayerRespawnEvent) {
    event.respawnLocation = lobby.spawnLocation
  }

  companion object {
    lateinit var lobby: World
    lateinit var zombie: Location
    val ZOMBIE_SPAWN_COMMAND
      get() = String.format(
        "execute in minecraft:${lobby.name} run summon zombie %f %f %f {PersistenceRequired:1,ArmorItems:[{},{},{},{Count:1,id:golden_helmet}],HandItems:[{id:acacia_wood,Count:1}],HandDropChances:[0.90f],CustomName:\"\\\"Zombie\\\"\"}",
        zombie.x,
        zombie.y,
        zombie.z
      )
  }
}
