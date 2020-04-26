package bomberman.core.logic.powerUp;

import org.jbox2d.dynamics.BodyType;

import bomberman.core.logic.Block;
import bomberman.core.logic.Bomb;
import bomberman.core.logic.Bomber;
import bomberman.core.logic.LogicWorld;

/**
 * The PowerUpBombKick {@link PowerUp} gives {@link Bomber} the ability to kick off the {@link Bomb}s.
 * 
 * @author Federico Scozzafava
 * @see http://bomberman.wikia.com/wiki/Kick
 */
public class PowerUpBombKick extends PowerUp
{

	/**
	 * The {@link LogicWorld} where the {@link PowerUp} is placed.
	 */
	private LogicWorld l;

	/**
	 * Instantiates a new {@link PowerUpBombKick}.
	 * 
	 * @param l
	 *            the {@link LogicWorld} where the {@link PowerUp} is placed.
	 * @param c
	 *            the {@link Block} in which the {@link PowerUp} is contained and placed.
	 */
	public PowerUpBombKick(LogicWorld l, Block c)
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
		b.setBombPass(false);
		b.setBombKick(true);
		for (Bomb x : l.getBombs())
		{
			x.getBody().getFixtureList().setSensor(false);
			x.getBody().setType(BodyType.DYNAMIC);
		}
		destroy();
	}

}
