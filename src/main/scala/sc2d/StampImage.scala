package sc2d

import java.awt.Rectangle
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage

case class StampImage(image:BufferedImage, x:Int, y:Int) extends Pickable {
	// TODO check
	def pick(at:Point2D):Boolean	=
			((image getRGB (at.getX.toInt, at.getY.toInt)) & 0xff000000L) != 0
		
	val repaint:Rectangle2D	= 
			new Rectangle(
					x,
					y,
					image.getWidth,
					image.getHeight)
	
	def paint(g:Graphics2D) {
		g	drawImage	(image, x, y, null)
	}
}
