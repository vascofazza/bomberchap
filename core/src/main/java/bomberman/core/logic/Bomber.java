package bomberman.core.logic;

import bomberman.core.logic.enemy.Enemy;
import bomberman.core.logic.powerUp.PowerUp;
import bomberman.core.util.Constants;

/**
 * The Bomber class defines the logic structure of the BomberMan character.
 * 
 * @author Federico Scozzafava
 * @see http://bomberman.wikia.com/wiki/White_Bomberman
 */
public class Bomber extends DynamicPhysicsEntity implements
		PhysicsEntity.HasContactListener
{
	
	/**
	 * The BomberMan's ability to kick off the {@link Bomb}s.
	 */
	private boolean bombKick = false;
	
	/**
	 * The BomberMan's ability to pass over the {@link Bomb}s.
	 */
	private boolean bombPass = false;
	
	/**
	 * The bombs explosion range.
	 */
	private int bombRange = Constants.INIT_FRE_RANGE;
	
	/**
	 * The number of bombs BomberMan can place at the same time.
	 */
	private int bombs = Constants.BOMBERMAN_INIT_BOMBS;
	
	
	/**
	 * The {@link LogicWorld}.
	 */
	private LogicWorld logicWorld;

	/**
	 * Instantiates a new {@link Bomber}.
	 * 
	 * @param l
	 *            the {@link LogicWorld}
	 * @param x
	 *            the x position in pixels
	 * @param y
	 *            the y position in pixels
	 */
	public Bomber(LogicWorld l, float x, float y)
	{
		super(l, x, y, Constants.BOMBERMAN_INIT_LIFES,
				Constants.BOMBERMAN_INIT_SPEED, false);
		logicWorld = l;
	}

	/**
	 * Increase the quantity of {@link Bomb}s BomberMan can place at the same time.
	 */
	public void addBomb()
	{
		bombs++;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity.HasContactListener#contact(bomberman.core.logic.PhysicsEntity)
	 */
	@Override
	public void contact(PhysicsEntity other)
	{
		if (other instanceof Enemy || other instanceof FireEntity)
			die();
		else if (other instanceof PowerUp)
		{
			PowerUp x = (PowerUp) other;
			x.apply(this);
		}
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Entity#destroy()
	 */
	@Override
	public void destroy()
	{
		logicWorld.lose();
		super.destroy();
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Entity#die()
	 */
	@Override
	public void die()
	{
		logicWorld.increaseScore(Constants.BOMBER_DIE_SCORE);
		super.die();
	}

	/**
	 * Gets the "bomb pass" feature status.
	 * 
	 * @return the bomb pass status
	 */
	public boolean getBombPassStatus()
	{
		return bombPass;
	}

	/**
	 * Increment bomb explosion range.
	 * 
	 * @param range
	 *            the explosion range
	 */
	public void incrementBombRange(int range)
	{
		if (bombRange > Constants.FIRE_MAX_RANGE || range < 0 && bombRange == 1)
			return;
		bombRange += range;
	}

	/**
	 * Gets the "bomb kick" feature status.
	 * 
	 * @return the bombKick status
	 */
	public boolean getBombKickStatus()
	{
		return bombKick;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity.HasContactListener#release(bomberman.core.logic.PhysicsEntity)
	 */
	@Override
	public void release(PhysicsEntity entityB)
	{}

	/**
	 * Decreases the quantity of {@link Bomb} BomberMan can place at the same time.
	 */
	public void removeBomb()
	{
		if (bombs == 1) return;
		bombs--;
	}

	/**
	 * Place a new {@link Bomb}.
	 * 
	 * @return if the number of bomb you can place at the same time
	 * has not exceeded, the bomb placed, otherwise null.
	 */
	public Bomb placeBomb()
	{
		if (bombs > 0)
		{
			Block c = logicWorld.getBlockAt(getCenter());
			Bomb b = new Bomb(logicWorld, this, c, bombRange, bombPass,
					bombKick);
			((FreeBlock) c).setBomb(b);
			bombs--;
			return b;
		}
		return null;
	}

	/**
	 * Sets the bomb kick feature's status.
	 * 
	 * @param bombKick
	 *            the bombKick status to set
	 */
	public void setBombKick(boolean bombKick)
	{
		this.bombKick = bombKick;
	}

	/**
	 * Sets the bomb pass status.
	 * 
	 * @param bombPass
	 *            the bombPass status to set
	 */
	public void setBombPass(boolean bombPass)
	{
		this.bombPass = bombPass;
	}
}
