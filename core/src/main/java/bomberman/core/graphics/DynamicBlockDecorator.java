package bomberman.core.graphics;

import playn.core.Surface;
import bomberman.core.logic.Block;

/**
 * The decorator associated to {@link Block}.
 * 
 * @author Federico Scozzafava
 * @see http://en.wikipedia.org/wiki/Decorator_pattern
 */
public class DynamicBlockDecorator implements GfxObject
{
	
	/**
	 * The block's {@link Sprite}.
	 */
	private static Sprite anim = new Sprite(
			DecoratedEntityEngine.getTexture("dynamicGroundImage"), false);
	
	/**
	 * The {@link Block}.
	 */
	private Block c;
	
	/**
	 * The {@link DecoratedWorld}.
	 */
	private DecoratedWorld d;
	
	/**
	 * The {@link SpritePlayer} instance.
	 */
	private SpritePlayer sp;

	/**
	 * Instantiates a new dynamic block decorator.
	 * 
	 * @param d
	 *            the {@link DecoratedWorld}
	 * @param c
	 *            the relative {@link Block}
	 */
	public DynamicBlockDecorator(DecoratedWorld d, Block c)
	{
		this.c = c;
		this.d = d;
		sp = new SpritePlayer();
		sp.playAnimation(0, anim);
	}

	/**
	 * Removes the {@link GfxObject} from the {@link DecoratedWorld}.
	 */
	private void destroy()
	{
		d.removeGfxObject(this);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.graphics.GfxObject#drawSurface(playn.core.Surface)
	 */
	@Override
	public void drawSurface(Surface surface)
	{
		sp.draw(surface, c.getBottomCenter());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o)
	{
		return o instanceof Block ? (Block) o == c : false;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.graphics.GfxObject#paint(float)
	 */
	@Override
	public void paint(float alpha)
	{}

	/* (non-Javadoc)
	 * @see bomberman.core.graphics.GfxObject#update(int)
	 */
	@Override
	public void update(int delta)
	{
		if (sp.frameIndex() == anim.frameCount()) destroy();
		sp.playAnimation(delta, anim);
	}

}
