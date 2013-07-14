package sc2d

import java.awt.Shape
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

case class CompoundFigure(subs:Seq[Figure]) extends Figure {
	def pick(at:Point2D):Boolean	=
			subs exists { _ pick at }
			
	val bounds:Rectangle2D	=
			if (subs.nonEmpty) {
				val out	= subs.head.bounds
				subs.tail.foreach { it => 
					Rectangle2D union (out, it.bounds, out )
				}
				out
			}
			else {
				new Rectangle2D.Double()
			}
	
	def paint(g:Graphics2D) {
		subs foreach { _ paint g }
	}
}
