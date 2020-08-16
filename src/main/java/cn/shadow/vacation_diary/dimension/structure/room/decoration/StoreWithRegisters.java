package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.EmptyRoom;
import cn.shadow.vacation_diary.dimension.structure.room.RegisterRoom;
import cn.shadow.vacation_diary.dimension.structure.room.StorageSingleRowRoom;
import net.minecraft.block.Blocks;

public class StoreWithRegisters extends RoomProvider {

	public StoreWithRegisters() {
		super();

		roomTypes.add(new StorageSingleRowRoom(Blocks.BOOKSHELF));
		roomTypes.add(new EmptyRoom());
		roomTypes.add(new EmptyRoom());
		roomTypes.add(new RegisterRoom());
		roomTypes.add(new RegisterRoom());
	}

}
