package bomberman.core.graphics;

import playn.core.Surface;
import bomberman.core.logic.FireEntity;

/**
 * The decorator associated to {@link FireEntity}.
 * 
 * @author Federico Scozzafava
 * @see http://en.wikipedia.org/wiki/Decorator_pattern
 */
public class FireDecorator implements GfxObject
{

	/**
	 * The {@link Sprite} animation.
	 */
	private static Sprite anim = new Sprite(
			DecoratedEntityEngine.getTexture("fire"), true);
	
	/**
	 * The {@link DecoratedWorld}.
	 */
	private DecoratedWorld decoratedWorld;
	
	/**
	 * The relative {@link FireEntity}.
	 */
	private FireEntity fire;
	
	/**
	 * The {@link SpritePlayer} instance.
	 */
	private SpritePlayer sp;

	/**
	 * Instantiates a new fire decorator.
	 * 
	 * @param f
	 *            the {@link FireEntity}
	 * @param d
	 *            the {@link DecoratedWorld}
	 */
	public FireDecorator(FireEntity f, DecoratedWorld d)
	{
		fire = f;
		sp = new SpritePlayer();
		decoratedWorld = d;
	}

	/**
	 * Removes the {@link GfxObject} from the relative {@link DecoratedWorld}.
	 */
	private void destroy()
	{
		decoratedWorld.removeGfxObject(this);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.graphics.GfxObject#drawSurface(playn.core.Surface)
	 */
	@Override
	public void drawSurface(Surface surface)
	{
		sp.draw(surface, fire.getBottomCenter());
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
		if (fire.getTimer() < 0) destroy();
		sp.playAnimation(delta, anim);
	}

}
