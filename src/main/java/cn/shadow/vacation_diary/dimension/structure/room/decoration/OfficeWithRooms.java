package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.ClosetRoom;
import cn.shadow.vacation_diary.dimension.structure.room.DeskAdminRoom;
import cn.shadow.vacation_diary.dimension.structure.room.DeskCornerRoom;
import cn.shadow.vacation_diary.dimension.structure.room.DeskCubbyRoom;
import cn.shadow.vacation_diary.dimension.structure.room.DeskExecutiveRoom;
import cn.shadow.vacation_diary.dimension.structure.room.DeskForTwoRoom;
import cn.shadow.vacation_diary.dimension.structure.room.DividedEllRoom;
import cn.shadow.vacation_diary.dimension.structure.room.DividedSingleRoom;
import cn.shadow.vacation_diary.dimension.structure.room.EmptyRoom;

public class OfficeWithRooms extends RoomProvider {

	public OfficeWithRooms() {
		super();

//		roomTypes.add(new DebugRoom());

		roomTypes.add(new EmptyRoom());

		roomTypes.add(new ClosetRoom());

		roomTypes.add(new DividedSingleRoom());
		roomTypes.add(new DividedEllRoom());

		roomTypes.add(new DeskCubbyRoom());
		roomTypes.add(new DeskForTwoRoom());
		roomTypes.add(new DeskExecutiveRoom());
		roomTypes.add(new DeskAdminRoom());
		roomTypes.add(new DeskCornerRoom());

		// two wide table with chair and a book stand (with possible flower pot)
	}

}
