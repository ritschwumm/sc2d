package sc2d

import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D
import java.awt.geom.AffineTransform

case class WithTransform(transform:AffineTransform, sub:Figure) extends Figure {
	def pick(at:Point2D):Boolean	= {
		val	tmp	= new Point2D.Double
		transform inverseTransform (at, tmp)
		sub pick tmp 
	}
	
	val bounds:Rectangle2D	= 
			(transform createTransformedShape sub.bounds).getBounds2D
		
	def paint(g:Graphics2D) {
		val old	= g.getTransform
		g	transform		transform
		sub	paint			g
		g	setTransform	old
	}
}
