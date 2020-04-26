package bomberman.core.logic;

import bomberman.core.logic.powerUp.*;


/**
 * The BrickBlock class extends {@link Block}. It represents a rigid breakable block
 * of ground. {@link Entity} instances cannot pass over it, but {@link Bomb}s can destroy it. 
 * In its inside contains the eventual {@link PowerUp}, represented with a static {@link org.jbox2d.dynamics.Body}.
 * 
 * @author Federico Scozzafava
 */
public class BrickBlock extends Block
{

	/**
	 * The {@link LogicWorld}
	 */
	private LogicWorld logicWorld;
	
	/**
	 * The {@link PowerUpEnum} contained in this {@link Block}
	 * 
	 * @see {@link PowerUp}
	 */
	private PowerUpEnum powerUp;

	/**
	 * Instantiates a new {@link BrickBlock}.
	 * 
	 * @param x
	 *            the x position in pixels
	 * @param y
	 *            the y position in pixels
	 * @param dimension
	 *            the dimension (width and height) in pixels
	 * @param logicWorld
	 *            the {@link LogicWorld}
	 */
	public BrickBlock(float x, float y, float dimension, LogicWorld logicWorld)
	{
		super(x, y, dimension, false, logicWorld);
	}

	/**
	 * Instantiates a new brick block.
	 * 
	 * @param power
	 *            the {@link PowerUpEnum} associated with the {@link PowerUp} contained in this {@link Block}
	 * @param posX
	 *            the x position in pixels
	 * @param posY
	 *            the y position in pixels
	 * @param dimension
	 *            the dimension (width and height) in pixels
	 * @param logicWorld
	 *            the {@link LogicWorld}
	 */
	public BrickBlock(PowerUpEnum power, float posX, float posY, float dimension,
			LogicWorld logicWorld)
	{
		this(posX, posY, dimension, logicWorld);
		powerUp = power;
		this.logicWorld = logicWorld;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Block#destroy()
	 */
	@Override
	public Block destroy()
	{
		getBody().getWorld().destroyBody(getBody());
		return new FreeBlock(getPosition().getX(), getPosition().getY(),
				getBodyDimensions().getX(), logicWorld);
	}

	/**
	 * Gets the {@link PowerUp}'s relative {@link PowerUpEnum}.
	 * 
	 * @return the associated {@link PowerUpEnum}
	 */
	public PowerUpEnum getPowerUp()
	{
		return powerUp;
	}

}
