package cn.shadow.vacation_diary.dimension.support;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public final class Mapper {

	public static Block getStairsFor(Block material) {
		MapperEntry entry = getMaterialMapping(material);
		if (entry != null)
			return entry.stairs;
		else
			return Blocks.STONE_BRICK_STAIRS;
	}

	public static Block getStairWallFor(Block material) {
		MapperEntry entry = getMaterialMapping(material);
		if (entry != null)
			return entry.stairWalls;
		else
			return Blocks.STONE_BRICKS;
	}

	public static Block getStairPlatformFor(Block material) {
		MapperEntry entry = getMaterialMapping(material);
		if (entry != null)
			return entry.stairPlatform;
		else
			return Blocks.STONE;
	}

	public static Block getDoorsFor(Block material) {
		MapperEntry entry = getMaterialMapping(material);
		if (entry != null)
			return entry.door;
		else
			return Blocks.BIRCH_DOOR;
	}

	public static Block getColumnFor(Block material) {
		MapperEntry entry = getMaterialMapping(material);
		if (entry != null)
			return entry.columns;
		else
			return Blocks.COBBLESTONE_WALL;
	}

	private static class MapperEntry {
		Block columns;
		Block door;
		Block stairPlatform;
		Block stairs;
		Block stairWalls;

		MapperEntry(Block aColumns, Block aDoor, Block aStairPlatform, Block aStairs, Block aStairWalls) {
			columns = aColumns;
			door = aDoor;
			stairPlatform = aStairPlatform;
			stairs = aStairs;
			stairWalls = aStairWalls;
		}
		
		public void addToMapping(Map<Block, MapperEntry> mapping) {
			if(!mapping.containsKey(columns)) mapping.put(columns, this);
			if(!mapping.containsKey(door)) mapping.put(door, this);
			if(!mapping.containsKey(stairPlatform)) mapping.put(stairPlatform, this);
			if(!mapping.containsKey(stairs)) mapping.put(stairs, this);
			if(!mapping.containsKey(stairWalls)) mapping.put(stairWalls, this);
		}
	}
	
	private static MapperEntry getMaterialMapping(Block block) {
		if(!MAPPING.containsKey(block)) return DEFAULT;
		return MAPPING.get(block);
	}

	private static final Map<Block, MapperEntry> MAPPING;
	
	public static final MapperEntry DEFAULT = new MapperEntry(
			Blocks.COBBLESTONE_WALL, 
			Blocks.DARK_OAK_DOOR, 
			Blocks.STONE, 
			Blocks.DARK_OAK_STAIRS, 
			Blocks.STONE);
		
	static {
		// 初始化mapping
		// TODO: 更多装修风格
		MAPPING = new HashMap<>();
		{
			MapperEntry entry = new MapperEntry(
					Blocks.ACACIA_FENCE, 
					Blocks.ACACIA_DOOR, 
					Blocks.ACACIA_PLANKS, 
					Blocks.ACACIA_STAIRS, 
					Blocks.ACACIA_PLANKS);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.BIRCH_FENCE, 
					Blocks.BIRCH_DOOR, 
					Blocks.BIRCH_PLANKS, 
					Blocks.BIRCH_STAIRS, 
					Blocks.BIRCH_PLANKS);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.DARK_OAK_FENCE, 
					Blocks.DARK_OAK_DOOR, 
					Blocks.DARK_OAK_PLANKS, 
					Blocks.DARK_OAK_STAIRS, 
					Blocks.DARK_OAK_PLANKS);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.OAK_FENCE, 
					Blocks.OAK_DOOR, 
					Blocks.OAK_PLANKS, 
					Blocks.OAK_STAIRS, 
					Blocks.OAK_PLANKS);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.JUNGLE_FENCE, 
					Blocks.JUNGLE_DOOR, 
					Blocks.JUNGLE_PLANKS, 
					Blocks.JUNGLE_STAIRS, 
					Blocks.JUNGLE_PLANKS);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.SPRUCE_FENCE, 
					Blocks.SPRUCE_DOOR, 
					Blocks.SPRUCE_PLANKS, 
					Blocks.SPRUCE_STAIRS, 
					Blocks.SPRUCE_PLANKS);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.IRON_BARS, 
					Blocks.BIRCH_DOOR, 
					Blocks.PRISMARINE_BRICKS, 
					Blocks.PRISMARINE_BRICK_STAIRS, 
					Blocks.PRISMARINE_BRICKS);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.IRON_BARS, 
					Blocks.BIRCH_DOOR, 
					Blocks.DARK_PRISMARINE, 
					Blocks.DARK_PRISMARINE_STAIRS, 
					Blocks.DARK_PRISMARINE);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.IRON_BARS, 
					Blocks.BIRCH_DOOR, 
					Blocks.DARK_PRISMARINE, 
					Blocks.DARK_PRISMARINE_STAIRS, 
					Blocks.DARK_PRISMARINE);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.IRON_BARS, 
					Blocks.BIRCH_DOOR, 
					Blocks.PRISMARINE, 
					Blocks.PRISMARINE_STAIRS, 
					Blocks.PRISMARINE);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.PURPUR_PILLAR, 
					Blocks.BIRCH_DOOR, 
					Blocks.PURPUR_BLOCK, 
					Blocks.PURPUR_STAIRS, 
					Blocks.PURPUR_BLOCK);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.NETHER_BRICK_FENCE, 
					Blocks.BIRCH_DOOR, 
					Blocks.NETHER_BRICKS, 
					Blocks.NETHER_BRICK_STAIRS, 
					Blocks.NETHER_BRICKS);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.IRON_BARS, 
					Blocks.BIRCH_DOOR, 
					Blocks.RED_SANDSTONE, 
					Blocks.RED_SANDSTONE_STAIRS, 
					Blocks.RED_SANDSTONE);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.IRON_BARS, 
					Blocks.BIRCH_DOOR, 
					Blocks.SANDSTONE, 
					Blocks.SANDSTONE_STAIRS, 
					Blocks.SANDSTONE);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.QUARTZ_PILLAR, 
					Blocks.BIRCH_DOOR, 
					Blocks.QUARTZ_BLOCK, 
					Blocks.QUARTZ_STAIRS, 
					Blocks.QUARTZ_BLOCK);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.IRON_BARS, 
					Blocks.BIRCH_DOOR, 
					Blocks.STONE_BRICKS, 
					Blocks.STONE_BRICK_STAIRS, 
					Blocks.STONE_BRICKS);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.COBBLESTONE_WALL, 
					Blocks.BIRCH_DOOR, 
					Blocks.COBBLESTONE, 
					Blocks.COBBLESTONE_STAIRS, 
					Blocks.COBBLESTONE);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.IRON_BARS, 
					Blocks.OAK_DOOR, 
					Blocks.STONE, 
					Blocks.STONE_BRICK_STAIRS, 
					Blocks.STONE);
			entry.addToMapping(MAPPING);
		}
		{
			MapperEntry entry = new MapperEntry(
					Blocks.COBBLESTONE_WALL, 
					Blocks.BIRCH_DOOR, 
					Blocks.BRICKS, 
					Blocks.BRICK_STAIRS, 
					Blocks.BRICKS);
			entry.addToMapping(MAPPING);
		}
	}

}
