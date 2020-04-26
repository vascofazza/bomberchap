package bomberman.core.graphics;

import java.util.Stack;

import playn.core.Keyboard;
import playn.core.Keyboard.TypedEvent;
import playn.core.Pointer;
import playn.core.Pointer.Event;
import playn.core.Surface;
import bomberman.core.logic.Bomb;
import bomberman.core.logic.Bomber;
import bomberman.core.logic.Direction;
import bomberman.core.util.Constants;

/**
 * The decorator associated to {@link Bomber}.
 * 
 * @author Federico Scozzafava
 * @see http://en.wikipedia.org/wiki/Decorator_pattern
 * @see http://bomberman.wikia.com/wiki/White_Bomberman
 */
public class BomberDecorator extends Bomber implements GfxObject,
		Keyboard.Listener, Pointer.Listener
{
	
	/**
	 * The down {@link Sprite}.
	 */
	private static Sprite down = new Sprite(
			DecoratedEntityEngine.getTexture("bomberDown"), true);
	
	/**
	 * The dying {@link Sprite}.
	 */
	private static Sprite dying = new Sprite(
			DecoratedEntityEngine.getTexture("bomberDying"), false);
	
	/**
	 * The idle {@link Sprite}.
	 */
	private static Sprite idle = new Sprite(
			DecoratedEntityEngine.getTexture("bomberIdle"), false);
	
	/**
	 * The left {@link Sprite}.
	 */
	private static Sprite left = new Sprite(
			DecoratedEntityEngine.getTexture("bomberLeft"), true);
	
	/**
	 * The right {@link Sprite}.
	 */
	private static Sprite right = new Sprite(
			DecoratedEntityEngine.getTexture("bomberRight"), true);
	
	/**
	 * The up {@link Sprite}.
	 */
	private static Sprite up = new Sprite(
			DecoratedEntityEngine.getTexture("bomberUp"), true);
	
	/**
	 * The delegate {@link Sprite}.
	 */
	private Sprite currentSprite;
	
	/**
	 * The {@link DecoratedWorld}.
	 */
	private DecoratedWorld decoratedWorld;
	
	/**
	 * The die timer. Indicates how long the dying {@link Sprite} should be visible
	 */
	private int dieTimer = 0;
	
	/**
	 * The moves stack contains the keys pressed at the current update, prevents multiple keys
	 * overlapping.
	 */
	private Stack<Direction> moves;
	
	/**
	 * The {@link SpritePlayer} instance.
	 */
	private SpritePlayer sp;

	/**
	 * Instantiates a new {@link Bomber} decorator together with its logic entity.
	 * 
	 * @param d
	 *            the {@link DecoratedWorld}.
	 * @param x
	 *            the x screen position in pixels
	 * @param y
	 *            the y screen position in pixels
	 */
	public BomberDecorator(DecoratedWorld d, float x, float y)
	{
		super(d.getLogicWorld(), x, y);
		sp = new SpritePlayer();
		decoratedWorld = d;
		moves = new Stack<Direction>();
		currentSprite = idle;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Bomber#destroy()
	 */
	@Override
	public void destroy()
	{
		super.destroy();
		decoratedWorld.removeGfxObject(this);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Bomber#die()
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
	 * @see playn.core.Keyboard.Listener#onKeyDown(playn.core.Keyboard.Event)
	 */
	@Override
	public void onKeyDown(playn.core.Keyboard.Event event)
	{
		switch (event.key())
		{
		case SPACE:
			placeBomb();
			break;
		case LEFT:
			moves.push(Direction.LEFT);
			break;
		case UP:
			moves.push(Direction.UP);
			break;
		case RIGHT:
			moves.push(Direction.RIGHT);
			break;
		case DOWN:
			moves.push(Direction.DOWN);
			break;
		case ESCAPE:
			moves.clear();
		default:
			setIdle();
			break;
		}
	}

	/* (non-Javadoc)
	 * @see playn.core.Keyboard.Listener#onKeyTyped(playn.core.Keyboard.TypedEvent)
	 */
	@Override
	public void onKeyTyped(TypedEvent event)
	{}

	/* (non-Javadoc)
	 * @see playn.core.Keyboard.Listener#onKeyUp(playn.core.Keyboard.Event)
	 */
	@Override
	public void onKeyUp(playn.core.Keyboard.Event event)
	{
		switch (event.key())
		{
		case LEFT:
			moves.remove(Direction.LEFT);
			break;
		case UP:
			moves.remove(Direction.UP);
			break;
		case RIGHT:
			moves.remove(Direction.RIGHT);
			break;
		case DOWN:
			moves.remove(Direction.DOWN);
			break;
		default:
			break;
		}
		if (moves.isEmpty())
		{
			setIdle();
			return;
		}
	}

	/* (non-Javadoc)
	 * @see playn.core.Pointer.Listener#onPointerCancel(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerCancel(Event event)
	{}

	/* (non-Javadoc)
	 * @see playn.core.Pointer.Listener#onPointerDrag(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerDrag(Event event)
	{}

	/* (non-Javadoc)
	 * @see playn.core.Pointer.Listener#onPointerEnd(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerEnd(Event event)
	{}

	/* (non-Javadoc)
	 * @see playn.core.Pointer.Listener#onPointerStart(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerStart(Event event)
	{}

	/* (non-Javadoc)
	 * @see bomberman.core.graphics.GfxObject#paint(float)
	 */
	@Override
	public void paint(float alpha)
	{}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.DynamicPhysicsEntity#reset()
	 */
	@Override
	public void reset()
	{
		moves.clear();
		super.reset();
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Bomber#setBomb()
	 */
	@Override
	public Bomb placeBomb()
	{
		Bomb b = super.placeBomb();
		if (b != null)
		{
			decoratedWorld.addBomb(b);
			return b;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.DynamicPhysicsEntity#update(int)
	 */
	@Override
	public void update(int delta)
	{
		if (!moves.isEmpty()) move(moves.peek());
		switch (getState())
		{
		case UP:
			currentSprite = up;
			break;
		case DOWN:
			currentSprite = down;
			break;
		case LEFT:
			currentSprite = left;
			break;
		case RIGHT:
			currentSprite = right;
			break;
		case DYING:
			currentSprite = dying;
			break;
		case IDLE:
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
