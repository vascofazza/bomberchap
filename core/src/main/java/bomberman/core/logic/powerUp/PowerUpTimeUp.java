/**
 * 
 */
package bomberman.core.logic.powerUp;

import bomberman.core.logic.Block;
import bomberman.core.logic.Bomber;
import bomberman.core.logic.LogicWorld;
import bomberman.core.util.Constants;

/**
 * The PowerUpTimeUp {@link PowerUp} add some additional time to the {@link Timer}.
 * 
 * @author Federico Scozzafava
 * @see http://bomberman.wikia.com/wiki/Clock
 */
public class PowerUpTimeUp extends PowerUp
{

	/**
	 * The {@link LogicWorld} where the {@link PowerUp} is placed.
	 */
	private LogicWorld l;

	/**
	 * Instantiates a new {@link PowerUpTimeUp}.
	 * 
	 * @param l
	 *            the {@link LogicWorld} where the {@link PowerUp} is placed.
	 * @param c
	 *            the {@link Block} in which the {@link PowerUp} is contained and placed.
	 */
	public PowerUpTimeUp(LogicWorld l, Block c)
	{
		super(l, c);
		this.l = l;
	}

	/*
	 * (non-Javadoc)
	 * @see bomberman.core.logic.PowerUp#apply(bomberman.core.logic.Bomber)
	 */
	@Override
	public void apply(Bomber b)
	{
		l.getTimer().addTime(Constants.TIMER_INCREMENT);
		destroy();
	}

}
