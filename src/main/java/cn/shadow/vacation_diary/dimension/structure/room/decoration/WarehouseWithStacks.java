package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.StorageStacksRoom;
import net.minecraft.block.Blocks;

public class WarehouseWithStacks extends RoomProvider {

	public WarehouseWithStacks() {
		super();

		roomTypes.add(new StorageStacksRoom(Blocks.BOOKSHELF));
		roomTypes.add(new StorageStacksRoom(Blocks.PISTON));
		roomTypes.add(new StorageStacksRoom(Blocks.CRAFTING_TABLE));
	}

}
