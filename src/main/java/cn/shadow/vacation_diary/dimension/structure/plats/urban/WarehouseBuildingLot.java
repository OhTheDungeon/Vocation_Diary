package cn.shadow.vacation_diary.dimension.structure.plats.urban;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.WarehouseWithBooks;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.WarehouseWithBoxes;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.WarehouseWithChests;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.WarehouseWithNothing;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.WarehouseWithRandom;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.WarehouseWithStacks;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;

public class WarehouseBuildingLot extends IndustrialBuildingLot {

	private static final RoomProvider contentsRandom = new WarehouseWithRandom();
	private static final RoomProvider contentsBooks = new WarehouseWithBooks();
	private static final RoomProvider contentsBoxes = new WarehouseWithBoxes();
	private static final RoomProvider contentsEmpty = new WarehouseWithNothing();
	private static final RoomProvider contentsChests = new WarehouseWithChests();
	private static final RoomProvider contentsStacks = new WarehouseWithStacks();

	public enum ContentStyle {
		RANDOM, BOOKS, BOXES, EMPTY, STACKS, CHESTS
	}

	private ContentStyle contentStyle;

	public WarehouseBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		contentStyle = pickContentStyle();
		firstFloorHeight = aboveFloorHeight * 2;
	}

	private ContentStyle pickContentStyle() {
		ContentStyle[] values = ContentStyle.values();
		return values[chunkOdds.getRandomInt(values.length)];
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);

		// other bits
		if (result && relative instanceof WarehouseBuildingLot) {
			WarehouseBuildingLot relativebuilding = (WarehouseBuildingLot) relative;

			// any other bits
			contentStyle = relativebuilding.contentStyle;
		}

		return result;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new WarehouseBuildingLot(platmap, chunkX, chunkZ);
	}

	@Override
	public RoomProvider roomProviderForFloor(VocationCityWorldGenerator generator, SupportBlocks chunk, int floor, int floorY) {
		switch (contentStyle) {
		default:
		case RANDOM:
			return contentsRandom;
		case BOOKS:
			return contentsBooks;
		case BOXES:
			return contentsBoxes;
		case EMPTY:
			return contentsEmpty;
		case STACKS:
			return contentsStacks;
		case CHESTS:
			return contentsChests;
		}
	}

	@Override
	protected InteriorStyle pickInteriorStyle() {
		switch (chunkOdds.getRandomInt(10)) {
		case 1:
			return InteriorStyle.RANDOM;
		case 2:
		case 3:
			return InteriorStyle.COLUMNS_ONLY;
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		default:
			return InteriorStyle.COLUMNS_OFFICES;
		}
	}
}
