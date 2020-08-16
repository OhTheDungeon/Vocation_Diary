package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.DeskAdminRoom;
import cn.shadow.vacation_diary.dimension.structure.room.DeskCornerRoom;
import cn.shadow.vacation_diary.dimension.structure.room.DeskCubbyRoom;
import cn.shadow.vacation_diary.dimension.structure.room.DeskForTwoRoom;
import cn.shadow.vacation_diary.dimension.structure.room.DeskInternsRoom;
import cn.shadow.vacation_diary.dimension.structure.room.MeetingForFourRoom;
import cn.shadow.vacation_diary.dimension.structure.room.MeetingForSixRoom;

public class OfficeWithCubicles extends RoomProvider {

	public OfficeWithCubicles() {
		super();

//		roomTypes.add(new DebugRoom());

		roomTypes.add(new MeetingForSixRoom());
		roomTypes.add(new MeetingForFourRoom());

		roomTypes.add(new DeskCubbyRoom());
		roomTypes.add(new DeskForTwoRoom());
		roomTypes.add(new DeskAdminRoom());
		roomTypes.add(new DeskCornerRoom());
		roomTypes.add(new DeskCornerRoom());
		roomTypes.add(new DeskInternsRoom());
		roomTypes.add(new DeskInternsRoom());

		// single table
		// double table
		// workbench
		// anvil
		// chest
	}

}
