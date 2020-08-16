package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import cn.shadow.vacation_diary.util.MaterialTable;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public class OreProvider_Nether extends OreProvider {

	public OreProvider_Nether(VocationCityWorldGenerator generator) {
		super(generator);

		surfaceMaterial = Blocks.NETHERRACK;
		subsurfaceMaterial = Blocks.NETHERRACK;
		stratumMaterial = Blocks.NETHERRACK;

		fluidMaterial = Blocks.LAVA;
		fluidFluidMaterial = Blocks.LAVA;
		fluidFrozenMaterial = Blocks.OBSIDIAN;
		fluidSubsurfaceMaterial = Blocks.LAVA;
		fluidSurfaceMaterial = Blocks.LAVA;
		
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NetherOres, 0, Blocks.LAVA));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NetherOres, 1, Blocks.LAVA));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NetherOres, 2, Blocks.SOUL_SAND));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NetherOres, 3, Blocks.SOUL_SAND));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NetherOres, 4, Blocks.GLOWSTONE));

		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NetherOres, 5, Blocks.GLOWSTONE));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NetherOres, 6, Blocks.NETHER_QUARTZ_ORE));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NetherOres, 7, Blocks.SOUL_SAND));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NetherOres, 8, Blocks.SOUL_SAND));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NetherOres, 9, Blocks.OBSIDIAN));
	}

	@Override
	public Biome remapBiome(Biome biome) {
		return Biomes.THE_VOID;
	}

	@Override
	public void dropSnow(VocationCityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, double level) {

		// do nothing
	}
}
