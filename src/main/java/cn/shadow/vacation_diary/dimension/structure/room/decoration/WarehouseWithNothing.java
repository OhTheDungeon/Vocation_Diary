package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.EmptyRoom;
import cn.shadow.vacation_diary.dimension.structure.room.StorageDoubleShelvesRoom;

public class WarehouseWithNothing extends RoomProvider {

	public WarehouseWithNothing() {
		super();

		roomTypes.add(new EmptyRoom());
		roomTypes.add(new StorageDoubleShelvesRoom());
	}

}
