package sc2d

import java.awt.Shape
import java.awt.Graphics2D
import java.awt.geom.Area
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

object WithClip {
	def apply(clip:Shape, sub:Figure):Figure	=
		sub match {
			case WithClip(clip1, sub1)	=>
				val out	= new Area(clip)
				out.intersect(new Area(clip1))
				new WithClip(out, sub1)
			case sub1					=>
				new WithClip(clip, sub1)
		}
}

final case class WithClip(clip:Shape, sub:Figure) extends Figure {
	def pick(at:Point2D):Boolean	=
		(clip	contains	at) &&
		(sub	pick		at)

	lazy val bounds:Rectangle2D	=
		sub.bounds createIntersection clip.getBounds2D

	def paint(g:Graphics2D):Unit	= {
		val oldClip	= g.getClip
		g	clip	clip
		sub	paint	g
		g	setClip	oldClip
	}
}
