package bomberman.core.logic;

import org.jbox2d.dynamics.BodyType;
import bomberman.core.util.Constants;

/**
 * The Bomb class defines the logic structure of a bomb.
 * 
 * @author Federico Scozzafava
 */
public class Bomb extends DynamicPhysicsEntity implements PhysicsEntity.HasContactListener
{

	/**
	 * The {@link LogicWorld}
	 */
	private LogicWorld logicWorld;
	
	/**
	 * The bomb timer.
	 */
	private int bombTimer;
	
	/**
	 * The caller who placed the {@link Bomb}.
	 */
	private Bomber caller;
	
	/**
	 * The {@link Bomb} explosion range.
	 */
	private int range;

	/**
	 * Instantiates a new bomb.
	 * 
	 * @param logicWorld
	 *            the {@link LogicWorld}
	 * @param b
	 *            the {@link Bomber} who placed the {@link Bomb};
	 * @param c
	 *            the {@link Block} in which the {@link Bomb} is placed.
	 * @param range
	 *            the explosion range.
	 */
	public Bomb(LogicWorld logicWorld, Bomber b, Block c, int range)
	{
		super(logicWorld, c.getPosition().getX(), c.getPosition().getY(), 0, 0,
				true);
		setCenter(c.getCenter());
		getBody().setType(BodyType.KINEMATIC);
		getBody().setLinearDamping(0);
		this.range = range;
		caller = b;
		bombTimer = Constants.BOMB_TIME;
		this.logicWorld = logicWorld;
	}

	/**
	 * Instantiates a new bomb with some extra features.
	 * 
	 * @param logicWorld
	 *            the {@link LogicWorld}
	 * @param bomber
	 *            the {@link Bomber} who placed the {@link Bomb}.
	 * @param c
	 *            the {@link Block} in which the {@link Bomb} is placed.
	 * @param bombRange
	 *            the explosion range
	 * @param bombPass
	 *            Indicates if {@link Bomber} can pass over this {@link Bomb}
	 * @param bombKick
	 *            Indicates if {@link Bomber} can kick off this {@link Bomb}
	 */
	public Bomb(LogicWorld logicWorld, Bomber bomber, Block c, int bombRange,
			boolean bombPass, boolean bombKick)
	{
		this(logicWorld, bomber, c, bombRange);
		getBody().setType(bombKick ? BodyType.DYNAMIC : BodyType.STATIC);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity.HasContactListener#contact(bomberman.core.logic.PhysicsEntity)
	 */
	@Override
	public void contact(PhysicsEntity other)
	{
		if (other instanceof FireEntity) bombTimer = 0;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Entity#destroy()
	 */
	@Override
	public void destroy()
	{
		caller.addBomb();
		super.destroy();
	}

	/**
	 * Explode.
	 */
	public void explode()
	{
		bombTimer = 0;
	}

	/**
	 * Gets the range.
	 * 
	 * @return the range
	 */
	public int getRange()
	{
		return range;
	}

	/**
	 * Gets the timer.
	 * 
	 * @return the timer
	 */
	public int getTimer()
	{
		return bombTimer;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity.HasContactListener#release(bomberman.core.logic.PhysicsEntity)
	 */
	@Override
	public void release(PhysicsEntity entityB)
	{
		if (entityB instanceof Bomber && !caller.getBombPassStatus())
			getBody().m_fixtureList.setSensor(false);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.DynamicPhysicsEntity#update(int)
	 */
	@Override
	public void update(int delta)
	{
		if (bombTimer-- < 0)
		{
			logicWorld.handleExplosion(this);
			destroy();
		}
		super.setPosition(getBodyPosition());
	}

}
