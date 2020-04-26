package bomberman.core.logic;

import bomberman.core.logic.DynamicPhysicsEntity;
import bomberman.core.logic.enemy.Enemy;

/**
 * The MoveStrategy interface provides base methods to define the class of autonomous movement 
 * strategies for {@link Enemy} and {@link DynamicPhysicsEntity} instances.
 * 
 * @author Federico Scozzafava
 * @see http://en.wikipedia.org/wiki/Strategy_pattern
 */
public interface MoveStrategy
{

	/**
	 * Change the current direction.
	 */
	public void changeDirection();

	/**
	 * Called at every update moves the {@link DynamicPhysicsEntity} in the current direction.
	 */
	public void move();

	/**
	 * Sets the {@link DynamicPhysicsEntity} to move.
	 * 
	 * @param entity
	 *            the {@link DynamicPhysicsEntity}
	 * @return the current {@link MoveStrategy}
	 */
	public MoveStrategy setEntity(DynamicPhysicsEntity entity);

}
