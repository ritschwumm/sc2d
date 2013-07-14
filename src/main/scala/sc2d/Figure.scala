package sc2d

import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

trait Figure {
	def pick(point:Point2D):Boolean
	def bounds:Rectangle2D
	def paint(g:Graphics2D):Unit
}
