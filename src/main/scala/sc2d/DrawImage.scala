package sc2d

import java.awt.Rectangle
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage

case class DrawImage(image:BufferedImage, x:Int, y:Int) extends Figure {
	def pick(at:Point2D):Boolean	= {
		val atX	= at.getX.toInt - x
		val atY	= at.getY.toInt - y
		atX >= 0 && atX < image.getWidth	&&
		atY >= 0 && atY < image.getHeight	&&
		((image getRGB (atX, atY)) & 0xff000000L) != 0
	}
		
	val bounds:Rectangle2D	= 
			new Rectangle(
					x,
					y,
					image.getWidth,
					image.getHeight)
	
	def paint(g:Graphics2D) {
		g	drawImage	(image, x, y, null)
	}
}
