package cn.shadow.vacation_diary.dimension.support;

import java.util.Map;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.structure.context.DataContext;
import cn.shadow.vacation_diary.dimension.structure.provider.LootProvider;
import cn.shadow.vacation_diary.dimension.structure.provider.LootProvider.LootLocation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ChestType;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.SlabType;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;

public abstract class SupportBlocks extends AbstractBlocks {

	private boolean doPhysics;
	private boolean doClearData;

	SupportBlocks(VocationCityWorldGenerator generator) {
		super(generator);

		doPhysics = false;
		doClearData = false;
	}

	public abstract BaseBlock getActualBlock(int x, int y, int z);

	public final boolean getDoPhysics() {
		return doPhysics;
	}

	public boolean setDoPhysics(boolean dophysics) {
		boolean was = doPhysics;
		doPhysics = dophysics;
		return was;
	}

	public final boolean getDoClearData() {
		return doClearData;
	}

	public final void setDoClearData(boolean docleardata) {
		doClearData = docleardata;
	}
	
	@Override
	public void setBlockState(int x, int y, int z, BlockState blockState) {
		BaseBlock block = getActualBlock(x, y, z);
		block.setBlockState(blockState, getDoPhysics(x, z));
	}

	@Override
	public final void setBlockIfEmpty(int x, int y, int z, Block material) {
		BaseBlock block = getActualBlock(x, y, z);
		if (isEmpty(block) && !isEmpty(x, y - 1, z))
			setActualBlock(block, material, getDoPhysics(x, z));
	}

	private boolean getDoPhysics(int x, int z) {
		boolean thisDoPhysics = doPhysics;
		if (thisDoPhysics)
			thisDoPhysics = !onEdgeXZ(x, z);
		return thisDoPhysics;
	}

	private void setActualBlock(BaseBlock block, Block material, boolean thisDoPhysics) {
		block.setType(material, thisDoPhysics);
	}

	@Override
	public void setBlock(int x, int y, int z, Block material) {
		setActualBlock(getActualBlock(x, y, z), material, getDoPhysics(x, z));
	}

	private boolean isType(BaseBlock block, Block... types) {
		Block type = block.getType();
		for (Block test : types)
			if (type == test)
				return true;
		return false;
	}

	public final boolean isType(int x, int y, int z, Block type) {
		return getActualBlock(x, y, z).getType() == type;
	}

	public final boolean isOfTypes(int x, int y, int z, Block... types) {
		return isType(getActualBlock(x, y, z), types);
	}

	public final void setBlockIfNot(int x, int y, int z, Block... types) {
		if (!isOfTypes(x, y, z, types))
			setBlock(x, y, z, types[0]);
	}

	private boolean isEmpty(BaseBlock block) {
		if (onEdgeXZ(block.getX(), block.getZ()))
			return block.getType() == Blocks.AIR;
		else
			return block.isEmpty();
	}

	//NOTE when used as the default world generator (via bukkit.yml), testing via .isEmpty() on or near the edge (0, 1, 14, 15) causes exceptions
	@Override
	public final boolean isEmpty(int x, int y, int z) {
		if (onEdgeXZ(x, z))
			return isType(x, y, z, Blocks.AIR);
		else
			return getActualBlock(x, y, z).isEmpty();
	}

	public abstract boolean isSurroundedByEmpty(int x, int y, int z);

	public final boolean isWater(int x, int y, int z) {
		return isOfTypes(x, y, z, Blocks.WATER);
	}

	public abstract boolean isByWater(int x, int y, int z);

	public final BlockPos getBlockLocation(int x, int y, int z) {
		return getActualBlock(x, y, z).getLocation();
	}

	@Override
	public final void setAtmosphereBlock(int x, int y, int z, Block material) {
		setBlock(x, y, z, material);
		BlockState blockState;
		// West
		if (x > 0) {
			blockState = getActualBlock(x - 1, y, z).getBlockState();
			if(blockState.has(SixWayBlock.EAST)) {
				blockState = blockState.with(SixWayBlock.EAST, Boolean.valueOf(false));
				getActualBlock(x - 1, y, z).setBlockState(blockState, false);
			}
		}
		// East
		if (x < 15) {
			blockState = getActualBlock(x + 1, y, z).getBlockState();
			if(blockState.has(SixWayBlock.WEST)) {
				blockState = blockState.with(SixWayBlock.WEST, Boolean.valueOf(false));
				getActualBlock(x + 1, y, z).setBlockState(blockState, false);
			}
		}
		// North
		if (z > 0) {
			blockState = getActualBlock(x, y, z - 1).getBlockState();
			if(blockState.has(SixWayBlock.SOUTH)) {
				blockState = blockState.with(SixWayBlock.SOUTH, Boolean.valueOf(false));
				getActualBlock(x, y, z - 1).setBlockState(blockState, false);
			}
		}

		// South
		if (z < 15) {
			blockState = getActualBlock(x, y, z + 1).getBlockState();
			if(blockState.has(SixWayBlock.SOUTH)) {
				blockState = blockState.with(SixWayBlock.SOUTH, Boolean.valueOf(false));
				getActualBlock(x, y, z + 1).setBlockState(blockState, false);
			}
		}
	}

	@Override
	public final void clearBlock(int x, int y, int z) {
		getActualBlock(x, y, z).setType(Blocks.AIR, getDoPhysics(x, z));
	}

	@Override
	public final void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Block material) {
		if(material.getDefaultState().has(SixWayBlock.NORTH)) {
			setBlocks(x1 + 1, x2 - 1, y1, y2, z1, z1 + 1, material, Direction.EAST, Direction.WEST); // N
			setBlocks(x1 + 1, x2 - 1, y1, y2, z2 - 1, z2, material, Direction.EAST, Direction.WEST); // S
			setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material, Direction.SOUTH, Direction.NORTH); // W
			setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material, Direction.SOUTH, Direction.NORTH); // E
			setBlocks(x1, y1, y2, z1, material, Direction.SOUTH, Direction.EAST); // NW
			setBlocks(x1, y1, y2, z2 - 1, material, Direction.NORTH, Direction.EAST); // SW
			setBlocks(x2 - 1, y1, y2, z1, material, Direction.SOUTH, Direction.WEST); // NE
			setBlocks(x2 - 1, y1, y2, z2 - 1, material, Direction.NORTH, Direction.WEST); // SE
		} else {
			setBlocks(x1, x2, y1, y2, z1, z1 + 1, material); // N
			setBlocks(x1, x2, y1, y2, z2 - 1, z2, material); // S
			setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material); // W
			setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material); // E
		}
	}

	public final void fillBlocks(int x1, int x2, int y, int z1, int z2, Block material) {
		fillBlocks(x1, x2, y, y + 1, z1, z2, material);
	}

	private void fillBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Block material) {
		if(!(material.getDefaultState().has(SixWayBlock.NORTH))) {
			setBlocks(x1, x2, y1, y2, z1, z2, material);
		}
		setBlocks(x1, x2, y1, y2, z1, z2, material, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
		setBlocks(x1 + 1, x2 - 1, y1, y2, z1, z1 + 1, material, Direction.EAST, Direction.WEST, Direction.SOUTH); // N
		setBlocks(x1 + 1, x2 - 1, y1, y2, z2 - 1, z2, material, Direction.EAST, Direction.WEST, Direction.NORTH); // S
		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material, Direction.SOUTH, Direction.NORTH, Direction.EAST); // W
		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material, Direction.SOUTH, Direction.NORTH, Direction.WEST); // E
		setBlocks(x1, y1, y2, z1, material, Direction.SOUTH, Direction.EAST); // NW
		setBlocks(x1, y1, y2, z2 - 1, material, Direction.NORTH, Direction.EAST); // SW
		setBlocks(x2 - 1, y1, y2, z1, material, Direction.SOUTH, Direction.WEST); // NE
		setBlocks(x2 - 1, y1, y2, z2 - 1, material, Direction.NORTH, Direction.WEST); // SE
	}

	@Override
	public final int setLayer(int blocky, Block material) {
		setBlocks(0, width, blocky, blocky + 1, 0, width, material);
		return blocky + 1;
	}

	@Override
	public final int setLayer(int blocky, int height, Block material) {
		setBlocks(0, width, blocky, blocky + height, 0, width, material);
		return blocky + height;
	}

	@Override
	public final int setLayer(int blocky, int height, int inset, Block material) {
		setBlocks(inset, width - inset, blocky, blocky + height, inset, width - inset, material);
		return blocky + height;
	}

	// @@ I REALLY NEED TO FIGURE A DIFFERENT WAY TO DO THIS
	final boolean isNonstackableBlock(BaseBlock block) { // either because it really isn't or it just doesn't look
		// good
		return !block.isSolid();
	}

	public final void setBlock(int x, int y, int z, Block material, boolean light) {
		BaseBlock block = getActualBlock(x, y, z);
		BlockState blockState = material.getDefaultState();
		
		if(blockState.has(BlockStateProperties.LIT)) {
			blockState = blockState.with(BlockStateProperties.LIT, Boolean.valueOf(light));
		}
		block.setBlockState(blockState, getDoPhysics(x, z));
	}
	
	public final void setCauldron(int x, int y, int z, Odds odds) {
		int level = (int) (odds.getRandomDouble() * 3);
		BaseBlock block = getActualBlock(x, y, z);
		BlockState blockState = Blocks.CAULDRON.getDefaultState().with(
				BlockStateProperties.LEVEL_0_3, Integer.valueOf(MathHelper.clamp(level, 0, 3)));
		block.setBlockState(blockState, false);
	}

	public final void colorizeBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Block find, Colors colors) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					if (isType(x, y, z, find))
						setBlock(x, y, z, colors.getTerracotta());
				}
			}
		}
	}

	public final void setBlockRandomly(int x, int y, int z, Odds odds, Block... materials) {
		setBlock(x, y, z, odds.getRandomMaterial(materials));
	}

	public final void setVine(int x, int y, int z, Direction... faces) {
		BaseBlock block = getActualBlock(x, y, z);
		
		BlockState blockState = Blocks.VINE.getDefaultState();
		Map<Direction, BooleanProperty> map = VineBlock.FACING_TO_PROPERTY_MAP;
		for(Direction face : faces) {
			if(map.containsKey(face)) {
				BooleanProperty property = map.get(face);
				blockState = blockState.with(property, Boolean.valueOf(true));
			}
		}
		block.setBlockState(blockState, getDoPhysics(x, z));
	}

	public final void setVines(int x, int y1, int y2, int z, Direction... faces) {
		for (int y = y1; y < y2; y++)
			setVine(x, y, z, faces);
	}

	@Override
	public final void setBlock(int x, int y, int z, Block material, SlabType type) {
		BaseBlock block = getActualBlock(x, y, z);
		BlockState blockState = material.getDefaultState();
		if(blockState.has(BlockStateProperties.SLAB_TYPE)) {
			blockState = blockState.with(BlockStateProperties.SLAB_TYPE, type);
		}
		block.setBlockState(blockState, getDoPhysics(x, z));
	}

	@Override
	public final void setBlock(int x, int y, int z, Block material, Direction facing) {
		BaseBlock block = getActualBlock(x, y, z);
		BlockState blockState = material.getDefaultState();
		
		if(blockState.has(BlockStateProperties.AXIS)) {
			blockState = blockState.with(BlockStateProperties.AXIS, facing.getAxis());
		} else if(blockState.has(BlockStateProperties.FACING)) {
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
		block.setBlockState(blockState, getDoPhysics(x, z));
	}

	@Override
	public final void setBlock(int x, int y, int z, Block material, Direction... facing) {
		BaseBlock block = getActualBlock(x, y, z);
		BlockState blockState = material.getDefaultState();
		
		Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP;
		for(Direction face : facing) {
			BooleanProperty property = FACING_TO_PROPERTY_MAP.get(face);
			if(blockState.has(property)) {
				blockState = blockState.with(property, Boolean.valueOf(true));
			}
		}
		block.setBlockState(blockState, getDoPhysics(x, z));
	}

	public final void setStair(int x, int y, int z, Block material, BlockFace facing) {
		setStair(x, y, z, material, facing, StairsShape.STRAIGHT);
	}
	
	public final void setStair(int x, int y, int z, Block material, Direction facing, StairsShape shape) {
		BaseBlock block = getActualBlock(x, y, z);
		BlockState blockState = material.getDefaultState();
		
		if(material instanceof StairsBlock) {
			blockState = blockState.with(HorizontalBlock.HORIZONTAL_FACING, facing);
			blockState = blockState.with(BlockStateProperties.STAIRS_SHAPE, shape);
		}
		
		block.setBlockState(blockState, getDoPhysics(x, z));
	}

	public final void setStair(int x, int y, int z, Block material, BlockFace facing, StairsShape shape) {
		setStair(x, y, z, material, facing.toDirection(), shape);
	}

	public final void drawCrane(DataContext context, Odds odds, int x, int y, int z) {
		Colors colors = new Colors(odds);

		// vertical bit
		setBlocks(x, y, y + 8, z, Blocks.IRON_BARS, Direction.WEST);
		setBlocks(x - 1, y, y + 8, z, Blocks.IRON_BARS, Direction.EAST); // 1.9 shows iron fences very thin now
		setBlocks(x, y + 8, y + 10, z, Blocks.STONE);
		setBlocks(x - 1, y + 8, y + 10, z, Blocks.STONE_SLAB);
		setBlock(x, y + 10, z, context.torchMat, Direction.UP);

		// horizontal bit
		setBlock(x + 1, y + 8, z, Blocks.GLASS);
		setBlocks(x + 2, x + 10, y + 8, y + 9, z, z + 1, Blocks.IRON_BARS, Direction.EAST, Direction.WEST);
		setBlock(x + 10, y + 8, z, Blocks.IRON_BARS, Direction.WEST);
		setBlocks(x + 1, x + 10, y + 9, y + 10, z, z + 1, Blocks.STONE_SLAB);
		setBlock(x + 10, y + 9, z, Blocks.STONE_BRICK_STAIRS, Direction.WEST);

		// counter weight
		setBlock(x - 2, y + 9, z, Blocks.STONE_SLAB);
		setBlock(x - 3, y + 9, z, Blocks.STONE_BRICK_STAIRS, Direction.EAST);
		setBlocks(x - 3, x - 1, y + 7, y + 9, z, z + 1, colors.getConcrete());
	}

	public final void setTable(int x1, int x2, int y, int z1, int z2, Block tableLeg, Block tableTop) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setTable(x, y, z, tableLeg, tableTop);
			}
		}
	}

	public final void setTable(int x, int y, int z, Block tableLeg, Block tableTop) {
		setBlock(x, y, z, tableLeg);
		setBlock(x, y + 1, z, tableTop);
	}

	private void setDoorBlock(int x, int y, int z, Block material, BlockFace facing, DoubleBlockHalf half, DoorHingeSide hinge,
			boolean doPhysics) {
		BaseBlock block = getActualBlock(x, y, z);
		BlockState blockState = material.getDefaultState();
		
		if(material instanceof DoorBlock) {
			blockState = blockState.with(BlockStateProperties.DOUBLE_BLOCK_HALF, half);
			blockState = blockState.with(HorizontalBlock.HORIZONTAL_FACING, facing.toDirection());
			blockState = blockState.with(BlockStateProperties.DOOR_HINGE, hinge);
		}
		
		block.setBlockState(blockState, getDoPhysics(x, z));
	}

	//@@	public void setDoor(int x, int y, int z, Material material, BadMagic.Door direction) {
	public void setDoor(int x, int y, int z, Block material, BlockFace facing) {
		clearBlock(x, y, z);
		clearBlock(x, y + 1, z);

		DoorHingeSide hinge = DoorHingeSide.LEFT;

		facing = fixFacing(facing);
		facing = facing.getOppositeFace();

		setDoorBlock(x, y, z, material, facing, DoubleBlockHalf.LOWER, hinge, false);
		setDoorBlock(x, y + 1, z, material, facing, DoubleBlockHalf.UPPER, hinge, true);
	}

	public void setFenceDoor(int x, int y1, int y2, int z, Block material, BlockFace facing) {

		facing = fixFacing(facing);

		if (facing == BlockFace.NORTH || facing == BlockFace.SOUTH) {
			setBlocks(x, y1, y2, z, material, Direction.EAST, Direction.WEST);
		} else if (facing == BlockFace.EAST || facing == BlockFace.WEST) {
			setBlocks(x, y1, y2, z, material, Direction.NORTH, Direction.SOUTH);
		}
	}

	public final void setLadder(int x, int y1, int y2, int z, Direction direction) {

		// this calculates which wall the ladder is on
		int offsetX = 0;
		int offsetZ = 0;
		switch (direction) {
		case EAST:
			offsetX = -1;
			break;
		case WEST:
			offsetX = 1;
			break;
		case SOUTH:
			offsetZ = -1;
			break;
		case NORTH:
		default:
			offsetZ = 1;
			break;
		}

		// only put the ladder on the wall (see above) if there is actually a wall
		for (int y = y1; y < y2; y++) {
			if (!isEmpty(x + offsetX, y, z + offsetZ)) {
				setBlock(x, y, z, Blocks.LADDER, direction);
			}
		}
	}

	public final void setTallBlock(int x, int y, int z, Block material) {
		setBlock(x, y, z, material, DoubleBlockHalf.LOWER);
		setBlock(x, y + 1, z, material, DoubleBlockHalf.UPPER);
	}

	private void setBlock(int x, int y, int z, Block material, DoubleBlockHalf half) {
		BaseBlock block = getActualBlock(x, y, z);
		BlockState blockState = material.getDefaultState();
		
		if(blockState.has(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
			blockState = blockState.with(BlockStateProperties.DOUBLE_BLOCK_HALF, half);
		}
		block.setBlockState(blockState, getDoPhysics(x, z));
	}
	
	public final void setBlock(int x, int y, int z, Block material, Direction facing, Half half) {
		BaseBlock block = getActualBlock(x, y, z);
		BlockState blockState = material.getDefaultState();
		
		if(blockState.has(BlockStateProperties.HALF)) {
			blockState = blockState.with(BlockStateProperties.HALF, half);
		}
		if(blockState.has(HorizontalBlock.HORIZONTAL_FACING)) {
			blockState = blockState.with(HorizontalBlock.HORIZONTAL_FACING, facing);
		}
		
		block.setBlockState(blockState, getDoPhysics(x, z));
	}

	public final void setBlock(int x, int y, int z, Block material, Direction facing, DoubleBlockHalf half) {
		BaseBlock block = getActualBlock(x, y, z);
		BlockState blockState = material.getDefaultState();
		
		if(blockState.has(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
			blockState = blockState.with(BlockStateProperties.DOUBLE_BLOCK_HALF, half);
		}
		if(blockState.has(HorizontalBlock.HORIZONTAL_FACING)) {
			blockState = blockState.with(HorizontalBlock.HORIZONTAL_FACING, facing);
		}
		
		block.setBlockState(blockState, getDoPhysics(x, z));
	}

	public final void setBlocks(int x1, int x2, int y, int z1, int z2, Block material, Direction facing) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				setBlock(x, y, z, material, facing);
	}

	public final void setBlocks(int x1, int x2, int y, int z1, int z2, Block material, Direction... facing) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				setBlock(x, y, z, material, facing);
	}

	public final void setChest(VocationCityWorldGenerator generator, int x, int y, int z, Odds odds,
			LootProvider lootProvider, LootLocation lootLocation) {
		if (!onEdgeXZ(x, z)) {
			Direction facing = Direction.NORTH;
			if (isEmpty(x - 1, y, z))
				facing = Direction.WEST;
			else if (isEmpty(x - 1, y, z))
				facing = Direction.EAST;
			else if (isEmpty(x, y, z + 1))
				facing = Direction.SOUTH;
			setChest(generator, x, y, z, facing, odds, lootProvider, lootLocation);
		}
	}

	public final void setChest(VocationCityWorldGenerator generator, int x, int y, int z, Direction facing, Odds odds,
			LootProvider lootProvider, LootLocation lootLocation) {
		if (!onNearEdgeXZ(x, z)) {
			setBlock(x, y, z, Blocks.CHEST, facing);
			BaseBlock block = getActualBlock(x, y, z);
			connectDoubleChest(x, y, z, facing);
			if (isType(block, Blocks.CHEST))
				lootProvider.setLoot(generator, odds, "DIM" + world.getDimension().getType().getId(), lootLocation, block);
		}
	}

	public final void setDoubleChest(VocationCityWorldGenerator generator, int x, int y, int z, Direction facing, Odds odds,
			LootProvider lootProvider, LootLocation lootLocation) {
		switch (facing) {
		default:
		case EAST:
		case WEST:
			if (z == 15) // Whoops, too far
				z = 14;
			setChest(generator, x, y, z, facing, odds, lootProvider, lootLocation);
			setChest(generator, x, y, z + 1, facing, odds, lootProvider, lootLocation);
			break;
		case NORTH:
		case SOUTH:
			if (x == 15) // Whoops, too far
				x = 14;
			setChest(generator, x, y, z, facing, odds, lootProvider, lootLocation);
			setChest(generator, x + 1, y, z, facing, odds, lootProvider, lootLocation);
			break;
		}
	}

	public final void setWallSign(int x, int y, int z, Direction facing, String... lines) {
		setWallSign(x, y, z, Blocks.BIRCH_WALL_SIGN, facing, lines);
	}

	public final void setWallSign(int x, int y, int z, Block sign, Direction facing, String... lines) {
		BaseBlock block = getActualBlock(x, y, z);
		BlockState blockState = block.getBlockState();
		
		if(blockState.has(HorizontalBlock.HORIZONTAL_FACING)) {
			blockState = blockState.with(HorizontalBlock.HORIZONTAL_FACING, facing);
		}
		
		block.setBlockState(blockState, getDoPhysics(x, z));
		
		TileEntity te = block.getTileEntity();
		if(te != null) {
			if(te instanceof SignTileEntity) {
				SignTileEntity sign_te = (SignTileEntity) te;
				for (int i = 0; i < lines.length && i < 4; i++)
					sign_te.setText(i, new StringTextComponent(lines[i]));
			}
		}		
	}

	public final void setSignPost(int x, int y, int z, Block sign, int rotation, String... lines) {
		BaseBlock block = getActualBlock(x, y, z);
		BlockState blockState = sign.getDefaultState();
		
		if(blockState.has(BlockStateProperties.ROTATION_0_15)) {
			blockState = blockState.with(BlockStateProperties.ROTATION_0_15, Integer.valueOf(rotation & 15));
		}
		
		block.setBlockState(blockState, getDoPhysics(x, z));
		
		TileEntity te = block.getTileEntity();
		if(te != null) {
			if(te instanceof SignTileEntity) {
				SignTileEntity sign_te = (SignTileEntity) te;
				for (int i = 0; i < lines.length && i < 4; i++)
					sign_te.setText(i, new StringTextComponent(lines[i]));
			}
		}		
	}

//	private int lastDistance = -1;

	public final void setLeaf(int x, int y, int z, Block material, boolean isPersistent) {
		BaseBlock block = getActualBlock(x, y, z);
		BlockState blockState = material.getDefaultState();
		
		if(blockState.has(BlockStateProperties.PERSISTENT)) {
			blockState = blockState.with(BlockStateProperties.PERSISTENT, Boolean.valueOf(true));
		}
		block.setBlockState(blockState, getDoPhysics(x, z));
	}

	public final void setLeaves(int x, int y1, int y2, int z, Block material, boolean isPersistent) {
		for (int y = y1; y < y2; y++)
			setLeaf(x, y, z, material, isPersistent);
	}

	public final void setLeaves(int x1, int x2, int y1, int y2, int z1, int z2, Block material,
			boolean isPersistent) {
		for (int x = x1; x < x2; x++)
			for (int y = y1; y < y2; y++)
				for (int z = z1; z < z2; z++)
					setLeaf(x, y, z, material, isPersistent);
	}

	public final void setLeafWalls(int x1, int x2, int y1, int y2, int z1, int z2, Block material,
			boolean isPersistent) {
		setLeaves(x1, x2, y1, y2, z1, z1 + 1, material, isPersistent); // N
		setLeaves(x1, x2, y1, y2, z2 - 1, z2, material, isPersistent); // S
		setLeaves(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material, isPersistent); // W
		setLeaves(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material, isPersistent); // E
	}

	private void setBedBlock(int x, int y, int z, Block material, Direction facing, BedPart part,
			boolean doPhysics) {
		BaseBlock block = getActualBlock(x, y, z);
		
		BlockState blockState = material.getDefaultState();
		if(blockState.has(BlockStateProperties.HORIZONTAL_FACING)) {
			blockState = blockState.with(BlockStateProperties.HORIZONTAL_FACING, facing);
			blockState = blockState.with(BlockStateProperties.BED_PART, part);
		}
		block.setBlockState(blockState, getDoPhysics(x, z));
	}

	public final void setBed(int x, int y, int z, Block material, Direction facing) {
		switch (facing) {
		default:
		case NORTH:
			setBedBlock(x, y, z, material, Direction.SOUTH, BedPart.FOOT, false);
			setBedBlock(x, y, z + 1, material, Direction.SOUTH, BedPart.HEAD, true);
			break;
		case SOUTH:
			setBedBlock(x, y, z + 1, material, Direction.NORTH, BedPart.FOOT, false);
			setBedBlock(x, y, z, material, Direction.NORTH, BedPart.HEAD, true);
			break;
		case EAST:
			setBedBlock(x + 1, y, z, material, Direction.WEST, BedPart.FOOT, false);
			setBedBlock(x, y, z, material, Direction.WEST, BedPart.HEAD, true);
			break;
		case WEST:
			setBedBlock(x, y, z, material, Direction.EAST, BedPart.FOOT, false);
			setBedBlock(x + 1, y, z, material, Direction.EAST, BedPart.HEAD, true);
			break;
		}
	}

	private void connectDoubleChest(int x, int y, int z, Direction facing) {
		BaseBlock block = getActualBlock(x, y, z);
		if (!isType(block, Blocks.CHEST)) {
			return;
		}
		BlockState blockState = block.getBlockState();
		if(blockState.get(ChestBlock.TYPE) != ChestType.SINGLE) {
			return;
		}
		
		BaseBlock checkLeftBlock, checkRightBlock;
		switch (facing) {
		default:
		case EAST:
			checkLeftBlock = z > 0 ? getActualBlock(x, y, z - 1) : null;
			checkRightBlock = z < 15 ? getActualBlock(x, y, z + 1) : null;
			break;
		case SOUTH:
			checkLeftBlock = x < 15 ? getActualBlock(x + 1, y, z) : null;
			checkRightBlock = x > 0 ? getActualBlock(x - 1, y, z) : null;
			break;
		case WEST:
			checkLeftBlock = z < 15 ? getActualBlock(x, y, z + 1) : null;
			checkRightBlock = z > 0 ? getActualBlock(x, y, z - 1) : null;
			break;
		case NORTH:
			checkLeftBlock = x > 0 ? getActualBlock(x - 1, y, z) : null;
			checkRightBlock = x < 15 ? getActualBlock(x + 1, y, z) : null;
			break;
		}
		
		if (checkLeftBlock != null && isType(checkLeftBlock, Blocks.CHEST)
				&& checkLeftBlock.getBlockState().get(HorizontalBlock.HORIZONTAL_FACING) == facing) {
			BlockState data = block.getBlockState();
			BlockState checkLeftData = checkLeftBlock.getBlockState();
			
			data = data.with(ChestBlock.TYPE, ChestType.RIGHT);
			checkLeftData = checkLeftData.with(ChestBlock.TYPE, ChestType.LEFT);
			
			block.setBlockState(data, false);
			checkLeftBlock.setBlockState(checkLeftData, false);
		} else if (checkRightBlock != null && isType(checkRightBlock, Blocks.CHEST)
				&& checkRightBlock.getBlockState().get(HorizontalBlock.HORIZONTAL_FACING) == facing) {
			BlockState data = block.getBlockState();
			BlockState checkRightBlockData = checkRightBlock.getBlockState();
			
			data = data.with(ChestBlock.TYPE, ChestType.LEFT);
			checkRightBlockData = checkRightBlockData.with(ChestBlock.TYPE, ChestType.RIGHT);
			
			block.setBlockState(data, false);
			checkRightBlock.setBlockState(checkRightBlockData, false);
		}
	}

	public final void setGate(int x, int y, int z, Block material, Direction direction, boolean isOpen) {
		BaseBlock block = getActualBlock(x, y, z);
		block.setType(material, false);
		BlockState blockState = block.getBlockState();
		
		if(blockState.has(HorizontalBlock.HORIZONTAL_FACING)) {
			blockState = blockState.with(HorizontalBlock.HORIZONTAL_FACING, direction);
		}
		if(blockState.has(BlockStateProperties.OPEN)) {
			blockState = blockState.with(BlockStateProperties.OPEN, Boolean.valueOf(isOpen));
		}
		block.setBlockState(blockState, true);
	}

	public final void setWaterLoggedBlocks(int x, int y1, int y2, int z, Block material) {
		for (int y = y1; y < y2; y++)
			setWaterLoggedBlock(x, y, z, material);
	}

	private void setWaterLoggedBlock(int x, int y, int z, Block material) {
		BaseBlock block = getActualBlock(x, y, z);
		BlockState blockState = block.getBlockState();
		if(blockState.has(BlockStateProperties.WATERLOGGED)) {
			blockState = blockState.with(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
			block.setBlockState(blockState, false);
		}
	}
}
