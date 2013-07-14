package sc2d

import java.awt.Paint
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

case class WithPaint(paint:Paint, sub:Figure) extends Figure {
	def pick(at:Point2D):Boolean	=
			sub	pick at
		
	val bounds:Rectangle2D	= 
			sub.bounds
	
	def paint(g:Graphics2D) {
		val old	= g.getPaint
		g	setPaint	paint
		sub	paint		g
		g	setPaint	old
	}
}
