package cn.shadow.vacation_diary.dimension.structure.provider;

import java.util.Arrays;
import java.util.Locale;

import cn.shadow.vacation_diary.VacationDiary;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.BaseBlock;
import cn.shadow.vacation_diary.dimension.support.Odds;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class LootProvider_LootTable extends LootProvider {
	
	private final static ResourceLocation EMPTY = new ResourceLocation("minecraft", "empty");
	
	@Override
	public void setLoot(VocationCityWorldGenerator generator, Odds odds, String worldPrefix, LootLocation chestLocation,
			BaseBlock block) {
		TileEntity tileentity = block.getTileEntity();
		if (tileentity instanceof ChestTileEntity) {
			if (chestLocation == LootLocation.RANDOM) {
				chestLocation = Arrays.copyOfRange(LootLocation.values(), 2, LootLocation.values().length)[odds.getRandomInt(LootLocation.values().length - 1) + 1];
			}
			
			if (chestLocation == LootLocation.EMPTY) {
				((ChestTileEntity)tileentity).setLootTable(EMPTY, generator.getSeed());
			} else {
				String lootTable = "chests/" + chestLocation.name().toLowerCase(Locale.ROOT);
				ResourceLocation chest = new ResourceLocation(VacationDiary.MOD_ID, lootTable);
				((ChestTileEntity)tileentity).setLootTable(chest, generator.getWorld().getRandom().nextLong());
			}
		}
	}

	@Override
	public void saveLoots() {

	}

}
