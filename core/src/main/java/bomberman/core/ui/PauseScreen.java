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
 * The PauseScreen class takes control when the game is paused. Allows the user to 
 * restart the level, resume or exit the game.
 * @author Federico Scozzafava
 */
public class PauseScreen implements UIScreen
{

	/**
	 * The core class.
	 */
	private BomberMan bomberman;

	/**
	 * The layer which contains the pause image.
	 */
	private ImageLayer pauseLayer;

	/**
	 * The pause image.
	 */
	private Image pauseOverlay;

	/**
	 * Indicates if the resources are ready.
	 */
	private boolean ready = false;

	/**
	 * Creates a new UI screen that manage the pause game status.
	 * 
	 * @param bomberman
	 */
	public PauseScreen(BomberMan bomberman)
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
		LayerManager.overlay().remove(pauseLayer);
	}

	/* (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#loadAssets()
	 */
	@Override
	public void loadAssets()
	{
		pauseOverlay = assets().getImage(
				PropertiesManager.getParameter("pauseScreenOverlay"));
		pauseLayer = graphics().createImageLayer(pauseOverlay);
		pauseLayer.setTranslation(104, 122);
		ready = true;
	}

	/* (non-Javadoc)
	 * @see playn.core.Keyboard.Listener#onKeyDown(playn.core.Keyboard.Event)
	 */
	@Override
	public void onKeyDown(playn.core.Keyboard.Event event)
	{
		if (event.key() == Key.ESCAPE) bomberman.resume();
		if (event.key() == Key.SPACE) bomberman.startNewRound(true);
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
		LayerManager.overlay().add(pauseLayer);
	}

	/* (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#update(int)
	 */
	@Override
	public void update(int delta)
	{}

}
