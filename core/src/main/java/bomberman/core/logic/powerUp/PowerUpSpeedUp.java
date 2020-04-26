package bomberman.core.logic.powerUp;

import bomberman.core.logic.Block;
import bomberman.core.logic.Bomber;
import bomberman.core.logic.LogicWorld;
import bomberman.core.util.Constants;

/**
 * The PowerUpSpeedUp {@link PowerUp} increases the movement speed of the {@link Bomber}.
 * 
 * @author Federico Scozzafava
 * @see http://bomberman.wikia.com/wiki/Skate
 */
public class PowerUpSpeedUp extends PowerUp
{

	/**
	 * Instantiates a new {@link PowerUpSpeedUp}.
	 * 
	 * @param l
	 *            the {@link LogicWorld} where the {@link PowerUp} is placed.
	 * @param c
	 *            the {@link Block} in which the {@link PowerUp} is contained and placed.
	 */
	public PowerUpSpeedUp(LogicWorld l, Block c)
	{
		super(l, c);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.powerUp.PowerUp#apply(bomberman.core.logic.Bomber)
	 */
	@Override
	public void apply(Bomber b)
	{
		b.increaseSpeed(Constants.SPEED_INCREMENT);
		destroy();
	}

}
