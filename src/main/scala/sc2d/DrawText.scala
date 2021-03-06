package sc2d

import java.awt.Font
import java.awt.Paint
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D
import java.awt.geom.AffineTransform
import java.awt.font.TextLayout
import java.awt.font.FontRenderContext

object DrawText {
	private val identityTransform	= new AffineTransform
	// TODO fake FontRenderContext returns sizes different from what's used in paint
	// @see BasicGraphicsUtils in java 9, maybe this can help
	private val fontRenderContext	= new FontRenderContext(null, false, false)
}

// NOTE "" has no size at all, whereas " " has
/** xAlign 0 is left, 0.5 is center, 1 is right */
final case class DrawText(text:String, paint:Paint, font:Font, x:Float, y:Float, xAlign:Float) extends Figure {
	private val textLayout	=
		new TextLayout(
			text,
			font,
			DrawText.fontRenderContext
		)

	private val offsetX		=
		x - textLayout.getAdvance * xAlign

	private val offsetY	=
		y - textLayout.getBaseline

	// NOTE slow
	private lazy val outline	=
		textLayout getOutline DrawText.identityTransform

	def pick(at:Point2D):Boolean	= {
		val	tmp		=
			new Point2D.Double(
				at.getX - offsetX,
				at.getY - offsetY
			)
		outline contains tmp
	}

	val bounds:Rectangle2D	= {
		// BETTER use getPixelBounds => that would need a FontRenderContext
		val textBounds	= textLayout.getBounds
		new Rectangle2D.Double(
			textBounds.getX	+ offsetX,
			textBounds.getY	+ offsetY,
			textBounds.getWidth,
			textBounds.getHeight
		)
	}

	def paint(g:Graphics2D):Unit	= {
		val oldPaint	= g.getPaint
		val oldFont		= g.getFont
		g	setPaint	paint
		g	setFont		font
		g	.drawString	(text, offsetX, offsetY)
		g	setPaint	oldPaint
		g	setFont		oldFont
	}
}
