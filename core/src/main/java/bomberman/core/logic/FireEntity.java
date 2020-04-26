package bomberman.core.logic;

import org.jbox2d.dynamics.BodyType;

import bomberman.core.util.Constants;

/**
 * The FireEntity class represents the flame that comes up with the explosion produced by
 * a {@link Bomb}.
 * 
 * @author Federico Scozzafava
 */
public class FireEntity extends DynamicPhysicsEntity
{
	
	/**
	 * The time flame should acts in the {@link LogicWorld}.
	 */
	private int timer = Constants.FIRE_TIME;

	/**
	 * Instantiates a new fire entity.
	 * 
	 * @param logicWorld
	 *            the {@link LogicWorld} where the entity acts.
	 * @param c
	 *            the {@link Block} where to place the entity
	 */
	public FireEntity(LogicWorld logicWorld, Block c)
	{
		super(logicWorld, c.getPosition().getX(), c.getPosition().getY(), 1, 0,
				true);
		setCenter(c.getCenter());
		getBody().setType(BodyType.KINEMATIC);
		getBody().setActive(true);
	}

	/**
	 * Gets the timer.
	 * 
	 * @return the timer
	 */
	public int getTimer()
	{
		return timer;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.DynamicPhysicsEntity#update(int)
	 */
	@Override
	public void update(int delta)
	{
		super.update(delta);
		if (timer-- < 0) destroy();
	}

}
