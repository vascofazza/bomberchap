package bomberman.core.logic.enemy;

import bomberman.core.logic.Block;
import bomberman.core.logic.LogicWorld;
import bomberman.core.logic.Strategy1;
import bomberman.core.util.Constants;

/**
 * The specific implementation of Onil.
 * 
 * @author Federico Scozzafava
 * @see http://bomberman.wikia.com/wiki/Onil
 */
public class EnemyOnil extends Enemy
{

	/**
	 * The {@link LogicWorld} where the enemy acts.
	 */
	private LogicWorld logicWorld;

	/**
	 * Instantiates a new {@link EnemyOnil}.
	 * 
	 * @param logicWorld
	 *            The {@link LogicWorld} where the enemy acts.
	 * @param c
	 *            the {@link Block} where the enemy is placed.
	 */
	public EnemyOnil(LogicWorld logicWorld, Block c)
	{
		super(logicWorld, c, Constants.ONIL_SPEED, new Strategy1());
		this.logicWorld = logicWorld;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Entity#die()
	 */
	@Override
	public void die()
	{
		logicWorld.increaseScore(Constants.ENEMY_ONIL_DIE_SCORE);
		super.die();
	}
}
