package cn.shadow.vacation_diary.dimension;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeContainer;

public class LinearBiomeContainer extends BiomeContainer {
	private final Biome[] biomes;
	private final ChunkPos chunkPos;
	private static final int WIDTH_BITS = (int)Math.round(Math.log(16.0D) / Math.log(2.0D)) - 2;

	public LinearBiomeContainer(ChunkPos chunkPosIn, Biome[] biomes) {
		super(biomes);
		this.biomes = biomes;
		this.chunkPos = chunkPosIn;
	}
	
	public void setBiome(int x, int z, Biome biome) {
		for(int y = 0; y < 256; y++) setBiome(x, y, z, biome);
	}
	
	public void setBiome(int x, int y, int z, Biome biome) {
		int i = x & HORIZONTAL_MASK;
		int j = MathHelper.clamp(y, 0, VERTICAL_MASK);
		int k = z & HORIZONTAL_MASK;
		this.biomes[j << WIDTH_BITS + WIDTH_BITS | k << WIDTH_BITS | i] = biome;
	}
	
	public ChunkPos getChunkPos() {
		return this.chunkPos;
	}
}
