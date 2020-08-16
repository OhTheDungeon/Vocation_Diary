package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.StorageDoubleRowRoom;
import cn.shadow.vacation_diary.dimension.structure.room.StorageSingleRowRoom;
import net.minecraft.block.Blocks;

public class WarehouseWithBooks extends RoomProvider {

	public WarehouseWithBooks() {
		super();

		roomTypes.add(new StorageSingleRowRoom(Blocks.BOOKSHELF));
		roomTypes.add(new StorageDoubleRowRoom(Blocks.BOOKSHELF));
	}

}
