package bomberman.core.logic;

/**
 * The ScoreManager class manages the player's score during the game.
 * 
 * @author Federico Scozzafava
 */
public class ScoreManager
{
	
	/**
	 * The current score.
	 */
	private int score = 0;

	/**
	 * Gets the current score.
	 * 
	 * @return the score
	 */
	public int getScore()
	{
		return score;
	}

	/**
	 * Increase the score.
	 * 
	 * @param v
	 *            the increment value
	 */
	public void increaseScore(int v)
	{
		score += v;
	}

	/**
	 * Resets the score.
	 */
	public void reset()
	{
		score = 0;
	}
}
