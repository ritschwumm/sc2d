package sc2d

import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

object CompoundFigure {
	val noBounds	= new Rectangle2D.Double()

	def of(subs:Figure*):Figure	= CompoundFigure(subs)

	def apply(subs:Seq[Figure]):Figure	=
		new CompoundFigure(
			subs flatMap {
				case CompoundFigure(subs)	=> subs
				case x						=> Vector(x)
			}
		)
}

final case class CompoundFigure(subs:Seq[Figure]) extends Figure {
	def pick(at:Point2D):Boolean	=
		subs exists (_ pick at)

	lazy val bounds:Rectangle2D	=
		subs.size match {
			case 0	=> CompoundFigure.noBounds
			case 1	=> subs.head.bounds
			case _	=>
				val head	= subs.head.bounds
				val out		=
					new Rectangle2D.Double(
						head.getX,
						head.getY,
						head.getWidth,
						head.getHeight
					)
				subs.tail.foreach { it =>
					Rectangle2D.union(it.bounds, out, out)
				}
				out
		}

	def paint(g:Graphics2D):Unit	=
		subs foreach { _ paint g }
}
