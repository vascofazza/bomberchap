package bomberman.core.logic;

/**
 * The State enum represents the states that an {@link Entity} can assume. 
 */
public enum State
{

	/**
	 * Indicates that the {@link Entity} is moving down.
	 */
	DOWN,
	/**
	 * Indicates that the {@link Entity} is dying.
	 */
	DYING,
	/**
	 * Indicates that the {@link Entity} is idle.
	 */
	IDLE,
	/**
	 * Indicates that the {@link Entity} is moving left.
	 */
	LEFT,
	/**
	 * Indicates that the {@link Entity} is moving right.
	 */
	RIGHT,
	/**
	 * Indicates that the {@link Entity} is moving up.
	 */
	UP;
}
