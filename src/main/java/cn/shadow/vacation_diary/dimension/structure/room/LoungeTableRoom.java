package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;

public class LoungeTableRoom extends LoungeRoom {

	public LoungeTableRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {
		Block tableLeg = getTableLeg(odds);
		Block tableTop = getTableTop(odds);

		int offset;
		switch (sideWithWall) {
		default:
		case NORTH:
		case SOUTH:
			offset = odds.getRandomInt(width);
			chunk.setTable(x + offset, x + 1 + offset, y, z, z + depth, tableLeg, tableTop);
			break;
		case WEST:
		case EAST:
			offset = odds.getRandomInt(depth);
			chunk.setTable(x, x + width, y, z + offset, z + 1 + offset, tableLeg, tableTop);
			break;
		}
	}

}
