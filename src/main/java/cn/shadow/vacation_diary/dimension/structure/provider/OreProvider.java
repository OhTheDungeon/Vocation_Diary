package cn.shadow.vacation_diary.dimension.structure.provider;

import java.util.ArrayList;
import java.util.List;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.world.biome.Biome;

public abstract class OreProvider extends Provider {

	//	public final static int lavaFluidLevel = 24;
	public final static int lavaFieldLevel = 12;
	private final static double oreSprinkleOdds = Odds.oddsLessLikely;
	private final static double snowSprinkleOdds = Odds.oddsMoreLikely;

	List<Block> ore_types = new ArrayList<>();

	public Block surfaceMaterial;
	public Block subsurfaceMaterial;
	public Block stratumMaterial;
	public Block substratumMaterial;

	public Block fluidMaterial;
	public Block fluidFluidMaterial;
	public Block fluidSurfaceMaterial;
	public Block fluidSubsurfaceMaterial;
	public Block fluidFrozenMaterial;

	OreProvider(VocationCityWorldGenerator generator) {
		super();

		surfaceMaterial = Blocks.GRASS_BLOCK;
		subsurfaceMaterial = Blocks.DIRT;
		stratumMaterial = Blocks.STONE;
		substratumMaterial = Blocks.BEDROCK;

		fluidMaterial = Blocks.WATER;
		fluidFluidMaterial = Blocks.WATER;
		fluidSurfaceMaterial = Blocks.SAND;
		fluidSubsurfaceMaterial = Blocks.GRAVEL;
		fluidFrozenMaterial = Blocks.PACKED_ICE;
	}

	// Based on work contributed by drew-bahrue
	// (https://github.com/echurchill/CityWorld/pull/2)
	public static OreProvider loadProvider(VocationCityWorldGenerator generator) {

		OreProvider provider = null;

		// default to stock OreProvider
		if (provider == null) {

			switch (generator.worldStyle) {
//			case ASTRAL:
//				provider = new OreProvider_Astral(generator);
//				break;
//			case SNOWDUNES:
//				provider = new OreProvider_SnowDunes(generator);
//				break;
//			case SANDDUNES:
//				provider = new OreProvider_SandDunes(generator);
//				break;
//			case FLOODED:
//			case FLOATING:
//			case DESTROYED:
//			case MAZE:
//			case NATURE:
//			case METRO:
//			case SPARSE:
			case NORMAL:
				switch (generator.worldType) {
				case NETHER:
					provider = new OreProvider_Nether(generator);
					break;
				case THE_END:
					provider = new OreProvider_TheEnd(generator);
					break;
				case NORMAL:
					if (generator.getWorldSettings().includeDecayedNature)
						provider = new OreProvider_Decayed(generator);
					else
						provider = new OreProvider_Normal(generator);
					break;
				}
			}
		}

		return provider;
	}

	public Biome remapBiome(Biome biome) {
		return biome;
	}

	public void sprinkleSnow(VocationCityWorldGenerator generator, SupportBlocks chunk, Odds odds, int x1, int x2, int y,
			int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				if (odds.playOdds(snowSprinkleOdds))
					chunk.setBlock(x, y, z, Blocks.SNOW_BLOCK);
			}
		}
	}

	public void dropSnow(VocationCityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {
		dropSnow(generator, chunk, x, y, z, 0);
	}

	public void dropSnow(VocationCityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, double level) {
		y = chunk.findLastEmptyBelow(x, y + 1, z, y - 6);
		if (!chunk.isOfTypes(x, y - 1, z, Blocks.AIR, Blocks.SNOW)) {
			int ilevel = (int) level;
			if(ilevel <= 0) ilevel = 1;
			if(ilevel > 8) ilevel = 8;
			BlockState snow = Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, Integer.valueOf(ilevel));
			chunk.setBlockState(x, y, z, snow);
		}
	}

	/**
	 * Populates the world with ores.
	 *
	 * original authors Nightgunner5, Markus Persson modified by simplex wildly
	 * modified by daddychurchill
	 */

	// WATER LAVA GRAV COAL IRON GOLD LAPIS REDST DIAM EMER
	private static final int[] ore_iterations = new int[] { 8, 6, 40, 30, 12, 4, 2, 4, 2, 10 };
	private static final int[] ore_amountToDo = new int[] { 1, 1, 12, 8, 8, 3, 3, 10, 3, 1 };
	private static final int[] ore_maxY = new int[] { 128, 32, 111, 128, 61, 29, 25, 16, 15, 32 };
	private static final int[] ore_minY = new int[] { 32, 2, 40, 16, 10, 8, 8, 6, 2, 2 };
	private static final boolean[] ore_upper = new boolean[] { true, false, false, true, true, true, true, true, false, false };
	private static final boolean[] ore_liquid = new boolean[] { true, true, false, false, false, false, false, false, false,
			false };

	public void sprinkleOres(VocationCityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, AbstractCachedYs blockYs,
			Odds odds) {

		// do it... maybe!
		int oreCount = Math.min(ore_types.size(), ore_iterations.length);
		for (int typeNdx = 0; typeNdx < oreCount; typeNdx++) {
			sprinkleOre(generator, lot, chunk, blockYs, odds, ore_types.get(typeNdx), ore_maxY[typeNdx],
					ore_minY[typeNdx], ore_iterations[typeNdx], ore_amountToDo[typeNdx], ore_upper[typeNdx],
					ore_liquid[typeNdx]);
		}
	}

	private void sprinkleOre(VocationCityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, AbstractCachedYs blockYs,
			Odds odds, Block material, int maxY, int minY, int iterations, int amount, boolean mirror,
			boolean liquid) {

		// do we do this one?
		if ((liquid && generator.getWorldSettings().includeUndergroundFluids) || (!liquid && generator.getWorldSettings().includeOres)) {
			if (material != stratumMaterial) {

				// sprinkle it around!
				int range = maxY - minY;
				for (int iter = 0; iter < iterations; iter++) {
					int x = odds.getRandomInt(16);
					int y = odds.getRandomInt(range) + minY;
					int z = odds.getRandomInt(16);
					if (y < blockYs.getBlockY(x, z))
						growVein(generator, lot, chunk, blockYs, odds, x, y, z, amount, material);
					if (mirror) {
						y = (generator.seaLevel + generator.landRange) - minY - odds.getRandomInt(range);
						if (y < blockYs.getBlockY(x, z))
							growVein(generator, lot, chunk, blockYs, odds, x, y, z, amount, material);
					}
				}
			}
		}
	}

	private void growVein(VocationCityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, AbstractCachedYs blockYs,
			Odds odds, int originX, int originY, int originZ, int amountToDo, Block material) {
		int trysLeft = amountToDo * 2;
		int oresDone = 0;
		if (lot.isValidStrataY(generator, originX, originY, originZ)
				&& blockYs.getBlockY(originX, originZ) > originY + amountToDo / 4) {
			while (oresDone < amountToDo && trysLeft > 0) {

				// shimmy
				int x = chunk.clampXZ(originX + odds.getRandomInt(Math.max(1, amountToDo / 2)) - amountToDo / 4);
				int y = chunk.clampY(originY + odds.getRandomInt(Math.max(1, amountToDo / 4)) - amountToDo / 8);
				int z = chunk.clampXZ(originZ + odds.getRandomInt(Math.max(1, amountToDo / 2)) - amountToDo / 4);

				// ore it is
				oresDone += placeOre(generator, chunk, odds, x, y, z, amountToDo - oresDone, material);

				// one less try to try
				trysLeft--;
			}
		}
	}

	private int placeOre(VocationCityWorldGenerator generator, SupportBlocks chunk, Odds odds, int centerX, int centerY,
			int centerZ, int oresToDo, Block material) {
		int count = 0;
		if (centerY > 0 && centerY < chunk.height) {
			if (placeBlock(chunk, odds, centerX, centerY, centerZ, material)) {
				count++;
				if (count < oresToDo && centerX < 15
						&& placeBlock(chunk, odds, centerX + 1, centerY, centerZ, material))
					count++;
				if (count < oresToDo && centerX > 0 && placeBlock(chunk, odds, centerX - 1, centerY, centerZ, material))
					count++;
				if (count < oresToDo && centerZ < 15
						&& placeBlock(chunk, odds, centerX, centerY, centerZ + 1, material))
					count++;
				if (count < oresToDo && centerZ > 0 && placeBlock(chunk, odds, centerX, centerY, centerZ - 1, material))
					count++;
			}
		}
		return count;
	}

	private boolean placeBlock(SupportBlocks chunk, Odds odds, int x, int y, int z, Block material) {
		if (odds.playOdds(oreSprinkleOdds))
			if (chunk.isType(x, y, z, stratumMaterial)) {
				chunk.setBlock(x, y, z, material);
				return true;
			}
		return false;
	}

}
