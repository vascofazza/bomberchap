package bomberman.core.logic.powerUp;

import bomberman.core.logic.Block;
import bomberman.core.logic.Bomber;
import bomberman.core.logic.LogicWorld;

/**
 * The PowerUpBomb {@link PowerUp} increase the quantity of {@link Bomb} that {@link Bomber} can
 * place at the same time.
 * 
 * @author Federico Scozzafava
 * @see http://bomberman.wikia.com/wiki/Bomb-Up
 */
public class PowerUpBomb extends PowerUp
{

	/**
	 * Instantiates a new {@link PowerUpBomb}.
	 * 
	 * @param l
	 *            the {@link LogicWorld} where the {@link PowerUp} is placed.
	 * @param c
	 *            the {@link Block} in which the {@link PowerUp} is contained and placed.
	 */
	public PowerUpBomb(LogicWorld l, Block c)
	{
		super(l, c);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.powerUp.PowerUp#apply(bomberman.core.logic.Bomber)
	 */
	@Override
	public void apply(Bomber b)
	{
		b.addBomb();
		destroy();
	}

}
