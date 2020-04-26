package bomberman.core.logic;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import bomberman.core.util.Constants;
import bomberman.core.util.Vector2;

/**
 * The subclass of {@link Entity} that handles interactions with the {@code Box2d}
 * physics engine and updates the state of the entity as time passes.
 * 
 * @see LogicWorld
 */
public abstract class DynamicPhysicsEntity extends Entity implements
		PhysicsEntity
{

	/**
	 * The {@link LogicWorld}
	 */
	private LogicWorld logicWorld;
	/**
	 * The physics body associated with the entity that is used the physics
	 * simulating for calculating motion and collisions.
	 */
	private Body body;
	
	/**
	 * The box2d scale factor.
	 */
	private float box2dScale = Constants.BOX2D_WORLD_SCALE;
	
	/**
	 * The {@link Body} height.
	 */
	private float height;
	
	/**
	 * The {@code Box2d} parameter that indicates if the {@link Body} associated to
	 * this {@link DynamicPhysicsEntity} should interact and be affected by colliding
	 * with other entities and static bodies. If true, although the body's physics is not 
	 * affected by the world, the relative {@link PhysicsEntity.ContactListener} is still listening for
	 * Contacts.
	 * 
	 * @see http://www.box2d.org/manual.html#_Toc258082972
	 */
	private boolean isSensor;
	
	/**
	 * The {@link Body} speed.
	 */
	private float speed;
	
	/**
	 * The {@link Body} width.
	 */
	private float width;

	/**
	 * Instantiates a new {@link DynamicPhysicsEntity}
	 * 
	 * @param logicWorld
	 *            the {@link LogicWorld} in which the entity acts
	 * @param x
	 *            the x position in pixels
	 * @param y
	 *            the y position in pixels
	 * @param lives
	 *            the entity's lives
	 * @param speed
	 *            the entity's speed
	 * @param isSensor
	 *            The {@code Box2d} parameter that indicates if the {@link Body} associated to
	 * this {@link DynamicPhysicsEntity} should interact and be affected by colliding
	 * with other entities and static bodies. If true, although the body's physics is not 
	 * affected by the world, the relative {@link PhysicsEntity.ContactListener} <b>is still listening 
	 * for {@code Contacts}</b>.
	 * 
	 * @see http://www.box2d.org/manual.html#_Toc258082972
	 */
	public DynamicPhysicsEntity(LogicWorld logicWorld, float x, float y,
			int lives, float speed, boolean isSensor)
	{
		super(logicWorld, x, y, lives);
		this.logicWorld = logicWorld;
		this.speed = speed;
		this.isSensor = isSensor;
		initBody();
	}

	/**
	 * Instantiates a new {@link DynamicPhysicsEntity}
	 * 
	 * @param logicWorld
	 *            the {@link LogicWorld}
	 * @param pos
	 *            the position of the entity in pixels, expressed with a {@link Vector2}
	 * @param lives
	 *            the entity's lives
	 * @param speed
	 *            the entity's speed
	 * @param isSensor
	 *            The {@code Box2d} parameter that indicates if the {@link Body} associated to
	 * this {@link DynamicPhysicsEntity} should interact and be affected by colliding
	 * with other entities and static bodies. If true, although the body's physics is not 
	 * affected by the world, the relative {@link PhysicsEntity.ContactListener} <b>is still listening 
	 * for {@code Contacts}</b>.
	 *            
	 * @see http://www.box2d.org/manual.html#_Toc258082972
	 */

	public DynamicPhysicsEntity(LogicWorld logicWorld, Vector2 pos, int lives,
			float speed, boolean isSensor)
	{
		this(logicWorld, pos.getX(), pos.getY(), lives, speed, isSensor);
	}

	/**
	 * Align the position of the entity with the relative {@link Block} in grid. Useful 
	 * in managing entities that moves independently using a {@link MoveStrategy}, prevents
	 * unwanted collisions and bad movements.
	 * 
	 * @param c
	 *            the {@link Block} to which align to
	 */
	public void alignPosition(Block c)
	{
		Vector2 maxDistance = c.getBodyDimensions().sub(getBodyDimensions());
		Vector2 midDistance = maxDistance.div(2f);
		float x = getPosition().getX() < c.getPosition().add(midDistance)
				.getX() ? c.getPosition().getX() : c.getPosition()
				.add(maxDistance).getX();
		float y = getPosition().getY() < c.getPosition().add(midDistance)
				.getY() ? c.getPosition().getY() : c.getPosition()
				.add(maxDistance).getY();
		setPosition(x, y);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Body getBody()
	{
		return body;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#getBodyDimensions()
	 */
	@Override
	public Vector2 getBodyDimensions()
	{
		return new Vector2(width, height);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#getBodyPosition()
	 */
	@Override
	public Vector2 getBodyPosition()
	{
		return new Vector2(body.getPosition()).mul(box2dScale).toInt();
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#getBodyRealPosition()
	 */
	@Override
	public Vector2 getBodyRealPosition()
	{
		return new Vector2(body.getPosition());
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#getBottomCenter()
	 */
	@Override
	public Vector2 getBottomCenter()
	{
		return getPosition().add(
				new Vector2(getDimension() / 2f, getDimension()));
	}

	// @Override
	// public Vector2 getBodyCenter() { return getPosition().add(new
	// Vector2(width/2f, height/2f));}
	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#getCenter()
	 */
	@Override
	public Vector2 getCenter()
	{
		return getPosition().add(getDimension() / 2f);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#getDimension()
	 */
	@Override
	public float getDimension()
	{
		return Constants.FIXED_SQUARE_SIZE;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#getScaledPosition()
	 */
	@Override
	public Vector2 getScaledPosition()
	{
		return getPosition().div(box2dScale);
	}

	/**
	 * Initialize the {@code Box2d} body.
	 */
	private void initBody()
	{
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.position.set(getScaledPosition().getVec2());
		CircleShape shape = new CircleShape();
		shape.setRadius(Constants.FIXED_SQUARE_SIZE / 2.5f / box2dScale);
		height = width = shape.getRadius() * 2 * box2dScale;
		FixtureDef fd = new FixtureDef();
		fd.friction = 0;
		fd.isSensor = isSensor;
		fd.shape = shape;
		body = logicWorld.getWorld().createBody(bd);
		body.createFixture(fd);
	}
	
	/**
	 * Increase the speed.
	 * 
	 * @param increment
	 *            the speed increment
	 */
	public void increaseSpeed(float increment)
	{
		if (speed == Constants.BOMBERMAN_MAX_SPEED || increment < 0
				&& speed == Constants.BOMBERMAN_MIN_SPEED) return;
		speed += increment;
	}

	/**
	 * Moves the {@link Body} by setting its linear velocity.
	 * 
	 * @param d
	 *            the {@link Direction} in which to move
	 */
	public void move(Direction d)
	{
		float x = 0;
		float y = 0;
		switch (d)
		{
		case UP:
			y = -speed * Constants.IMPULSE_ACTION;
			setState(State.UP);
			break;
		case DOWN:
			y = speed * Constants.IMPULSE_ACTION;
			setState(State.DOWN);
			break;
		case LEFT:
			x = -speed * Constants.IMPULSE_ACTION;
			setState(State.LEFT);
			break;
		case RIGHT:
			x = speed * Constants.IMPULSE_ACTION;
			setState(State.RIGHT);
			break;
		}
		getBody().setLinearVelocity(new Vec2(x, y));
	}

	/**
	 * Moves the {@link Body} by setting its linear velocity.
	 * 
	 * @param x
	 *            the x position where to move in pixels
	 * @param y
	 *            the y position where to move in pixels
	 */
	public void move(float x, float y)
	{
		move(new Vector2(x, y));
	}

	/**
	 * Moves the {@link Body} by setting its linear velocity.
	 * 
	 * @param v
	 *            the {@link Vector2} representing the position where to move
	 */
	public void move(Vector2 v)
	{
		Vector2 actualPos = getPosition();
		float x = 0;
		float y = 0;
		if (actualPos.getX() != v.getX()) if (actualPos.getX() > v.getX())
		{
			x = -speed * Constants.IMPULSE_ACTION;
			setState(State.UP);
		}
		else
		{
			x = speed * Constants.IMPULSE_ACTION;
			setState(State.RIGHT);
		}
		if (actualPos.getY() != v.getY()) if (actualPos.getY() > v.getY())
		{
			y = -speed * Constants.IMPULSE_ACTION;
			setState(State.UP);
		}
		else
		{
			y = speed * Constants.IMPULSE_ACTION;
			setState(State.DOWN);
		}
		getBody().setLinearVelocity(new Vec2(x, y));
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.Entity#reset()
	 */
	@Override
	public void reset()
	{
		super.reset();
		if (body.getFixtureList() == null) initBody();
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#setCenter(bomberman.core.util.Vector2)
	 */
	@Override
	public void setCenter(Vector2 pos)
	{
		setPosition(pos.getX() - getDimension() / 2f, pos.getY()
				- getDimension() / 2f);
	}

	/**
	 * Sets the position of this entity.
	 * 
	 * @param x
	 *            the x position in pixels
	 * @param y
	 *            the y position in pixels
	 */
	@Override
	public void setPosition(float x, float y)
	{
		super.setPosition(x, y);
		getBody().setTransform(getScaledPosition().getVec2(), 0);
	}

	/**
	 * Updates the state (position and state) of the entity.
	 * 
	 * @param delta
	 *            the delta parameter
	 */
	@Override
	public void update(int delta)
	{
		super.setPosition(getBodyPosition());
		if (getState() == State.IDLE)
			getBody().setLinearVelocity(new Vec2(0, 0)); // IMPEDISCE LO
															// SCIVOLAMENTO
	}
}
