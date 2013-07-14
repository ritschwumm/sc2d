package sc2d

import java.awt.Font
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D
import java.awt.geom.AffineTransform
import java.awt.font.TextLayout
import java.awt.font.FontRenderContext

object DrawText {
	private val identityTransform	= new AffineTransform
}

// NOTE "" has no size at all, whereas " " has
case class DrawText(text:String, font:Font, x:Float, y:Float, leftAlign:Boolean) extends Figure {
	// TODO fake FontRenderContext returns sizes different from what's used in paint 
	private val textLayout	= new TextLayout(
			text,
			font, 
			new FontRenderContext(null, false, false))
			
	private val offsetX		=
			if (leftAlign)	x
			else			x - textLayout.getAdvance
			
	private val offsetY	= 
			y - textLayout.getBaseline
	
	// NOTE slow
	def pick(at:Point2D):Boolean	= {
		val shape	= textLayout getOutline DrawText.identityTransform
		val	tmp		= new Point2D.Double(
				at.getX - offsetX,
				at.getY - offsetY)
		shape contains tmp
	}
	
	val bounds:Rectangle2D	= {
		// BETTER use getPixelBounds?
		val textBounds	= textLayout.getBounds
		new Rectangle2D.Double(
				textBounds.getX	+ offsetX,
				textBounds.getY	+ offsetY,
				textBounds.getWidth,
				textBounds.getHeight)
	}
	
	def paint(g:Graphics2D) {
		g	setFont		font
		g	drawString	(text, offsetX, offsetY)
	}
}