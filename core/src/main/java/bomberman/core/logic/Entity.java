package bomberman.core.logic;

import bomberman.core.util.Vector2;

/**
 * The Entity class represent a base class of logic objects acting in a {@link LogicWorld}.
 * An entity is represented by its {@link State}, position and number of lives. A logic entity
 * provides an {@link #update(int)} method that update its state at each frame as time passes.
 * 
 * @author Federico Scozzafava
 */
public abstract class Entity
{

	/**
	 * The entity's lives.
	 */
	private int lives;
	
	/**
	 * The {@link LogicWorld} in which {@link Entity} acts.
	 */
	private LogicWorld logicWorld;
	
	/**
	 * The original x position in pixels 
	 * (used to reset the {@link Entity} at its original position).
	 */
	private float originalX;
	
	/**
	 * The original y position in pixels 
	 * (used to reset the {@link Entity} at its original position).
	 */
	private float originalY;
	
	/**
	 * The current state of the {@link Entity}.
	 */
	private State state = State.IDLE;
	
	/**
	 * The current x position in pixels.
	 */
	private float x;
	
	/**
	 * The current y position in pixels.
	 */
	private float y;

	/**
	 * Instantiates a new {@link Entity}.
	 * 
	 * @param l
	 *            the {@link LogicWorld} in which the entity acts
	 * @param x
	 *            the x position in pixels
	 * @param y
	 *            the y position in pixels
	 * @param lives
	 *            the number of lives
	 */
	public Entity(LogicWorld l, float x, float y, int lives)
	{
		this.x = originalX = x;
		this.y = originalY = y;
		this.lives = lives;
		logicWorld = l;
		l.addEntity(this);
	}

	/**
	 * Instantiates a new {@link Entity}.
	 * 
	 * @param l
	 *            the {@link LogicWorld}
	 * @param pos
	 *            the position in pixels expressed through a {@link Vector2}
	 * @param lives
	 *            the number of lives
	 */
	public Entity(LogicWorld l, Vector2 pos, int lives)
	{
		this(l, pos.getX(), pos.getY(), lives);
	}

	/**
	 * Destroys instantly the {@link Entity} and removes it from the {@link EntityEngine}.
	 */
	public void destroy()
	{
		state = State.DYING;
		logicWorld.removeEntity(this);
	}

	/**
	 * The {@link Entity} dies, its state and position are reseted.
	 * If the number of {@link #lives} go below 1 the entity is destroyed.
	 */
	public void die()
	{
		state = State.DYING;
		if (--lives == 0)
		{
			destroy();
			return;
		}
		reset();
	}

	/**
	 * Gets the lives number.
	 * 
	 * @return the lives number
	 */
	public int getLives()
	{
		return lives;
	}
	
	/**
	 * Gets the {@link LogicWorld}
	 * 
	 * @return the {@link LogicWorld}
	 */
	public LogicWorld getLogicWorld()
	{
		return logicWorld;
	}

	/**
	 * Gets the current position.
	 * 
	 * @return the position through a {@link Vector2}.
	 */
	public Vector2 getPosition()
	{
		return new Vector2(x, y);
	}

	/**
	 * Gets the current {@link State} of the {@link Entity}.
	 * 
	 * @return the {@link State}
	 */
	public State getState()
	{
		return state;
	}

	/**
	 * Checks if the {@link Entity} is alive.
	 * 
	 * @return true, if is alive
	 */
	public boolean isAlive()
	{
		return lives > 0;
	}

	/**
	 * Resets the state and original position of the {@link Entity}.
	 */
	public void reset()
	{
		setPosition(originalX, originalY);
		setIdle();
	}

	/**
	 * Sets the {@link Entity} to idle status.
	 */
	public void setIdle()
	{
		state = State.IDLE;
	}

	/**
	 * Sets the lives number.
	 * 
	 * @param x
	 *            the new lives number
	 */
	public void setLives(int x)
	{
		lives = x;
	}

	/**
	 * Sets the position.
	 * 
	 * @param x
	 *            the x position in pixels
	 * @param y
	 *            the y position in pixels
	 */
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the position.
	 * 
	 * @param v
	 *            the position in pixels expressed through a {@link Vector2}
	 */
	public void setPosition(Vector2 v)
	{
		setPosition(v.getX(), v.getY());
	}

	/**
	 * Sets the {@link State}.
	 * 
	 * @param s
	 *            the new state
	 */
	public void setState(State s)
	{
		state = s;
	}

	/**
	 * Updates the state of the {@link Entity}.
	 * 
	 * @param delta
	 *            the time delta parameter
	 */
	abstract public void update(int delta);
}
