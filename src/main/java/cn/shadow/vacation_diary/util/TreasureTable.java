package cn.shadow.vacation_diary.util;

import cn.shadow.vacation_diary.dimension.support.MaterialList;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class TreasureTable {
	private final static String tagRandomMaterials_BunkerChests = "Random_Materials_For_Bunker_Chests";
	public final static MaterialList itemsRandomMaterials_BunkerChests = createList(tagRandomMaterials_BunkerChests,
			Items.IRON_PICKAXE, Items.IRON_SWORD, Items.IRON_SHOVEL, Items.IRON_BOOTS,
			Items.IRON_CHESTPLATE, Items.IRON_HELMET, Items.IRON_LEGGINGS, Items.CHAINMAIL_BOOTS,
			Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_HELMET, Items.CHAINMAIL_LEGGINGS, Items.TORCH,
			Items.BUCKET, Items.WATER_BUCKET, Items.LAVA_BUCKET, Items.REDSTONE);

	private final static String tagRandomMaterials_MineChests = "Random_Materials_For_Mine_Chests";
	public final static MaterialList itemsRandomMaterials_MineChests = createList(tagRandomMaterials_MineChests,
			Items.STONE_PICKAXE, Items.STONE_SWORD, Items.STONE_SHOVEL, Items.IRON_PICKAXE,
			Items.IRON_SWORD, Items.IRON_SHOVEL, Items.LEATHER_BOOTS, Items.LEATHER_CHESTPLATE,
			Items.LEATHER_HELMET, Items.LEATHER_LEGGINGS, Items.TORCH, Items.COMPASS, Items.CLOCK,
			Items.FLINT, Items.FLINT_AND_STEEL, Items.BUCKET, Items.WATER_BUCKET, Items.LAVA_BUCKET,
			Items.GUNPOWDER, Items.SAND, Items.BOOK, Items.COAL, Items.DIAMOND, Items.IRON_INGOT,
			Items.GOLD_INGOT, Items.APPLE, Items.ROTTEN_FLESH);

	private final static String tagRandomMaterials_SewerChests = "Random_Materials_For_Sewer_Chests";
	public final static MaterialList itemsRandomMaterials_SewerChests = createList(tagRandomMaterials_SewerChests,
			Items.WOODEN_PICKAXE, Items.WOODEN_SWORD, Items.WOODEN_SHOVEL, Items.STONE_PICKAXE,
			Items.STONE_SWORD, Items.STONE_SHOVEL, Items.LEATHER_BOOTS, Items.LEATHER_CHESTPLATE,
			Items.LEATHER_HELMET, Items.LEATHER_LEGGINGS, Items.TORCH, Items.COMPASS, Items.CLOCK,
			Items.FLINT_AND_STEEL, Items.FIREWORK_ROCKET, Items.COAL, Items.APPLE, Items.ROTTEN_FLESH);

	private final static String tagRandomMaterials_BuildingChests = "Random_Materials_For_Building_Chests";
	public final static MaterialList itemsRandomMaterials_BuildingChests = createList(tagRandomMaterials_BuildingChests,
			Items.STRING, Items.TORCH, Items.COMPASS, Items.CLOCK, Items.SHEARS, Items.BOWL,
			Items.BUCKET, Items.GLASS_BOTTLE, Items.FLOWER_POT, Items.PAINTING, Items.ITEM_FRAME,
			Items.WHITE_BANNER, Items.FEATHER, Items.PAPER, Items.BOOK, Items.WRITABLE_BOOK,
			Items.MAP, Items.NAME_TAG, Items.ROTTEN_FLESH);

	private final static String tagRandomMaterials_WarehouseChests = "Random_Materials_For_Warehouse_Chests";
	public final static MaterialList itemsRandomMaterials_WarehouseChests = createList(tagRandomMaterials_WarehouseChests,
			Items.FLINT_AND_STEEL, Items.RABBIT_FOOT, Items.RABBIT_HIDE, Items.LEATHER,
			Items.LEATHER_BOOTS, Items.LEATHER_CHESTPLATE, Items.LEATHER_HELMET, Items.LEATHER_LEGGINGS,
			Items.CHAINMAIL_BOOTS, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_HELMET,
			Items.CHAINMAIL_LEGGINGS, Items.IRON_BOOTS, Items.IRON_CHESTPLATE, Items.IRON_HELMET,
			Items.IRON_LEGGINGS, Items.STONE_HOE, Items.STONE_AXE, Items.STONE_PICKAXE,
			Items.STONE_SWORD, Items.LEAD, Items.CARROT_ON_A_STICK, Items.FISHING_ROD,
			Items.TOTEM_OF_UNDYING, Items.MUSIC_DISC_11, Items.MUSIC_DISC_13, Items.MUSIC_DISC_BLOCKS,
			Items.MUSIC_DISC_CAT, Items.MUSIC_DISC_CHIRP, Items.MUSIC_DISC_FAR, Items.MUSIC_DISC_MALL,
			Items.MUSIC_DISC_MELLOHI, Items.MUSIC_DISC_STAL, Items.MUSIC_DISC_STRAD, Items.MUSIC_DISC_WAIT,
			Items.MUSIC_DISC_WARD, Items.ROTTEN_FLESH);

	private final static String tagRandomMaterials_FoodChests = "Random_Materials_For_Food_Chests";
	public final static MaterialList itemsRandomMaterials_FoodChests = createList(tagRandomMaterials_FoodChests, Items.SUGAR,
			Items.CAKE, Items.COOKIE, Items.EGG, Items.APPLE, Items.MELON,
			Items.GLISTERING_MELON_SLICE, Items.CARROT, Items.BREAD, Items.BEEF, Items.CHICKEN,
			Items.COD, Items.MUTTON, Items.RABBIT, Items.POTATO, Items.POISONOUS_POTATO,
			Items.PUMPKIN, Items.BROWN_MUSHROOM, Items.RED_MUSHROOM, Items.BEETROOT, Items.COOKED_BEEF,
			Items.COOKED_CHICKEN, Items.COOKED_COD, Items.COOKED_MUTTON, Items.COOKED_RABBIT,
			Items.BAKED_POTATO, Items.PUMPKIN_PIE, Items.MUSHROOM_STEW, Items.BEETROOT_SOUP,
			Items.GOLDEN_CARROT, Items.GOLDEN_APPLE, Items.ROTTEN_FLESH);

	private final static String tagRandomMaterials_StorageShedChests = "Random_Materials_For_Storage_Shed_Chests";
	public final static MaterialList itemsRandomMaterials_StorageShedChests = createList(tagRandomMaterials_StorageShedChests,
			Items.WOODEN_AXE, Items.WOODEN_SHOVEL, Items.WOODEN_PICKAXE, Items.IRON_AXE,
			Items.IRON_SHOVEL, Items.IRON_PICKAXE, Items.IRON_HELMET, Items.TORCH, Items.PAPER,
			Items.BOOK, Items.COOKED_MUTTON, Items.BOWL, Items.MUSHROOM_STEW);

	private final static String tagRandomMaterials_FarmChests = "Random_Materials_For_Farm_Chests";
	public final static MaterialList itemsRandomMaterials_FarmChests = createList(tagRandomMaterials_FarmChests,
			Items.WOODEN_SHOVEL, Items.WOODEN_PICKAXE, Items.WOODEN_HOE, Items.IRON_SHOVEL,
			Items.IRON_PICKAXE, Items.IRON_HOE, Items.LEATHER_BOOTS, Items.LEATHER_CHESTPLATE,
			Items.LEATHER_HELMET, Items.LEATHER_LEGGINGS, Items.BOW, Items.ARROW, Items.PAPER,
			Items.BOOK, Items.TORCH, Items.FISHING_ROD, Items.SHEARS, Items.BUCKET,
			Items.WATER_BUCKET, Items.MILK_BUCKET, Items.BONE, Items.BOWL, Items.COOKIE, Items.SUGAR,
			Items.COOKED_PORKCHOP, Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON,
			Items.COOKED_RABBIT, Items.BAKED_POTATO, Items.MELON, Items.PUMPKIN_PIE,
			Items.MUSHROOM_STEW);

	private final static String tagRandomMaterials_FarmOutputChests = "Random_Materials_For_Farm_Output_Chests";
	public final static MaterialList itemsRandomMaterials_FarmOutputChests = createList(tagRandomMaterials_FarmOutputChests,
			Items.LEATHER, Items.PORKCHOP, Items.BEEF, Items.CHICKEN, Items.RABBIT, Items.MUTTON,
			Items.WHEAT_SEEDS, Items.WHITE_WOOL, Items.ORANGE_WOOL, Items.MAGENTA_WOOL,
			Items.LIGHT_BLUE_WOOL, Items.YELLOW_WOOL, Items.LIME_WOOL, Items.PINK_WOOL, Items.GRAY_WOOL,
			Items.LIGHT_GRAY_WOOL, Items.CYAN_WOOL, Items.PURPLE_WOOL, Items.BLUE_WOOL, Items.BROWN_WOOL,
			Items.GREEN_WOOL, Items.RED_WOOL, Items.BLACK_WOOL, Items.POTATO, Items.CARROT,
			Items.PUMPKIN_SEEDS, Items.MELON_SEEDS, Items.CARROT, Items.POTATO, Items.SUGAR_CANE,
			Items.APPLE);

	private final static String tagRandomMaterials_LumberChests = "Random_Materials_For_Lumber_Chests";
	public final static MaterialList itemsRandomMaterials_LumberChests = createList(tagRandomMaterials_LumberChests,
			Items.WOODEN_AXE, Items.WOODEN_SHOVEL, Items.WOODEN_PICKAXE, Items.IRON_AXE,
			Items.IRON_SHOVEL, Items.IRON_PICKAXE, Items.IRON_HELMET, Items.FLINT_AND_STEEL, Items.COAL,
			Items.TORCH, Items.COOKED_PORKCHOP, Items.COOKED_CHICKEN, Items.COOKIE);

	private final static String tagRandomMaterials_LumberOutputChests = "Random_Materials_For_Lumber_Output_Chests";
	public final static MaterialList itemsRandomMaterials_LumberOutputChests = createList(tagRandomMaterials_LumberOutputChests,
			Items.SPRUCE_PLANKS, // simple but cheesy way to increase the odds for this one
			Items.SPRUCE_PLANKS, Items.SPRUCE_PLANKS, Items.SPRUCE_PLANKS, Items.SPRUCE_PLANKS,
			Items.SPRUCE_PLANKS, Items.SPRUCE_PLANKS, Items.SPRUCE_PLANKS, Items.SPRUCE_PLANKS,
			Items.SPRUCE_PLANKS, Items.SPRUCE_LOG, Items.SPRUCE_LOG, Items.SPRUCE_LOG, Items.STICK,
			Items.STICK, Items.STICK, Items.OAK_DOOR, Items.OAK_FENCE, Items.OAK_FENCE_GATE,
			Items.OAK_STAIRS, Items.OAK_PRESSURE_PLATE, Items.OAK_TRAPDOOR, Items.OAK_SLAB,
			Items.OAK_BOAT, Items.OAK_LOG, Items.OAK_PLANKS, Items.OAK_WOOD, Items.SPRUCE_DOOR,
			Items.SPRUCE_FENCE, Items.SPRUCE_FENCE_GATE, Items.SPRUCE_STAIRS, Items.SPRUCE_PRESSURE_PLATE,
			Items.SPRUCE_TRAPDOOR, Items.SPRUCE_SLAB, Items.SPRUCE_BOAT, Items.SPRUCE_LOG,
			Items.SPRUCE_PLANKS, Items.SPRUCE_WOOD, Items.BIRCH_DOOR, Items.BIRCH_FENCE,
			Items.BIRCH_FENCE_GATE, Items.BIRCH_STAIRS, Items.BIRCH_PRESSURE_PLATE, Items.BIRCH_TRAPDOOR,
			Items.BIRCH_SLAB, Items.BIRCH_BOAT, Items.BIRCH_LOG, Items.BIRCH_PLANKS, Items.BIRCH_WOOD,
			Items.JUNGLE_DOOR, Items.JUNGLE_FENCE, Items.JUNGLE_FENCE_GATE, Items.JUNGLE_STAIRS,
			Items.JUNGLE_PRESSURE_PLATE, Items.JUNGLE_TRAPDOOR, Items.JUNGLE_SLAB, Items.JUNGLE_BOAT,
			Items.JUNGLE_LOG, Items.JUNGLE_PLANKS, Items.JUNGLE_WOOD, Items.ACACIA_DOOR,
			Items.ACACIA_FENCE, Items.ACACIA_FENCE_GATE, Items.ACACIA_STAIRS, Items.ACACIA_PRESSURE_PLATE,
			Items.ACACIA_TRAPDOOR, Items.ACACIA_SLAB, Items.ACACIA_BOAT, Items.ACACIA_LOG,
			Items.ACACIA_PLANKS, Items.ACACIA_WOOD, Items.DARK_OAK_DOOR, Items.DARK_OAK_FENCE,
			Items.DARK_OAK_FENCE_GATE, Items.DARK_OAK_STAIRS, Items.DARK_OAK_PRESSURE_PLATE,
			Items.DARK_OAK_TRAPDOOR, Items.DARK_OAK_SLAB, Items.DARK_OAK_BOAT, Items.DARK_OAK_LOG,
			Items.DARK_OAK_PLANKS, Items.DARK_OAK_WOOD, Items.SPRUCE_SIGN, Items.ACACIA_SIGN,
			Items.BIRCH_SIGN, Items.DARK_OAK_SIGN, Items.JUNGLE_SIGN, Items.OAK_SIGN,
			Items.SPRUCE_SIGN, Items.ACACIA_SIGN, Items.BIRCH_SIGN, Items.DARK_OAK_SIGN,
			Items.JUNGLE_SIGN, Items.OAK_SIGN);

	private final static String tagRandomMaterials_QuaryChests = "Random_Materials_For_Quary_Chests";
	public final static MaterialList itemsRandomMaterials_QuaryChests = createList(tagRandomMaterials_QuaryChests,
			Items.STONE_SHOVEL, Items.STONE_PICKAXE, Items.IRON_SHOVEL, Items.IRON_PICKAXE,
			Items.IRON_HELMET, Items.FLINT_AND_STEEL, Items.TORCH, Items.BUCKET, Items.COOKED_BEEF,
			Items.COOKED_CHICKEN);

	private final static String tagRandomMaterials_QuaryOutputChests = "Random_Materials_For_Quary_Output_Chests";
	public final static MaterialList itemsRandomMaterials_QuaryOutputChests = createList(tagRandomMaterials_QuaryOutputChests,
			Items.IRON_INGOT, // easy but stupid way to increase odds of some of these happening
			Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_INGOT,
			Items.IRON_INGOT, Items.COAL, Items.COAL, Items.COAL, Items.COAL, Items.COAL,
			Items.COAL, Items.COAL, Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT,
			Items.REDSTONE, Items.REDSTONE, Items.REDSTONE, Items.DIAMOND, Items.EMERALD);

	private static MaterialList createList(String name, Item... materials) {

		// create the list and add all of the goodies
		MaterialList list = new MaterialList(name, materials);

		// return it so we can specifically remember it
		return list;
	}

}
