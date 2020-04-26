package bomberman.core.logic.powerUp;

import java.util.Random;

import bomberman.core.logic.Block;
import bomberman.core.logic.Bomber;
import bomberman.core.logic.LogicWorld;
import bomberman.core.util.Constants;

/**
 * The PowerUpSkull {@link PowerUp} adds a disease effect to the {@link Bomber}.
 * 
 * @author Federico Scozzafava
 * @see http://bomberman.wikia.com/wiki/Skull
 */
public class PowerUpSkull extends PowerUp
{

	/**
	 * The {@link LogicWorld} where the {@link PowerUp} is placed.
	 */
	private LogicWorld l;

	/**
	 * Instantiates a new {@link PowerUpSkull}.
	 * 
	 * @param l
	 *            the {@link LogicWorld} where the {@link PowerUp} is placed.
	 * @param c
	 *            the {@link Block} in which the {@link PowerUp} is contained and placed.
	 */
	public PowerUpSkull(LogicWorld l, Block c)
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
		switch (new Random().nextInt(6))
		{
		case 0:
			b.removeBomb();
			break;
		case 1:
			b.die();
			break;
		case 2:
			l.getTimer().addTime(-Constants.TIMER_INCREMENT / 4);
			break;
		case 3:
			b.incrementBombRange(-Constants.FIRE_RANGE_INCREMENT);
			break;
		default:
			b.increaseSpeed(-Constants.SPEED_INCREMENT);
			break;
		}
		destroy();
	}

}
