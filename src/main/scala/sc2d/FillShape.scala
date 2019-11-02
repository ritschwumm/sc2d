package sc2d

import java.awt.Paint
import java.awt.Shape
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

final case class FillShape(shape:Shape, paint:Paint) extends Figure {
	def pick(at:Point2D):Boolean	=
			shape contains at

	val bounds:Rectangle2D	= {
		val shapeBounds	= shape.getBounds2D
		new Rectangle2D.Double(
			shapeBounds.getX,
			shapeBounds.getY,
			shapeBounds.getWidth	+ 1,
			shapeBounds.getHeight	+ 1
		)
	}

	def paint(g:Graphics2D):Unit	= {
		val oldPaint	= g.getPaint
		g	setPaint	paint
		g	fill		shape
		g	setPaint	oldPaint
	}
}
