package bomberman.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.keyboard;
import static playn.core.PlayN.pointer;
import playn.core.Game;
import playn.core.ImmediateLayer;
import playn.core.Keyboard;
import playn.core.Pointer;
import playn.core.Pointer.Event;
import playn.core.Sound;
import playn.core.Surface;
import bomberman.core.graphics.LayerManager;
import bomberman.core.ui.GameScreen;
import bomberman.core.ui.LoadScreen;
import bomberman.core.ui.LoseScreen;
import bomberman.core.ui.PauseScreen;
import bomberman.core.ui.StartScreen;
import bomberman.core.ui.UIScreen;
import bomberman.core.ui.WinScreen;

/**
 * The class responsible for maintaining the core game loop and all other
 * components of the Bomberman game. This class maintains references to each of
 * the individual gameplay mechanics and serves as a call through for changing
 * the state of the game (e.g., switching from the start {@code UIScreen} to a new round
 * of the game). Importantly, this class serves as the primary entry point for
 * all timing-based updates and graphics refreshes to the game and then delegates the updates to the
 * appropriate {@link UIScreen} based on the current state of the game.
 * 
 * @see StartScreen
 * @see bomberman.core.graphics.DecoratedWorld
 * @see UIScreen
 */
public class BomberMan extends Game.Default implements Keyboard.Listener,
		Pointer.Listener
{
	
	/**
	 * The screen that is currently active and should receive input focus
	 */
	private UIScreen delegate;

	/**
	 * The resources associated with the game screen that players see when a new
	 * round is started.
	 */
	private UIScreen gameScreen;

	/**
	 * The immediate layer.
	 */
	private ImmediateLayer immediateLayer;

	/**
	 * The resources associated with the load screen that players see while resources are loading
	 */
	private UIScreen loadScreen;

	/**
	 * The resources associated with the game screen that players see when a
	 * round has ended.
	 */
	private UIScreen loseScreen;

	/**
	 * The resources associated with the game screen that players see when the
	 * game is paused.
	 */
	private UIScreen pauseScreen;

	/**
	 * The main game sound.
	 */
	private Sound roundSound;

	/**
	 * The resources associated with the game screen that players see when the game starts.
	 */
	private UIScreen startScreen;

	/**
	 * The sound played in the start {@link UIScreen}.
	 */
	private Sound startSound;

	/**
	 * The resources associated with the game screen that shows up when player wins
	 */
	private UIScreen winScreen;

	/**
	 * Instantiates a new bomber man.
	 */
	public BomberMan()
	{
		super(33); // call update every 33ms (30 times per second)
	}

	/* (non-Javadoc)
	 * @see playn.core.Game#init()
	 */
	@Override
	public void init()
	{
		keyboard().setListener(this);
		pointer().setListener(this);

		startScreen = new StartScreen(this);

		gameScreen = new GameScreen(this);

		pauseScreen = new PauseScreen(this);

		winScreen = new WinScreen(this);

		loseScreen = new LoseScreen(this);

		loadScreen = new LoadScreen(this, startScreen, pauseScreen, winScreen,
				loseScreen, gameScreen);

		loadScreen.show();

		delegate = loadScreen;

		roundSound = assets().getMusic(
				PropertiesManager.getParameter("roundSound"));
		roundSound.setLooping(true);
		startSound = assets().getMusic(
				PropertiesManager.getParameter("startSound"));
		startSound.setLooping(false);

		immediateLayer = graphics().createImmediateLayer(
				new ImmediateLayer.Renderer()
				{
					@Override
					public void render(Surface surface)
					{
						delegate.drawSurface(surface);
					}
				});
		LayerManager.immediate().add(immediateLayer);
	}

	/**
	 * Called by the {@link DecoratedWorld} when the player loses.
	 */
	public void lose()
	{
		roundSound.stop();
		loseScreen.show();
		delegate = loseScreen;
	}

	/* (non-Javadoc)
	 * @see playn.core.Keyboard.Listener#onKeyDown(playn.core.Keyboard.Event)
	 */
	@Override
	public void onKeyDown(Keyboard.Event event)
	{
		delegate.onKeyDown(event);
	}

	/* (non-Javadoc)
	 * @see playn.core.Keyboard.Listener#onKeyTyped(playn.core.Keyboard.TypedEvent)
	 */
	@Override
	public void onKeyTyped(Keyboard.TypedEvent event)
	{
		delegate.onKeyTyped(event);
	}

	/* (non-Javadoc)
	 * @see playn.core.Keyboard.Listener#onKeyUp(playn.core.Keyboard.Event)
	 */
	@Override
	public void onKeyUp(Keyboard.Event event)
	{
		delegate.onKeyUp(event);
	}

	/* (non-Javadoc)
	 * @see playn.core.Pointer.Listener#onPointerCancel(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerCancel(Event event)
	{
		delegate.onPointerCancel(event);
	}

	/* (non-Javadoc)
	 * @see playn.core.Pointer.Listener#onPointerDrag(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerDrag(Event event)
	{
		delegate.onPointerDrag(event);
	}

	/* (non-Javadoc)
	 * @see playn.core.Pointer.Listener#onPointerEnd(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerEnd(Event event)
	{
		delegate.onPointerEnd(event);
	}

	/* (non-Javadoc)
	 * @see playn.core.Pointer.Listener#onPointerStart(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerStart(Event event)
	{
		delegate.onPointerStart(event);
	}

	/* (non-Javadoc)
	 * @see playn.core.Game.Default#paint(float)
	 */
	@Override
	public void paint(float alpha)
	{
		delegate.paint(alpha);
	}

	/**
	 * Called by the {@link DecoratedWorld} when the game is paused.
	 */
	public void pause()
	{
		roundSound.stop();
		pauseScreen.show();
		delegate = pauseScreen;
	}

	/**
	 * Called by the {@link PauseScreen} when the game resumes.
	 */
	public void resume()
	{
		pauseScreen.hide();
		delegate = gameScreen;
		roundSound.play();
	}

	/**
	 * Brings the user to the {@link StartScreen}.
	 */
	public void start()
	{
		LayerManager.clear();
		startScreen.show();
		delegate = startScreen;
		startSound.play();
	}

	/**
	 * Start a new round.
	 * 
	 * @param newLevel
	 *            Indicates if we are starting a new game or the next level
	 */
	public void startNewRound(boolean newLevel)
	{
		((GameScreen) gameScreen).startNewRandomRound(newLevel);
		startSound.stop();
		LayerManager.clear();
		gameScreen.show();
		delegate = gameScreen;
		roundSound.play();
	}

	/* (non-Javadoc)
	 * @see playn.core.Game.Default#update(int)
	 */
	@Override
	public void update(int delta)
	{
		delegate.update(delta);
	}

	/**
	 * Called by {@link bomberman.core.graphics.DecoratedWorld} when the player wins.
	 */
	public void win()
	{
		roundSound.stop();
		winScreen.show();
		delegate = winScreen;
	}
}
