package cn.shadow.vacation_diary.dimension.support;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.DyeColor;

public final class Colors {

	public enum ColorSet {
		ALL, GREEN, WHITE, TAN, PINK, NETHER, THEEND, DARK, LIGHT, RANDOM
	}

	private static final DyeColor[] setAll = { DyeColor.WHITE, DyeColor.ORANGE, DyeColor.MAGENTA, DyeColor.LIGHT_BLUE,
			DyeColor.YELLOW, DyeColor.LIME, DyeColor.PINK, DyeColor.GRAY, DyeColor.LIGHT_GRAY, DyeColor.CYAN,
			DyeColor.PURPLE, DyeColor.BLUE, DyeColor.BROWN, DyeColor.GREEN, DyeColor.RED, DyeColor.BLACK };

	private static final DyeColor[] setGreen = { DyeColor.BROWN, DyeColor.GREEN, DyeColor.GRAY };

	private static final DyeColor[] setDark = { DyeColor.GRAY, DyeColor.CYAN, DyeColor.PURPLE, DyeColor.BLUE, DyeColor.BROWN,
			DyeColor.GREEN, DyeColor.RED, DyeColor.BLACK };

	private static final DyeColor[] setLight = { DyeColor.WHITE, DyeColor.ORANGE, DyeColor.MAGENTA, DyeColor.LIGHT_BLUE,
			DyeColor.YELLOW, DyeColor.LIME, DyeColor.PINK, DyeColor.LIGHT_GRAY };

	private static final DyeColor[] setNether = { DyeColor.RED, DyeColor.BROWN, DyeColor.PURPLE, DyeColor.BLACK,
			DyeColor.GRAY };

	private static final DyeColor[] setTan = { DyeColor.ORANGE, DyeColor.YELLOW };

	private static final DyeColor[] setPink = { DyeColor.PINK, DyeColor.LIGHT_GRAY, DyeColor.RED };

	private static final DyeColor[] setWhite = { DyeColor.WHITE, DyeColor.LIGHT_GRAY };

	private static final DyeColor[] setEnd = { DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.PINK };

	private DyeColor[] colors;
	private final Odds odds;

	public Colors(Odds odds) {
		this.odds = odds;
		setColors(setAll);
	}

//	public Colors(Odds odds, DyeColor ... dyeColors) {
//		this.odds = odds;
//		assert(dyeColors.length > 0);
//		setColors(dyeColors);
//	}

	public Colors(Odds odds, ColorSet set) {
		this.odds = odds;
		setColors(set);
	}

	public void setColors(ColorSet set) {

		// do something special for random
		if (set == ColorSet.RANDOM) {
			ColorSet[] all = ColorSet.values();
			set = all[odds.getRandomInt(all.length)];
			if (set == ColorSet.RANDOM)
				set = ColorSet.ALL;
		}

		// now do it
		switch (set) {
		default:
		case ALL:
			setColors(setAll);
			break;
		case DARK:
			setColors(setDark);
			break;
		case GREEN:
			setColors(setGreen);
			break;
		case LIGHT:
			setColors(setLight);
			break;
		case NETHER:
			setColors(setNether);
			break;
		case PINK:
			setColors(setPink);
			break;
		case TAN:
			setColors(setTan);
			break;
		case THEEND:
			setColors(setEnd);
			break;
		case WHITE:
			setColors(setWhite);
			break;
		}
	}

	private void setColors(DyeColor... dyeColors) {
		colors = dyeColors;
	}

	public void fixColor() {
		if (colors.length > 1)
			setColors(getRandomColor());
	}

	public DyeColor getRandomColor() {
		int count = colors.length;
		if (count == 1)
			return colors[0];
		else
			return colors[odds.getRandomInt(count)];
	}

	public Block getGlass() {
		return getGlass(getRandomColor());
	}

	private static Block getGlass(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Blocks.BLACK_STAINED_GLASS;
		case BLUE:
			return Blocks.BLUE_STAINED_GLASS;
		case BROWN:
			return Blocks.BROWN_STAINED_GLASS;
		case CYAN:
			return Blocks.CYAN_STAINED_GLASS;
		case GRAY:
			return Blocks.GRAY_STAINED_GLASS;
		case GREEN:
			return Blocks.GREEN_STAINED_GLASS;
		case LIGHT_BLUE:
			return Blocks.LIGHT_BLUE_STAINED_GLASS;
		case LIGHT_GRAY:
			return Blocks.LIGHT_GRAY_STAINED_GLASS;
		case LIME:
			return Blocks.LIME_STAINED_GLASS;
		case MAGENTA:
			return Blocks.MAGENTA_STAINED_GLASS;
		case ORANGE:
			return Blocks.ORANGE_STAINED_GLASS;
		case PINK:
			return Blocks.PINK_STAINED_GLASS;
		case PURPLE:
			return Blocks.PURPLE_STAINED_GLASS;
		case RED:
			return Blocks.RED_STAINED_GLASS;
		case WHITE:
			return Blocks.WHITE_STAINED_GLASS;
		case YELLOW:
			return Blocks.YELLOW_STAINED_GLASS;
		}
	}

	public Block getGlassPane() {
		return getGlass(getRandomColor());
	}

	public Block getCarpet() {
		return getCarpet(getRandomColor());
	}

	private static Block getCarpet(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Blocks.BLACK_CARPET;
		case BLUE:
			return Blocks.BLUE_CARPET;
		case BROWN:
			return Blocks.BROWN_CARPET;
		case CYAN:
			return Blocks.CYAN_CARPET;
		case GRAY:
			return Blocks.GRAY_CARPET;
		case GREEN:
			return Blocks.GREEN_CARPET;
		case LIGHT_BLUE:
			return Blocks.LIGHT_BLUE_CARPET;
		case LIGHT_GRAY:
			return Blocks.LIGHT_GRAY_CARPET;
		case LIME:
			return Blocks.LIME_CARPET;
		case MAGENTA:
			return Blocks.MAGENTA_CARPET;
		case ORANGE:
			return Blocks.ORANGE_CARPET;
		case PINK:
			return Blocks.PINK_CARPET;
		case PURPLE:
			return Blocks.PURPLE_CARPET;
		case RED:
			return Blocks.RED_CARPET;
		case WHITE:
			return Blocks.WHITE_CARPET;
		case YELLOW:
			return Blocks.YELLOW_CARPET;
		}
	}

	public Block getWool() {
		return getWool(getRandomColor());
	}

	private static Block getWool(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Blocks.BLACK_WOOL;
		case BLUE:
			return Blocks.BLUE_WOOL;
		case BROWN:
			return Blocks.BROWN_WOOL;
		case CYAN:
			return Blocks.CYAN_WOOL;
		case GRAY:
			return Blocks.GRAY_WOOL;
		case GREEN:
			return Blocks.GREEN_WOOL;
		case LIGHT_BLUE:
			return Blocks.LIGHT_BLUE_WOOL;
		case LIGHT_GRAY:
			return Blocks.LIGHT_GRAY_WOOL;
		case LIME:
			return Blocks.LIME_WOOL;
		case MAGENTA:
			return Blocks.MAGENTA_WOOL;
		case ORANGE:
			return Blocks.ORANGE_WOOL;
		case PINK:
			return Blocks.PINK_WOOL;
		case PURPLE:
			return Blocks.PURPLE_WOOL;
		case RED:
			return Blocks.RED_WOOL;
		case WHITE:
			return Blocks.WHITE_WOOL;
		case YELLOW:
			return Blocks.YELLOW_WOOL;
		}
	}

	public Block getBed() {
		return getBed(getRandomColor());
	}

	private static Block getBed(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Blocks.BLACK_BED;
		case BLUE:
			return Blocks.BLUE_BED;
		case BROWN:
			return Blocks.BROWN_BED;
		case CYAN:
			return Blocks.CYAN_BED;
		case GRAY:
			return Blocks.GRAY_BED;
		case GREEN:
			return Blocks.GREEN_BED;
		case LIGHT_BLUE:
			return Blocks.LIGHT_BLUE_BED;
		case LIGHT_GRAY:
			return Blocks.LIGHT_GRAY_BED;
		case LIME:
			return Blocks.LIME_BED;
		case MAGENTA:
			return Blocks.MAGENTA_BED;
		case ORANGE:
			return Blocks.ORANGE_BED;
		case PINK:
			return Blocks.PINK_BED;
		case PURPLE:
			return Blocks.PURPLE_BED;
		case RED:
			return Blocks.RED_BED;
		case WHITE:
			return Blocks.WHITE_BED;
		case YELLOW:
			return Blocks.YELLOW_BED;
		}
	}

	public Block getTerracotta() {
		return getTerracotta(getRandomColor());
	}

	private static Block getTerracotta(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Blocks.BLACK_TERRACOTTA;
		case BLUE:
			return Blocks.BLUE_TERRACOTTA;
		case BROWN:
			return Blocks.BROWN_TERRACOTTA;
		case CYAN:
			return Blocks.CYAN_TERRACOTTA;
		case GRAY:
			return Blocks.GRAY_TERRACOTTA;
		case GREEN:
			return Blocks.GREEN_TERRACOTTA;
		case LIGHT_BLUE:
			return Blocks.LIGHT_BLUE_TERRACOTTA;
		case LIGHT_GRAY:
			return Blocks.LIGHT_GRAY_TERRACOTTA;
		case LIME:
			return Blocks.LIME_TERRACOTTA;
		case MAGENTA:
			return Blocks.MAGENTA_TERRACOTTA;
		case ORANGE:
			return Blocks.ORANGE_TERRACOTTA;
		case PINK:
			return Blocks.PINK_TERRACOTTA;
		case PURPLE:
			return Blocks.PURPLE_TERRACOTTA;
		case RED:
			return Blocks.RED_TERRACOTTA;
		case WHITE:
			return Blocks.WHITE_TERRACOTTA;
		case YELLOW:
			return Blocks.YELLOW_TERRACOTTA;
		}
	}

	public Block getGlazedTerracotta() {
		return getGlazedTerracotta(getRandomColor());
	}

	private static Block getGlazedTerracotta(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Blocks.BLACK_GLAZED_TERRACOTTA;
		case BLUE:
			return Blocks.BLUE_GLAZED_TERRACOTTA;
		case BROWN:
			return Blocks.BROWN_GLAZED_TERRACOTTA;
		case CYAN:
			return Blocks.CYAN_GLAZED_TERRACOTTA;
		case GRAY:
			return Blocks.GRAY_GLAZED_TERRACOTTA;
		case GREEN:
			return Blocks.GREEN_GLAZED_TERRACOTTA;
		case LIGHT_BLUE:
			return Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA;
		case LIGHT_GRAY:
			return Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA;
		case LIME:
			return Blocks.LIME_GLAZED_TERRACOTTA;
		case MAGENTA:
			return Blocks.MAGENTA_GLAZED_TERRACOTTA;
		case ORANGE:
			return Blocks.ORANGE_GLAZED_TERRACOTTA;
		case PINK:
			return Blocks.PINK_GLAZED_TERRACOTTA;
		case PURPLE:
			return Blocks.PURPLE_GLAZED_TERRACOTTA;
		case RED:
			return Blocks.RED_GLAZED_TERRACOTTA;
		case WHITE:
			return Blocks.WHITE_GLAZED_TERRACOTTA;
		case YELLOW:
			return Blocks.YELLOW_GLAZED_TERRACOTTA;
		}
	}

	public Block getConcrete() {
		return getConcrete(getRandomColor());
	}

	private static Block getConcrete(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Blocks.BLACK_CONCRETE;
		case BLUE:
			return Blocks.BLUE_CONCRETE;
		case BROWN:
			return Blocks.BROWN_CONCRETE;
		case CYAN:
			return Blocks.CYAN_CONCRETE;
		case GRAY:
			return Blocks.GRAY_CONCRETE;
		case GREEN:
			return Blocks.GREEN_CONCRETE;
		case LIGHT_BLUE:
			return Blocks.LIGHT_BLUE_CONCRETE;
		case LIGHT_GRAY:
			return Blocks.LIGHT_GRAY_CONCRETE;
		case LIME:
			return Blocks.LIME_CONCRETE;
		case MAGENTA:
			return Blocks.MAGENTA_CONCRETE;
		case ORANGE:
			return Blocks.ORANGE_CONCRETE;
		case PINK:
			return Blocks.PINK_CONCRETE;
		case PURPLE:
			return Blocks.PURPLE_CONCRETE;
		case RED:
			return Blocks.RED_CONCRETE;
		case WHITE:
			return Blocks.WHITE_CONCRETE;
		case YELLOW:
			return Blocks.YELLOW_CONCRETE;
		}
	}

	public Block getConcretePowder() {
		return getConcretePowder(getRandomColor());
	}

	private static Block getConcretePowder(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Blocks.BLACK_CONCRETE_POWDER;
		case BLUE:
			return Blocks.BLUE_CONCRETE_POWDER;
		case BROWN:
			return Blocks.BROWN_CONCRETE_POWDER;
		case CYAN:
			return Blocks.CYAN_CONCRETE_POWDER;
		case GRAY:
			return Blocks.GRAY_CONCRETE_POWDER;
		case GREEN:
			return Blocks.GREEN_CONCRETE_POWDER;
		case LIGHT_BLUE:
			return Blocks.LIGHT_BLUE_CONCRETE_POWDER;
		case LIGHT_GRAY:
			return Blocks.LIGHT_GRAY_CONCRETE_POWDER;
		case LIME:
			return Blocks.LIME_CONCRETE_POWDER;
		case MAGENTA:
			return Blocks.MAGENTA_CONCRETE_POWDER;
		case ORANGE:
			return Blocks.ORANGE_CONCRETE_POWDER;
		case PINK:
			return Blocks.PINK_CONCRETE_POWDER;
		case PURPLE:
			return Blocks.PURPLE_CONCRETE_POWDER;
		case RED:
			return Blocks.RED_CONCRETE_POWDER;
		case WHITE:
			return Blocks.WHITE_CONCRETE_POWDER;
		case YELLOW:
			return Blocks.YELLOW_CONCRETE_POWDER;
		}
	}
}
