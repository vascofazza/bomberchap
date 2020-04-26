package bomberman.core.ui;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Key;
import playn.core.Keyboard.TypedEvent;
import playn.core.Pointer.Event;
import playn.core.Surface;
import bomberman.core.BomberMan;
import bomberman.core.PropertiesManager;
import bomberman.core.graphics.LayerManager;

/**
 * The WinScreen class responsible of displaying the victory message. Allows the user to 
 * begin the next level or exit the game.
 * 
 * @author Federico Scozzafava
 */
public class WinScreen implements UIScreen
{

	/**
	 * The core class.
	 */
	private BomberMan bomberman;

	/**
	 * Indicates if the resources are ready.
	 */
	private boolean ready = false;

	/**
	 * The layer which contains the victory image.
	 */
	private ImageLayer winLayer;

	/**
	 * The victory overlay image.
	 */
	private Image winOverlay;

	/**
	 * Creates a new overlay screen that handle the victory game status.
	 * 
	 * @param bomberman
	 *            The core class.
	 */
	public WinScreen(BomberMan bomberman)
	{
		this.bomberman = bomberman;
	}

	/* (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#drawSurface(playn.core.Surface)
	 */
	@Override
	public void drawSurface(Surface surface)
	{}

	/* (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#hide()
	 */
	@Override
	public void hide()
	{
		LayerManager.overlay().remove(winLayer);
	}

	/* (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#loadAssets()
	 */
	@Override
	public void loadAssets()
	{
		winOverlay = assets().getImage(
				PropertiesManager.getParameter("winScreenOverlay"));
		winLayer = graphics().createImageLayer(winOverlay);
		winLayer.setTranslation(104, 122);
		ready = true;
	}

	/* (non-Javadoc)
	 * @see playn.core.Keyboard.Listener#onKeyDown(playn.core.Keyboard.Event)
	 */
	@Override
	public void onKeyDown(playn.core.Keyboard.Event event)
	{
		if (event.key() == Key.SPACE) bomberman.startNewRound(false);
		if (event.key() == Key.ENTER) bomberman.start();
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
	{}

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
	 * @see bobmerman.core.ui.UIScreen#paint(float)
	 */
	@Override
	public void paint(float alpha)
	{}

	/* (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#show()
	 */
	@Override
	public void show()
	{
		if (!ready) throw new RuntimeException("assets not loaded");
		LayerManager.overlay().add(winLayer);
	}

	/* (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#update(int)
	 */
	@Override
	public void update(int delta)
	{}

}
