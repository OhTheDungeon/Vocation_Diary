package cn.shadow.vacation_diary.dimension.support;

import java.util.Stack;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public final class RememberedBlocks {

	private final SupportBlocks blocks;
	private final Stack<rememberedBlock> originals;

	public RememberedBlocks(SupportBlocks chunk) {
		blocks = chunk;
		originals = new Stack<>();
	}

	private static class rememberedBlock {
		private final Block origMaterial;
		private final int origX;
		private final int origY;
		private final int origZ;

		rememberedBlock(BaseBlock block, int x, int y, int z) {
			origMaterial = block.getType();
			origX = x;
			origY = y;
			origZ = z;
		}
	}

	public void setBlocks(int x1, int x2, int y, int z1, int z2, Block material) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				BaseBlock block = blocks.getActualBlock(x, y, z);
				originals.push(new rememberedBlock(block, x, y, z));
				block.setType(material, true);
			}
		}
	}

	public void clearBlocks(int x1, int x2, int y, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				BaseBlock block = blocks.getActualBlock(x, y, z);
				originals.push(new rememberedBlock(block, x, y, z));

				// now clear it
				if (!block.isEmpty()) {
					block.setType(Blocks.AIR, true);
				}
			}
		}
	}

	public void clearBlocks(int x1, int x2, int y1, int y2, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					BaseBlock block = blocks.getActualBlock(x, y, z);
					originals.push(new rememberedBlock(block, x, y, z));

					// now clear it
					if (!block.isEmpty()) {
						block.setType(Blocks.AIR, true);
					}
				}
			}
		}
	}

}
