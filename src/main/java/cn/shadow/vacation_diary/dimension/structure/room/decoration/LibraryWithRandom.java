package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.LibraryDoubleRoom;
import cn.shadow.vacation_diary.dimension.structure.room.LibrarySingleRoom;
import cn.shadow.vacation_diary.dimension.structure.room.LibraryStudyRoom;
import cn.shadow.vacation_diary.dimension.structure.room.LoungeChairsRoom;
import cn.shadow.vacation_diary.dimension.structure.room.LoungeCouchRoom;
import cn.shadow.vacation_diary.dimension.structure.room.LoungeEllCouchRoom;
import cn.shadow.vacation_diary.dimension.structure.room.LoungeGameRoom;
import cn.shadow.vacation_diary.dimension.structure.room.LoungeQuadRoom;
import cn.shadow.vacation_diary.dimension.structure.room.LoungeTrioRoom;

public class LibraryWithRandom extends RoomProvider {

	public LibraryWithRandom() {
		super();

		roomTypes.add(new LibrarySingleRoom());
		roomTypes.add(new LibraryDoubleRoom());
		roomTypes.add(new LibraryStudyRoom());

		roomTypes.add(new LoungeEllCouchRoom());
		roomTypes.add(new LoungeTrioRoom());
		roomTypes.add(new LoungeQuadRoom());
		roomTypes.add(new LoungeChairsRoom());
		roomTypes.add(new LoungeGameRoom());
		roomTypes.add(new LoungeCouchRoom());
	}

}
