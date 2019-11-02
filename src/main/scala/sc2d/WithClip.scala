package sc2d

import java.awt.Shape
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

final case class WithClip(clip:Shape, sub:Figure) extends Figure {
	def pick(at:Point2D):Boolean	=
			(clip	contains	at) &&
			(sub	pick		at)

	val bounds:Rectangle2D	=
			sub.bounds createIntersection clip.getBounds2D

	def paint(g:Graphics2D):Unit	= {
		val oldClip	= g.getClip
		g	clip	clip
		sub	paint	g
		g	setClip	oldClip
	}
}
