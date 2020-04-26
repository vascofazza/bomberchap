package bomberman.core.graphics;

import static playn.core.PlayN.assets;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import playn.core.Image;
import playn.core.PlayN;
import playn.core.Surface;
import bomberman.core.PropertiesManager;
import bomberman.core.logic.EntityEngine;
import bomberman.core.logic.Entity;
import bomberman.core.util.Constants;

/**
 * The DecoratedEntityEngine class manages the {@link GfxObject} acting in the.
 * {@link DecoratedWorld} the same as {@link EntityEngine} does.
 * 
 * @author Federico Scozzafava
 * @see bomberman.core.logic.EntityEngine
 * @see http://en.wikipedia.org/wiki/Decorator_pattern
 */
public class DecoratedEntityEngine
{

	/**
	 * The singleton pattern.
	 */
	private static DecoratedEntityEngine instance;

	/**
	 * The {@link GfxObject} entities list.
	 */
	private final LinkedList<GfxObject> gfxObjs = new LinkedList<GfxObject>();

	/**
	 * The entities that will be added to the engine during the next call to.
	 * {@link #update(int)}
	 */
	private final Stack<GfxObject> gfxToAdd = new Stack<GfxObject>();

	/**
	 * The entities that will be removed from the engine during the next call
	 * to. {@link #update(int)}
	 */
	private final Stack<GfxObject> gfxToRemove = new Stack<GfxObject>();

	/**
	 * The texture map used by {@link #getTexture(String)}.
	 */
	private final Map<String, Image> textureMap = new HashMap<String, Image>();

	/**
	 * Instantiates a new decorated entity engine.
	 */
	private DecoratedEntityEngine()
	{}

	/**
	 * Gets the single instance of DecoratedEntityEngine.
	 * 
	 * @return single instance of DecoratedEntityEngine
	 */
	public static DecoratedEntityEngine getInstance()
	{
		if (instance == null) instance = new DecoratedEntityEngine();
		return instance;
	}

	/**
	 * Gets the texture for those {@link Entity} that are instanced and
	 * decorated ad runtime.
	 * 
	 * @param name
	 *            the texture's name
	 * @return the texture
	 */
	public static Image getTexture(String name)
	{
		return getInstance().textureMap.get(name);
	}

	/**
	 * Adds the provided {@link GfxObject} to the list entities managed by this
	 * engine.
	 * 
	 * @param o
	 *            the {@link GfxObject}
	 */
	public void add(GfxObject o)
	{
		gfxToAdd.push(o);
	}

	/**
	 * Clear and resets the engine.
	 */
	public void clearAll()
	{
		gfxToAdd.clear();
		gfxToRemove.clear();
		gfxObjs.clear();
	}

	/**
	 * Adds the entities to this engine when the {@link #update(int)} is called,
	 * when it's secure to do this.
	 */
	private void doAdd()
	{
		while (!gfxToAdd.isEmpty())
			gfxObjs.addFirst(gfxToAdd.pop());
	}

	/**
	 * Removes the entities from this engine when the {@link #update(int)} is
	 * called, when it's secure to do this.
	 */
	private void doRemove()
	{
		while (!gfxToRemove.isEmpty())
			gfxObjs.remove(gfxToRemove.pop());
	}

	/**
	 * Perform the accelerated draw for each {@link GfxObject}. Delegated by
	 * 
	 * @param surface
	 *            the surface on which to draw {@link PlayN}
	 * @see bomberman.core.BomberMan
	 */
	public void drawSurface(Surface surface)
	{
		for (GfxObject x : gfxObjs)
			x.drawSurface(surface);
	}

	/**
	 * Loads the assets for each {@link GfxObject}.
	 */
	public void loadAssets()
	{
		String[] images = Constants.TEXTURES;
		for (String x : images)
			textureMap.put(x,
					assets().getImageSync(PropertiesManager.getParameter(x)));
	}

	/**
	 * Perform the Paint method for each {@link GfxObject}. Delegated by
	 * 
	 * @param alpha
	 *            the alpha parameter {@link PlayN}
	 * @see bomberman.core.BomberMan
	 */
	public void paint(float alpha)
	{
		for (GfxObject x : gfxObjs)
			x.paint(alpha);
	}

	/**
	 * Removes the {@code Entity} from the list of entities managed by this
	 * engine, unregistering it with the physics engine if necessary.
	 * 
	 * @param o
	 *            the {@link GfxObject}
	 */
	public void remove(GfxObject o)
	{
		gfxToRemove.push(o);
	}

	/**
	 * Removes all the entities managed by this engine, causing them to stop
	 * being displayed and interacting through the physics engine.
	 */
	public void removeAll()
	{
		for (GfxObject x : gfxObjs)
			gfxToRemove.push(x);
	}

	/**
	 * Updates the logic state of each {@link GfxObject}.
	 * 
	 * @param delta
	 *            the delta parameter
	 * @see bomberman.core.BomberMan
	 */
	public void update(int delta)
	{
		doAdd();
		doRemove();
		for (GfxObject x : gfxObjs)
			x.update(delta);
	}

}
