package bomberman.core.logic;

/**
 * The FreeBlock class represent a free piece of ground.
 * 
 * @author Federico Scozzafava
 * @see {@link Block}
 */
public class FreeBlock extends Block
{

	/**
	 * Instantiates a new free block.
	 * 
	 * @param posX
	 *            the x position in pixels
	 * @param posY
	 *            the y position in pixels
	 * @param dimension
	 *            the width and height in pixels
	 * @param logicWorld
	 *            the {@link LogicWorld} in which the {@link Block} is placed
	 */
	public FreeBlock(float posX, float posY, float dimension, LogicWorld logicWorld)
	{
		super(posX, posY, dimension, dimension, true, logicWorld);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Block#destroy()
	 */
	@Override
	public Block destroy()
	{
		Bomb bomb = getBomb();
		if (bomb != null) bomb.explode();
		removeBomb();
		return this;
	}

}
