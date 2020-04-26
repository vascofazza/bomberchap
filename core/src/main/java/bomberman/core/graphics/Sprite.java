package bomberman.core.graphics;

import playn.core.Image;
import bomberman.core.util.Constants;

/**
 * The Sprite class contains all details about the animation that will be 
 * played through the {@link SpritePlayer}. This implementation assumes square frame.
 * 
 * @see http://en.wikipedia.org/wiki/Sprite_(computer_graphics)
 */
class Sprite
{
	
	/**
	 * The time each frame will be displayed.
	 */
	private float frameTime;
	
	/**
	 * The is looping.
	 */
	private boolean isLooping;
	
	/**
	 * The sprite sheet image consist of a horizontal stripe which contains all the frames.
	 * 
	 * @see http://en.wikipedia.org/wiki/Sprite_(computer_graphics)
	 */
	private Image texture;

	/**
	 * Instantiates a new sprite. for simplicity we assume square frames, 
	 * it therefore appears that the image will be wide {@code x} times its height
	 * 
	 * @param texture
	 *            the sprite sheet
	 * @param isLooping
	 *            if true, once the animation has been reproduced, it will restart automatically
	 * @see http://en.wikipedia.org/wiki/Sprite_(computer_graphics)
	 */
	public Sprite(Image texture, boolean isLooping)
	{
		this(texture, Constants.SPRITE_FRAME_TIME, isLooping);
	}

	/**
	 * Instantiates a new sprite. for simplicity we assume square frames, 
	 * it therefore appears that the image will be wide {@code x} times its height
	 * 
	 * @param texture
	 *            the sprite sheet
	 * @param isLooping
	 *            if true, once the animation has been reproduced, it will restart automatically
	 * @param frameTime The time each frame will be displayed.
	 * @see http://en.wikipedia.org/wiki/Sprite_(computer_graphics)
	 */
	public Sprite(Image texture, float frameTime, boolean isLooping)
	{
		this.texture = texture;
		this.frameTime = frameTime;
		this.isLooping = isLooping;
	}

	/**
	 * Frame number in the animation.
	 * 
	 * @return the number of frame
	 */
	public int frameCount()
	{
		return (int) (texture.width() / frameWidth());
	}

	/**
	 * Frame height.
	 * 
	 * @return the height
	 */
	public int frameHeight()
	{
		return (int) texture.height();
	}

	/**
	 * Frame time expressed in milliseconds.
	 * 
	 * @return the time in milliseconds
	 */
	public float frameTime()
	{
		return frameTime;
	}

	/**
	 * Frame width (assuming square frame).
	 * 
	 * @return the width
	 */
	public int frameWidth()
	{
		return (int) texture.height();
	}

	/**
	 * Gets the source sprite sheet.
	 * 
	 * @return the image
	 */
	public Image getTexture()
	{
		return texture;
	}

	/**
	 * Checks if is looping.
	 * 
	 * @return true, if is looping
	 */
	public boolean isLooping()
	{
		return isLooping;
	}

	/**
	 * Sets the frame time.
	 * 
	 * @param t
	 *            the new frame time
	 */
	public void setFrameTime(float t)
	{
		frameTime = t;
	}

}
