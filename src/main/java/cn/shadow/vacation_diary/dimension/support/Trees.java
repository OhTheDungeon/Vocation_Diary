package cn.shadow.vacation_diary.dimension.support;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public final class Trees {

	private final Odds odds;

	public Trees(Odds odds) {
		this.odds = odds;
	}

	public TreeSpecies getRandomSpecies() {
		TreeSpecies[] values = TreeSpecies.values();
		return values[odds.getRandomInt(values.length)];
	}

	public Block getRandomWoodSign() {
		return getRandomWoodSign(odds.getRandomWoodSpecies());
	}

	public static Block getRandomWoodSign(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Blocks.ACACIA_SIGN;
		case BIRCH:
			return Blocks.BIRCH_SIGN;
		case DARK_OAK:
			return Blocks.DARK_OAK_SIGN;
		case GENERIC:
			return Blocks.OAK_SIGN;
		case JUNGLE:
			return Blocks.JUNGLE_SIGN;
		case REDWOOD:
			return Blocks.SPRUCE_SIGN;
		}
	}

	public Block getRandomWoodWallSign() {
		return getRandomWoodWallSign(odds.getRandomWoodSpecies());
	}

	public static Block getRandomWoodWallSign(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Blocks.ACACIA_WALL_SIGN;
		case BIRCH:
			return Blocks.BIRCH_WALL_SIGN;
		case DARK_OAK:
			return Blocks.DARK_OAK_WALL_SIGN;
		case GENERIC:
			return Blocks.OAK_WALL_SIGN;
		case JUNGLE:
			return Blocks.JUNGLE_WALL_SIGN;
		case REDWOOD:
			return Blocks.SPRUCE_WALL_SIGN;
		}
	}

	public Block getRandomWoodLog() {
		return getRandomWoodLog(odds.getRandomWoodSpecies());
	}

	public static Block getRandomWoodLog(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Blocks.ACACIA_LOG;
		case BIRCH:
			return Blocks.BIRCH_LOG;
		case DARK_OAK:
			return Blocks.DARK_OAK_LOG;
		case GENERIC:
			return Blocks.OAK_LOG;
		case JUNGLE:
			return Blocks.JUNGLE_LOG;
		case REDWOOD:
			return Blocks.SPRUCE_LOG;
		}
	}

	public Block getRandomWoodStairs() {
		return getRandomWoodStairs(odds.getRandomWoodSpecies());
	}

	private static Block getRandomWoodStairs(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Blocks.ACACIA_STAIRS;
		case BIRCH:
			return Blocks.BIRCH_STAIRS;
		case DARK_OAK:
			return Blocks.DARK_OAK_STAIRS;
		case GENERIC:
			return Blocks.OAK_STAIRS;
		case JUNGLE:
			return Blocks.JUNGLE_STAIRS;
		case REDWOOD:
			return Blocks.SPRUCE_STAIRS;
		}
	}

	public Block getRandomWoodSlab() {
		return getRandomWoodSlab(odds.getRandomWoodSpecies());
	}

	private static Block getRandomWoodSlab(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Blocks.ACACIA_SLAB;
		case BIRCH:
			return Blocks.BIRCH_SLAB;
		case DARK_OAK:
			return Blocks.DARK_OAK_SLAB;
		case GENERIC:
			return Blocks.OAK_SLAB;
		case JUNGLE:
			return Blocks.JUNGLE_SLAB;
		case REDWOOD:
			return Blocks.SPRUCE_SLAB;
		}
	}

	public Block getRandomWoodDoor() {
		return getRandomWoodDoor(odds.getRandomWoodSpecies());
	}

	private static Block getRandomWoodDoor(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Blocks.ACACIA_DOOR;
		case BIRCH:
			return Blocks.BIRCH_DOOR;
		case DARK_OAK:
			return Blocks.DARK_OAK_DOOR;
		case GENERIC:
			return Blocks.OAK_DOOR;
		case JUNGLE:
			return Blocks.JUNGLE_DOOR;
		case REDWOOD:
			return Blocks.SPRUCE_DOOR;
		}
	}

	public Block getRandomWoodTrapDoor() {
		return getRandomWoodTrapDoor(odds.getRandomWoodSpecies());
	}

	private static Block getRandomWoodTrapDoor(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Blocks.ACACIA_TRAPDOOR;
		case BIRCH:
			return Blocks.BIRCH_TRAPDOOR;
		case DARK_OAK:
			return Blocks.DARK_OAK_TRAPDOOR;
		case GENERIC:
			return Blocks.OAK_TRAPDOOR;
		case JUNGLE:
			return Blocks.JUNGLE_TRAPDOOR;
		case REDWOOD:
			return Blocks.SPRUCE_TRAPDOOR;
		}
	}

	public Block getRandomWoodFence() {
		return getRandomWoodFence(odds.getRandomWoodSpecies());
	}

	private static Block getRandomWoodFence(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Blocks.ACACIA_FENCE;
		case BIRCH:
			return Blocks.BIRCH_FENCE;
		case DARK_OAK:
			return Blocks.DARK_OAK_FENCE;
		case GENERIC:
			return Blocks.OAK_FENCE;
		case JUNGLE:
			return Blocks.JUNGLE_FENCE;
		case REDWOOD:
			return Blocks.SPRUCE_FENCE;
		}
	}

	public Block getRandomWoodLeaves() {
		return getRandomWoodLeaves(odds.getRandomWoodSpecies());
	}

	public static Block getRandomWoodLeaves(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Blocks.ACACIA_LEAVES;
		case BIRCH:
			return Blocks.BIRCH_LEAVES;
		case DARK_OAK:
			return Blocks.DARK_OAK_LEAVES;
		case GENERIC:
			return Blocks.OAK_LEAVES;
		case JUNGLE:
			return Blocks.JUNGLE_LEAVES;
		case REDWOOD:
			return Blocks.SPRUCE_LEAVES;
		}
	}

}
