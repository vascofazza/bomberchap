package bomberman.core.logic.powerUp;

import bomberman.core.logic.Block;
import bomberman.core.logic.Bomber;
import bomberman.core.logic.LogicWorld;
import bomberman.core.util.Constants;

/**
 * The PowerUpBombRangeDown {@link PowerUp} decrease the {@link Bomb}'s explosion range.
 * 
 * @author Federico Scozzafava
 * @see http://bomberman.wikia.com/wiki/Fire_Down
 */
public class PowerUpBombRangeDown extends PowerUp
{

	/**
	 * Instantiates a new {@link PowerUpBombRangeDown}.
	 * 
	 * @param l
	 *            the {@link LogicWorld} where the {@link PowerUp} is placed.
	 * @param c
	 *            the {@link Block} in which the {@link PowerUp} is contained and placed.
	 */
	public PowerUpBombRangeDown(LogicWorld l, Block c)
	{
		super(l, c);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.powerUp.PowerUp#apply(bomberman.core.logic.Bomber)
	 */
	@Override
	public void apply(Bomber b)
	{
		b.incrementBombRange(-Constants.FIRE_RANGE_INCREMENT);
		destroy();

	}

}
