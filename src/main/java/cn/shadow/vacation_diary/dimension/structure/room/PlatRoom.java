package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;

public abstract class PlatRoom {

	PlatRoom() {
		// TODO Auto-generated constructor stub
	}

	public abstract void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y,
			int z, int width, int height, int depth, Direction sideWithWall, Block materialWall,
			Block materialGlass);

	private final Block[] TableTops = { Blocks.ACACIA_PRESSURE_PLATE, Blocks.BIRCH_PRESSURE_PLATE,
			Blocks.DARK_OAK_PRESSURE_PLATE, Blocks.JUNGLE_PRESSURE_PLATE, Blocks.OAK_PRESSURE_PLATE,
			Blocks.SPRUCE_PRESSURE_PLATE,

			Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
			Blocks.STONE_PRESSURE_PLATE,

			Blocks.BLACK_CARPET, Blocks.BLUE_CARPET, Blocks.BROWN_CARPET, Blocks.CYAN_CARPET,
			Blocks.GRAY_CARPET, Blocks.GREEN_CARPET, Blocks.LIGHT_BLUE_CARPET, Blocks.LIGHT_GRAY_CARPET,
			Blocks.LIME_CARPET, Blocks.MAGENTA_CARPET, Blocks.ORANGE_CARPET, Blocks.PINK_CARPET,
			Blocks.PURPLE_CARPET, Blocks.RED_CARPET, Blocks.WHITE_CARPET, Blocks.YELLOW_CARPET };

	private final Block[] TableLegs = { Blocks.ACACIA_FENCE, Blocks.BIRCH_FENCE, Blocks.DARK_OAK_FENCE,
			Blocks.JUNGLE_FENCE, Blocks.OAK_FENCE, Blocks.SPRUCE_FENCE,

			Blocks.NETHER_BRICK_FENCE, Blocks.COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_WALL,
			Blocks.IRON_BARS };

	Block getTableTop(Odds odds) {
		return odds.getRandomMaterial(TableTops);
	}

	Block getTableLeg(Odds odds) {
		return odds.getRandomMaterial(TableLegs);
	}
}
