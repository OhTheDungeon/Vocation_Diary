package cn.shadow.vacation_diary.dimension.structure.provider;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.plats.PlatLot;
import cn.shadow.vacation_diary.dimension.support.AbstractBlocks;
import cn.shadow.vacation_diary.dimension.support.AbstractCachedYs;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.dimension.support.SupportBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;

public class ThingProvider extends Provider {

	private ThingProvider() {
		// TODO Auto-generated constructor stub
	}

	public static ThingProvider loadProvider(VocationCityWorldGenerator generator) {
		// for now
		return new ThingProvider();
	}

	public void generateStatue(AbstractBlocks chunk, Odds odds, int x, int y, int z) {
		Block matBlock = Blocks.QUARTZ_BLOCK;
		int tallness = odds.calcRandomRange(3, 5);

		// legs
		int y1 = y;
		int y2 = y1 + tallness;
		chunk.setBlocks(x - 1, y1, y1 + tallness, z, matBlock);
		chunk.setBlocks(x + 1, y1, y1 + tallness, z, matBlock);

		// torso
		y1 = y2;
		y2 = y1 + tallness - odds.calcRandomRange(0, 1);
		chunk.setBlocks(x - 1, x + 2, y1, y2, z, z + 1, matBlock);

		// arms
		chunk.setBlocks(x - 2, y1, y2 - 1, z, matBlock);
		chunk.setBlocks(x + 2, y1, y2 - 1, z, matBlock);

		// shoulders
//		chunk.setBlock(x - 2, y2 - 1, z, matBlock, 0);
//		chunk.setBlock(x + 2, y2 - 1, z, matBlock, 1);
		chunk.setBlock(x - 2, y2 - 1, z, matBlock);
		chunk.setBlock(x + 2, y2 - 1, z, matBlock);

		// head
		y1 = y2;
		chunk.setBlock(x, y1, z, matBlock);
		chunk.setBlocks(x - 1, x + 2, y1 + 1, z, z + 1, matBlock);
		chunk.setBlock(x, y1 + 2, z, matBlock);
	}

	private static final byte NON = 0;
	private static final byte WHT = 1;
	private static final byte YEL = 2;
	private static final byte RED = 3;
	private static final byte BLK = 4;

	private final byte[][] chickenFeet = new byte[][] {
			// 0 1 2 3 4 5 6 7
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 0
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 1
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 2
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 3
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 4
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 5
			{ NON, YEL, YEL, YEL, YEL, YEL, YEL, NON }, // 6
			{ NON, YEL, YEL, YEL, YEL, YEL, YEL, NON }, // 7
			{ NON, NON, YEL, NON, NON, YEL, NON, NON }, // 8
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 9
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 10
			{ NON, NON, NON, NON, NON, NON, NON, NON } // 11
	};

	private final byte[][] chickenLegs = new byte[][] {
			// 0 1 2 3 4 5 6 7
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 0
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 1
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 2
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 3
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 4
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 5
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 6
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 7
			{ NON, NON, YEL, NON, NON, YEL, NON, NON }, // 8
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 9
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 10
			{ NON, NON, NON, NON, NON, NON, NON, NON } // 11
	};

	private final byte[][] chickenUnder = new byte[][] {
			// 0 1 2 3 4 5 6 7
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 0
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 1
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 2
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 3
			{ NON, WHT, WHT, WHT, WHT, WHT, WHT, NON }, // 4
			{ NON, WHT, WHT, WHT, WHT, WHT, WHT, NON }, // 5
			{ NON, WHT, WHT, WHT, WHT, WHT, WHT, NON }, // 6
			{ NON, WHT, WHT, WHT, WHT, WHT, WHT, NON }, // 7
			{ NON, WHT, WHT, WHT, WHT, WHT, WHT, NON }, // 8
			{ NON, WHT, WHT, WHT, WHT, WHT, WHT, NON }, // 9
			{ NON, WHT, WHT, WHT, WHT, WHT, WHT, NON }, // 10
			{ NON, WHT, WHT, WHT, WHT, WHT, WHT, NON } // 11
	};

	private final byte[][] chickenBody = new byte[][] {
			// 0 1 2 3 4 5 6 7
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 0
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 1
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 2
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 3
			{ NON, WHT, WHT, WHT, WHT, WHT, WHT, NON }, // 4
			{ WHT, WHT, WHT, WHT, WHT, WHT, WHT, WHT }, // 5
			{ WHT, WHT, WHT, WHT, WHT, WHT, WHT, WHT }, // 6
			{ WHT, WHT, WHT, WHT, WHT, WHT, WHT, WHT }, // 7
			{ WHT, WHT, WHT, WHT, WHT, WHT, WHT, WHT }, // 8
			{ WHT, WHT, WHT, WHT, WHT, WHT, WHT, WHT }, // 9
			{ WHT, WHT, WHT, WHT, WHT, WHT, WHT, WHT }, // 10
			{ NON, WHT, WHT, WHT, WHT, WHT, WHT, NON } // 11
	};

	private final byte[][] chickenMouth = new byte[][] {
			// 0 1 2 3 4 5 6 7
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 0
			{ NON, NON, NON, RED, RED, NON, NON, NON }, // 1
			{ NON, NON, WHT, WHT, WHT, WHT, NON, NON }, // 2
			{ NON, NON, WHT, WHT, WHT, WHT, NON, NON }, // 3
			{ NON, WHT, WHT, WHT, WHT, WHT, WHT, NON }, // 4
			{ WHT, WHT, WHT, WHT, WHT, WHT, WHT, WHT }, // 5
			{ WHT, WHT, WHT, WHT, WHT, WHT, WHT, WHT }, // 6
			{ WHT, WHT, WHT, WHT, WHT, WHT, WHT, WHT }, // 7
			{ WHT, WHT, WHT, WHT, WHT, WHT, WHT, WHT }, // 8
			{ WHT, WHT, WHT, WHT, WHT, WHT, WHT, WHT }, // 9
			{ WHT, WHT, WHT, WHT, WHT, WHT, WHT, WHT }, // 10
			{ NON, WHT, WHT, WHT, WHT, WHT, WHT, NON } // 11
	};

	private final byte[][] chickenHead = new byte[][] {
			// 0 1 2 3 4 5 6 7
			{ NON, NON, NON, YEL, YEL, NON, NON, NON }, // 0
			{ NON, NON, NON, YEL, YEL, NON, NON, NON }, // 1
			{ NON, NON, WHT, WHT, WHT, WHT, NON, NON }, // 2
			{ NON, NON, WHT, WHT, WHT, WHT, NON, NON }, // 3
			{ NON, NON, WHT, WHT, WHT, WHT, NON, NON }, // 4
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 5
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 6
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 7
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 8
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 9
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 10
			{ NON, NON, NON, NON, NON, NON, NON, NON } // 11
	};

	private final byte[][] chickenEyes = new byte[][] {
			// 0 1 2 3 4 5 6 7
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 0
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 1
			{ NON, NON, BLK, WHT, WHT, BLK, NON, NON }, // 2
			{ NON, NON, WHT, WHT, WHT, WHT, NON, NON }, // 3
			{ NON, NON, WHT, WHT, WHT, WHT, NON, NON }, // 4
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 5
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 6
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 7
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 8
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 9
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 10
			{ NON, NON, NON, NON, NON, NON, NON, NON } // 11
	};

	private final byte[][] chickenTops = new byte[][] {
			// 0 1 2 3 4 5 6 7
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 0
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 1
			{ NON, NON, WHT, WHT, WHT, WHT, NON, NON }, // 2
			{ NON, NON, WHT, WHT, WHT, WHT, NON, NON }, // 3
			{ NON, NON, WHT, WHT, WHT, WHT, NON, NON }, // 4
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 5
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 6
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 7
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 8
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 9
			{ NON, NON, NON, NON, NON, NON, NON, NON }, // 10
			{ NON, NON, NON, NON, NON, NON, NON, NON } // 11
	};

	private void generateLayer(AbstractBlocks chunk, int x, int y, int z, byte[][] layer) {
		for (int xI = 0; xI < 12; xI++) {
			for (int zI = 0; zI < 8; zI++) {
				switch (layer[xI][zI]) {
				default:
				case NON:
					break;
				case WHT:
//					if (cI % 2 == 0)
					chunk.setBlock(x + xI, y, z + zI, Blocks.WHITE_WOOL);
//					else
//						chunk.setBlock(x + xI, y, z + zI, Material.WHITE_WOOL, DyeColor.LIGHT_GRAY);
					break;
				case YEL:
					chunk.setBlock(x + xI, y, z + zI, Blocks.YELLOW_WOOL);
					break;
				case RED:
					chunk.setBlock(x + xI, y, z + zI, Blocks.RED_WOOL);
					break;
				case BLK:
					chunk.setBlock(x + xI, y, z + zI, Blocks.BLACK_WOOL);
					break;
				}
			}
		}
	}

	public void generateChicken(AbstractBlocks chunk, int x, int y, int z) {
		generateLayer(chunk, x, y, z, chickenFeet);
		generateLayer(chunk, x, y + 1, z, chickenLegs);
		generateLayer(chunk, x, y + 2, z, chickenLegs);
		generateLayer(chunk, x, y + 3, z, chickenLegs);
		generateLayer(chunk, x, y + 4, z, chickenLegs);
		generateLayer(chunk, x, y + 5, z, chickenUnder);
		generateLayer(chunk, x, y + 6, z, chickenUnder);
		generateLayer(chunk, x, y + 7, z, chickenBody);
		generateLayer(chunk, x, y + 8, z, chickenBody);
		generateLayer(chunk, x, y + 9, z, chickenMouth);
		generateLayer(chunk, x, y + 10, z, chickenMouth);
		generateLayer(chunk, x, y + 11, z, chickenHead);
		generateLayer(chunk, x, y + 12, z, chickenHead);
		generateLayer(chunk, x, y + 13, z, chickenEyes);
		generateLayer(chunk, x, y + 14, z, chickenTops);
	}

	public void generateBones(VocationCityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, AbstractCachedYs blockYs,
			Odds odds) {
		int y = odds.calcRandomRange(10, blockYs.getMinHeight() - 20);
		generateBones(generator, lot, chunk, y, odds);
	}

	private void generateBones(VocationCityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, int y, Odds odds) {
		int x = 7;
		int z = 15;
		generateBones(generator, lot, chunk, x, y, z, odds, false);
	}

	public void generateBones(VocationCityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, int x, int y, int z,
			Odds odds, boolean smaller) {
//		generator.reportLocation("Fossil", chunk);
//		Material matBlock = Material.QUARTZ_BLOCK;
		Block matBlock = Blocks.BONE_BLOCK;
		Block matStair = Blocks.QUARTZ_STAIRS;

		// figure the starting location within the chunk
//		chunk.setBlocks(0, 16, 255, 0, 16, Material.GLASS);

		// what bits does it have?
		boolean gotTorso = odds.flipCoin(); // arms just below the head
		boolean gotHind = odds.flipCoin(); // legs on either end
		if (gotTorso && gotHind) // a centaur, pretty rare beast?
			gotTorso = odds.playOdds(Odds.oddsPrettyUnlikely);

		// figure out the legs
		int backLegHeight = odds.calcRandomRange(smaller ? 2 : 3, smaller ? 3 : 5);
		int backLegWidth = odds.calcRandomRange(1, 3);
		int frontLegHeight = Math.min(odds.calcRandomRange(2, smaller ? 3 : 5), backLegHeight);
		int frontLegWidth = odds.calcRandomRange(1, 3);
		int armLength = odds.calcRandomRange(2, smaller ? 3 : 4);
		int armWidth = odds.calcRandomRange(2, 3);

		// calculate the lengths of the sections
		int spineLength = 0;
		if (gotTorso)
			spineLength = odds.calcRandomRange(armLength + 1, armLength + 3);
		int hindLength = 0;
		if (gotHind)
			hindLength = odds.calcRandomRange(smaller ? 2 : 3, smaller ? 4 : 6);

		// up on the back legs?
		boolean isHindUpright = gotHind && odds.playOdds(Odds.oddsSomewhatLikely);

		// figure out the tail bit
		int tailLength = 0;
		if (!smaller && (gotHind || odds.playOdds(Odds.oddsExtremelyUnlikely)))
			tailLength = odds.calcRandomRange(0, 3);

		// start at the back
		int sectionZ = z;
		int sectionY = y;

		// tail?
		if (tailLength > 0) {
			sectionZ = sectionZ - tailLength;
			int tailZ = sectionZ + 1;
			int tailY = sectionY + backLegHeight;
			for (int zO = 0; zO < tailLength; zO++) {
				if (tailY > y && odds.flipCoin()) {
					chunk.setBlock(x, tailY, tailZ + zO, matStair, Direction.NORTH);
					tailY--;
				}
				chunk.setBlock(x, tailY, tailZ + zO, matBlock);
			}
		}

		// back legs
		sectionY = sectionY + backLegHeight;
		generateLimbs(chunk, odds, x, sectionY, sectionZ, backLegWidth, backLegHeight, matBlock, matStair);

		// hind section
		if (gotHind) {
			sectionZ = sectionZ - hindLength;
			int hindZ = sectionZ;
			int hindY = y + Math.max(backLegHeight, frontLegHeight);
			if (isHindUpright)
				hindY = hindY + hindLength;
			sectionY = hindY;

			// now the front legs
			generateLimbs(chunk, odds, x, hindY, hindZ, frontLegWidth, frontLegHeight, matBlock, matStair);

			// now for the spine and ribs
			for (int zO = 0; zO <= hindLength; zO++) {
				chunk.setBlock(x, hindY, hindZ + zO, matBlock);

				// ribs
				if (zO > 0 && zO % 2 == 0 && zO + 1 < hindLength && odds.playOdds(Odds.oddsPrettyLikely)) {
					chunk.setBlock(x - 1, hindY, hindZ + zO, matStair, Direction.EAST);
					chunk.setBlock(x + 1, hindY, hindZ + zO, matStair, Direction.WEST);

					chunk.setBlock(x - 1, hindY - 1, hindZ + zO, matBlock);
					chunk.setBlock(x + 1, hindY - 1, hindZ + zO, matBlock);

					chunk.setBlock(x - 1, hindY - 2, hindZ + zO, matStair, Direction.EAST, Half.TOP);
					chunk.setBlock(x + 1, hindY - 2, hindZ + zO, matStair, Direction.WEST, Half.TOP);
				}

				// move down
				if (isHindUpright) {
					chunk.setBlock(x, hindY + 1, hindZ + zO, matStair, Direction.NORTH);
					chunk.setBlock(x, hindY - 1, hindZ + zO, matStair, Direction.SOUTH, Half.TOP);
					hindY--;
				}
			}
		}

		// torso section
		if (gotTorso) {

			// spine
			boolean showRibs = armWidth > 2 && odds.flipCoin();
			for (int yO = 0; yO < spineLength; yO++) {
				chunk.setBlock(x, sectionY + yO, sectionZ, matBlock);
				if (yO > 0 && yO < spineLength - 1 && showRibs) {
					chunk.setBlock(x - 1, sectionY + yO, sectionZ, matStair, Direction.EAST);
					chunk.setBlock(x + 1, sectionY + yO, sectionZ, matStair, Direction.WEST);
				}
			}

			// arms
			sectionY += spineLength;
			generateLimbs(chunk, odds, x, sectionY, sectionZ, armWidth, armLength, matBlock, matStair);
		}

		// add the head
		if (odds.playOdds(Odds.oddsTremendouslyLikely)) {

			// long neck
			boolean tiltedNeck = (gotHind && !gotTorso && isHindUpright && odds.playOdds(Odds.oddsSomewhatUnlikely))
					|| (gotHind && !gotTorso && odds.playOdds(Odds.oddsVeryLikely))
					|| (gotTorso && odds.playOdds(Odds.oddsExtremelyUnlikely));

			// so do it
			int neckLength = odds.calcRandomRange(1, smaller ? 2 : 3);
			if (tiltedNeck) {
				for (int yO = 0; yO < neckLength; yO++) {

					chunk.setBlock(x, sectionY, sectionZ, matBlock);
					chunk.setBlock(x, sectionY + 1, sectionZ, matStair, Direction.NORTH);
					chunk.setBlock(x, sectionY, sectionZ - 1, matStair, Direction.SOUTH, Half.TOP);

					sectionY++;
					sectionZ--;
				}
			} else {
				if (!smaller && odds.playOdds(Odds.oddsPrettyUnlikely))
					neckLength = odds.calcRandomRange(3, 6);
				chunk.setBlocks(x, sectionY, sectionY + neckLength, sectionZ, matBlock);
				sectionY += neckLength;
			}

			// now the head itself
			generateHead(chunk, odds, x, sectionY, sectionZ, matBlock, matStair);
		}
	}

	private void generateLimbs(SupportBlocks chunk, Odds odds, int x, int y, int z, int xO, int yO, Block matBlock,
			Block matStair) {
		if (odds.flipCoin()) { // rounded tops
			chunk.setBlocks(x - xO, x, y, z, z + 1, matBlock);
			chunk.setBlocks(x + 1, x + xO + 1, y, z, z + 1, matBlock);
		} else {
			chunk.setBlocks(x - xO + 1, x, y, z, z + 1, matBlock);
			chunk.setBlocks(x + 1, x + xO, y, z, z + 1, matBlock);

			chunk.setBlock(x - xO, y, z, matStair, Direction.EAST);
			chunk.setBlock(x + xO, y, z, matStair, Direction.WEST);
		}

		// vertical bits
		chunk.setBlocks(x - xO, x - xO + 1, y - yO + 1, y, z, z + 1, matBlock);
		chunk.setBlocks(x + xO, x + xO + 1, y - yO + 1, y, z, z + 1, matBlock);

		// wrist/heels
		boolean forceToes = false;
		if (odds.flipCoin()) {
			chunk.setBlock(x - xO, y - yO, z, matBlock);
			chunk.setBlock(x + xO, y - yO, z, matBlock);
		} else {
			forceToes = true;
			chunk.setBlock(x - xO, y - yO, z, matStair, Direction.NORTH, Half.TOP);
			chunk.setBlock(x + xO, y - yO, z, matStair, Direction.NORTH, Half.TOP);
		}

		// fingers/toes
		if (forceToes || odds.flipCoin()) {
			chunk.setBlock(x - xO, y - yO, z - 1, matStair, Direction.SOUTH);
			chunk.setBlock(x + xO, y - yO, z - 1, matStair, Direction.SOUTH);
		}
	}

	private void generateHead(SupportBlocks chunk, Odds odds, int x, int y, int z, Block matBlock,
			Block matStair) {
		int headHeight = odds.calcRandomRange(2, 3);
		int y1 = y;
		int y2 = y + 1;
		int y3 = y + headHeight - 1;
		boolean tallHead = y2 != y3;
		boolean needEyes = odds.playOdds(Odds.oddsExceedinglyLikely);
		boolean cyclopsEye = tallHead && odds.playOdds(Odds.oddsLikely);

		// whole head
		chunk.setBlocks(x - 1, x + 2, y, y + headHeight, z, z + 1, matBlock);

		// edit the top bit
		if (odds.flipCoin()) { // rounded tops
			chunk.setBlock(x - 1, y3, z, matStair, Direction.EAST);
			chunk.setBlock(x + 1, y3, z, matStair, Direction.WEST);
//		} else if (odds.flipCoin()) { // hatish top
//			chunk.setBlock(x - 1, y3, z, matStair, BlockFace.EASTFLIP);
//			chunk.setBlock(x + 1, y3, z, matStair, BlockFace.WESTFLIP);
		} else if (!cyclopsEye && needEyes && odds.playOdds(Odds.oddsLikely)) {
			chunk.setBlock(x - 1, y3, z, matStair, Direction.SOUTH, Half.TOP);
			chunk.setBlock(x + 1, y3, z, matStair, Direction.SOUTH, Half.TOP);
			needEyes = false;
		}

		// edit the bottom bit
		if (odds.flipCoin()) { // rounded bottoms
			chunk.setBlock(x - 1, y1, z, matStair, Direction.EAST, Half.TOP);
			chunk.setBlock(x + 1, y1, z, matStair, Direction.WEST, Half.TOP);
//		} else if (odds.flipCoin()) { // second neck
//			chunk.setBlock(x - 1, y1, z, matStair, BlockFace.EAST);
//			chunk.setBlock(x + 1, y1, z, matStair, BlockFace.WEST);
		}

		// got to eat
		if (odds.playOdds(Odds.oddsPrettyLikely)) // mouth
			chunk.setBlock(x, y1, z, matStair, Direction.SOUTH);
		else if (!cyclopsEye && odds.flipCoin()) { // snout/beak
			chunk.setBlock(x, y2, z - 1, matStair, Direction.SOUTH);
			chunk.setBlock(x, y1, z - 1, matStair, Direction.SOUTH, Half.TOP);
		}

		// finalize the eye bits
		if (cyclopsEye && tallHead) {
			chunk.setBlock(x, y3, z, matStair, Direction.SOUTH, Half.TOP);
			chunk.setBlock(x, y2, z, matStair, Direction.SOUTH);
			needEyes = false;
		} else if (needEyes) {
			if (tallHead) {
				chunk.setBlock(x - 1, y2, z, matStair, Direction.SOUTH, Half.TOP);
				chunk.setBlock(x + 1, y2, z, matStair, Direction.SOUTH, Half.TOP);
			} else {
				chunk.setBlock(x - 1, y2, z, matStair, Direction.SOUTH);
				chunk.setBlock(x + 1, y2, z, matStair, Direction.SOUTH);
			}
			needEyes = false;
		}
	}

}
