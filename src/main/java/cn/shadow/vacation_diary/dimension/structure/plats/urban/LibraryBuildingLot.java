package cn.shadow.vacation_diary.dimension.structure.plats.urban;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.FinishedBuildingLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.LibraryWithAllBooks;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.LibraryWithLounges;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.LibraryWithMostlyBooks;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.LibraryWithNoBooks;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.LibraryWithRandom;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.LibraryWithSomeBooks;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;

public class LibraryBuildingLot extends FinishedBuildingLot {

	private static final RoomProvider contentsRandom = new LibraryWithRandom();
	private static final RoomProvider contentsNoBooks = new LibraryWithNoBooks();
	private static final RoomProvider contentsSomeBooks = new LibraryWithSomeBooks();
	private static final RoomProvider contentsMostlyBooks = new LibraryWithMostlyBooks();
	private static final RoomProvider contentsAllBooks = new LibraryWithAllBooks();
	private static final RoomProvider contentsLounges = new LibraryWithLounges();

	public LibraryBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomProvider roomProviderForFloor(VocationCityWorldGenerator generator, SupportBlocks chunk, int floor, int floorY) {
		if (floor == 0)
			return contentsLounges;
		else
			switch (chunkOdds.getRandomInt(5)) {
			case 1:
				return contentsNoBooks;
			case 2:
				return contentsSomeBooks;
			case 3:
				return contentsMostlyBooks;
			case 4:
				return contentsAllBooks;
			default:
				return contentsRandom;
			}
	}

	@Override
	protected InteriorStyle getFloorsInteriorStyle(int floor) {
		if (floor == 0)
			return InteriorStyle.COLUMNS_OFFICES;
		else
			return super.getFloorsInteriorStyle(floor);
	}

	@Override
	protected InteriorStyle pickInteriorStyle() {
		switch (chunkOdds.getRandomInt(10)) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			return InteriorStyle.COLUMNS_OFFICES;
		case 9:
		default:
			return InteriorStyle.COLUMNS_ONLY;
		}
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new LibraryBuildingLot(platmap, chunkX, chunkZ);
	}

}
