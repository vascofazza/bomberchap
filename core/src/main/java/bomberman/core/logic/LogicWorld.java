package bomberman.core.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.jbox2d.dynamics.World;
import bomberman.core.logic.enemy.Enemy;
import bomberman.core.logic.powerUp.PowerUp;
import bomberman.core.logic.powerUp.PowerUpEnum;
import bomberman.core.util.Constants;
import bomberman.core.util.Timer;
import bomberman.core.util.Vector2;

/**
 * The class responsible for managing all logic game-related aspects during a single
 * round of game play. This includes (1) ensuring that the {@link EntityEngine}
 * is notified of updates, (2) keeping track of the player's scoring and other
 * game's informations like timer, and (3) managing the game state when rounds 
 * end or begin.
 * 
 * @author Federico Scozzafava
 */
public class LogicWorld
{

	/**
	 * The Coordinate class represents a tuple of integer x,y coordinates.
	 */
	private class Coordinate
	{
		
		/**
		 * The x coordinate.
		 */
		private int x;
		
		/**
		 * The y coordinate.
		 */
		private int y;

		/**
		 * Instantiates a new coordinate tuple.
		 * 
		 * @param x
		 *            the x coordinate
		 * @param y
		 *            the y coordinate
		 */
		public Coordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		/**
		 * @return the x coordinate.
		 */
		public int x() { return x; }
		
		/**
		 * @return the y coordinate.
		 */
		public int y() { return y; }
	}

	/**
	 * The list of {@link BrickBlock}s in the ground grid at the moment.
	 */
	private List<BrickBlock> dynamicGround;
	
	/**
	 * The {@link EntityEngine} instance.
	 */
	private EntityEngine entityEngine = EntityEngine.getInstance();
	
	/**
	 * The list of {@link FreeBlock}s in the ground grid at the moment.
	 */
	private List<FreeBlock> freeGround;
	
	/**
	 * The ground grid.
	 */
	private Block[][] grid;
	
	/**
	 * The {@link ScoreManager} responsible of managing the player's score.
	 */
	private ScoreManager scoreManager;
	
	/**
	 * The fixed dimension size of the {@link DynamicPhysicsEntity}'s body.
	 */
	private final float size;
	
	/**
	 * The list of {@link WallBlock}s in the ground grid at the moment.
	 */
	private List<WallBlock> staticGround;
	
	/**
	 * The game {@link Timer}.
	 */
	private Timer timer;
	
	/**
	 * The x position in pixels.
	 */
	private int x;

	/**
	 * The y position in pixels.
	 */
	private int y;

	/**
	 * Instantiates a new {@link LogicWorld}.
	 * 
	 * @param x
	 *            the world's width expressed in {@link Block}s.
	 * @param y
	 *            the world's height expressed in {@link Block}s.
	 * @param fixedSize
	 *            the fixed size dimension
	 */
	public LogicWorld(int x, int y, float fixedSize)
	{
		size = fixedSize;
		this.x = x;
		this.y = y;
		scoreManager = new ScoreManager();
	}

	/**
	 * Adds the entity to the {@link EntityEngine}.
	 * 
	 * @param e
	 *            the {@link Entity}
	 */
	public void addEntity(Entity e)
	{
		entityEngine.add(e);
	}

	/**
	 * Initialize the main grid.
	 * 
	 * @param x
	 *            the grid's width expressed in {@link Block}s
	 * @param y
	 *            the grid's height expressed in {@link Block}s
	 */
	private void initGrid(int x, int y)
	{
		for (int j = 0; j < x; j++)
			for (int k = 0; k < y; k++)
				if (k == 0 || k == y - 1 || j == 0 || j == x - 1 || j % 2 == 0
						&& k % 2 == 0)
				{
					WallBlock cm = new WallBlock(j * size, k * size, size, this);
					grid[j][k] = cm;
					staticGround.add(cm);
				}
				else
				{
					FreeBlock temp = new FreeBlock(j * size, k * size, size,
							this);
					grid[j][k] = temp;
					if (j > 2 && k > 2) freeGround.add(temp);
				}
	}

	/**
	 * Gets the adjacent {@link Block}s to a given {@link Block}.
	 * 
	 * @param c
	 *            the given {@link Block}
	 * @return the adjacent {@code Blocks}
	 */
	public List<Block> getAdjacent(Block c)
	{
		List<Block> set = new ArrayList<Block>();
		Coordinate coords = null;
		try
		{
			coords = getCoordinate(c);
		}
		catch (RuntimeException e)
		{
			coords = getCoordinate(getBlockAt(c.getCenter()));
		}
		for (int x = coords.x() - 1; x <= coords.x() + 1; x++)
			for (int y = coords.y() - 1; y <= coords.y() + 1; y++)
				if (x > 0 && x < this.x && y > 0 && y < this.y
						&& (x == coords.x() || y == coords.y()))
					set.add(grid[x][y]);
		return set;
	}

	/**
	 * Gets the bombs actually in game.
	 * 
	 * @return the bombs
	 */
	public List<Bomb> getBombs()
	{
		return entityEngine.getBombs();
	}

	/**
	 * Gets the border blocks.
	 * 
	 * @return the border blocks
	 */
	public Set<Block> getBorderBlocks()
	{
		Set<Block> set = new HashSet<Block>();

		for (int x = 0; x < this.x; x++)
			for (int y = 0; y < this.y; y++)
				if (x == 0 || x == this.x - 1 || y == 0 || y == this.y - 1)
					set.add(grid[x][y]);
		return set;
	}

	/**
	 * Gets the {@link Block} at the specified position.
	 * 
	 * @param v
	 *            the position in pixels represented through a {@link Vector2}.
	 * @return the {@link Block}, if nothing found null.
	 */
	public Block getBlockAt(Vector2 v)
	{
		for (Block[] j : grid)
			for (Block y : j)
				if (y.contains(v)) return y;
		return null;
	}

	/**
	 * Gets the coordinate in grid of the given {@link Block}.
	 * 
	 * @param c
	 *            the {@link Block}
	 * @return the coordinate
	 * @throws RuntimeException if the {@link Block} isn't in grid.
	 */
	private Coordinate getCoordinate(Block c)
	{
		for (int x = 0; x < this.x; x++)
			for (int y = 0; y < this.y; y++)
			{
				Block target = grid[x][y];
				if (target == c) return new Coordinate(x, y);
			}
		throw new RuntimeException("block not in grid");
	}

	/**
	 * Gets {@link BrickBlock} actually in grid.
	 * 
	 * @return the dynamic ground
	 */
	public List<BrickBlock> getDynamicGround()
	{
		return dynamicGround;
	}

	/**
	 * Gets the enemies actually in {@link EntityEngine}.
	 * 
	 * @return the enemies
	 */
	public List<Enemy> getEnemies()
	{
		return entityEngine.getEnemies();
	}

	/**
	 * Gets the adjacent {@link FreeBlock}s to the given {@link Block}.
	 * 
	 * @param c
	 *            the given {@link Block}
	 * @return the free adjacent {@link Block}s
	 */
	public List<Block> getFreeAdjacent(Block c)
	{
		List<Block> res = new ArrayList<Block>();
		for (Block x : getAdjacent(c))
			if (x instanceof FreeBlock) res.add(x);
		return res;
	}

	/**
	 * Gets the a random {@link FreeBlock} in grid.
	 * 
	 * @return the random {@link FreeBlock}
	 */
	public FreeBlock getRandomFreeBlock()
	{
		if (freeGround.size() < 1)
			throw new RuntimeException("no free blocks available");
		Random x = new Random();
		FreeBlock temp = freeGround.get(x.nextInt(freeGround.size()));
		freeGround.remove(temp);
		return temp;
	}

	/**
	 * Gets the {@link ScoreManager} responsible of managing the player's score for the
	 * current round.
	 * 
	 * @return the {@link ScoreManager}
	 */
	public ScoreManager getScoreManager()
	{
		return scoreManager;
	}

	/**
	 * Gets the list of {@link WallBlock}s currently in grid.
	 * 
	 * @return the static ground
	 */
	public List<WallBlock> getStaticGround()
	{
		return staticGround;
	}

	/**
	 * Gets the {@link Timer} responsible of managing the time elapsed for the current
	 * round.
	 * 
	 * @return the timer
	 */
	public Timer getTimer()
	{
		return timer;
	}

	/**
	 * Gets the {@code Box2d}'s {@link World} managed in {@link EntityEngine}.
	 * 
	 * @return the {@link World}
	 */
	public World getWorld()
	{
		return entityEngine.getWorld();
	}

	/**
	 * Handle {@link Bomb}s explosion. Called by the {@link Bomb}
	 * 
	 * @param bomb
	 *            the {@link Bomb}
	 * @return the list of {@link FireEntity} instances produced by the explosion
	 */
	protected List<DynamicPhysicsEntity> handleExplosion(Bomb bomb)
	{
		List<DynamicPhysicsEntity> res = new ArrayList<DynamicPhysicsEntity>();
		Block center = getBlockAt(bomb.getCenter());
		Coordinate pos = getCoordinate(center);
		int range = bomb.getRange();
		for (int x = pos.x(); x >= pos.x() - range; x--)
			if (0 < x && x < this.x)
			{
				DynamicPhysicsEntity f = instanceAt(x, pos.y);
				if (f == null) break;
				res.add(f);
				if (f instanceof PowerUp) break;

			}
		for (int x = pos.x() + 1; x <= pos.x() + range; x++)
			if (0 < x && x < this.x)
			{
				DynamicPhysicsEntity f = instanceAt(x, pos.y);
				if (f == null) break;
				res.add(f);
				if (f instanceof PowerUp) break;

			}
		for (int y = pos.y() - 1; y >= pos.y() - range; y--)
			if (0 < y && y < this.y)
			{
				DynamicPhysicsEntity f = instanceAt(pos.x, y);
				if (f == null) break;
				res.add(f);
				if (f instanceof PowerUp) break;

			}
		for (int y = pos.y() + 1; y <= pos.y() + range; y++)
			if (0 < y && y < this.y)
			{
				DynamicPhysicsEntity f = instanceAt(pos.x, y);
				if (f == null) break;
				res.add(f);
				if (f instanceof PowerUp) break;
			}
		return res;
	}

	/**
	 * Increase player's score.
	 * 
	 * @param v
	 *            the score value
	 */
	public void increaseScore(int v)
	{
		scoreManager.increaseScore(v);
	}

	/**
	 * Initialize a new round with the specified number of {@link BrickBlock}s and {@link Timer} value.
	 * 
	 * @param blocks
	 *            the number of {@link BrickBlock} in grid
	 * @param time
	 *            the {@link Timer} initial value
	 */
	public void initializeGame(int blocks, int time)
	{
		timer = new Timer(time);
		grid = new Block[x][y];
		// reset the bombs status
		for (Bomb b : entityEngine.getBombs())
			b.destroy();
		entityEngine.removeAll();
		entityEngine.clearWorld();
		dynamicGround = new LinkedList<BrickBlock>();
		staticGround = new ArrayList<WallBlock>();
		freeGround = new LinkedList<FreeBlock>();
		initGrid(x, y);
		placeBloks(blocks);
	}

	/**
	 * Method responsible of instantiating {@link FireEntity} and / or {@link PowerUp} objects
	 * when an explosion occurs. Called by {@link #handleExplosion(Bomb)}.
	 * 
	 * @param x
	 *            the x coordinate in grid of the {@link Block} 
	 *            where to instantiate the object
	 * @param y
	 *            the y coordinate in grid of the {@link Block} 
	 *            where to instantiate the object
	 * @return the adequate {@link DynamicPhysicsEntity}
	 */
	private DynamicPhysicsEntity instanceAt(int x, int y)
	{
		Block c = grid[x][y];
		grid[x][y] = c.destroy();
		dynamicGround.remove(c);
		if (c instanceof FreeBlock)
			return new FireEntity(this, c);
		else if (c instanceof BrickBlock
				&& ((BrickBlock) c).getPowerUp() != null)
		{
			scoreManager.increaseScore(Constants.BLOCK_SCORE);
			try
			{
				PowerUp p = ((BrickBlock) c).getPowerUp().newInstance(this, c);

				if (p != null) addEntity(p);
				return p;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Called by the game logic when game overs.
	 */
	public void lose()
	{
		scoreManager.reset();
	}

	/**
	 * Places {@link BrickBlock}s in grid and instantiate the
	 * relative {@link PowerUp}.
	 * 
	 * @param n
	 *            the number of {@link Block}s to place
	 */
	private void placeBloks(int n)
	{
		int count = n;
		while (count > 0)
		{
			int a = new Random().nextInt(x - 1);
			int b = new Random().nextInt(y - 1);
			if (grid[a][b] instanceof FreeBlock && !(a < 3 && b < 3))
			{
				freeGround.remove(grid[a][b]);
				double rand = new Random().nextDouble();
				double v = 0.0;
				PowerUpEnum powerUp = null;
				for (PowerUpEnum x : PowerUpEnum.values())
				{
					powerUp = x;
					v += powerUp.getProbability();
					if (v > rand) break;
				}
				if (--count == 1) powerUp = PowerUpEnum.DOOR;
				BrickBlock cm = new BrickBlock(powerUp, a * size, b * size,
						size, this);
				grid[a][b] = cm;
				dynamicGround.add(cm);
			}
		}
	}

	/**
	 * When an {@link Entity} is destroyed it is removed from the {@link EntityEngine}.
	 * 
	 * @param e
	 *            the {@link Entity}
	 */
	public void removeEntity(Entity e)
	{
		entityEngine.remove(e);
	}

	/**
	 * Updates the {@link EntityEngine}.
	 * 
	 * @param delta
	 *            the delta time parameter
	 */
	public void update(int delta)
	{
		entityEngine.update(delta);
		timer.update(delta);
		if (timer.getTimeInt() < 0) lose();
	}

	/**
	 * Called by the game when player wins the round.
	 */
	public void win()
	{
		scoreManager.increaseScore(Constants.SCORE_WIN);
	}
}
