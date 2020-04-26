package bomberman.core.logic.powerUp;

import bomberman.core.logic.Block;
import bomberman.core.logic.LogicWorld;

/**
 * The Enum PowerUpEnum is the access point for reflection-based instantiating {@link PowerUp}s.
 * In fact the specific {@link PowerUp} is not instanced since the {@link Block} in which it's
 * contained doesn't expose it. Each enum value has a float point value that indicates its
 * percentage of chance to appear and the {@link PowerUp}'s specific class type.
 * 
 * @author Federico Scozzafava
 */
public enum PowerUpEnum
{

	/**
	 * The {@link PowerUpBombKick}.
	 */
	BOMB_KICK(.05f, PowerUpBombKick.class),
	/**
	 * The {@link PowerUpBombPass}.
	 */
	BOMB_PASS(.05f, PowerUpBombPass.class),
	/**
	 * The {@link PowerUpBombRangeDown}.
	 */
	BOMB_RANGE_DOWN(.05f, PowerUpBombRangeDown.class),
	/**
	 * The {@link PowerUpBombRangeUp}.
	 */
	BOMB_RANGE_UP(.06f, PowerUpBombRangeUp.class),
	/**
	 * The {@link PowerUpBomb}.
	 */
	BOMB_UP(.065f, PowerUpBomb.class),
	/**
	 * The {@link Door}.
	 */
	DOOR(0f, Door.class),
	/**
	 * The {@link PowerUpLifeUp}.
	 */
	LIFE_UP(.055f, PowerUpLife.class),
	/**
	 * void {@link PowerUp}.
	 */
	NULL(.3f, null),
	/**
	 * The {@link PowerUpSkull}.
	 */
	SKULL(.15f, PowerUpSkull.class),
	/**
	 * The {@link PowerUpSpeedDown}.
	 */
	SPEED_DOWN(.05f, PowerUpSpeedDown.class),
	/**
	 * The {@link PowerUpSpeedUp}.
	 */
	SPEED_UP(.06f, PowerUpSpeedUp.class),
	/**
	 * The {@link PowerUpTimeDown}
	 */
	TIME_DOWN(.05f, PowerUpTimeDown.class),
	/**
	 * The {@link PowerUpTimeUp}.
	 */
	TIME_UP(.06f, PowerUpTimeUp.class);

	/**
	 * The type.
	 */
	private final Class<? extends PowerUp> powerUp;

	/**
	 * The probability.
	 */
	private final float probability;

	/**
	 * Instantiates a new power up enum.
	 * 
	 * @param probability
	 *            the probability
	 * @param powerUp
	 *            the power up
	 */
	private PowerUpEnum(float probability, Class<? extends PowerUp> powerUp)
	{
		this.probability = probability;
		this.powerUp = powerUp;
	}

	/**
	 * Gets the {@link PowerUp} class.
	 * 
	 * @return the {@link PowerUp} class
	 */
	public Class<? extends PowerUp> getPowClass()
	{
		return powerUp;
	}

	/**
	 * Gets the probability.
	 * 
	 * @return the probability
	 */
	public float getProbability() // TODO chance?
	{
		return probability;
	}

	/**
	 * Return the relative {@link PowerUp} instanced.
	 * 
	 * @param l
	 *            The {@link LogicWorld} where the {@link PowerUp} is placed.
	 * @param c
	 *            the c
	 * @return the {@link Block} in which the {@link PowerUp} is contained and placed.
	 * @throws Exception
	 *             java reflection exception
	 */
	public PowerUp newInstance(LogicWorld l, Block c) throws Exception
	{
		return powerUp != null ? powerUp.getConstructor(LogicWorld.class,
				Block.class).newInstance(l, c) : null;
	}

}
