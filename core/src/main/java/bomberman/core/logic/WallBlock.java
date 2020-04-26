package bomberman.core.logic;

/**
 * The WallBlock class extends the {@link Block} class. It represents a rigid unbreakable block
 * of ground. {@link Entity} instances cannot pass over it. Represented with a static {@link Body}.
 * 
 * @author Federico Scozzafava
 */
public class WallBlock extends Block
{

	/**
	 * Instantiates a new {@link WallBlock}.
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
	public WallBlock(float x, float y, float dimension, LogicWorld logicWorld)
	{
		super(x, y, dimension, dimension, false, logicWorld);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Block#destroy()
	 */
	@Override
	public Block destroy()
	{
		return this;
	}

}
