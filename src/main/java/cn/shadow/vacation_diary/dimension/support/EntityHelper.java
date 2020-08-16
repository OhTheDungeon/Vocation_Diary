package cn.shadow.vacation_diary.dimension.support;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class EntityHelper {
	// TODO : 合并到instanceof MobEntity
	public static boolean isAlive(EntityType<?> entity) {
		if(entity.getClassification() == EntityClassification.MONSTER
				|| entity.getClassification() == EntityClassification.CREATURE
				|| entity.getClassification() == EntityClassification.AMBIENT
				|| entity.getClassification() == EntityClassification.WATER_CREATURE) 
			return true;
		else return false;
	}
}
