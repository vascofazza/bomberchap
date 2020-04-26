package bomberman.core.logic;

import org.jbox2d.dynamics.Body;

import bomberman.core.util.Vector2;

/**
 * An interface for entities that have an associated {@link Body} for modeling
 * their physical interactions.
 */
public interface PhysicsEntity
{

	/**
	 * A marker interface for whether this physics entity listens for contact
	 * with other entities.
	 * 
	 * @see HasContactEvent
	 */
	public interface HasContactListener
	{

		/**
		 * Called when this entity contacts another {@link PhysicsEntity}
		 * instance.
		 * 
		 * @param other
		 *            the other {@link PhysicsEntity}
		 */
		public void contact(PhysicsEntity other);

		/**
		 * Called when this entity release the contact with another {@link PhysicsEntity}.
		 * 
		 * @param other
		 *            the other {@link PhysicsEntity}
		 */
		public void release(PhysicsEntity other);
	}

	/**
	 * Returns the body used in physics simulation for this entity.
	 * 
	 * @return the body
	 */
	public Body getBody();

	/**
	 * Gets the body dimensions in pixels.
	 * 
	 * @return the body dimensions in pixels
	 */
	public Vector2 getBodyDimensions();

	/**
	 * Gets the body position in {@code Box2d} scaled reference system.
	 * 
	 * @return the body position in pixels
	 */
	public Vector2 getBodyPosition();

	/**
	 * Gets the body real position (referred to {@code Box2d} scaled proportion system}.
	 * 
	 * @return the body scaled real position
	 */
	public Vector2 getBodyRealPosition();

	/**
	 * Gets the bottom center of the entity.
	 * 
	 * @return the bottom center in pixels
	 */
	public Vector2 getBottomCenter();

	/**
	 * Gets the entity center in pixels.
	 * 
	 * @return the center in pixels
	 */
	public Vector2 getCenter();

	/**
	 * Gets the entity width and height dimension (assuming square reference system}.
	 * 
	 * @return the dimension
	 */
	public float getDimension();

	/**
	 * Gets the position in pixels.
	 * 
	 * @return the position in pixels
	 */
	public Vector2 getPosition();

	/**
	 * Gets the position in {@code Box2d} scaled reference system.
	 * 
	 * @return the scaled position
	 */
	public Vector2 getScaledPosition();

	/**
	 * Sets the entity position by its center in pixels 
	 * (it also affects the {@code Box2d} {@link Body} position).
	 * 
	 * @param pos
	 *            the center in pixels
	 */
	public void setCenter(Vector2 pos);

	/**
	 * Sets the entity position in pixels 
	 * (it also affects the {@code Box2d} {@link Body} position).
	 * 
	 * @param v
	 *            the new position in pixels
	 */
	public void setPosition(Vector2 v);
}
