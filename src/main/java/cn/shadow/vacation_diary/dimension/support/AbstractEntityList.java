package cn.shadow.vacation_diary.dimension.support;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityType;

public abstract class AbstractEntityList {

	AbstractEntityList(String name) {
		super();
		listName = name;
	}

	public final String listName;
	private List<EntityType<?>> items;

	AbstractEntityList(String name, EntityType<?>... entities) {
		super();
		listName = name;
		add(entities);
	}

	private void init(boolean clear) {
		if (items == null)
			items = new ArrayList<>();
		else if (clear)
			items.clear();
	}

	private void add(EntityType<?>... entities) {
		init(false);
		for (EntityType<?> entity : entities) {
			if(EntityHelper.isAlive(entity)) {
				items.add(entity);
			}
		}
	}

	@SuppressWarnings("unused")
	private void add(EntityType<?> entity) {
		init(false);
		items.add(entity);
	}

	public void remove(EntityType<?> entity) {
		if (items != null)
			for (int i = items.size() - 1; i >= 0; i--)
				if (items.get(i) == entity)
					items.remove(i);
	}

	private int count() {
		return items == null ? 0 : items.size();
	}

	public int getHerdSize(Odds odds, EntityType<?> entity) {
		return 1;
	}

	private EntityType<?> getFirstEntity() {
		if (items == null || count() == 0)
			return null;
		else
			return items.get(0);
	}

	public EntityType<?> getRandomEntity(Odds odds) {
		return getRandomEntity(odds, getFirstEntity());
	}

	private EntityType<?> getRandomEntity(Odds odds, EntityType<?> defaultEntity) {
		if (items == null || count() == 0)
			return defaultEntity;
		else
			return items.get(odds.getRandomInt(count()));
	}
}
