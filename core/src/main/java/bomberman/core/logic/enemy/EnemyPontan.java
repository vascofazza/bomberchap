package bomberman.core.logic.enemy;

import bomberman.core.logic.Block;
import bomberman.core.logic.LogicWorld;
import bomberman.core.logic.Strategy1;
import bomberman.core.util.Constants;

/**
 * The specific implementation of Pontan.
 * 
 * @author Federico Scozzafava
 * @see http://bomberman.wikia.com/wiki/Pontan
 */
public class EnemyPontan extends Enemy
{
	
	/**
	 * The {@link LogicWorld} where the enemy acts.
	 */
	private LogicWorld logicWorld;

	/**
	 * Instantiates a new {@link EnemyPontan}.
	 * 
	 * @param logicWorld
	 *            the {@link LogicWorld} where the enemy acts.
	 * @param c
	 *            the {@link Block} where the enemy is placed.
	 */
	public EnemyPontan(LogicWorld logicWorld, Block c)
	{
		super(logicWorld, c, Constants.PONTAN_SPEED, new Strategy1());
		this.logicWorld = logicWorld;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Entity#die()
	 */
	@Override
	public void die()
	{
		logicWorld.increaseScore(Constants.ENEMY_PONTAN_DIE_SCORE);
		super.die();
	}
}
