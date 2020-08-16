package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;

public class LoungeGameRoom extends LoungeChairsRoom {

	public LoungeGameRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {

		super.drawFixture(generator, chunk, odds, floor, x, y, z, width, height, depth, sideWithWall, materialWall,
				materialGlass);

		Block tableLeg = getTableLeg(odds);
		Block tableTop = getTableTop(odds);

		switch (sideWithWall) {
		default:
		case NORTH:
		case SOUTH:
			chunk.setTable(x + 1, y, z, tableLeg, tableTop);
			chunk.setTable(x + 1, y, z + 2, tableLeg, tableTop);
			break;
		case WEST:
		case EAST:
			chunk.setTable(x, y, z + 1, tableLeg, tableTop);
			chunk.setTable(x + 2, y, z + 1, tableLeg, tableTop);
			break;
		}
	}

}
