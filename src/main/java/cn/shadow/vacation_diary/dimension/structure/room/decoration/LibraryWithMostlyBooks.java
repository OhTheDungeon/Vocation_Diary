package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.LibraryDoubleRoom;
import cn.shadow.vacation_diary.dimension.structure.room.StorageDoubleShelvesRoom;

public class LibraryWithMostlyBooks extends RoomProvider {

	public LibraryWithMostlyBooks() {
		super();

		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new LibraryDoubleRoom());
		roomTypes.add(new LibraryDoubleRoom());
		roomTypes.add(new LibraryDoubleRoom());
	}

}
