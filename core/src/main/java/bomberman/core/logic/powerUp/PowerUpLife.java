package bomberman.core.logic.powerUp;

import bomberman.core.logic.Block;
import bomberman.core.logic.Bomber;
import bomberman.core.logic.LogicWorld;

/**
 * The PowerUpLife {@link PowerUp} gives {@link Bomber} one extra life.
 * 
 * @author Federico Scozzafava
 * @see http://bomberman.wikia.com/wiki/Heart
 */
public class PowerUpLife extends PowerUp
{

	/**
	 * Instantiates a new {@link PowerUpLife}.
	 * 
	 * @param l
	 *            the {@link LogicWorld} where the {@link PowerUp} is placed.
	 * @param c
	 *            the {@link Block} in which the {@link PowerUp} is contained and placed.
	 */
	public PowerUpLife(LogicWorld l, Block c)
	{
		super(l, c);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.powerUp.PowerUp#apply(bomberman.core.logic.Bomber)
	 */
	@Override
	public void apply(Bomber b)
	{
		b.setLives(b.getLives() + 1);
		destroy();
	}

}
