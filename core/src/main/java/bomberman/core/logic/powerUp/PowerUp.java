package bomberman.core.logic.powerUp;

import bomberman.core.logic.Block;
import bomberman.core.logic.Bomber;
import bomberman.core.logic.DynamicPhysicsEntity;
import bomberman.core.logic.LogicWorld;
import bomberman.core.util.Constants;

/**
 * The PowerUp class defines the base model for all the BomberMan powerUps. A {@link PowerUp}
 * adds or remove feature to {@link Bomber}man character.
 * 
 * @author Federico Scozzafava
 * @see http://bomberman.wikia.com/wiki/Power-Ups
 */
public abstract class PowerUp extends DynamicPhysicsEntity
{

	/**
	 * The time {@link PowerUp} should be visible in milliseconds.
	 */
	private int timer = Constants.POWERUP_TIME;

	/**
	 * Instantiates a new generic {@link PowerUp}.
	 * 
	 * @param l
	 *            the {@link LogicWorld} in which {@link PowerUp} acts.
	 * @param c
	 *            the {@link Block} in which the {@link PowerUp} is contained and placed.
	 */
	public PowerUp(LogicWorld l, Block c)
	{
		super(l, c.getPosition().getX(), c.getPosition().getY(), 1, 0, true);
	}

	/**
	 * Apply the {@link PowerUp}'s specific effect to the given {@link Bomber}.
	 * 
	 * @param b
	 *            the {@link Bomber}
	 */
	abstract public void apply(Bomber b);

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Entity#destroy()
	 */
	@Override
	public void destroy()
	{
		timer = 0;
	}

	/**
	 * Gets the time the {@link PowerUp} will be visible.
	 * 
	 * @return the time in milliseconds
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
		if (timer-- < 0) super.destroy();
	}
}
