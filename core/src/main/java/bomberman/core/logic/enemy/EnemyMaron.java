package bomberman.core.logic.enemy;

import bomberman.core.logic.Block;
import bomberman.core.logic.LogicWorld;
import bomberman.core.logic.Strategy2;
import bomberman.core.util.Constants;

/**
 * The specific implementation of Maron.
 * 
 * @author Federico Scozzafava
 * @see http://bomberman.wikia.com/wiki/Maron
 */
public class EnemyMaron extends Enemy
{

	/**
	 * The {@link LogicWorld} where the enemy acts.
	 */
	private LogicWorld logicWorld;

	/**
	 * Instantiates a new {@link EnemyMaron}.
	 * 
	 * @param logicWorld
	 *            the {@link LogicWorld} where the enemy acts.
	 * @param c
	 *            the {@link Block} where the enemy is placed.
	 */
	public EnemyMaron(LogicWorld logicWorld, Block c)
	{
		super(logicWorld, c, Constants.MARON_SPEED, new Strategy2());
		this.logicWorld = logicWorld;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Entity#die()
	 */
	@Override
	public void die()
	{
		logicWorld.increaseScore(Constants.ENEMY_MARON_DIE_SCORE);
		super.die();
	}

}
