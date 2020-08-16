package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.StorageDoubleShelvesRoom;

public class LibraryWithNoBooks extends RoomProvider {

	public LibraryWithNoBooks() {
		super();

		roomTypes.add(new StorageDoubleShelvesRoom());
	}

}
