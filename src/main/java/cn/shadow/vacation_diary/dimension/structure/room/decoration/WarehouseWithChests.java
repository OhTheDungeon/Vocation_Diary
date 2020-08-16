package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.StorageEmptyChestsRoom;
import cn.shadow.vacation_diary.dimension.structure.room.StorageFilledChestsRoom;

public class WarehouseWithChests extends RoomProvider {

	public WarehouseWithChests() {
		super();

		roomTypes.add(new StorageFilledChestsRoom());
		roomTypes.add(new StorageFilledChestsRoom());
		roomTypes.add(new StorageFilledChestsRoom());
		roomTypes.add(new StorageEmptyChestsRoom());
	}

}
