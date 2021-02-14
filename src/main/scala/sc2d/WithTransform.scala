package sc2d

import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D
import java.awt.geom.AffineTransform

object WithTransform {
	def apply(transform:AffineTransform, sub:Figure):Figure	=
		sub match {
			case WithTransform(transform1, sub1)	=>
				val out	= transform.clone().asInstanceOf[AffineTransform]
				out.concatenate(transform1)
				new WithTransform(out, sub1)

			case sub1	=>
				new WithTransform(transform, sub1)
		}
}

final case class WithTransform(transform:AffineTransform, sub:Figure) extends Figure {
	def pick(at:Point2D):Boolean	= {
		val	tmp	= new Point2D.Double
		transform.inverseTransform(at, tmp)
		sub pick tmp
	}

	lazy val bounds:Rectangle2D	=
		transform.createTransformedShape(sub.bounds).getBounds2D

	def paint(g:Graphics2D):Unit	= {
		val oldTransform	= g.getTransform
		g	transform		transform
		sub	paint			g
		g	setTransform	oldTransform
	}
}
