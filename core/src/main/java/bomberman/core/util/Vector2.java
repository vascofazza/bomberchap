package bomberman.core.util;

import org.jbox2d.common.Vec2;

/**
 * The Class Vector2 provides utility methods for vectors managing and conversion.
 */
public class Vector2
{

	/**
	 * The Constant Zero.
	 */
	public static final Vector2 Zero = new Vector2(0, 0);

	/**
	 * The x vector's component.
	 */
	private float x;
	
	/**
	 * The y vector's component.
	 */
	private float y;

	/**
	 * Instantiates a new {@link Vector2}.
	 * 
	 * @param x
	 *            the x component
	 * @param y
	 *            the y component
	 */
	public Vector2(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Instantiates a new {@link Vector2} boxing a {@code Box2d}'s {@link Vec2} type.
	 * 
	 * @param position
	 *            the position {@link Vec2}.
	 */
	public Vector2(Vec2 position)
	{
		this(position.x, position.y);
	}

	/**
	 * Adds the given value to the {@link Vector2}'s components.
	 * 
	 * @param a
	 *            the value
	 * @return the new {@link Vector2}
	 */
	public Vector2 add(float a)
	{
		return new Vector2(x + a, y + a);
	}

	/**
	 * Adds the given {@link Vector2} to the current one.
	 * 
	 * @param v
	 *            the {@link Vector2} to add
	 * @return the new {@link Vector2}
	 */
	public Vector2 add(Vector2 v)
	{
		return new Vector2(x + v.x, y + v.y);
	}

	/**
	 * Divides the current {@link Vector2}'s components by a value.
	 * 
	 * @param a
	 *            the value
	 * @return the new {@link Vector2}
	 */
	public Vector2 div(float a)
	{
		return new Vector2(x / a, y / a);
	}

	/**
	 * Divides the current {@link Vector2}'s by the given one.
	 * 
	 * @param v
	 *            the given {@link Vector2}
	 * @return the new {@link Vector2}
	 */
	public Vector2 div(Vector2 v)
	{
		return new Vector2(x / v.x, y / v.y);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object v)
	{
		return v instanceof Vector2 ? x == ((Vector2) v).x
				&& y == ((Vector2) v).y : false;
	}

	/**
	 * Gets the equivalent {@link Vec2}.
	 * 
	 * @return the {@link Vec2}
	 */
	public Vec2 getVec2()
	{
		return new Vec2(x, y);
	}

	/**
	 * Gets the x component.
	 * 
	 * @return the x
	 */
	public float getX()
	{
		return x;
	}

	/**
	 * Gets the y component.
	 * 
	 * @return the y
	 */
	public float getY()
	{
		return y;
	}

	/**
	 * Length squared.
	 * 
	 * @return the float
	 */
	public float lengthSquared()
	{
		return x * x + y * y;
	}

	/**
	 * Multiplies the current {@link Vector2}'s components by a value.
	 * 
	 * @param a
	 *            the value
	 * @return the new {@link Vector2}
	 */
	public Vector2 mul(float a)
	{
		return new Vector2(x * a, y * a);
	}

	/**
	 * Multiplies the current {@link Vector2}'s by the given one.
	 * 
	 * @param v
	 *            the given {@link Vector2}
	 * @return the new {@link Vector2}
	 */
	public Vector2 mul(Vector2 v)
	{
		return new Vector2(x * v.x, y * v.y);
	}

	/**
	 * Subtracts the given {@link Vector2} to the current one.
	 * 
	 * @param v
	 *            the given {@link Vector2}
	 * @return the new {@link Vector2}
	 */
	public Vector2 sub(Vector2 v)
	{
		return new Vector2(x - v.x, y - v.y);
	}

	/**
	 * Returns a new {@link Vector2} with integer rounded components.
	 * 
	 * @return the new integer {@link Vector2}
	 */
	public Vector2 toInt()
	{
		return new Vector2(Math.round(x), Math.round(y));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return x + " - " + y;
	}
}
