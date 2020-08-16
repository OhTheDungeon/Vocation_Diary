package cn.shadow.vacation_diary.dimension.structure.room;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;

public class StorageStacksRoom extends StorageTypeRoom {

	public StorageStacksRoom(Block type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {

		int minheight = odds.getRandomInt(height / 2);
		for (int x1 = x; x1 < x + width; x1++) {
			for (int z1 = z; z1 < z + depth; z1++) {
				setStorageBlocks(generator, chunk, odds, x1, y, y + Math.max(minheight, odds.getRandomInt(height - 1)),
						z1);
			}
		}
	}

}
