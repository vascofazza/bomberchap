package bomberman.core.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import bomberman.core.logic.enemy.Enemy;

/**
 * The class for managing the interaction of {@link Entity} instances via the.
 * {@code Box2d} physics engine. This class provides thread-safe access for
 * adding and removing objects during game play and also handles drawing all of
 * the objects on screen.
 * <p>
 * This class is partially based on the playn-peaphysics example (Copyright 2011
 * The PlayN Authors), which is licensed under the Apache License, Version 2.0.
 */
public class EntityEngine implements ContactListener
{

	/**
	 * The singleton pattern.
	 */
	private static EntityEngine instance;

	/**
	 * Gets the single instance of EntityEngine.
	 * 
	 * @return single instance of EntityEngine
	 */
	public static EntityEngine getInstance()
	{
		if (instance == null) instance = new EntityEngine();
		return instance;
	}

	/**
	 * A mapping from the {@code Box2D} {@link Body} to its corresponding {@link PhysicsEntity} game object.
	 */
	private final Map<Body, PhysicsEntity> bodyEntityLUT = new HashMap<Body, PhysicsEntity>();

	/**
	 * The set of objects that came in contact with each other during the call
	 * to {@link #update(int)}.
	 */
	private final Stack<Contact> contacts = new Stack<Contact>();

	/**
	 * The list of entities being managed by this engine.
	 */
	private final List<Entity> entities = new ArrayList<Entity>();

	/**
	 * The entities that will be added to the engine during the next call to.
	 * {@link #update(int)}
	 */
	private final Stack<Entity> entitiesToAdd = new Stack<Entity>();

	/**
	 * The entities that will be removed from the engine during the next call
	 * to. {@link #update(int)}
	 */
	private final Stack<Entity> entitiesToRemove = new Stack<Entity>();

	/**
	 * The set of objects that released the contact with each other during the
	 * call to {@link #update(int)}.
	 */
	private final Stack<Contact> release = new Stack<Contact>();

	/**
	 * {@code Box2d} object containing physics world
	 */
	private final World world;

	/**
	 * Creates a new {@link EntityEngine} that manages the {@code Box2d} physical world and
	 * physical state of all objects in the game, wrapping most of the logic for interacting
	 * with {@code Box2D}.
	 */
	private EntityEngine()
	{
		// Create the physics world that will handle all the target collisions
		// In 2d bomberMan game there is no gravity
		world = new World(new Vec2(0, 0));
		world.setWarmStarting(true);
		world.setAutoClearForces(true);
		world.setContactListener(this);
	}

	/**
	 * Adds the provided {@code Entity} to the list entities managed by this
	 * engine.
	 * 
	 * @param entity
	 *            the entity
	 */
	public void add(Entity entity)
	{
		entitiesToAdd.push(entity);
	}

	/**
	 * Called when {@code Box2d} finishes recording the contact between two {@link DynamicPhysicsEntity}.
	 * 
	 * @param contact
	 *            the contact {@link Body} instances, records the contact in
	 *            this instance's list of contacts to process during
	 *            {@link #update(int)}.
	 */
	@Override
	public void beginContact(Contact contact)
	{
		contacts.push(contact);
	}

	/**
	 * Clear the {@code Box2D} world removing all bodies and fixtures contained in it
	 * (including the ground bodies}.
	 */
	public void clearWorld()
	{
		Body temp = world.getBodyList();
		while (temp != null)
		{
			world.destroyBody(temp);
			temp = temp.getNext();
		}
	}

	/**
	 * Adds the entity o the list of entities and optionally registers the
	 * entity with the physics engine. This method is private, as it is only
	 * called during the {@link #update(int)} method to ensure a consistent
	 * state where entities are not added during painting.
	 * 
	 * @param entity
	 *            the entity
	 */
	private void doAdd(Entity entity)
	{
		entities.add(entity);
		if (entity instanceof PhysicsEntity)
		{
			PhysicsEntity physicsEntity = (PhysicsEntity) entity;
			bodyEntityLUT.put(physicsEntity.getBody(), physicsEntity);
		}
	}

	/**
	 * Removes the {@code Entity} from the list of entities managed by this
	 * engine, unregistering it with the physics engine if necessary. This
	 * method is private to ensure that entities are only removed in a
	 * consistent manner at once.
	 * 
	 * @param entity
	 *            the entity
	 */
	private void doRemove(Entity entity)
	{
		entities.remove(entity);
		if (entity instanceof PhysicsEntity)
		{
			PhysicsEntity physicsEntity = (PhysicsEntity) entity;
			Body body = physicsEntity.getBody();
			bodyEntityLUT.remove(body);
			if (body != null)
				world.destroyBody(body);
		}
	}

	/**
	 * Called when {@code Box2d} finishes recording the contact release between two {@link DynamicPhysicsEntity}.
	 * 
	 * @param contact
	 *            the contact {@link Body} instances, records the contact in
	 *            this instance's list of contacts to process during
	 *            {@link #update(int)}.
	 */
	@Override
	public void endContact(Contact contact)
	{
		// Record this contact so that we can properly handle the contact release
		// outside the physics loop
		release.push(contact);
	}

	/**
	 * Gets the bombs in this engine.
	 * 
	 * @return the bombs
	 */
	public List<Bomb> getBombs()
	{
		List<Bomb> res = new ArrayList<Bomb>();
		for (Entity x : entities)
			if (x instanceof Bomb) res.add((Bomb) x);
		return res;
	}

	/**
	 * Gets the enemies in thi engine.
	 * 
	 * @return the enemies
	 */
	public List<Enemy> getEnemies()
	{
		List<Enemy> res = new ArrayList<Enemy>();
		for (Entity x : entities)
			if (x instanceof Enemy) res.add((Enemy) x);
		return res;
	}

	/**
	 * Returns the {@code Box2d} {@link World} in which objects interact. This method
	 * is intended only for entities that need to initialize their state later
	 * and then add themselves to the world.
	 * 
	 * @return the {@link World}
	 * @see DynamicPhysicsEntity
	 */
	public World getWorld()
	{
		return world;
	}

	// Box2d's post solve
	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#postSolve(org.jbox2d.dynamics.contacts.Contact, org.jbox2d.callbacks.ContactImpulse)
	 */
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse)
	{}

	// Box2d's pre solve
	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#preSolve(org.jbox2d.dynamics.contacts.Contact, org.jbox2d.collision.Manifold)
	 */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold)
	{}

	/**
	 * Handle physic engine contacts out of physics loop.
	 */
	private void processContacts()
	{
		while (!contacts.isEmpty())
		{
			Contact contact = contacts.pop();

			// Handle collision
			PhysicsEntity entityA = bodyEntityLUT
					.get(contact.m_fixtureA.m_body);
			PhysicsEntity entityB = bodyEntityLUT
					.get(contact.m_fixtureB.m_body);

			if (entityA != null && entityB != null)
			{
				if (entityA instanceof PhysicsEntity.HasContactListener)
					((PhysicsEntity.HasContactListener) entityA)
							.contact(entityB);

				if (entityB instanceof PhysicsEntity.HasContactListener)
					((PhysicsEntity.HasContactListener) entityB)
							.contact(entityA);
			}
		}
	}

	/**
	 * Handle physic engine contacts release out of physics loop.
	 */
	private void processReleases()
	{
		while (!release.isEmpty())
		{
			Contact contact = release.pop();

			// Handle collision
			PhysicsEntity entityA = bodyEntityLUT
					.get(contact.m_fixtureA.m_body);
			PhysicsEntity entityB = bodyEntityLUT
					.get(contact.m_fixtureB.m_body);

			if (entityA != null && entityB != null)
			{
				if (entityA instanceof PhysicsEntity.HasContactListener)
					((PhysicsEntity.HasContactListener) entityA)
							.release(entityB);

				if (entityB instanceof PhysicsEntity.HasContactListener)
					((PhysicsEntity.HasContactListener) entityB)
							.release(entityA);
			}
		}
	}

	/**
	 * Removes the {@code Entity} from the list of entities managed by this
	 * engine, unregistering it with the physics engine if necessary.
	 */
	public void remove(Entity entity)
	{
		entitiesToRemove.push(entity);
	}

	/**
	 * Removes all the entities managed by this engine, causing them to stop
	 * being displayed and interacting through the physics engine.
	 */
	public void removeAll()
	{
		while (entities.size() > 0)
		{
			Entity e = entities.get(0);
			// NOTE: this call to doRemove is safe because removeAll is only
			// called within an update loop when the round is over
			doRemove(e);
		}
	}

	/**
	 * Updates the state of the engine, adding and removing entities as
	 * necessary.
	 * 
	 * @param delta
	 *            the delta
	 */
	public void update(int delta)
	{
		for (Entity e : entities)
			e.update(delta);
		// Add all the new objects
		while (!entitiesToAdd.isEmpty())
		{
			Entity entity = entitiesToAdd.pop();
			doAdd(entity);
		}

		// Remove all the objects that need to be removed
		while (!entitiesToRemove.isEmpty())
		{
			Entity entity = entitiesToRemove.pop();
			doRemove(entity);
		}

		// The step delta is fixed so box2d isn't affected by frame rate
		world.step(0.033f, 6, 3);

		// Process all the objects that touched each other, which was discovered
		// by {@code Box2d} and then recorded in our contacts list
		processContacts();
		processReleases();
	}
}
