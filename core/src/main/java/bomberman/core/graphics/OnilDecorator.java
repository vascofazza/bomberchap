package bomberman.core.graphics;

import playn.core.Surface;
import bomberman.core.logic.Block;
import bomberman.core.logic.enemy.EnemyOnil;
import bomberman.core.util.Constants;

/**
 * The decorator associated to {@link EnemyOnil}.
 * 
 * @author Federico Scozzafava
 * @see http://en.wikipedia.org/wiki/Decorator_pattern
 * @see http://bomberman.wikia.com/wiki/Onil
 */
public class OnilDecorator extends EnemyOnil implements GfxObject
{

	/**
	 * The dying {@link Sprite}.
	 */
	private static Sprite dying = new Sprite(
			DecoratedEntityEngine.getTexture("onilDying"), false);
	
	/**
	 * The idle {@link Sprite}.
	 */
	private static Sprite idle = new Sprite(
			DecoratedEntityEngine.getTexture("onilIdle"), true);
	
	/**
	 * The delegate {@link Sprite}.
	 */
	private Sprite currentSprite;
	
	/**
	 * The {@link SpritePlayer} instance
	 */
	private DecoratedWorld decoratedWorld;
	
	/**
	 * The die timer. Indicates how long the dying {@link Sprite} should be visible
	 */
	private int dieTimer = 0;
	
	/**
	 * The {@link SpritePlayer} instance.
	 */
	private SpritePlayer sp;

	/**
	 * Instantiates a new {@link EnemyOnil} entity decorator together with its logic entity.
	 * 
	 * @param d
	 *            the {@link DecoratedWorld}
	 * @param c
	 *            the {@link Block} in which to initialize the entity
	 */
	public OnilDecorator(DecoratedWorld d, Block c)
	{
		super(d.getLogicWorld(), c);
		decoratedWorld = d;
		sp = new SpritePlayer();
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Entity#destroy()
	 */
	@Override
	public void destroy()
	{
		super.destroy();
		decoratedWorld.removeGfxObject(this);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.enemy.EnemyOnil#die()
	 */
	@Override
	public void die()
	{
		dieTimer = Constants.DIE_TIME;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.graphics.GfxObject#drawSurface(playn.core.Surface)
	 */
	@Override
	public void drawSurface(Surface surface)
	{
		sp.draw(surface, getBottomCenter());
	}

	/* (non-Javadoc)
	 * @see bomberman.core.graphics.GfxObject#paint(float)
	 */
	@Override
	public void paint(float alpha)
	{}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.enemy.Enemy#update(int)
	 */
	@Override
	public void update(int delta)
	{
		switch (getState())
		{
		case DYING:
			currentSprite = dying;
			break;
		default:
			currentSprite = idle;
			break;
		}
		if (currentSprite == null)
			throw new RuntimeException("Assets not initialized");
		if (dieTimer > 0)
		{
			dieTimer--;
			currentSprite = dying;
			if (dieTimer == 0) super.die();
		}
		else
			super.update(delta);
		sp.playAnimation(delta, currentSprite);
	}

}
