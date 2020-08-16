package cn.shadow.vacation_diary.dimension.structure.provider;

import java.util.ArrayList;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.util.MaterialTable;
import net.minecraft.block.Blocks;

class OreProvider_Normal extends OreProvider {

	public OreProvider_Normal(VocationCityWorldGenerator generator) {
		super(generator);

		ore_types = new ArrayList<>();
		
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NormalOres, 0, Blocks.WATER));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NormalOres, 1, Blocks.LAVA));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NormalOres, 2, Blocks.GRAVEL));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NormalOres, 3, Blocks.COAL_ORE));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NormalOres, 4, Blocks.IRON_ORE));

		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NormalOres, 5, Blocks.GOLD_ORE));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NormalOres, 6, Blocks.LAPIS_ORE));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NormalOres, 7, Blocks.REDSTONE_ORE));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NormalOres, 8, Blocks.DIAMOND_ORE));
		ore_types.add(MaterialTable.getNthBlock(MaterialTable.itemsMaterialListFor_NormalOres, 9, Blocks.EMERALD_ORE));
	}
}
