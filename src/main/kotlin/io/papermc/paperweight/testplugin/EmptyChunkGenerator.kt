package io.papermc.paperweight.testplugin

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
            chunkData.setBlock(0, 0, 0, org.bukkit.Material.BEDROCK)
        } else {
            chunkData.setBlock(0, 0, 0, org.bukkit.Material.AIR)
        }
    }
}
