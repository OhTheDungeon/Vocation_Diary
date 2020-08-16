package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.EmptyRoom;

public class OfficeWithNothing extends RoomProvider {

	public OfficeWithNothing() {
		super();

		roomTypes.add(new EmptyRoom());
	}

}
