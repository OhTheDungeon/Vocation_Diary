package cn.shadow.vacation_diary.dimension.support;

import java.util.Map;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;

public final class InitialBlocks extends AbstractBlocks {
	public final IChunk chunk;

	public InitialBlocks(VocationCityWorldGenerator aGenerator, IChunk chunk, int sectionX, int sectionZ) {
		super(aGenerator);

		this.sectionX = sectionX;
		this.sectionZ = sectionZ;
		this.chunk = chunk;
	}

//	public short getBlockType(int x, int y, int z) {
//		return chunkData.
//		return getBlock(x, y, z);
//	}

	public boolean isType(int x, int y, int z, Block material) {
		return chunk.getBlockState(new BlockPos(x, y , z)).getBlock().equals(material);
	}

	public boolean isType(int x, int y, int z, Block... materials) {
		Block block = chunk.getBlockState(new BlockPos(x, y , z)).getBlock();
		for (Block material : materials)
			if (block.equals(material))
				return true;
		return false;
	}

	@Override
	public boolean isEmpty(int x, int y, int z) {
		return chunk.getBlockState(new BlockPos(x, y , z)).getBlock().equals(Blocks.AIR);
	}

	@Override
	public void setAtmosphereBlock(int x, int y, int z, Block material) {
		BlockPos pos = new BlockPos(x, y, z);
		chunk.setBlockState(pos, material.getDefaultState(), false);
		BlockState blockState = null;

		// West
		if (x > 0) {
			BlockPos sub_pos = new BlockPos(x - 1, y, z);
			blockState = chunk.getBlockState(sub_pos);
			if(blockState.has(SixWayBlock.EAST)) {
				blockState = blockState.with(SixWayBlock.EAST, Boolean.valueOf(false));
				chunk.setBlockState(sub_pos, blockState, false);
			}
		}
		// East
		if (x < 15) {
			BlockPos sub_pos = new BlockPos(x + 1, y, z);
			blockState = chunk.getBlockState(sub_pos);
			if(blockState.has(SixWayBlock.WEST)) {
				blockState = blockState.with(SixWayBlock.WEST, Boolean.valueOf(false));
				chunk.setBlockState(sub_pos, blockState, false);
			}
		}
		// North
		if (z > 0) {
			BlockPos sub_pos = new BlockPos(x, y, z - 1);
			blockState = chunk.getBlockState(sub_pos);
			if(blockState.has(SixWayBlock.SOUTH)) {
				blockState = blockState.with(SixWayBlock.SOUTH, Boolean.valueOf(false));
				chunk.setBlockState(sub_pos, blockState, false);
			}
		}
		// South
		if (z < 15) {
			BlockPos sub_pos = new BlockPos(x, y, z + 1);
			blockState = chunk.getBlockState(sub_pos);
			if(blockState.has(SixWayBlock.NORTH)) {
				blockState = blockState.with(SixWayBlock.NORTH, Boolean.valueOf(false));
				chunk.setBlockState(sub_pos, blockState, false);
			}
		}
	}

	public Block getBlock(int x, int y, int z) {
		return chunk.getBlockState(new BlockPos(x, y, z)).getBlock();
	}

	@Override
	public void setBlock(int x, int y, int z, Block material) {
		chunk.setBlockState(new BlockPos(x, y, z), material.getDefaultState(), false);
	}
	
	@Override
	public final void setBlock(int x, int y, int z, Block material, SlabType type) {
		BlockState blockState = material.getDefaultState();
		if(blockState.has(BlockStateProperties.SLAB_TYPE)) {
			blockState = blockState.with(BlockStateProperties.SLAB_TYPE, type);
		}
		chunk.setBlockState(new BlockPos(x, y, z), blockState, false);
	}
	
	@Override
	public final void setBlock(int x, int y, int z, Block material, Direction facing) {
		BlockState blockState = material.getDefaultState();
		
		if(blockState.has(BlockStateProperties.FACING)) {
			blockState = blockState.with(BlockStateProperties.FACING, facing);
		} else if(blockState.has(HorizontalBlock.HORIZONTAL_FACING)) {
			blockState = blockState.with(HorizontalBlock.HORIZONTAL_FACING, facing);
		} else {
			BooleanProperty property = SixWayBlock.FACING_TO_PROPERTY_MAP.get(facing);
			if(blockState.has(property)) {
				blockState = blockState.with(property, Boolean.valueOf(true));
			} else if(blockState.has(BlockStateProperties.ROTATION_0_15)) {
				switch (facing) {
				case NORTH:
				case SOUTH:
					blockState = blockState.with(BlockStateProperties.ROTATION_0_15, Integer.valueOf(0));
					break;
				default:
					blockState = blockState.with(BlockStateProperties.ROTATION_0_15, Integer.valueOf(4));
				}
			}
		}
		chunk.setBlockState(new BlockPos(x, y, z), blockState, false);
	}

	@Override
	public void setBlock(int x, int y, int z, Block material, Direction... facing) {
		BlockState blockState = material.getDefaultState();
		Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP;
		for(Direction face : facing) {
			BooleanProperty property = FACING_TO_PROPERTY_MAP.get(face);
			if(blockState.has(property)) {
				blockState = blockState.with(property, Boolean.valueOf(true));
			}
		}
		chunk.setBlockState(new BlockPos(x, y, z), blockState, false);
	}

	@Override
	public void setBlockIfEmpty(int x, int y, int z, Block material) {
		if (isEmpty(x, y, z) && !isEmpty(x, y - 1, z))
			chunk.setBlockState(new BlockPos(x, y, z), material.getDefaultState(), false);
	}

	@Override
	public void clearBlock(int x, int y, int z) {
		chunk.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), false);
	}

	// ================ Walls
	@Override
	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Block material) {
		setBlocks(x1, x2, y1, y2, z1, z1 + 1, material);
		setBlocks(x1, x2, y1, y2, z2 - 1, z2, material);
		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material);
		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material);
	}

//	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, MaterialData material) {
//		setBlocks(x1, x2, y1, y2, z1, z1 + 1, material);
//		setBlocks(x1, x2, y1, y2, z2 - 1, z2, material);
//		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material);
//		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material);
//	}
//	
//	@Override
//	public void setBlock(int x, int y, int z, MaterialData material) {
//		chunkData.setBlock(x, y, z, material);
//	}
//	
//	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, MaterialData material) {
//		chunkData.setRegion(x1, y1, z1, x2, y2, z2, material);
//	}
//	
//	@Override
//	public void setBlockTypeAndColor(int x, int y, int z, Block material, DyeColor color) {
//		BlackMagic.setBlockTypeAndColor(chunkData, x, y, z, material, color);
//	}
//	
//	@Override
//	public final void setStair(int x, int y, int z, Block material, BadMagic.Stair direction) {
//		BlackMagic.setBlockTypeAndData(chunkData, x, y, z, material, direction.getData());
//	}

	// ================ Layers
	@Override
	public int setLayer(int blocky, Block material) {
		setBlocks(0, width, blocky, blocky + 1, 0, width, material);
		return blocky + 1;
	}

	@Override
	public int setLayer(int blocky, int height, Block material) {
		setBlocks(0, width, blocky, blocky + height, 0, width, material);
		return blocky + height;
	}

	@Override
	public int setLayer(int blocky, int height, int inset, Block material) {
		setBlocks(inset, width - inset, blocky, blocky + height, inset, width - inset, material);
		return blocky + height;
	}

	@Override
	public void setDoor(int x, int y, int z, Block material, BlockFace facing) {
		clearBlock(x, y, z);
		clearBlock(x, y + 1, z);

		BlockState dataBottom = material.getDefaultState();
		BlockState dataTop = material.getDefaultState();

		facing = fixFacing(facing);
		facing = facing.getOppositeFace();
		
		if(dataBottom.has(HorizontalBlock.HORIZONTAL_FACING)) {
			dataBottom = dataBottom.with(HorizontalBlock.HORIZONTAL_FACING, facing.toDirection());
		}
		if(dataTop.has(HorizontalBlock.HORIZONTAL_FACING)) {
			dataTop = dataTop.with(HorizontalBlock.HORIZONTAL_FACING, facing.toDirection());
		}
		if(dataBottom.has(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
			dataBottom = dataBottom.with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER);
		}
		if(dataTop.has(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
			dataTop = dataTop.with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER);
		}
		
		chunk.setBlockState(new BlockPos(x, y, z), dataBottom, false);
		chunk.setBlockState(new BlockPos(x, y + 1, z), dataTop, false);
	}

	@Override
	public void setBlockState(int x, int y, int z, BlockState blockState) {
		chunk.setBlockState(new BlockPos(x, y, z), blockState, false);
	}
}
