package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Colors;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

class TreeProvider_Spooky extends TreeProvider {

	public TreeProvider_Spooky(VocationCityWorldGenerator generator) {
		super(generator);
	}

	@Override
	protected void generateLeafBlock(SupportBlocks chunk, int x, int y, int z, Block material, Colors colors) {
		if (material == Blocks.ACACIA_LEAVES) {
			if (chunk.isEmpty(x, y, z))
				chunk.setBlock(x, y, z, Blocks.COBWEB);
		} else if (material == Blocks.BIRCH_LEAVES) {
			if (chunk.isEmpty(x, y, z))
				chunk.setBlock(x, y, z, Blocks.IRON_BARS);
		} else if (material == Blocks.DARK_OAK_LEAVES) {
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsLikely))
					chunk.setBlock(x, y, z, Blocks.SPONGE);
				else
					chunk.setBlock(x, y, z, Blocks.WET_SPONGE);
		} else if (material == Blocks.JUNGLE_LEAVES) {
			if (chunk.isEmpty(x, y - 1, z))
				chunk.setBlock(x, y - 1, z, colors.getCarpet());
		} else {
			// chunk.setBlock(x, y, z, Material.AIR);
		}
	}
}
