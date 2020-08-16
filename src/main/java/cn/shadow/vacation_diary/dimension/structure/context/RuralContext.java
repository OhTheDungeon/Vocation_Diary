package cn.shadow.vacation_diary.dimension.structure.context;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;

abstract class RuralContext extends CivilizedContext {

	RuralContext(VocationCityWorldGenerator generator) {
		super(generator);

		maximumFloorsAbove = 1;
		maximumFloorsBelow = 1;
	}
}
