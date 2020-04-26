package bomberman.core.logic.powerUp;

import bomberman.core.logic.Block;
import bomberman.core.logic.Bomber;
import bomberman.core.logic.LogicWorld;

/**
 * The Door is a <b>special</b> {@link PowerUp}: it's the access point to the next level. The door
 * is enabled only if there are no more enemies on the ground.
 * 
 * @author Federico Scozzafava
 */
public class Door extends PowerUp
{

	/**
	 * The {@link LogicWorld} where the {@link PowerUp} is placed.
	 */
	private LogicWorld l;

	/**
	 * Instantiates a new {@link Door}.
	 * 
	 * @param l
	 *            the {@link LogicWorld} where the {@link PowerUp} is placed.
	 * @param c
	 *            the {@link Block} in which the {@link PowerUp} is contained and placed.
	 */
	public Door(LogicWorld l, Block c)
	{
		super(l, c);
		this.l = l;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.powerUp.PowerUp#apply(bomberman.core.logic.Bomber)
	 */
	@Override
	public void apply(Bomber b)
	{
		if (!l.getEnemies().isEmpty()) return;
		l.win();
		destroy();
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.powerUp.PowerUp#update(int)
	 */
	@Override
	public void update(int delta)
	{}

}
