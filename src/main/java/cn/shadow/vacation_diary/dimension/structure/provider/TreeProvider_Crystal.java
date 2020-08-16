package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Colors;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

class TreeProvider_Crystal extends TreeProvider {

	private final Odds odds;

	public TreeProvider_Crystal(VocationCityWorldGenerator generator) {
		super(generator);
		odds = new Odds();
	}

	@Override
	protected void generateTrunkBlock(SupportBlocks chunk, int x, int y, int z, int w, int h, Block material) {
		if (odds.playOdds(Odds.oddsSomewhatLikely))
			material = Blocks.GLOWSTONE;

		super.generateTrunkBlock(chunk, x, y, z, w, h, material);
	}

	@Override
	protected void generateLeafBlock(SupportBlocks chunk, int x, int y, int z, Block material, Colors colors) {
		if (material == Blocks.ACACIA_LEAVES) {
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlock(x, y, z, Blocks.GLASS_PANE);
				else
					chunk.setBlock(x, y, z, Blocks.GLASS);
		} else if (material == Blocks.BIRCH_LEAVES) {
			if (chunk.isEmpty(x, y - 1, z))
				chunk.setBlock(x, y - 1, z, colors.getCarpet());
		} else if (material == Blocks.DARK_OAK_LEAVES) {
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlock(x, y, z, colors.getGlass());
				else
					chunk.setBlock(x, y, z, colors.getGlassPane());
		} else if (material == Blocks.JUNGLE_LEAVES) {
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlock(x, y, z, colors.getGlassPane());
				else
					chunk.setBlock(x, y, z, colors.getGlass());
		} else {
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlock(x, y, z, Blocks.GLASS);
				else
					chunk.setBlock(x, y, z, Blocks.GLASS_PANE);
		}
	}
}
