package cn.shadow.vacation_diary.dimension.structure.plats.nature;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.ConstructLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.structure.provider.StructureInAirProvider;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;

public class HotairBalloonLot extends ConstructLot {

	public HotairBalloonLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		trulyIsolated = true;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new HotairBalloonLot(platmap, chunkX, chunkZ);
	}

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return blockYs.getMaxHeight() + 20;
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {
		// TODO what?
	}

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {

		// place snow
		generateSurface(generator, chunk, false);

		// where is the surface?
		int atY = getBottomY(generator);

		// hot air balloon
		generator.reportLocation("Hot Air Balloon", chunk);
		int rangeY = Math.max(2, chunk.height - StructureInAirProvider.hotairBalloonHeight - atY);
		generator.structureInAirProvider.generateHotairBalloon(generator, chunk, context,
				atY + chunkOdds.getRandomInt(rangeY), chunkOdds);
	}
}
