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
 * The StartScreen class provides a main UI screen, it's displayed when the program starts.
 * Allows the user to begin the game.
 * 
 * @author Federico Scozzafava
 */
public class StartScreen implements UIScreen
{

	/**
	 * The background image.
	 */
	private Image background;

	/**
	 * The core class.
	 */
	private BomberMan bomberman;

	/**
	 * The text color.
	 */
	private int color = 0xFFFF0000;

	/**
	 * The fade increment.
	 */
	private int increment = 0x00110000;

	/**
	 * The start screen layer which contains the background.
	 */
	private ImageLayer startScreenLayer;

	/**
	 * The start text component.
	 */
	private TextGuiComponent startTextComponent;
	
	/**
	 * The text.
	 */
	private String text;

	/**
	 * Instantiates a new start screen.
	 * 
	 * @param bomberman The core class.
	 */
	public StartScreen(BomberMan bomberman)
	{
		startTextComponent = new TextGuiComponent(LayerManager.overlay(),
				graphics().width() / 2f, graphics().height() / 2f + 100, true);
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
		LayerManager.background().remove(startScreenLayer);
	}

	/* (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#loadAssets()
	 */
	@Override
	public void loadAssets()
	{
		background = assets().getImage(
				PropertiesManager.getParameter("startScreen"));
		startScreenLayer = graphics().createImageLayer(background);
		text = PropertiesManager.getParameter("startText");
	}

	/* (non-Javadoc)
	 * @see playn.core.Keyboard.Listener#onKeyDown(playn.core.Keyboard.Event)
	 */
	@Override
	public void onKeyDown(playn.core.Keyboard.Event event)
	{}

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
	{
		if (event.key() == Key.SPACE) bomberman.startNewRound(true);
	}

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
		LayerManager.background().add(startScreenLayer);
	}

	/* (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#update(int)
	 */
	@Override
	public void update(int delta)
	{
		if (color == 0xFF000000 || color == 0xFFFF0000) increment *= -1;
		color += increment;
		startTextComponent.update(text, 28, color,
				TextGuiComponent.Effect.OUTLINE);
	}

}
