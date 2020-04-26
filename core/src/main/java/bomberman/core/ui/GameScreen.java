package bomberman.core.ui;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import java.util.Random;
import bomberman.core.BomberMan;
import bomberman.core.PropertiesManager;
import bomberman.core.graphics.*;
import bomberman.core.logic.Block;
import bomberman.core.util.Constants;
import playn.core.Keyboard.TypedEvent;
import playn.core.Pointer.Event;
import playn.core.Image;
import playn.core.Key;
import playn.core.Surface;

/**
 * The Class UIGameScreen is responsible of instantiating the graphic and logic game classes.
 * It displays the game layer, score, time and lives overlays.
 * 
 * @author Federico Scozzafava
 */
public class GameScreen implements UIScreen
{

	/** 
	 * The core class.
	 */
	private BomberMan bomberman;

	/** 
	 * The world.
	 */
	private DecoratedWorld world;

	/**
	 * The bomber character. 
	 */
	private BomberDecorator bomber;

	/** 
	 * The background image. 
	 */
	private Image bgImage;

	/**
	 * The timer graphics component. 
	 */
	private TextGuiComponent timerGfxComponent;

	/** 
	 * The score graphics component. 
	 */
	private TextGuiComponent scoreGfxComponent;

	/** 
	 * The lives graphics component. 
	 */
	private TextGuiComponent livesGfxComponent;

	/**
	 * The game screen responsible of displaying the game, score, time and lives overlays.
	 * 
	 * @param bomberman the Bomberman core class.
	 */
	public GameScreen(BomberMan bomberman)
	{
		this.bomberman = bomberman;
		world = new DecoratedWorld(bomberman, 19, 15);
		timerGfxComponent = new TextGuiComponent(LayerManager.overlay(),
				310, 512, true);
		scoreGfxComponent = new TextGuiComponent(LayerManager.overlay(),
				485, 498, false);
		livesGfxComponent = new TextGuiComponent(LayerManager.overlay(),
				110, 498, false);
	}

	/**
	 * Generate new random round.
	 * 
	 * @param newLevel If true resets all character's parameters.
	 */
	public void startNewRandomRound(boolean newLevel)
	{
		Random x = new Random();
		startNewRound(Constants.MIN_BRICK_BLOCKS + x.nextInt(30),
				Constants.MIN_ENEMIES + x.nextInt(4), Constants.INIT_TIMER_VALUE,
				newLevel);
	}

	/**
	 * Generate new round.
	 * 
	 * @param blocks The number of breakable blocks in this round.
	 * @param enemies The number of enemies in this round
	 * @param time The timer initial value.
	 * @param newLevel If true resets all character's parameters.
	 */
	public void startNewRound(int blocks, int enemies, int time,
			boolean newLevel)
	{
		Random x = new Random();
		world.initializeGame(blocks, time);
		if (newLevel)
			bomber = new BomberDecorator(world, 32, 32);
		else
			world.addEntity(bomber);
		bomber.reset();
		world.addGfxObject(bomber);
		while (enemies > 0)
		{
			Block freeBlock = world.getRandomFreeBlock();
			GfxObject enemy = null;
			switch (x.nextInt(5))
			{
			case 0:
				enemy = new BaromDecorator(world, freeBlock);
				break;
			case 1:
				enemy = new MaronDecorator(world, freeBlock);
				break;
			case 2:
				enemy = new OnilDecorator(world, freeBlock);
				break;
			case 3:
				enemy = new PontanDecorator(world, freeBlock);
				break;
			case 4:
				enemy = new MinvoDecorator(world, freeBlock);
				break;
			}
			world.addGfxObject(enemy);
			enemies--;
		}
	}

	/* (non-Javadoc)
	 * @see playn.core.Pointer.Listener#onPointerStart(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerStart(Event event)
	{
		bomber.onPointerStart(event);
	}

	/* (non-Javadoc)
	 * @see playn.core.Pointer.Listener#onPointerEnd(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerEnd(Event event)
	{
		bomber.onPointerEnd(event);
	}

	/* (non-Javadoc)
	 * @see playn.core.Pointer.Listener#onPointerDrag(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerDrag(Event event)
	{
		bomber.onPointerDrag(event);
	}

	/* (non-Javadoc)
	 * @see playn.core.Pointer.Listener#onPointerCancel(playn.core.Pointer.Event)
	 */
	@Override
	public void onPointerCancel(Event event)
	{
		bomber.onPointerCancel(event);
	}

	/* (non-Javadoc)
	 * @see playn.core.Keyboard.Listener#onKeyDown(playn.core.Keyboard.Event)
	 */
	@Override
	public void onKeyDown(playn.core.Keyboard.Event event)
	{
		if (event.key() == Key.ESCAPE)
		{
			bomberman.pause();
		}
		bomber.onKeyDown(event);
	}

	/* (non-Javadoc)
	 * @see playn.core.Keyboard.Listener#onKeyTyped(playn.core.Keyboard.TypedEvent)
	 */
	@Override
	public void onKeyTyped(TypedEvent event)
	{
		bomber.onKeyTyped(event);
	}

	/* (non-Javadoc)
	 * @see playn.core.Keyboard.Listener#onKeyUp(playn.core.Keyboard.Event)
	 */
	@Override
	public void onKeyUp(playn.core.Keyboard.Event event)
	{
		bomber.onKeyUp(event);
	}

	/* (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#loadAssets()
	 */
	@Override
	public void loadAssets()
	{
		bgImage = assets().getImageSync(
				PropertiesManager.getParameter("gameScreen"));
		world.loadAssets();
	}

	/* (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#hide()
	 */
	@Override
	public void hide()
	{
		LayerManager.background().clear();
		world.hide();
	}

	/* (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#show()
	 */
	@Override
	public void show()
	{
		LayerManager.background().add(graphics().createImageLayer(bgImage));
		world.show();
	}

	/* (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#update(int)
	 */
	@Override
	public void update(int delta)
	{
		world.update(delta);
	}

	/* (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#paint(float)
	 */
	@Override
	public void paint(float alpha)
	{
		world.paint(alpha);
		timerGfxComponent.update(world.getTimer().toString(), 24, 0xFFFFFFFF,
				TextGuiComponent.Effect.NULL);
		livesGfxComponent.update(bomber.getLives() + "", 24, 0xFFFFFFFF,
				TextGuiComponent.Effect.OUTLINE);
		scoreGfxComponent.update(world.getScoreManager().getScore() + "", 24,
				0xFFFFFFFF, TextGuiComponent.Effect.OUTLINE);
	}

	/* (non-Javadoc)
	 * @see bobmerman.core.ui.UIScreen#drawSurface(playn.core.Surface)
	 */
	@Override
	public void drawSurface(Surface surface)
	{
		world.drawSurface(surface);
	}

}
