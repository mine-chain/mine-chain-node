package github.minechain

import org.bukkit.Material
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo

object ChunkGenerator : ChunkGenerator() {
  override fun generateSurface(
    worldInfo: WorldInfo,
    random: java.util.Random,
    chunkX: Int,
    chunkZ: Int,
    chunkData: ChunkData
  ) {
    if (this.isExist(chunkX, chunkZ)) {
      this.load(chunkData, chunkX, chunkZ)
    } else {
      this.random(chunkData, chunkX, chunkZ, random)
    }
  }

  fun isExist(chunkX: Int, chunkZ: Int): Boolean {
    // TODO implement check isExist exist schematic
    return false
  }

  fun load(chunkData: ChunkData, chunkX: Int, chunkZ: Int) {
    // TODO getting load schematic from network
  }

  fun random(chunkData: ChunkData, chunkX: Int, chunkZ: Int, random: java.util.Random) {
    for (x in 0 until 16) {
      for (y in 0 until 128) {
        for (z in 0 until 16) {
          val blockX = chunkX * 16 + x
          val blockZ = chunkZ * 16 + z
          val height = (random.nextInt(10) + 50) + (blockX + blockZ) / 2
          val blockY = Math.min(height, 128)
          if (blockY < 64) {
            chunkData.setBlock(x, y, z, Material.BEDROCK)
          } else {
            chunkData.setBlock(x, y, z, Material.AIR)
          }
        }
      }
    }
  }
}
