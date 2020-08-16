package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.LibraryDoubleRoom;
import cn.shadow.vacation_diary.dimension.structure.room.StorageDoubleShelvesRoom;

public class LibraryWithSomeBooks extends RoomProvider {

	public LibraryWithSomeBooks() {
		super();

		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new LibraryDoubleRoom());
		roomTypes.add(new LibraryDoubleRoom());
	}

}
