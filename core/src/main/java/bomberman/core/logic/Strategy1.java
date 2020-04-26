package bomberman.core.logic;

import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * The Strategy1 class is an implementation of the {@link MoveStrategy} interface.
 * This strategy lets the {@link DynamicPhysicsEntity} move in a pseudo-random way around the {@link FreeBlock}
 * ground, without moving on already visited blocks (when possible).
 * 
 * @author Federico Scozzafava
 * @see {@link MoveStrategy}
 * @see http://en.wikipedia.org/wiki/Strategy_pattern
 */
public class Strategy1 implements MoveStrategy
{
	
	/**
	 * The current {@link Block}.
	 */
	private Block current;
	
	/**
	 * The {@link DynamicPhysicsEntity}.
	 */
	private DynamicPhysicsEntity entity;
	
	/**
	 * The {@link LogicWorld} in which the entity acts.
	 */
	private LogicWorld logicWorld;
	
	/**
	 * The visited {@link Block}s.
	 */
	private Stack<Block> visited = new Stack<Block>();

	/* (non-Javadoc)
	 * @see bomberman.core.logic.MoveStrategy#changeDirection()
	 */
	@Override
	public void changeDirection()
	{
		Block bomb = current;
		current = visited.isEmpty() ? current : visited.peek();
		visited.clear();
		visited.push(bomb);
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
		Random x = new Random();
		visited.push(current);
		List<Block> adjacents = logicWorld.getFreeAdjacent(current);
		adjacents.size();
		adjacents.removeAll(visited);
		if (adjacents.size() > 0) 
			return adjacents.get(x.nextInt(adjacents.size()));
		visited.clear();
		visited.push(current);
		adjacents = logicWorld.getFreeAdjacent(current);
		adjacents.remove(current);
		return adjacents.size() < 1 ? current : adjacents.get(x.nextInt(adjacents.size()));
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
		return this;
	}

}
