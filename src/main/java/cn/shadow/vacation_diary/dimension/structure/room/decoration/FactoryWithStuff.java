package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.EmptyRoom;
import cn.shadow.vacation_diary.dimension.structure.room.StorageDoubleRowRoom;
import cn.shadow.vacation_diary.dimension.structure.room.StorageDoubleShelvesRoom;
import cn.shadow.vacation_diary.dimension.structure.room.StorageSingleRowRoom;
import cn.shadow.vacation_diary.dimension.structure.room.StorageSingleShelvesRoom;
import cn.shadow.vacation_diary.dimension.structure.room.StorageStacksRoom;
import net.minecraft.block.Blocks;

public class FactoryWithStuff extends RoomProvider {

	public FactoryWithStuff() {
		super();

		roomTypes.add(new EmptyRoom());
		roomTypes.add(new StorageSingleShelvesRoom());
		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new StorageSingleRowRoom(Blocks.BOOKSHELF));
		roomTypes.add(new StorageSingleRowRoom(Blocks.PISTON));
		roomTypes.add(new StorageSingleRowRoom(Blocks.CRAFTING_TABLE));
		roomTypes.add(new StorageDoubleRowRoom(Blocks.BOOKSHELF));
		roomTypes.add(new StorageDoubleRowRoom(Blocks.PISTON));
		roomTypes.add(new StorageDoubleRowRoom(Blocks.CRAFTING_TABLE));
		roomTypes.add(new StorageStacksRoom(Blocks.BOOKSHELF));
		roomTypes.add(new StorageStacksRoom(Blocks.PISTON));
		roomTypes.add(new StorageStacksRoom(Blocks.CRAFTING_TABLE));
	}

}
