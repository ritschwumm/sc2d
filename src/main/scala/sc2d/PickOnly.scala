package sc2d

import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

final case class PickOnly(sub:Figure) extends Figure {
	def pick(at:Point2D):Boolean	=
			sub	pick at
		
	val bounds:Rectangle2D	=
			sub.bounds
	
	def paint(g:Graphics2D) {}
}
