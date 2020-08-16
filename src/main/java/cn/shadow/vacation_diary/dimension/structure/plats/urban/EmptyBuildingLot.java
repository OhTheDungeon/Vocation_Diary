package cn.shadow.vacation_diary.dimension.structure.plats.urban;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.EmptyWithRooms;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;

public class EmptyBuildingLot extends LibraryBuildingLot {

	private static final RoomProvider contentsRooms = new EmptyWithRooms();

	public EmptyBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomProvider roomProviderForFloor(VocationCityWorldGenerator generator, SupportBlocks chunk, int floor, int floorY) {
		return contentsRooms;
	}

	@Override
	protected InteriorStyle getFloorsInteriorStyle(int floor) {
		int range = height / 4;
		if (floor < range)
			return InteriorStyle.WALLS_OFFICES;
		else if (floor < range * 2)
			return InteriorStyle.WALLS_ONLY;
		else if (floor < range * 3)
			return InteriorStyle.COLUMNS_OFFICES;
		else
			return InteriorStyle.COLUMNS_ONLY;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new EmptyBuildingLot(platmap, chunkX, chunkZ);
	}
}
