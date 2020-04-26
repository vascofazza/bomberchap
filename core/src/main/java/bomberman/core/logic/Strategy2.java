package bomberman.core.logic;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

/**
 * The Strategy2 class is an implementation of the {@link MoveStrategy} interface.
 * This strategy lets the {@link DynamicPhysicsEntity} move over all the ground (static and 
 * dynamic) maintaining the current {@link Direction} for a fixed number of blocks (when possible),
 * changing then direction.
 * 
 * @author Federico Scozzafava
 * @see {@link MoveStrategy}
 * @see http://en.wikipedia.org/wiki/Strategy_pattern
 */
public class Strategy2 implements MoveStrategy
{
	
	/**
	 * The Constant KEEP_DIRECTION specify the number of block to move on before 
	 * {@link #changeDirection()}.
	 */
	private static final int KEEP_DIRECTION = 3;
	
	/**
	 * The border {@link WallBlock}s where not to go over.
	 */
	private Set<Block> border;
	
	private int counter = 0;
	
	/**
	 * The current {@link Block}.
	 */
	private Block current;
	
	private int currentIndex = 0;
	
	/**
	 * The {@link DynamicPhysicsEntity}.
	 */
	private DynamicPhysicsEntity entity;
	
	/**
	 * The {@link LogicWorld}.
	 */
	private LogicWorld logicWorld;
	
	private int oldIndex = 0;
	
	/**
	 * The visited.
	 */
	private Stack<Block> visited = new Stack<Block>();

	/* (non-Javadoc)
	 * @see bomberman.core.logic.MoveStrategy#changeDirection()
	 */
	@Override
	public void changeDirection()
	{
		counter = KEEP_DIRECTION + 1;
		current = visited.isEmpty() ? current : visited.pop();
	}

	/**
	 * Gets the next move for the specified {@link MoveStrategy}.
	 * 
	 * @return the next {@link Block} where to move
	 * @throws RuntimeException if the {@link DynamicPhysicsEntity} is not specified
	 * in the {@link MoveStrategy}
	 */
	private Block getNextMove()
	{
		if (entity == null)
			throw new RuntimeException(
					"Strategy class not correctly initialized");
		oldIndex = currentIndex;
		Random x = new Random();
		visited.push(current);
		List<Block> adjacents = logicWorld.getAdjacent(current);
		adjacents.removeAll(border);
		if (counter > KEEP_DIRECTION || oldIndex >= adjacents.size())
		{
			int tempRand = x.nextInt(adjacents.size());

			currentIndex = tempRand;
			counter = 0;
		}
		counter++;
		return adjacents.get(currentIndex);
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.MoveStrategy#move()
	 */
	@Override
	public void move()
	{
		if (entity == null)
			throw new RuntimeException(
					"Strategy class not correctly initialized");
		if (current.contains(entity))
		{
			entity.alignPosition(current);
			current = getNextMove();
		}
		if (current.getBomb() != null) changeDirection();
		entity.move(current.getPosition());
	}

	/* (non-Javadoc)
	 * @see bomberman.core.logic.MoveStrategy#setEntity(bomberman.core.logic.DynamicPhysicsEntity)
	 */
	@Override
	public MoveStrategy setEntity(DynamicPhysicsEntity entity)
	{
		this.entity = entity;
		logicWorld = entity.getLogicWorld();
		current = logicWorld.getBlockAt(entity.getCenter());
		border = logicWorld.getBorderBlocks();
		return this;
	}

}
