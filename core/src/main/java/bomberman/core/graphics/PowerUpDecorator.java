package bomberman.core.graphics;

import playn.core.Surface;
import bomberman.core.logic.powerUp.PowerUp;

/**
 * The decorator associated to {@link PowerUp}
 * 
 * @author Federico Scozzafava.
 * @see http://en.wikipedia.org/wiki/Decorator_pattern
 */
public class PowerUpDecorator implements GfxObject
{

	/**
	 * The {@link DecoratedWorld}.
	 */
	private DecoratedWorld d;
	
	/**
	 * The {@link PowerUp} associated {@link Sprite}.
	 */
	private Sprite image;
	
	/**
	 * The {@link PowerUp}.
	 */
	private PowerUp p;
	
	/**
	 * The {@link SpritePlayer} instance.
	 */
	private SpritePlayer sp;

	/**
	 * Instantiates a new {@link PowerUp} decorator.
	 * 
	 * @param d
	 *            the {@link DecoratedWorld}
	 * @param p
	 *            the {@link PowerUp}
	 */
	public PowerUpDecorator(DecoratedWorld d, PowerUp p)
	{
		this.d = d;
		this.p = p;
		sp = new SpritePlayer();
		image = new Sprite(DecoratedEntityEngine.getTexture(p.getClass()
				.getSimpleName()), true);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.graphics.GfxObject#drawSurface(playn.core.Surface)
	 */
	@Override
	public void drawSurface(Surface surface)
	{
		sp.draw(surface, p.getBottomCenter());
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
		sp.playAnimation(delta, image);
		if (p.getTimer() < 0) d.removeGfxObject(this);
	}

}
