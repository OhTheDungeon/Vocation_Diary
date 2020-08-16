package cn.shadow.vacation_diary.dimension.structure.plats.urban;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.FinishedBuildingLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.StoreWithBooks;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.StoreWithNothing;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.StoreWithRandom;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.StoreWithRegisters;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;

public class StoreBuildingLot extends FinishedBuildingLot {

	private static final RoomProvider contentsRandom = new StoreWithRandom();
	private static final RoomProvider contentsBooks = new StoreWithBooks();
	private static final RoomProvider contentsEmpty = new StoreWithNothing();
	private static final RoomProvider contentsRegisters = new StoreWithRegisters();

	public enum ContentStyle {
		RANDOM, BOOKS, EMPTY
	}

	private ContentStyle contentStyle;

	public StoreBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		contentStyle = pickContentStyle();
	}

	private ContentStyle pickContentStyle() {
		switch (chunkOdds.getRandomInt(5)) {
		case 1:
			return ContentStyle.BOOKS;
		case 2:
			return ContentStyle.RANDOM;
		default:
			return ContentStyle.EMPTY;
		}
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);

		// other bits
		if (result && relative instanceof StoreBuildingLot) {
			StoreBuildingLot relativebuilding = (StoreBuildingLot) relative;

			// any other bits
			contentStyle = relativebuilding.contentStyle;
		}

		return result;
	}

	@Override
	protected InteriorStyle getFloorsInteriorStyle(int floor) {
		return InteriorStyle.COLUMNS_OFFICES;
	}

	@Override
	public RoomProvider roomProviderForFloor(VocationCityWorldGenerator generator, SupportBlocks chunk, int floor, int floorY) {
		if (floor == 0)
			return contentsRegisters;
		else
			switch (contentStyle) {
			case BOOKS:
				return contentsBooks;
			case RANDOM:
				return contentsRandom;
			default:
				return contentsEmpty;
			}
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new StoreBuildingLot(platmap, chunkX, chunkZ);
	}

}
