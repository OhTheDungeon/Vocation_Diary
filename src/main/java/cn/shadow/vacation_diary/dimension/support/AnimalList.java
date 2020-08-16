package cn.shadow.vacation_diary.dimension.support;

import net.minecraft.entity.EntityType;

public final class AnimalList extends AbstractEntityList {

	public AnimalList(String name) {
		super(name);
	}

	public AnimalList(String name, EntityType<?>... entities) {
		super(name, entities);
	}

	@Override
	public int getHerdSize(Odds odds, EntityType<?> entity) {
		if(entity == EntityType.WOLF || entity == EntityType.OCELOT || entity == EntityType.CAT || entity == EntityType.FOX) {
			return odds.getRandomInt(1, 2);
		} else if(entity == EntityType.HORSE || entity == EntityType.DONKEY || entity == EntityType.LLAMA
				|| entity == EntityType.COW || entity == EntityType.MOOSHROOM || entity == EntityType.MULE
				|| entity == EntityType.SHEEP || entity == EntityType.PIG) {
			return odds.getRandomInt(1, 3);
		} else {
			return odds.getRandomInt(1, 6);
		}
	}
}
