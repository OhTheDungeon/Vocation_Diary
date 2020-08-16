package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.VacationDiary;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;

public abstract class Provider {

	protected Provider() {
		// TODO Auto-generated constructor stub
	}

	protected static boolean isModVersionOrBetter(VocationCityWorldGenerator generator, VacationDiary mod, String minVersion) {
		return true;
	}

}
