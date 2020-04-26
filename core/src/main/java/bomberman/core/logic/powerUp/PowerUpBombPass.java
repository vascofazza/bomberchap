package bomberman.core.logic.powerUp;

import bomberman.core.logic.Block;
import bomberman.core.logic.Bomb;
import bomberman.core.logic.Bomber;
import bomberman.core.logic.LogicWorld;

/**
 * The PowerUpBombPass {@link PowerUp} gives {@link Bomber} the ability to pass over the 
 * {@link Bomb}s.
 * 
 * @author Federico Scozzafava
 * @see http://bomberman.wikia.com/wiki/Bomb_Pass
 */
public class PowerUpBombPass extends PowerUp
{

	/**
	 * The {@link LogicWorld} where the {@link PowerUp} is placed.
	 */
	private LogicWorld l;

	/**
	 * Instantiates a new {@link PowerUpBombPass}.
	 * 
	 * @param l
	 *            the {@link LogicWorld} where the {@link PowerUp} is placed.
	 * @param c
	 *            the {@link Block} in which the {@link PowerUp} is contained and placed.
	 */
	public PowerUpBombPass(LogicWorld l, Block c)
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
		b.setBombPass(true);
		for (Bomb x : l.getBombs())
			x.getBody().getFixtureList().setSensor(true);
		destroy();
	}

}
