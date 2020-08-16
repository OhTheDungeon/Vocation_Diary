package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.BaseBlock;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.util.TreasureTable;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class LootProvider_Normal extends LootProvider {
	
    private static ItemStack addItem(IItemHandler target, ItemStack remaining) {
        // Try to insert item into all slots
        for (int i = 0; i < target.getSlots(); i++) {
            remaining = target.insertItem(i, remaining, false);
            if (remaining.isEmpty()) { break; }
        }
        return remaining;
    }


	@Override
	public void setLoot(VocationCityWorldGenerator generator, Odds odds, String worldPrefix, LootLocation lootLocation,
			BaseBlock block) {
		TileEntity tileEntity = block.getTileEntity();
		if(tileEntity instanceof ChestTileEntity) {
			ChestTileEntity chestTileEntity = (ChestTileEntity) tileEntity;
			IItemHandler itemHandler = chestTileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP)
					.orElseThrow(() -> new RuntimeException("Item handler not present."));
			ItemStack[] items = getLoot(generator, odds, lootLocation, block);
			if (items != null)
				for(ItemStack item : items) {
					addItem(itemHandler, item);
				}
		}
	}

	@Override
	public void saveLoots() {
		// we don't need to do anything
	}

	private ItemStack[] getLoot(VocationCityWorldGenerator generator, Odds odds, LootLocation lootLocation, BaseBlock block) {

		// which mix?
		switch (lootLocation) {
		case EMPTY:
			return null;
		case BUNKER:
			return pickFromTreasures(TreasureTable.itemsRandomMaterials_BunkerChests, odds, 3, 2);
		case MINE:
			return pickFromTreasures(TreasureTable.itemsRandomMaterials_MineChests, odds, 3, 2);
		case SEWER:
			return pickFromTreasures(TreasureTable.itemsRandomMaterials_SewerChests, odds, 3, 2);
		case BUILDING:
			return pickFromTreasures(TreasureTable.itemsRandomMaterials_BuildingChests, odds, 5, 3);
		case WAREHOUSE:
			return pickFromTreasures(TreasureTable.itemsRandomMaterials_WarehouseChests, odds, 1, 32);
		case FOOD:
			return pickFromTreasures(TreasureTable.itemsRandomMaterials_FoodChests, odds, 5, 3);
		case STORAGE_SHED:
			return pickFromTreasures(TreasureTable.itemsRandomMaterials_StorageShedChests, odds, 3, 2);
		case FARMWORKS:
			return pickFromTreasures(TreasureTable.itemsRandomMaterials_FarmChests, odds, 4, 2);
		case FARMWORKS_OUTPUT:
			return pickFromTreasures(TreasureTable.itemsRandomMaterials_FarmOutputChests, odds, 8, 6);
		case WOODWORKS:
			return pickFromTreasures(TreasureTable.itemsRandomMaterials_LumberChests, odds, 4, 2);
		case WOODWORKS_OUTPUT:
			return pickFromTreasures(TreasureTable.itemsRandomMaterials_LumberOutputChests, odds, 8, 6);
		case STONEWORKS:
			return pickFromTreasures(TreasureTable.itemsRandomMaterials_QuaryChests, odds, 4, 2);
		case STONEWORKS_OUTPUT:
			return pickFromTreasures(TreasureTable.itemsRandomMaterials_QuaryOutputChests, odds, 10, 6);
		case RANDOM:
			lootLocation = LootLocation.values()[odds.getRandomInt(LootLocation.values().length - 1) + 1];
			return getLoot(generator, odds, lootLocation, block);
		}

		throw new IllegalArgumentException();
	}

}
