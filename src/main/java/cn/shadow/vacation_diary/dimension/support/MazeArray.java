package cn.shadow.vacation_diary.dimension.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class MazeArray {

	// lightly ported from Miga's Depth-first search maze algorithm
	// http://www.migapro.com/depth-first-search/

	public enum MazeBit {
		HALL, WALL
	}

	private final Random random;
	private final MazeBit[][] maze;
	private final int width;
	private final int height;

	public MazeArray(Odds odds, int width, int height) {
		this.random = new Random(odds.getRandomLong());
		this.width = width;
		this.height = height;
		this.maze = new MazeBit[width][height];
		generateMaze();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public MazeBit getBit(int x, int z) {
		if (x >= 0 && x < width && z >= 0 && z < height)
			return maze[x][z];
		else
			return MazeBit.WALL;
	}

	private void generateMaze() {

		// Initialize to all walls
		for (int x = 0; x < width; x++)
			for (int z = 0; z < height; z++)
				maze[x][z] = MazeBit.WALL;

		// Generate random c
		int x = random.nextInt(width);
		while (x % 2 == 0) {
			x = random.nextInt(width);
		}

		// z for row、x for column
		// Generate random z
		int z = random.nextInt(height);
		while (z % 2 == 0) {
			z = random.nextInt(height);
		}
		// Starting cell
		maze[x][z] = MazeBit.HALL;

		// Allocate the maze with recursive method
		recursion(x, z);
	}

	private void recursion(int x, int z) {

		// 4 random directions
		Integer[] randDirs = generateRandomDirections();

		// Examine each direction
		for (Integer randDir : randDirs) {

			switch (randDir) {
			case 1: // Up

				// Whether 2 cells up is out or not
				if (z - 2 <= 0)
					continue;
				if (maze[x][z - 2] != MazeBit.HALL) {
					maze[x][z - 2] = MazeBit.HALL;
					maze[x][z - 1] = MazeBit.HALL;
					recursion(x, z - 2);
				}
				break;
			case 2: // Right

				// Whether 2 cells to the right is out or not
				if (x + 2 >= width - 1)
					continue;
				if (maze[x + 2][z] != MazeBit.HALL) {
					maze[x + 2][z] = MazeBit.HALL;
					maze[x + 1][z] = MazeBit.HALL;
					recursion(x + 2, z);
				}
				break;
			case 3: // Down

				// Whether 2 cells down is out or not
				if (z + 2 >= height - 1)
					continue;
				if (maze[x][z + 2] != MazeBit.HALL) {
					maze[x][z + 2] = MazeBit.HALL;
					maze[x][z + 1] = MazeBit.HALL;
					recursion(x, z + 2);
				}
				break;
			case 4: // Left
			default:
				// Whether 2 cells to the left is out or not
				if (x - 2 <= 0)
					continue;
				if (maze[x - 2][z] != MazeBit.HALL) {
					maze[x - 2][z] = MazeBit.HALL;
					maze[x - 1][z] = MazeBit.HALL;
					recursion(x - 2, z);
				}
				break;
			}
		}

	}

	/**
	 * Generate an array with random directions 1-4
	 *
	 * @return Array containing 4 directions in random order
	 */
	private Integer[] generateRandomDirections() {

		ArrayList<Integer> randoms = new ArrayList<>();
		for (int i = 0; i < 4; i++)
			randoms.add(i + 1);
		Collections.shuffle(randoms);

		return randoms.toArray(new Integer[4]);
	}
}
