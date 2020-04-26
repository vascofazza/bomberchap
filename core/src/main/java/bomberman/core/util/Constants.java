package bomberman.core.util;

import bomberman.core.logic.*;

/**
 * The Class where game constants are defined.
 */
public abstract class Constants
{

	public static final float BAROM_SPEED = .6f;

	/**
	 * The Constant BLOCK_SCORE define the score player gains when destroys a {@link Block}.
	 */
	public static final int BLOCK_SCORE = 50;

	/**
	 * The Constant BOMB_TIME defines the time that passes from the when {@link Bomb} 
	 * was triggered and when it explodes in milliseconds.
	 */
	public static final int BOMB_TIME = 100;

	/**
	 * The Constant BOMBER_DIE_SCORE the score penality when {@link Bomber} dies.
	 */
	public static final int BOMBER_DIE_SCORE = -200;

	/**
	 * The initial {@link Bomber} boms.
	 */
	public static final int BOMBERMAN_INIT_BOMBS = 1;

	public static final int BOMBERMAN_INIT_LIFES = 3;

	public static final float BOMBERMAN_INIT_SPEED = 1f;

	public static final float BOMBERMAN_MAX_SPEED = 4f;

	public static final float BOMBERMAN_MIN_SPEED = 1f;

	/**
	 * The Constant BOX2D_WORLD_SCALE defines the scale proportions between the graphics
	 * world and the simulated physics {@code Box2d} world.
	 */
	public static final float BOX2D_WORLD_SCALE = 100f;

	public static final int DIE_TIME = 50;

	public static final int ENEMY_BAROM_DIE_SCORE = 500;

	public static final int ENEMY_MARON_DIE_SCORE = 700;

	public static final int ENEMY_MINBO_DIE_SCORE = 300;

	public static final int ENEMY_ONIL_DIE_SCORE = 200;

	public static final int ENEMY_PONTAN_DIE_SCORE = 250;

	public static final int FIRE_MAX_RANGE = 10;

	public static final int FIRE_RANGE_INCREMENT = 1;

	public static final int FIRE_TIME = 40;

	/**
	 * The Constant FIXED_SQUARE_SIZE defines the default size for squared graphics entities.
	 */
	public static final float FIXED_SQUARE_SIZE = 32;

	/**
	 * The Constant IMPULSE_ACTION define the proportional constant when applying a linear
	 * velocity impulse.
	 */
	public static final float IMPULSE_ACTION = .8f;

	public static final int INIT_FRE_RANGE = 1;

	public static final int INIT_TIMER_VALUE = 150;

	public static final float MARON_SPEED = 1f;

	public static final int MIN_BRICK_BLOCKS = 25;

	public static final int MIN_ENEMIES = 1;

	public static final float MINBO_SPEED = 1f;

	public static final float ONIL_SPEED = .6f;

	public static final float PONTAN_SPEED = .8f;

	public static final int POWERUP_TIME = 550;

	public static final int SCORE_WIN = 1000;

	public static final float SPEED_INCREMENT = 0.5f;

	public static final float SPRITE_FRAME_TIME = 200;

	/**
	 * The Constant TEXTURES defines all the texture that needs to be loaded before the game 
	 * begins.
	 */
	public static final String[] TEXTURES = new String[] { "bomberUp",
			"bomberDown", "bomberLeft", "bomberRight", "bomberIdle",
			"bomberDying", "baromIdle", "baromDying", "maronIdle",
			"maronDying", "pontanIdle", "pontanDying", "onilIdle", "onilDying",
			"minboIdle", "minboDying", "bomb", "fire", "Door", "PowerUpBomb",
			"PowerUpBombKick", "PowerUpBombPass", "PowerUpBombRangeUp",
			"PowerUpBombRangeDown", "PowerUpLife", "PowerUpSkull",
			"PowerUpSpeedUp", "PowerUpSpeedDown", "PowerUpTimeUp",
			"PowerUpTimeDown", "dynamicGroundImage" };

	public static final int TIMER_INCREMENT = 30;
}
