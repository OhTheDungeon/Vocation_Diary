package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.DividedEllRoom;
import cn.shadow.vacation_diary.dimension.structure.room.DividedSingleRoom;
import cn.shadow.vacation_diary.dimension.structure.room.EmptyRoom;

public class EmptyWithRooms extends RoomProvider {

	public EmptyWithRooms() {
		super();

		roomTypes.add(new EmptyRoom());

		roomTypes.add(new DividedSingleRoom());
		roomTypes.add(new DividedEllRoom());
	}

}
