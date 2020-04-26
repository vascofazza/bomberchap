package bomberman.core.graphics;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.List;
import playn.core.Image;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Sound;
import playn.core.Surface;
import playn.core.SurfaceImage;
import bomberman.core.ui.UIScreen;
import bomberman.core.BomberMan;
import bomberman.core.PropertiesManager;
import bomberman.core.logic.Bomb;
import bomberman.core.logic.Block;
import bomberman.core.logic.Entity;
import bomberman.core.logic.BrickBlock;
import bomberman.core.logic.WallBlock;
import bomberman.core.logic.DynamicPhysicsEntity;
import bomberman.core.logic.FireEntity;
import bomberman.core.logic.LogicWorld;
import bomberman.core.logic.powerUp.PowerUp;
import bomberman.core.util.Constants;

/**
 * The DecoratedWorld class represents the graphics side of the {@link LogicWorld}. It instances 
 * all necessary decorators for those {@link Entity} that logic produces at runtime, manages
 * the graphics aspect and animations for the ground and all {@link Entity} through the 
 * {@link DecoratedEntityEngine}.
 * 
 * @author Federico Scozzafava
 * @see bomberman.core.logic.LogicWorld
 * @see http://en.wikipedia.org/wiki/Decorator_pattern
 */
public class DecoratedWorld extends LogicWorld
{
	
	/**
	 * The explosions sound.
	 */
	private Sound bomb;
	
	/**
	 * The game core class.
	 */
	private BomberMan bomberman;
	
	/**
	 * The {@link DecoratedEntityEngine}.
	 */
	private DecoratedEntityEngine decoratedEntityEngine;
	
	/**
	 * The list containing all {@link DynamicBlockDecorator} for the ground.
	 */
	private List<GfxObject> dynamicGroundDecorators;
	
	/**
	 * The layer which contains the {@link BrickBlock} ground graphics aspect.
	 */
	private Layer dynamicGroundLayer;
	
	/**
	 * Indicates if the resources are ready.
	 */
	private boolean ready = false;
	
	/**
	 * The image associated to the {@link WallBlock}.
	 */
	private Image staticGroundImage;
	
	/**
	 * The layer which contains the {@link WallBlock} ground graphics aspect.
	 */
	private Layer staticGroundLayer;
	
	/**
	 * The {@code SurfaceImage} on which {@link DynamicBlockDecorator} will be drawn.
	 */
	private SurfaceImage surfaceDynamic;
	
	/**
	 * The {@code SurfaceImage} on which static ground ({@link WallBlock}) will be drawn.
	 */
	private SurfaceImage surfaceStatic;

	/**
	 * Instantiates a new {@link DecoratedWorld} together with {@link LogicWorld}.
	 * 
	 * @param bomberman
	 *            The game core class
	 * @param x
	 *            the {@link LogicWorld}'s width expressed in {@link Block}s
	 * @param y
	 *            the {@link LogicWorld}'s height expressed in {@link Block}s
	 */
	public DecoratedWorld(BomberMan bomberman, int x, int y)
	{
		super(x, y, Constants.FIXED_SQUARE_SIZE);
		this.bomberman = bomberman;
		decoratedEntityEngine = DecoratedEntityEngine.getInstance();
		staticGroundLayer = graphics().createGroupLayer();
	}

	/**
	 * Adds a {@link Bomb} to the engine. Called at runtime by the logic when a bomb is placed.
	 * @param b The {@link Bomb}
	 */
	public void addBomb(Bomb b)
	{
		BombDecorator bd = new BombDecorator(b, this);
		addGfxObject(bd);
	}
	

	/**
	 * Adds the {@link GfxObject} the {@link DecoratedEntityEngine}.
	 * 
	 * @param o
	 *            the {@link GfxObject}
	 */
	public void addGfxObject(GfxObject o)
	{
		decoratedEntityEngine.add(o);
	}

	/**
	 * Draw the static ground.
	 */
	private void drawStaticGround()
	{
		surfaceStatic = graphics().createSurface(graphics().width(),
				graphics().height());
		staticGroundLayer = graphics().createImageLayer(surfaceStatic);
		for (WallBlock x : getStaticGround())
			surfaceStatic.surface().drawImage(staticGroundImage,
					x.getPosition().getX(), x.getPosition().getY());
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

		decoratedEntityEngine.drawSurface(surface);
	}

	/**
	 * Gets the logic world.
	 * 
	 * @return the logic world
	 */
	public LogicWorld getLogicWorld()
	{
		return this;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.LogicWorld#handleExplosion(bomberman.core.logic.Bomb)
	 */
	@Override
	protected List<DynamicPhysicsEntity> handleExplosion(Bomb b)
	{
		bomb.play();
		List<DynamicPhysicsEntity> res = super.handleExplosion(b);
		for (DynamicPhysicsEntity f : res)
			if (f instanceof FireEntity)
				addGfxObject(new FireDecorator((FireEntity) f, this));
			else if (f instanceof PowerUp)
				addGfxObject(new PowerUpDecorator(this, (PowerUp) f));
		updateDynamicGround();
		return res;
	}

	/**
	 * Performs some graphics actions when the related {@link bomberman.core.ui.UIScreen} is being hidden.
	 */
	public void hide()
	{
		surfaceDynamic.destroy();
		surfaceStatic.destroy();
	}

	/**
	 * Initializes the dynamic ground.
	 * 
	 * @return the list
	 */
	private List<GfxObject> initDynamicGround()
	{
		List<GfxObject> res = new ArrayList<GfxObject>();
		for (BrickBlock c : getDynamicGround())
			res.add(new DynamicBlockDecorator(this, c));
		surfaceDynamic = graphics().createSurface(graphics().width(),
				graphics().height());
		dynamicGroundLayer = graphics().createImageLayer(surfaceDynamic);
		return res;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.LogicWorld#initializeGame(int, int)
	 */
	@Override
	public void initializeGame(int blocks, int time)
	{
		decoratedEntityEngine.clearAll();
		super.initializeGame(blocks, time);
		dynamicGroundDecorators = initDynamicGround();
	}

	/**
	 * Loads the ground assets and initializes the {@link DecoratedEntityEngine} resources.
	 */
	public void loadAssets()
	{
		if (ready) return;
		bomb = assets().getSound(PropertiesManager.getParameter("bombSound"));
		bomb.prepare();
		staticGroundImage = assets().getImageSync(
				PropertiesManager.getParameter("staticGroundImage"));
		decoratedEntityEngine.loadAssets();
		ready = true;
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.LogicWorld#lose()
	 */
	@Override
	public void lose()
	{
		bomberman.lose();
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
		decoratedEntityEngine.paint(alpha);
	}

	/**
	 * Removes the {@link GfxObject} to the associate {@link DecoratedEntityEngine}.
	 * 
	 * @param o
	 *            the {@link GfxObject}
	 */
	public void removeGfxObject(GfxObject o)
	{
		decoratedEntityEngine.remove(o);
	}

	/**
	 * Performs some graphics actions when the related {@link UIScreen} is being showed.
	 */
	public void show()
	{
		drawStaticGround();
		updateDynamicGround();
		LayerManager.ground().add(staticGroundLayer);
		LayerManager.ground().add(dynamicGroundLayer);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.LogicWorld#update(int)
	 */
	@Override
	public void update(int delta)
	{
		super.update(delta);
		decoratedEntityEngine.update(delta);
	}

	/**
	 * Update the dynamic ground. Called by {@link #handleExplosion} method, checks for 
	 * updated and destroyed {@link Block}s
	 */
	private void updateDynamicGround()
	{
		List<GfxObject> newList = new ArrayList<GfxObject>(
				dynamicGroundDecorators);
		List<BrickBlock> current = getDynamicGround();
		dynamicGroundDecorators.retainAll(current);
		newList.removeAll(current);
		for (GfxObject b : newList)
			addGfxObject(b);
		surfaceDynamic.surface().clear();
		for (GfxObject x : dynamicGroundDecorators)
			x.drawSurface(surfaceDynamic.surface());
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.LogicWorld#win()
	 */
	@Override
	public void win()
	{
		bomberman.win();
	}
}
