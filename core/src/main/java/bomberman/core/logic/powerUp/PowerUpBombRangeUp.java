package bomberman.core.logic.powerUp;

import bomberman.core.logic.Block;
import bomberman.core.logic.Bomber;
import bomberman.core.logic.LogicWorld;
import bomberman.core.util.Constants;

/**
 * The PowerUpBombRangeUp {@link PowerUp} increase the {@link Bomb}'s explosion range.
 * 
 * @author Federico Scozzafava
 * @see http://bomberman.wikia.com/wiki/Fire
 */
public class PowerUpBombRangeUp extends PowerUp
{

	/**
	 * Instantiates a new {@link PowerUpBombRangeUp}.
	 * 
	 * @param l
	 *            the {@link LogicWorld} where the {@link PowerUp} is placed.
	 * @param c
	 *            the {@link Block} in which the {@link PowerUp} is contained and placed.
	 */
	public PowerUpBombRangeUp(LogicWorld l, Block c)
	{
		super(l, c);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.powerUp.PowerUp#apply(bomberman.core.logic.Bomber)
	 */
	@Override
	public void apply(Bomber b)
	{
		b.incrementBombRange(Constants.FIRE_RANGE_INCREMENT);
		destroy();
	}

}
