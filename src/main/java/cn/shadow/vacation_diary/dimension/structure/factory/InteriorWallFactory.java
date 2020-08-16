package cn.shadow.vacation_diary.dimension.structure.factory;

import cn.shadow.vacation_diary.dimension.support.AbstractBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;

public class InteriorWallFactory extends MaterialFactory {

	public InteriorWallFactory(Odds odds, boolean decayed) {
		super(odds, decayed);
	}

	public InteriorWallFactory(MaterialFactory other) {
		super(other);
	}

	@Override
	protected VerticalStyle pickVerticalStyle() {
		switch (odds.getRandomInt(4)) {
		default:
			return VerticalStyle.GGGG;
		case 1:
			return VerticalStyle.WGGW;
		case 2:
			return VerticalStyle.WGGG;
		case 3:
			return VerticalStyle.WWWW;
		}
	}

	@Override
	protected HorizontalStyle pickHorizontalStyle() {
		return HorizontalStyle.GGGG;
	}

	@Override
	public void placeMaterial(AbstractBlocks blocks, Block primary, Block secondary, int x, int y1, int y2, int z,
			Direction... facing) {
		super.placeMaterial(blocks, primary, secondary, pickMaterial(primary, secondary, x), x, y1, y2, z, facing);
	}
}
