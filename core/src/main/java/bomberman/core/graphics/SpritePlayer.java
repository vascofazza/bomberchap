package bomberman.core.graphics;

import playn.core.Surface;
import bomberman.core.util.Vector2;

/**
 * The SpritePlayer class reproduces {@link Sprite} animations reading sequentially the
 * sprite sheet frame by frame. Due to the necessity to draw at each update, the player makes 
 * use of the advantages of GPU accelerated immediate layer, drawing directly on the GPU buffer.
 * 
 * @see http://en.wikipedia.org/wiki/Sprite_(computer_graphics)
 */
public class SpritePlayer
{
	
	/**
	 * The current {@link Sprite}.
	 */
	private Sprite sprite;
	
	/**
	 * The current frame index.
	 */
	private int frameIndex;
	
	/**
	 * The current time.
	 */
	private float time;

	/**
	 * Current {@link Sprite}.
	 * 
	 * @return the current {@link Sprite}
	 */
	public Sprite Sprite()
	{
		return sprite;
	}

	/**
	 * Draw the current frame at the position specified by the <b>bottom centered</b> {@link Vector2}. 
	 * This allows us to display animations bigger than the logic {@link bomberman.core.logic.DynamicPhysicsEntity}
	 * body while maintaining centered the image.
	 * 
	 * @param surf
	 *            the surface on which to draw
	 * @param position
	 *            the <b>bottom centered</b> position on the screen in pixels 
	 * @throws RuntimeException if the animation isn't specified
	 */
	public void draw(Surface surf, Vector2 position)
	{
		if (Sprite() == null)
			throw new RuntimeException("No animation is currently playing.");

		surf.drawImage(Sprite().getTexture(), position.getX() - sprite.frameWidth()
				/ 2f, position.getY() - sprite.frameHeight(),
				sprite.frameWidth(), sprite.frameHeight(), frameIndex*sprite.frameWidth(), 0,
				sprite.frameWidth(), sprite.frameHeight());
	}

	/**
	 * Current frame index.
	 * 
	 * @return the currently playing frame's index
	 */
	public int frameIndex()
	{
		return frameIndex;
	}

	/**
	* Starts or continue the animation playback.
	* 
	* @param gameTime
	*            the current game time
	* @param animation
	*            the animation to play
	*/
	public void playAnimation(float gameTime, Sprite animation)
	{
		// if the animation is already running continue.
		if (Sprite() == animation)
		{
			time += gameTime;
			while (time > Sprite().frameTime())
			{
				time -= Sprite().frameTime(); //reset the timer and change frame
				if (Sprite().isLooping())
					frameIndex = (frameIndex + 1) % Sprite().frameCount();
				else
					frameIndex = frameIndex + 1 < Sprite().frameCount() - 1 ? frameIndex + 1
							: Sprite().frameCount() - 1;
			}
			return;
		}
		// starts new animation.
		this.sprite = animation;
		frameIndex = 0;
		time = 0.0f;
	}
}
