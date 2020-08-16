package cn.shadow.vacation_diary.dimension.structure.plats.urban;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.FinishedBuildingLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.OfficeWithCubicles;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.OfficeWithLounges;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.OfficeWithNothing;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.OfficeWithRandom;
import cn.shadow.vacation_diary.dimension.structure.room.decoration.OfficeWithRooms;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;

public class OfficeBuildingLot extends FinishedBuildingLot {

	private static final RoomProvider contentsEmpty = new OfficeWithNothing();
	private static final RoomProvider contentsRandom = new OfficeWithRandom();
	private static final RoomProvider contentsCubes = new OfficeWithCubicles();
	private static final RoomProvider contentsRooms = new OfficeWithRooms();
	private static final RoomProvider contentsLounges = new OfficeWithLounges();

	public enum ContentStyle {
		RANDOM, EMPTY, OFFICES, CUBICLES
	}

	protected ContentStyle contentStyle;

	public OfficeBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		rounded = false;
		contentStyle = pickContentStyle();
	}

	private ContentStyle pickContentStyle() {
		switch (chunkOdds.getRandomInt(10)) {
		case 1:
		case 2:
		case 3:
			return ContentStyle.OFFICES;
		case 4:
		case 5:
		case 6:
			return ContentStyle.CUBICLES;
		case 7:
		case 8:
		case 9:
			return ContentStyle.RANDOM;
		default:
			return ContentStyle.EMPTY;
		}
	}

	@Override
	public RoomProvider roomProviderForFloor(VocationCityWorldGenerator generator, SupportBlocks chunk, int floor, int floorY) {
		switch (contentStyle) {
		case OFFICES:
			switch (chunkOdds.getRandomInt(10)) {
			case 1:
			case 2:
				return contentsLounges;
			case 3:
				return contentsEmpty;
			default:
				return contentsRooms;
			}
		case CUBICLES:
			switch (chunkOdds.getRandomInt(10)) {
			case 1:
			case 2:
				return contentsLounges;
			case 3:
				return contentsEmpty;
			default:
				return contentsCubes;
			}
		case RANDOM:
			switch (chunkOdds.getRandomInt(10)) {
			case 1:
			case 2:
			case 3:
				return contentsCubes;
			case 4:
			case 5:
			case 6:
				return contentsRooms;
			case 7:
				return contentsLounges;
			case 8:
				return contentsEmpty;
			default:
				return contentsRandom;
			}
		default:
			return contentsEmpty;
		}
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);

		// other bits
		if (result && relative instanceof OfficeBuildingLot) {
			OfficeBuildingLot relativebuilding = (OfficeBuildingLot) relative;

			// any other bits
			contentStyle = relativebuilding.contentStyle;
		}

		return result;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new OfficeBuildingLot(platmap, chunkX, chunkZ);
	}

}
