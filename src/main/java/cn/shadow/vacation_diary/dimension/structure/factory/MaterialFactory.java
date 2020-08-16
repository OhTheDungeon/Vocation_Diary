package cn.shadow.vacation_diary.dimension.structure.factory;

import cn.shadow.vacation_diary.dimension.support.AbstractBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;

public abstract class MaterialFactory {

	public enum VerticalStyle {
		GGGG, WGGG, WGGW, WWWW
	}

	public enum HorizontalStyle {
		WG, WGG, WGGG, WWG, WWGG, GGGG, RANDOM
	}

	private final VerticalStyle verticalStyle;
	public final HorizontalStyle horizontalStyle;
	private final Boolean decayed;
	final Odds odds;

	private static final double oddsOfDecay = Odds.oddsSomewhatLikely;

	MaterialFactory(Odds odds, boolean decayed) {
		super();
		this.odds = odds;
		this.decayed = decayed;
		verticalStyle = pickVerticalStyle();
		horizontalStyle = pickHorizontalStyle();
	}

	MaterialFactory(MaterialFactory other) {
		super();
		odds = other.odds;
		decayed = other.decayed;
		verticalStyle = other.verticalStyle;
		horizontalStyle = other.horizontalStyle;
	}

	public boolean isSame(MaterialFactory other) {
		return verticalStyle == other.verticalStyle && horizontalStyle == other.horizontalStyle;
	}

	VerticalStyle pickVerticalStyle() {
		switch (odds.getRandomInt(3)) {
		default:
			return VerticalStyle.GGGG;
		case 1:
			return VerticalStyle.WGGW;
		case 2:
			return VerticalStyle.WGGG;
//		case 3:
//			return VerticalStyle.WWWW;
		}
	}

	HorizontalStyle pickHorizontalStyle() {
		switch (odds.getRandomInt(7)) {
		default:
			return HorizontalStyle.RANDOM;
		case 1:
			return HorizontalStyle.WG;
		case 2:
			return HorizontalStyle.WGG;
		case 3:
			return HorizontalStyle.WGGG;
		case 4:
			return HorizontalStyle.WWG;
		case 5:
			return HorizontalStyle.WWGG;
		case 6:
			return HorizontalStyle.GGGG;
		}
	}

	Block pickMaterial(Block primary, Block secondary, int i) {
		switch (horizontalStyle) {
		case WG:
			return i % 2 == 0 ? primary : secondary;
		case WGG:
			return i % 3 == 0 ? primary : secondary;
		case WGGG:
			return i % 4 == 0 ? primary : secondary;
		case WWG:
			return ((i % 3 == 0) || (i % 3 == 1)) ? primary : secondary;
		case WWGG:
			return ((i % 4 == 0) || (i % 4 == 1)) ? primary : secondary;
		case GGGG:
			return secondary;
		default: // case RANDOM:
			return odds.flipCoin() ? primary : secondary;
		}
	}

	private void decayMaterial(AbstractBlocks blocks, int x, int y1, int y2, int z) {
		if (decayed && odds.playOdds(oddsOfDecay)) {
			int range = Math.max(1, y2 - y1);
			blocks.clearBlock(x, y1 + odds.getRandomInt(range), z);
		}
	}

	public abstract void placeMaterial(AbstractBlocks blocks, Block primary, Block secondary, int x, int y1,
			int y2, int z, Direction... facing);

	void placeMaterial(AbstractBlocks blocks, Block primary, Block secondary, Block glass, int x,
			int y1, int y2, int z, Direction... facing) {
		switch (verticalStyle) {
		case WGGG:
			blocks.setBlocks(x, y1, y1 + 1, z, primary);
			blocks.setBlocks(x, y1 + 1, y2, z, glass, facing);
			if (glass == secondary)
				decayMaterial(blocks, x, y1 + 1, y2, z);
			break;
		case WGGW:
			blocks.setBlocks(x, y1, y1 + 1, z, primary);
			blocks.setBlocks(x, y1 + 1, y2 - 1, z, glass, facing);
			blocks.setBlocks(x, y2 - 1, y2, z, primary);
			if (glass == secondary)
				decayMaterial(blocks, x, y1 + 1, y2 - 1, z);
			break;
		case GGGG:
			blocks.setBlocks(x, y1, y2, z, glass, facing);
			if (glass == secondary)
				decayMaterial(blocks, x, y1, y2, z);
			break;
		case WWWW:
			blocks.setBlocks(x, y1, y2, z, primary);
			break;
		}
	}
}
