package bomberman.core.ui;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Keyboard.TypedEvent;
import playn.core.Pointer.Event;
import playn.core.Surface;
import bomberman.core.BomberMan;
import bomberman.core.PropertiesManager;
import bomberman.core.graphics.LayerManager;

/**
 * The LoadScreen class is responsible of loading all screen's resources,
 * while providing an interface.
 *  
 * @author Federico Scozzafava
 */
public class LoadScreen implements UIScreen
{

	/**
	 * The core class.
	 */
	private BomberMan bomberman;

	/**
	 * The layer which contains the loading image.
	 */
	private ImageLayer loadingLayer;

	/**
	 * The loading image.
	 */
	private Image loadingOverlay;

	/**
	 * Indicates if the resources are ready.
	 */
	private boolean ready = false;

	/**
	 * The screens to load
	 */
	private UIScreen[] screens;

	/**
	 * The LoadScreen class is responsible of loading all screen's resources,
	 * while providing an interface.
	 * 
	 * @param bomberman
	 *            The core class.
	 * @param screens
	 *            The screen to load.
	 */
	public LoadScreen(BomberMan bomberman, UIScreen... screens)
	{
		this.bomberman = bomberman;
		this.screens = screens;
		loadingOverlay = assets().getImageSync(
				PropertiesManager.getParameter("loadingScreen"));
		loadingLayer = graphics().createImageLayer(loadingOverlay);
	}

	/*
	 * (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#drawSurface(playn.core.Surface)
	 */
	@Override
	public void drawSurface(Surface surface)
	{}

	/*
	 * (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#hide()
	 */
	@Override
	public void hide()
	{}

	/*
	 * (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#loadAssets()
	 */
	@Override
	public void loadAssets()
	{}

	/*
	 * (non-Javadoc)
	 * @see playn.core.Keyboard.Listener#onKeyDown(playn.core.Keyboard.Event)
	 */
	@Override
	public void onKeyDown(playn.core.Keyboard.Event event)
	{}

	/*
	 * (non-Javadoc)
	 * @see
	 * playn.core.Keyboard.Listener#onKeyTyped(playn.core.Keyboard.TypedEvent)
	 */
	@Override
	public void onKeyTyped(TypedEvent event)
	{}

	/*
	 * (non-Javadoc)
	 * @see playn.core.Keyboard.Listener#onKeyUp(playn.core.Keyboard.Event)
	 */
	@Override
	public void onKeyUp(playn.core.Keyboard.Event event)
	{}

	/*
	 * (non-Javadoc)
	 * @see
	 * playn.core.Pointer.Listener#onPointerCancel(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerCancel(Event event)
	{}

	/*
	 * (non-Javadoc)
	 * @see playn.core.Pointer.Listener#onPointerDrag(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerDrag(Event event)
	{}

	/*
	 * (non-Javadoc)
	 * @see playn.core.Pointer.Listener#onPointerEnd(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerEnd(Event event)
	{}

	/*
	 * (non-Javadoc)
	 * @see playn.core.Pointer.Listener#onPointerStart(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerStart(Event event)
	{}

	/*
	 * (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#paint(float)
	 */
	@Override
	public void paint(float alpha)
	{}

	/*
	 * (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#show()
	 */
	@Override
	public void show()
	{
		LayerManager.overlay().add(loadingLayer);
	}

	/*
	 * (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#update(int)
	 */
	@Override
	public void update(int delta)
	{
		if (ready)
		{

			for (UIScreen x : screens)
				x.loadAssets();
			bomberman.start();
		}
		ready = true;
	}

}
