package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.StorageDoubleRowRoom;
import net.minecraft.block.Blocks;

public class WarehouseWithBoxes extends RoomProvider {

	public WarehouseWithBoxes() {
		super();

		roomTypes.add(new StorageDoubleRowRoom(Blocks.PISTON));
	}

}
