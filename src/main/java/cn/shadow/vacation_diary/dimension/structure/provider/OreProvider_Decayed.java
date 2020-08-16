package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import net.minecraft.block.Blocks;

class OreProvider_Decayed extends OreProvider_Normal {

	public OreProvider_Decayed(VocationCityWorldGenerator generator) {
		super(generator);

		if (generator.getWorldSettings().includeLavaFields) {
			fluidMaterial = Blocks.LAVA;
			fluidFluidMaterial = Blocks.LAVA;
			fluidFrozenMaterial = Blocks.OBSIDIAN;
			fluidSubsurfaceMaterial = Blocks.LAVA;
			fluidSurfaceMaterial = Blocks.LAVA;
		}
		surfaceMaterial = Blocks.SAND;
		subsurfaceMaterial = Blocks.SANDSTONE;
	}
}
