package sc2d

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Paint
import java.awt.event._
import javax.swing._

// NOTE if background is None the component is considered non-opaque
class Canvas(background:Option[Paint], hints:Hints=Hints.empty, immediate:Boolean=false) extends JComponent {
	setOpaque(background.isDefined)

	private var figures:Seq[Figure]			= Seq.empty
	// private var repaints:Seq[Rectangle]	= Seq.empty

	def setFigures(figures:Seq[Figure]):Unit	= {
		this.figures	= figures
		/*
		repaints foreach repaint
		repaints		= figures map { _.bounds.getBounds }
		repaints foreach repaint
		*/
		// TODO better repainting mechanism
		if (immediate)	paintImmediately(0, 0, getWidth, getHeight)
		else			repaint()
		// TODO do we have to do this after paintImmediately?
		// ensure the gfx pipeline is really painted now
		//Toolkit.getDefaultToolkit().sync()
	}

	/** paint this component */
	override def paintComponent(graphics:Graphics):Unit	= {
		val	g1	= graphics.create()
		try {
			val	g	= g1.asInstanceOf[Graphics2D]
			g addRenderingHints hints.asJava

			val cb	= g.getClipBounds

			background foreach { bg =>
				g setPaint	bg
				g fill		cb
			}

			val iter	= figures.iterator
			while (iter.hasNext) {
				val figure	= iter.next()
				if (figure.bounds intersects cb) {
					figure paint g
				}
			}
		}
		finally {
			g1.dispose()
		}
	}

	// repaint on bounds changes
	this addComponentListener new ComponentListener {
		def componentHidden(ev:ComponentEvent):Unit		= {}
		def componentMoved(ev:ComponentEvent):Unit		= {}
		def componentResized(ev:ComponentEvent):Unit	= { repaint() }
		def componentShown(ev:ComponentEvent):Unit		= {}
    }
}
