package sc2d

import java.awt.Shape
import java.awt.Stroke
import java.awt.BasicStroke
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

case class StrokeShape(stroke:Stroke, shape:Shape) extends Figure {
	// TODO slow
	def pick(at:Point2D):Boolean	=
			stroke createStrokedShape shape contains at
		
	val bounds:Rectangle2D	= 
			stroke match {
				case bs:BasicStroke	=>
						val shapeBounds	= shape.getBounds2D
						val strokeSize	= bs.getLineWidth + 2
						new Rectangle2D.Double(
								shapeBounds.getX		- strokeSize,
								shapeBounds.getY		- strokeSize,
								shapeBounds.getWidth	+ 2*strokeSize,
								shapeBounds.getHeight	+ 2*strokeSize)
				case os:Stroke		=>
						val shapeBounds	= (os createStrokedShape shape).getBounds2D
						new Rectangle2D.Double(
								shapeBounds.getX		- 1,
								shapeBounds.getY		- 1,
								shapeBounds.getWidth	+ 2,
								shapeBounds.getHeight	+ 2)
			}
	
	def paint(g:Graphics2D) {
		val old	= g.getStroke
		g	setStroke	stroke
		g	draw		shape
		g	setStroke	old
	}
}
