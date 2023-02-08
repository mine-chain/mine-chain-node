package io.papermc.paperweight.testplugin

import org.bukkit.Material
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo

object EmptyChunkGenerator : ChunkGenerator() {
    override fun generateSurface(
        worldInfo: WorldInfo,
        random: java.util.Random,
        chunkX: Int,
        chunkZ: Int,
        chunkData: ChunkData
    ) {
      if (chunkX == 0 && chunkZ == 0) {
        this.setRegion(chunkData, 0, 0, 0, 15, 15, org.bukkit.Material.BEDROCK)
      } else {
        chunkData.setBlock(0, 0, 0, org.bukkit.Material.AIR)
      }
    }
    fun setRegion(chunkData: ChunkData, y: Int, xMin: Int, zMin: Int, xMax: Int, zMax: Int, material: Material) {
      for (x in xMin..xMax) {
        for (z in zMin..zMax) {
          chunkData.setBlock(x, y, z, material)
        }
      }
    }
}
