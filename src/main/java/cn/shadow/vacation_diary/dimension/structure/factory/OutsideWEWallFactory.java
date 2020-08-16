package cn.shadow.vacation_diary.dimension.structure.factory;

import cn.shadow.vacation_diary.dimension.support.AbstractBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;

public class OutsideWEWallFactory extends MaterialFactory {

	public OutsideWEWallFactory(Odds odds, boolean decayed) {
		super(odds, decayed);
	}

	public OutsideWEWallFactory(MaterialFactory other) {
		super(other);
	}

	@Override
	public void placeMaterial(AbstractBlocks blocks, Block primary, Block secondary, int x, int y1, int y2, int z,
			Direction... facing) {
		super.placeMaterial(blocks, primary, secondary, pickMaterial(primary, secondary, x), x, y1, y2, z, facing);
	}
}
