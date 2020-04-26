package bomberman.core.graphics;

import playn.core.Surface;

/**
 * The GfxObject Interface represents the graphics aspect of the {@code PhysicsEntity}, it 
 * provides some graphics methods inherited from {@link playn.core.PlayN} and delegate the {@link #update(int)}
 * method to the logic structure.
 * 
 * @author Federico Scozzafava
 * @see bomberman.core.logic.PhysicsEntity
 */
public interface GfxObject
{
	
	/**
	 * This method provides a GPU accelerated drawing.
	 * 
	 * @param surface
	 *            the surface on which to draw
	 * @see bomberman.core.BomberMan
	 */
	public void drawSurface(Surface surface);

	/**
	 * Delegated paint methods.
	 * 
	 * @param alpha
	 *            the alpha parameter
	 * @see playn.core.Game.Default#paint(float)
	 */
	public void paint(float alpha);

	/**
	 * Delegated update method.
	 * 
	 * @param delta
	 *            the delta parameter
	 * @see playn.core.Game.Default#update(int)
	 */
	public void update(int delta);
}
