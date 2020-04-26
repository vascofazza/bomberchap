package bomberman.core.graphics;

import playn.core.Surface;
import bomberman.core.logic.Bomb;

/**
 * The decorator associated to {@link Bomb}.
 * 
 * @author Federico Scozzafava
 * @see http://en.wikipedia.org/wiki/Decorator_pattern
 */
public class BombDecorator implements GfxObject
{

	/**
	 * The bomb's sprite.
	 */
	private static Sprite anim = new Sprite(
			DecoratedEntityEngine.getTexture("bomb"), true);
	
	/**
	 * The bomb attached to this decorator.
	 */
	private Bomb bomb;
	
	/**
	 * The decorated world.
	 */
	private DecoratedWorld decoratedWorld;
	
	/**
	 * The {@link SpritePlayer} instance.
	 */
	private SpritePlayer sp;

	/**
	 * Instantiates a new {@link Bomb} decorator.
	 * 
	 * @param b
	 *            The {@link Bomb}
	 * @param d
	 *            the {@link DecoratedWorld}
	 */
	public BombDecorator(Bomb b, DecoratedWorld d)
	{
		bomb = b;
		decoratedWorld = d;
		sp = new SpritePlayer();
	}

	/**
	 * Destroys this decorator removing itself from the {@link DecoratedWorld}.
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
		sp.draw(surface, bomb.getBottomCenter());
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
		int timer = bomb.getTimer();
		if (timer < 0) destroy();
		anim.setFrameTime(timer + delta);
		sp.playAnimation(delta, anim);
	}

}
