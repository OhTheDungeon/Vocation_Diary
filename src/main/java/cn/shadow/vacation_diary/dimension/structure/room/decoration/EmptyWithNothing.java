package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.EmptyRoom;

public class EmptyWithNothing extends RoomProvider {

	public EmptyWithNothing() {
		super();

		roomTypes.add(new EmptyRoom());
	}

}
