package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.LibraryDoubleRoom;

public class LibraryWithAllBooks extends RoomProvider {

	public LibraryWithAllBooks() {
		super();

		roomTypes.add(new LibraryDoubleRoom());
	}

}
