package cn.shadow.vacation_diary.dimension.structure.plats.urban;

import cn.shadow.vacation_diary.dimension.LinearBiomeContainer;
import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.plats.ConnectedLot;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.Colors;
import cn.shadow.vacation_diary.dimension.support.InitialBlocks;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.PlatMap;
import cn.shadow.vacation_diary.dimension.support.RealBlocks;
import cn.shadow.vacation_diary.util.MaterialTable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class GovernmentMonumentLot extends ConnectedLot {

	public GovernmentMonumentLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		botHeight = chunkOdds.calcRandomRange(2, 6);
		topHeight = chunkOdds.calcRandomRange(1, 3);
		monumentStyle = pickMonumentStyle();
		colors = new Colors(chunkOdds);
		loadMaterials(platmap);
	}

	private final int botHeight;
	private final int topHeight;
	private final int sectionHeight = 5;

	private Block foundationMaterial = Blocks.QUARTZ_BLOCK;
	private Block columnMaterial = Blocks.QUARTZ_BLOCK;
	private final Colors colors;

	private void loadMaterials(PlatMap platmap) {

		// what is it made of?
		foundationMaterial = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_GovernmentFoundations, chunkOdds, 
				foundationMaterial);
		columnMaterial = MaterialTable.getRandomBlock(MaterialTable.itemsSelectMaterial_GovernmentWalls, chunkOdds, 
				columnMaterial);
	}

	private enum MonumentStyle {
		COLUMN, PYRAMID, PEDESTAL, CHICKEN
	}

	private final MonumentStyle monumentStyle;

	private MonumentStyle pickMonumentStyle() {
		MonumentStyle[] values = MonumentStyle.values();
		return values[chunkOdds.getRandomInt(values.length)];
	}

	@Override
	protected void generateActualChunk(VocationCityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			LinearBiomeContainer biomes, DataContext context, int platX, int platZ) {
		int sidewalkLevel = getSidewalkLevel(generator);
		Block sidewalkMaterial = getSidewalkMaterial();

		chunk.setLayer(sidewalkLevel - 3, 3, foundationMaterial);
		chunk.setLayer(sidewalkLevel, sidewalkMaterial);

		switch (monumentStyle) {
		case COLUMN:
			drawColumn(generator, chunk, sidewalkLevel, chunkOdds.playOdds(Odds.oddsPrettyLikely));
			break;
		case PYRAMID:
			drawPyramid(generator, chunk, sidewalkLevel, chunkOdds.getRandomInt(2, 4));
			break;
		case PEDESTAL:
			drawPedestal(generator, chunk, sidewalkLevel, chunkOdds.playOdds(Odds.oddsPrettyLikely));
			break;
		case CHICKEN:
			drawChicken(generator, chunk, sidewalkLevel, chunkOdds.playOdds(Odds.oddsPrettyLikely));
			break;
		}
	}

	private void drawColumn(VocationCityWorldGenerator generator, InitialBlocks chunk, int y1, boolean doTop) {
		int y2 = y1 + botHeight * sectionHeight;
		int y3 = y2 + topHeight * sectionHeight;
		chunk.setBlocks(5, 11, y1, y2, 5, 11, columnMaterial);
		chunk.setWalls(6, 10, y2, y3, 6, 10, colors.getGlass());
		if (chunkOdds.flipCoin())
			chunk.setBlocks(7, 9, y2, y3, 7, 9, columnMaterial);
		if (doTop) {
			chunk.setBlocks(7, 9, y3, 7, 9, columnMaterial);
		}
	}

	private void drawChicken(VocationCityWorldGenerator generator, InitialBlocks chunk, int y1, boolean doTop) {
		int y2 = y1 + botHeight * sectionHeight;
		chunk.setBlocks(6, 10, y1, y2, 5, 11, columnMaterial);
		chunk.setBlocks(5, 6, y1, y2, 6, 10, columnMaterial);
		chunk.setBlocks(10, 11, y1, y2, 6, 10, columnMaterial);
		chunk.setBlocks(4, 12, y2, 4, 12, columnMaterial);
		if (doTop) {
			if (chunkOdds.playOdds(Odds.oddsSomewhatUnlikely))
				generator.thingProvider.generateChicken(chunk, 1, y2 + 1, 4);
			else
				chunk.setBlocks(5, 11, y2 + 1, y2 + chunkOdds.calcRandomRange(3, 8), 5, 11, colors.getGlass());
		}
	}

	private void drawPyramid(VocationCityWorldGenerator generator, InitialBlocks chunk, int y, int scaleFactor) {
		chunk.setBlocks(2, 7, y, y + scaleFactor, 2, 7, columnMaterial);
		chunk.setBlocks(10, 15, y, y + scaleFactor, 2, 7, columnMaterial);
		chunk.setBlocks(2, 7, y, y + scaleFactor, 10, 15, columnMaterial);
		chunk.setBlocks(10, 15, y, y + scaleFactor, 10, 15, columnMaterial);

		for (int i = 1; i < 7; i++) {
			int xy1 = 2 + i;
			int xy2 = 15 - i;
			int y1 = y + i * scaleFactor;
			int y2 = y1 + (i == 6 ? 1 : scaleFactor);
			if (i < 6) {
				chunk.setWalls(xy1, xy2, y1, y2, xy1, xy2, columnMaterial);
				chunk.setWalls(xy1 + 1, xy2 - 1, y1, y2, xy1 + 1, xy2 - 1, columnMaterial);
			} else
				chunk.setBlocks(xy1, xy2, y1, y2, xy1, xy2, columnMaterial);
		}
	}

	private void drawPedestal(VocationCityWorldGenerator generator, InitialBlocks chunk, int y1, boolean doTop) {
		int y2 = y1 + botHeight * sectionHeight;
		int y3 = y2 + topHeight * sectionHeight;
		chunk.setBlocks(5, 10, y1, y2, 5, 10, columnMaterial);
		if (chunkOdds.flipCoin()) {

			// corner supports?
			chunk.setBlocks(5, y2, y3, 5, columnMaterial);
			chunk.setBlocks(5, y2, y3, 9, columnMaterial);

			chunk.setBlocks(9, y2, y3, 5, columnMaterial);
			chunk.setBlocks(9, y2, y3, 9, columnMaterial);

			int yT = y3;
			if (chunkOdds.flipCoin())
				yT = yT - sectionHeight;

			// side supports?
			if (chunkOdds.flipCoin()) {
				chunk.setBlocks(5, y2, yT, 7, columnMaterial);

				chunk.setBlocks(7, y2, yT, 5, columnMaterial);
				chunk.setBlocks(7, y2, yT, 9, columnMaterial);

				chunk.setBlocks(9, y2, yT, 7, columnMaterial);
			}

			// center bit?
			if (chunkOdds.flipCoin()) {
				if (chunkOdds.flipCoin())
					chunk.setBlocks(7, y2, y3, 7, columnMaterial);
				else
					chunk.setBlocks(6, 9, y2, yT, 6, 9, colors.getGlass());
			}

		} else {
			chunk.setBlocks(6, 9, y2, y3, 6, 9, colors.getGlass());
		}
		chunk.setBlocks(5, 10, y3, 5, 10, columnMaterial);
		if (doTop) {
			generator.thingProvider.generateStatue(chunk, chunkOdds, 7, y3 + 1, 7);
		} else {
			chunk.setBlocks(6, 9, y3 + 1, 6, 9, columnMaterial);
		}
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new GovernmentMonumentLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected void generateActualBlocks(VocationCityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		// Don't need anything here
	}

	@Override
	public int getBottomY(VocationCityWorldGenerator generator) {
		return generator.streetLevel - sectionHeight;
	}

	@Override
	public int getTopY(VocationCityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return generator.streetLevel + botHeight * sectionHeight + topHeight * sectionHeight;
	}

}
