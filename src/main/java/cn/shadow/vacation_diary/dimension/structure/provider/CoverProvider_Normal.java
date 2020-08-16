package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;

public class CoverProvider_Normal extends CoverProvider {

	public CoverProvider_Normal(Odds odds) {
		super(odds);
	}

	@Override
	public void generateCoverage(VocationCityWorldGenerator generator, RealBlocks chunk, int x, int y, int z,
			CoverageType coverageType) {
		if (likelyCover(generator))
			setCoverage(generator, chunk, x, y, z, coverageType);
	}

}
