package bomberman.core.util;

/**
 * The Timer class is an utility class that manage the flow of the time during the game and
 * provides some helpful features like countdown time and string formatted visualization.
 */
public class Timer
{

	/**
	 * The current time.
	 */
	private float time;

	/**
	 * Instantiates a new {@link Timer}.
	 * 
	 * @param init
	 *            the initial timer value
	 */
	public Timer(float init)
	{
		time = init;
	}

	/**
	 * Adds time to the countdown {@link Timer} in seconds.
	 * 
	 * @param timerIncrement
	 *            the timer increment in seconds
	 */
	public void addTime(float timerIncrement)
	{
		time += timerIncrement;
	}

	/**
	 * Gets the current countdown time as float value.
	 * 
	 * @return the time as float value
	 */
	public float getTimeFloat()
	{
		return time;
	}

	/**
	 * Gets the current countdown time as integer.
	 * 
	 * @return the time as integer
	 */
	public int getTimeInt()
	{
		return (int) time;
	}

	/**
	 * Sets the countdown time.
	 * 
	 * @param t
	 *            the new time
	 */
	public void setTime(float t)
	{
		time = t;
	}

	
	/**
	 * Provides a string formatted of the countdown.
	 */
	@Override
	public String toString()
	{
		int seconds = (int) (time % 60);
		return (int) (time / 60f) + ":"
				+ (seconds < 10 ? "0" + seconds : seconds);
	}

	/**
	 * Updates the countdown.
	 * 
	 * @param delta
	 *            the time elapsed since last update call
	 */
	public void update(int delta)
	{
		time -= delta / 1000f;
	}
}
