package bomberman.core.ui;

import static playn.core.PlayN.graphics;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.GroupLayer;
import playn.core.Layer;
import playn.core.TextFormat;
import playn.core.TextLayout;

/**
 * A base class for displaying a text-based GUI component on the screen. This
 * class handles the common tasks of rendering the text to display and adding it
 * to a layer.
 */
public class TextGuiComponent
{
	/**
	 * The implemented text effects.
	 *
	 */
	public enum Effect
	{
		/**
		 * No effect.
		 */
		NULL, 
		/**
		 * Outline effect with fixed color and width.
		 */
		OUTLINE, 
		/**
		 * ShadowDL effect with fixed color and position offset
		 */
		SHADOW
	}

	/**
	 * This parameter indicates if the text's position is centered at the x and
	 * y coordinates
	 */
	private final boolean centered;

	/**
	 * The current layer displaying the text or {@code null} if nothing is being
	 * displayed currently
	 */
	private Layer current;

	/**
	 * The outline effect's color.
	 */
	private int outlineColor = 0xFF000000;

	/**
	 * The outline effect's width.
	 */
	private int outlineWidth = 5;
	
	/**
	 * The parent layer to which this component's content is added
	 */
	private final GroupLayer parent;

	/**
	 * The shadows effect's color
	 */
	private int shadowColor = 0xFFDDDDDD;

	/**
	 * The x position of the component in pixels
	 */
	private float x;

	/**
	 * The y position of the component in pixels
	 */
	private float y;

	/**
	 * Creates a new {@code TextGuiComponent} at the specified location with
	 * the specified parent to which the content will be added.
	 */
	public TextGuiComponent(GroupLayer parent, float x, float y,
			boolean centered)
	{
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.centered = centered;
	}

	/**
	 * Creates a new {@code Layer} containing the text rendered in the specified
	 * layout and color.
	 * @param layout The text.
	 * @param color The text's color.
	 * @param fx The text's effect
	 */
	public Layer createTextLayer(TextLayout layout, int color, Effect fx)
	{
		CanvasImage image = graphics().createImage(
				(int) Math.ceil(layout.width()),
				(int) Math.ceil(layout.height()));
		switch (fx)
		{
		case OUTLINE:
		{
			image.canvas().setStrokeWidth(outlineWidth);
			image.canvas().setStrokeColor(outlineColor);
			image.canvas().setLineCap(Canvas.LineCap.ROUND);
			image.canvas().setLineJoin(Canvas.LineJoin.ROUND);
			image.canvas().strokeText(layout, 0, 0);
		}
			;
			break;
		case SHADOW:
		{
			image.canvas().setFillColor(shadowColor);
			image.canvas().fillText(layout, 2, -2);
		}
			;
			break;
		case NULL:
			break;
		}
		image.canvas().setFillColor(color);
		image.canvas().fillText(layout, 0, 0);
		return graphics().createImageLayer(image);
	}

	/**
	 * Updates the text currently displayed by this component with the provided
	 * text.
	 * 
	 * @param content
	 *            the text to display.
	 * @param fontSize The font size.
	 * @param color The font color.
	 * @param fx The text effect
	 */
	public void update(String content, int fontSize, int color, Effect fx)
	{
		if (current != null) current.destroy();
		float x = this.x;
		float y = this.y;
		Font font = graphics().createFont("Arial", Font.Style.BOLD, fontSize);
		TextFormat format = new TextFormat().withFont(font);
		TextLayout layout = graphics().layoutText(content, format);
		current = createTextLayer(layout, color, fx);
		if (centered)
		{
			x = this.x - layout.width() / 2f;
			y = this.y - layout.height() / 2f;
		}
		current.setTranslation(x, y);
		parent.add(current);
	}
}
