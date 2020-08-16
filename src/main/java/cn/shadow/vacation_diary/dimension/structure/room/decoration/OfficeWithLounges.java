package cn.shadow.vacation_diary.dimension.structure.room.decoration;

import cn.shadow.vacation_diary.dimension.structure.provider.RoomProvider;
import cn.shadow.vacation_diary.dimension.structure.room.EmptyRoom;
import cn.shadow.vacation_diary.dimension.structure.room.LoungeCouchRoom;
import cn.shadow.vacation_diary.dimension.structure.room.LoungeKitchenetteRoom;
import cn.shadow.vacation_diary.dimension.structure.room.LoungeQuadRoom;
import cn.shadow.vacation_diary.dimension.structure.room.LoungeTVRoom;
import cn.shadow.vacation_diary.dimension.structure.room.LoungeTableRoom;
import cn.shadow.vacation_diary.dimension.structure.room.LoungeTrioRoom;

public class OfficeWithLounges extends RoomProvider {

	public OfficeWithLounges() {
		super();

//		roomTypes.add(new DebugRoom());

		roomTypes.add(new EmptyRoom());

		roomTypes.add(new LoungeCouchRoom());
		roomTypes.add(new LoungeTableRoom());
		roomTypes.add(new LoungeTVRoom());
		roomTypes.add(new LoungeQuadRoom());
		roomTypes.add(new LoungeTrioRoom());
		roomTypes.add(new LoungeKitchenetteRoom());
	}

}
