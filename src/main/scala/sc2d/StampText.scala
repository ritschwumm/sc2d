package sc2d

import java.awt.Font
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D
import java.awt.geom.AffineTransform
import java.awt.font.TextLayout
import java.awt.font.FontRenderContext

object StampText {
	private val identityTransform	= new AffineTransform
}

// NOTE "" has no size at all, whereas " " has
case class StampText(text:String, font:Font, x:Float, y:Float, left:Boolean) extends Pickable {
	private val textLayout	= new TextLayout(
			text,
			font, 
			new FontRenderContext(null, false, false))
			
	private val offsetX		=
			if (left)	x
			else		x - textLayout.getAdvance
			
	private val offsetY	= 
			y - textLayout.getBaseline
	
	// TODO slow, check
	def pick(at:Point2D):Boolean	= {
		val shape	= textLayout getOutline StampText.identityTransform
		val	tmp		= new Point2D.Double(
				at.getX - offsetX,
				at.getY - offsetY)
		shape contains tmp
	}
	
	// TODO seems to be too small on the right, probably because textLayout uses a fake FontRenderContext
	val repaint:Rectangle2D	= {
		val textBounds	= textLayout.getBounds
		new Rectangle2D.Double(
				textBounds.getX			+ offsetX,
				textBounds.getY			+ offsetY,
				textBounds.getWidth,
				textBounds.getHeight)
	}
	
	def paint(g:Graphics2D) {
		g	setFont		font
		g	drawString	(text, offsetX, offsetY)
	}
}
