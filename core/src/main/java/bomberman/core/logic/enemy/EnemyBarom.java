package bomberman.core.logic.enemy;

import bomberman.core.logic.Block;
import bomberman.core.logic.LogicWorld;
import bomberman.core.logic.Strategy2;
import bomberman.core.util.Constants;

/**
 * The specific implementation of Barom.
 * 
 * @author Federico Scozzafava
 * @see http://bomberman.wikia.com/wiki/Barom
 */
public class EnemyBarom extends Enemy
{

	/**
	 * The {@link LogicWorld}.
	 */
	private LogicWorld logicWorld;

	/**
	 * Instantiates a new {@link EnemyBarom}.
	 * 
	 * @param logicWorld
	 *            the {@link LogicWorld} where enemy acts.
	 * @param c
	 *            the {@link Block} where enemy is placed.
	 */
	public EnemyBarom(LogicWorld logicWorld, Block c)
	{
		super(logicWorld, c, Constants.BAROM_SPEED, new Strategy2());
		this.logicWorld = logicWorld;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Entity#die()
	 */
	@Override
	public void die()
	{
		logicWorld.increaseScore(Constants.ENEMY_BAROM_DIE_SCORE);
		super.die();
	}
}
