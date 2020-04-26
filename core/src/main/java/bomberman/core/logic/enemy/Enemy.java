package bomberman.core.logic.enemy;

import bomberman.core.logic.Block;
import bomberman.core.logic.DynamicPhysicsEntity;
import bomberman.core.logic.FireEntity;
import bomberman.core.logic.LogicWorld;
import bomberman.core.logic.MoveStrategy;
import bomberman.core.logic.PhysicsEntity;

/**
 * The Enemy class extends {@link DynamicPhysicsEntity} and defines the class of all enemies.
 * An {@link Enemy} moves independently in the grid using the specified {@link MoveStrategy}.
 * 
 * @author Federico Scozzafava
 * @see {@link MoveStrategy}
 * @see http://en.wikipedia.org/wiki/Strategy_pattern
 */
public abstract class Enemy extends DynamicPhysicsEntity implements
		PhysicsEntity.HasContactListener
{

	/**
	 * The contact boolean value helps when more enemies collide with each other.
	 */
	private boolean contact = false;
	
	/**
	 * The {@link MoveStrategy} strategy pattern.
	 * @see http://en.wikipedia.org/wiki/Strategy_pattern
	 */
	private MoveStrategy strategy;

	/**
	 * Instantiates a new {@link Enemy}.
	 * 
	 * @param logicWorld
	 *            the {@link LogicWorld} in which the enemy acts.
	 * @param c
	 *            the {@link Block} where to place the enemy.
	 * @param speed
	 *            the enemy speed
	 * @param strategy
	 *            the {@link MoveStrategy} strategy pattern
	 * @see http://en.wikipedia.org/wiki/Strategy_pattern
	 */
	public Enemy(LogicWorld logicWorld, Block c, float speed,
			MoveStrategy strategy)
	{
		super(logicWorld, c.getPosition(), 1, speed, true);
		this.strategy = strategy.setEntity(this);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity.HasContactListener#contact(bomberman.core.logic.PhysicsEntity)
	 */
	@Override
	public void contact(PhysicsEntity other)
	{
		if (other instanceof FireEntity)
			die();
		else if (other instanceof DynamicPhysicsEntity)
			strategy.changeDirection();// contact = true;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity.HasContactListener#release(bomberman.core.logic.PhysicsEntity)
	 */
	@Override
	public void release(PhysicsEntity other)
	{
		if (other instanceof Enemy) contact = false;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.DynamicPhysicsEntity#update(int)
	 */
	@Override
	public void update(int delta)
	{
		if (contact) strategy.changeDirection();
		strategy.move();
		super.update(delta);
	}

}
