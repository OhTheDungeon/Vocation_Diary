package cn.shadow.vacation_diary.dimension.support;

import net.minecraft.entity.EntityType;

public final class BeingList extends AbstractEntityList {

	public BeingList(String name) {
		super(name);
	}

	public BeingList(String name, EntityType<?>[] entities) {
		super(name, entities);
	}

}
