package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.util.MaterialTable;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public class OreProvider_TheEnd extends OreProvider {

	public OreProvider_TheEnd(VocationCityWorldGenerator generator) {
		super(generator);

		surfaceMaterial = Blocks.END_STONE;
		subsurfaceMaterial = Blocks.END_STONE;
		stratumMaterial = Blocks.END_STONE;

		fluidMaterial = Blocks.FROSTED_ICE;
		fluidFluidMaterial = Blocks.SNOW_BLOCK;
		fluidSurfaceMaterial = Blocks.PACKED_ICE;
		fluidSubsurfaceMaterial = Blocks.PACKED_ICE;
		fluidFrozenMaterial = Blocks.PACKED_ICE;
		
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_TheEndOres, 0, Blocks.WATER));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_TheEndOres, 1, Blocks.LAVA));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_TheEndOres, 2, Blocks.GRAVEL));
		
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_TheEndOres, 3, Blocks.QUARTZ_BLOCK));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_TheEndOres, 4, Blocks.GLOWSTONE));

		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_TheEndOres, 5, Blocks.PURPUR_BLOCK));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_TheEndOres, 6, Blocks.GOLD_ORE));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_TheEndOres, 7, Blocks.LAPIS_ORE));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_TheEndOres, 8, Blocks.DIAMOND_ORE));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_TheEndOres, 9, Blocks.OBSIDIAN));
	}

	@Override
	public Biome remapBiome(Biome biome) {
		return Biomes.END_MIDLANDS;
	}
}
