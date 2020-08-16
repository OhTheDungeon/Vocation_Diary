package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.StorageDoubleRowRoom;
import cn.shadow.vacation_diary.dimension.structure.room.StorageDoubleShelvesRoom;
import cn.shadow.vacation_diary.dimension.structure.room.StorageEmptyChestsRoom;
import cn.shadow.vacation_diary.dimension.structure.room.StorageFilledChestsRoom;
import net.minecraft.block.Blocks;

public class WarehouseWithRandom extends RoomProvider {

	public WarehouseWithRandom() {
		super();

		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new StorageDoubleRowRoom(Blocks.BOOKSHELF));
		roomTypes.add(new StorageDoubleRowRoom(Blocks.PISTON));
		roomTypes.add(new StorageDoubleRowRoom(Blocks.CRAFTING_TABLE));
		roomTypes.add(new StorageFilledChestsRoom());
		roomTypes.add(new StorageEmptyChestsRoom());
	}
}
