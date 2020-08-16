package cn.shadow.vacation_diary.dimension.support;

import net.minecraft.entity.EntityType;

public final class SeaAnimalList extends AbstractEntityList {

	public SeaAnimalList(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public SeaAnimalList(String name, EntityType<?>... entities) {
		super(name, entities);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getHerdSize(Odds odds, EntityType<?> entity) {
		if(entity == EntityType.SQUID || entity == EntityType.DOLPHIN || entity == EntityType.TURTLE) {
			return odds.getRandomInt(1, 3);
		} else if(entity == EntityType.ELDER_GUARDIAN || entity == EntityType.ELDER_GUARDIAN) {
			return 1;
		} else {
			return odds.getRandomInt(3, 6);
		}
	}
}
