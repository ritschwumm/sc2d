package sc2d

import java.awt.Paint
import java.awt.Shape
import java.awt.Stroke
import java.awt.BasicStroke
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

final case class StrokeShape(shape:Shape, paint:Paint, stroke:Stroke) extends Figure {
	private lazy val strokedShape	=
		stroke createStrokedShape shape

	def pick(at:Point2D):Boolean	=
		strokedShape contains at

	val bounds:Rectangle2D	=
		stroke match {
			case bs:BasicStroke	=>
				// TODO this is not worth much - oicking later will calculate the stroked shape anyway
				val shapeBounds	= shape.getBounds2D
				val strokeSize	= bs.getLineWidth + 2
				new Rectangle2D.Double(
					shapeBounds.getX		- strokeSize,
					shapeBounds.getY		- strokeSize,
					shapeBounds.getWidth	+ 2*strokeSize,
					shapeBounds.getHeight	+ 2*strokeSize
				)
			case os:Stroke		=>
				val shapeBounds	= strokedShape.getBounds2D
				new Rectangle2D.Double(
					shapeBounds.getX		- 1,
					shapeBounds.getY		- 1,
					shapeBounds.getWidth	+ 2,
					shapeBounds.getHeight	+ 2
				)
		}

	def paint(g:Graphics2D):Unit	= {
		val oldPaint	= g.getPaint
		val oldStroke	= g.getStroke
		g	setPaint	paint
		g	setStroke	stroke
		g	draw		shape
		g	setStroke	oldStroke
		g	setPaint	oldPaint
	}
}
