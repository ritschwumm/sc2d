package sc2d

import java.awt.Composite
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

case class WithComposite(composite:Composite, sub:Figure) extends Modifier {
	def pick(at:Point2D):Boolean	=
			sub	pick at
		
	val repaint:Rectangle2D	= 
			sub.repaint
	
	def paint(g:Graphics2D) {
		val old	= g.getComposite
		g	setComposite	composite
		sub	paint			g
		g	setComposite	old
	}
}
