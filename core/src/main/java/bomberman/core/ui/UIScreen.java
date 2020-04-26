package bomberman.core.ui;

import playn.core.Keyboard;
import playn.core.Pointer;
import playn.core.Surface;

/**
 * An interface for supporting the basic functionalities needed for each of the
 * game's components (such as the Load Screens, Pause Screens, and Game Levels)
 * that need to both receive information about how much time has elapsed and
 * receive callbacks to paint their state on the screen.
 * <p>
 * This interface wraps both {@link Keyboard.Listener} and
 * {@link Pointer.Listener}, whose methods are called when a particular
 * {@code UIScreen} is active.
 */
public interface UIScreen extends Pointer.Listener, Keyboard.Listener
{

	/**
	 * Called by PlayN, provides the immediate layer {@code renderer} accelerated GPU drawing.
	 * @param surface
	 *            The surface on which to draw
	 */
	void drawSurface(Surface surface);

	/**
	 * Hides the current {@code UIScreen}.
	 */
	void hide();

	/**
	 * Loads the current {@code UIScreen}'s assets.
	 */
	void loadAssets();

	/**
	 * When called by PlayN, notifies the active {@link UIScreen} to paint its
	 * associated elements.
	 */
	void paint(float alpha);

	/**
	 * Shows the current {@code UIScreen}.
	 */
	void show();

	/**
	 * When called by PlayN, notifies the active {@link UIScreen} that the
	 * provided amount of time has passed in the game
	 * 
	 * @param delta
	 *            the amount of time in milliseconds that has passed since the
	 *            last called to {@code update}.
	 */
	void update(int delta);

}
