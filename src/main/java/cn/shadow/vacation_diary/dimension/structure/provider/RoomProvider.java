package cn.shadow.vacation_diary.dimension.structure.provider;

import java.util.ArrayList;
import java.util.List;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.room.PlatRoom;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;

public abstract class RoomProvider extends Provider {

	protected final List<PlatRoom> roomTypes;

	protected RoomProvider() {
		super();
		roomTypes = new ArrayList<>();
	}

	private PlatRoom getRandomRoomGenerator(Odds odds) {
		int index = odds.getRandomInt(roomTypes.size());
		return roomTypes.get(index);
	}

	public void drawFixtures(VocationCityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, Direction sideWithWall, Block materialWall, Block materialGlass) {

		PlatRoom roomGen = getRandomRoomGenerator(odds);
		if (roomGen != null)
			roomGen.drawFixture(generator, chunk, odds, floor, x, y, z, width, height, depth, sideWithWall,
					materialWall, materialGlass);
	}
}
