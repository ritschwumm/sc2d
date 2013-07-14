package sc2d

import java.awt.Shape
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

case class WithClip(clip:Shape, sub:Figure) extends Modifier {
	// TODO check
	def pick(at:Point2D):Boolean	=
			(clip	contains	at) && 
			(sub	pick		at)
			
	// TODO check
	val repaint:Rectangle2D	= 
			sub.repaint createIntersection clip.getBounds2D
	
	def paint(g:Graphics2D) {
		val old	= g.getClip
		g	clip	clip
		sub	paint	g
		g	setClip	old
	}
}
