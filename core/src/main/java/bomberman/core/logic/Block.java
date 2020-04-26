package bomberman.core.logic;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import bomberman.core.util.Constants;
import bomberman.core.util.Vector2;

/**
 * The Block class defines an abstract piece of world's ground. In {@code Box2D}'s world
 * is represented with a static {@link Body}.
 * 
 * @author Federico Scozzafava
 */
public abstract class Block implements PhysicsEntity
{

	/**
	 * The {@link Bomb} contained in this {@link Block}.
	 */
	private Bomb b;
	
	/**
	 * The {@code box2d} {@link Body}.
	 */
	private Body body;
	
	/**
	 * The {@code box2d} scale factor.
	 */
	private float box2dScale = Constants.BOX2D_WORLD_SCALE;
	
	/**
	 * The {@link LogicWorld}.
	 */
	private LogicWorld logicWorld;
	
	/**
	 * The dimensions.
	 */
	private float x, y, width, heigth;

	/**
	 * Instantiates a new block.
	 * 
	 * @param x
	 *            the x position in pixels
	 * @param y
	 *            the y position in pixels
	 * @param width
	 *            the width in pixels
	 * @param height
	 *            the height in pixels
	 * @param nullBody
	 *            indicates if the {@link Block} has a physics {@code box2d} body. 
	 *            If true the {@link Body} is instanced.
	 * @param world
	 *            the {@link LogicWorld}
	 */
	public Block(float x, float y, float width, float height,
			boolean nullBody, LogicWorld world)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.heigth = height;
		logicWorld = world;
		if (!nullBody) createBody();
	}
	
	/**
	 * Instantiates a new block.
	 * 
	 * @param x
	 *            the x position in pixels
	 * @param y
	 *            the y position in pixels
	 * @param dimension
	 *            the dimension (width and height) in pixels
	 * @param nullBody
	 *            indicates if the {@link Block} has a physics {@code box2d} body. 
	 *            If true the {@link Body} is instanced.
	 * @param world
	 *            the {@link LogicWorld}
	 */
	public Block(float x, float y, float dimension, boolean nullBody, LogicWorld world)
	{
		this(x,y,dimension, dimension, nullBody, world);
	}

	/**
	 * Returns a boolean value that indicates if the specified {@link DynamicPhysicsEntity} is
	 * contained in this {@link Block}.
	 * 
	 * @param d
	 *            the {@link DynamicPhysicsEntity}
	 * @return true, if it's contained.
	 */
	public boolean contains(DynamicPhysicsEntity d)
	{
		return contains(d.getPosition())
				&& contains(d.getPosition().add(d.getBodyDimensions()));
	}
	
	/**
	 * Returns a boolean value that indicates if the {@link Vector2} components are
	 * contained in this {@link Block}.
	 * 
	 * @param v
	 *            the {@link Vector2}
	 * @return true, if the {@link Vector2} is contained.
	 */
	public boolean contains(Vector2 v)
	{
		return v.getX() >= x && v.getX() < x + width && v.getY() >= y
				&& v.getY() < y + heigth;
	}

	/**
	 * Instances the {@code box2d} {@link Body}.
	 */
	private void createBody()
	{
		BodyDef bd = new BodyDef();
		bd.type = BodyType.STATIC;
		bd.position.set(x / box2dScale, y / box2dScale);
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(width / 2 / box2dScale, heigth / 2 / box2dScale);
		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		body = logicWorld.getWorld().createBody(bd);
		body.createFixture(fd);
	}

	/**
	 * Destroys the {@link Block}. Depending on the block's type, it may return its content,
	 * another type of block, or itself.
	 * 
	 * @return another type of {@link Block} or itself.
	 */
	abstract public Block destroy();

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#getBody()
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
		return new Vector2(width, heigth);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#getBodyPosition()
	 */
	@Override
	public Vector2 getBodyPosition()
	{
		return new Vector2(body.getPosition()).mul(box2dScale);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#getBodyRealPosition()
	 */
	@Override
	public Vector2 getBodyRealPosition()
	{
		return new Vector2(body.getPosition());
	}

	/**
	 * Gets the bomb contained in this {@link Block}.
	 * 
	 * @return the bomb
	 */
	public Bomb getBomb()
	{
		return b;
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

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#getCenter()
	 */
	@Override
	public Vector2 getCenter()
	{
		return getBodyDimensions().div(2).add(getPosition());
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#getDimension()
	 */
	@Override
	public float getDimension()
	{
		return heigth;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#getPosition()
	 */
	@Override
	public Vector2 getPosition()
	{
		return new Vector2((int) x, (int) y);
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
	 * Removes the bomb contained in this {@link Block}.
	 */
	public void removeBomb()
	{
		b = null;
	}

	/**
	 * Sets the bomb in this {@link Block}.
	 * 
	 * @param b
	 *            the new bomb
	 */
	public void setBomb(Bomb b)
	{
		this.b = b;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#setCenter(bomberman.core.util.Vector2)
	 */
	@Override
	public void setCenter(Vector2 pos)
	{
		setPosition(pos.add(-getDimension() / 2f));
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.PhysicsEntity#setPosition(bomberman.core.util.Vector2)
	 */
	@Override
	public void setPosition(Vector2 vect)
	{
		x = vect.getX();
		y = vect.getY();
		body.setTransform(new Vec2(x / box2dScale, y / box2dScale), 0);
	}
}
