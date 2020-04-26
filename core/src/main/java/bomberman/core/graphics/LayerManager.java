package bomberman.core.graphics;

import static playn.core.PlayN.graphics;
import playn.core.GroupLayer;

/**
 * A class for managing the four groups of layers used in the game. The game
 * has four classes of layers: (1) the background, which consists of static
 * images, (2) the foreground which consists of all active entities,
 * (3) the overlay that occludes anything in the foreground and displays
 * gameplay information (scores, the timer, and so on), and (4) the immediate layer
 * that provides a GPU accelerated drawing method (mainly used for displaying {@link Sprite}s 
 * entities and animations). Objects in the game can
 * request the group of layers associated with each class and then insert their
 * layer into the appropriate group so that it is displayed in the correct
 * visual ordering.
 */
public class LayerManager
{

	/**
	 * The singleton pattern.
	 */
	private static LayerManager instance;

	/**
	 * The group for layers that are background material.
	 */
	private final GroupLayer backgroundLayer;

	/**
	 * The group for layers that are in the foreground (entities, targets).
	 */
	private final GroupLayer foregroundLayer;

	/**
	 * The group for layers that are part of static background.
	 */
	private final GroupLayer groundLayer;

	/**
	 * The group for layers that needs to be cleared and drawn every update.
	 */
	private final GroupLayer immediateLayer;

	/**
	 * The top-most group that displays information on top of all objects in the
	 * foreground.
	 */
	private final GroupLayer overlayLayer;

	/**
	 * Creates the {@code LayerManager} and initializes all the layer groups.
	 */
	private LayerManager()
	{
		backgroundLayer = graphics().createGroupLayer();
		groundLayer = graphics().createGroupLayer();
		foregroundLayer = graphics().createGroupLayer();
		immediateLayer = graphics().createGroupLayer();
		overlayLayer = graphics().createGroupLayer();
		// Add the layers in the order we want them displayed
		graphics().rootLayer().add(backgroundLayer);
		graphics().rootLayer().add(groundLayer);
		graphics().rootLayer().add(foregroundLayer);
		graphics().rootLayer().add(immediateLayer);
		graphics().rootLayer().add(overlayLayer);
	}

	/**
	 * Returns the group to which background layers should be added.
	 * 
	 * @return the group layer
	 */
	public static GroupLayer background()
	{
		return instance().backgroundLayer;
	}

	/**
	 * Clear all the layers.
	 */
	public static void clear()
	{
		instance().backgroundLayer.clear();
		instance().foregroundLayer.clear();
		instance().groundLayer.clear();
		instance().overlayLayer.clear();
	}

	/**
	 * Returns the group to which foreground layers should be added.
	 * 
	 * @return the group layer
	 */
	public static GroupLayer foreground()
	{
		return instance().foregroundLayer;
	}

	/**
	 * Returns the group to which static ground layers should be added.
	 * 
	 * @return the group layer
	 */
	public static GroupLayer ground()
	{
		return instance().groundLayer;
	}

	/**
	 * Returns the group to which {@link Sprite}s and animations should be added.
	 * 
	 * @return the group layer
	 */
	public static GroupLayer immediate()
	{
		return instance().immediateLayer;
	}

	/**
	 * Returns the only instance of the {@code LayerManager}.
	 * 
	 * @return the layer manager
	 */
	public static LayerManager instance()
	{
		if (instance == null) instance = new LayerManager();
		return instance;
	}

	/**
	 * Returns the group to which layers in the overlay should be added.
	 * 
	 * @return the group layer
	 */
	public static GroupLayer overlay()
	{
		return instance().overlayLayer;
	}

}
