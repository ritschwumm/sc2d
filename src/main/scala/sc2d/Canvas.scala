package sc2d

import java.awt.{ List=>AwtList, Canvas=>AwtCanvas, _ }
import java.awt.event._
import java.awt.geom._
import javax.swing._

final class Canvas(background:Option[Paint], qualityOverSpeed:Boolean) extends JComponent {
	setOpaque(background.isDefined)
	
	private var figures:Seq[Figure]		= Seq.empty
	private var repaints:Seq[Rectangle]	= Seq.empty
		
	def setFigures(figures:Seq[Figure]) {
		this.figures	= figures
		// TODO slow. check swing coalescing, too.
		repaints foreach repaint
		repaints		= figures map { _.bounds.getBounds }
		repaints foreach repaint
	}
	
	/** paint this component */
	override def paintComponent(graphics:Graphics) {
		// TODO handle opaque
		// Further, if you do not invoker super's implementation you must honor the opaque property,
		// that is if this component is opaque, you must completely fill in the background in a non-opaque color. 
		// super.paintComponent(graphics);
		
		val	g1	= graphics.create()
		try {
			val	g	= g1.asInstanceOf[Graphics2D]
		
			// g setRenderingHint (RenderingHints.KEY_STROKE_CONTROL,	RenderingHints.VALUE_STROKE_DEFAULT)
			// g setRenderingHint (RenderingHints.KEY_STROKE_CONTROL,	RenderingHints.VALUE_STROKE_NORMALIZE)
			// g setRenderingHint (RenderingHints.KEY_STROKE_CONTROL,	RenderingHints.VALUE_STROKE_PURE)
			if (qualityOverSpeed) {
				g setRenderingHint (RenderingHints.KEY_ANTIALIASING, 			RenderingHints.VALUE_ANTIALIAS_ON)
				g setRenderingHint (RenderingHints.KEY_TEXT_ANTIALIASING,		RenderingHints.VALUE_TEXT_ANTIALIAS_ON)	// VALUE_TEXT_ANTIALIAS_GASP
				g setRenderingHint (RenderingHints.KEY_INTERPOLATION,			RenderingHints.VALUE_INTERPOLATION_BICUBIC)
				g setRenderingHint (RenderingHints.KEY_COLOR_RENDERING,		RenderingHints.VALUE_COLOR_RENDER_QUALITY)
				g setRenderingHint (RenderingHints.KEY_ALPHA_INTERPOLATION,	RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY)
			}
			else {
				g setRenderingHint (RenderingHints.KEY_ANTIALIASING, 			RenderingHints.VALUE_ANTIALIAS_OFF)
				g setRenderingHint (RenderingHints.KEY_TEXT_ANTIALIASING,		RenderingHints.VALUE_TEXT_ANTIALIAS_OFF)
				g setRenderingHint (RenderingHints.KEY_INTERPOLATION,			RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR)
				g setRenderingHint (RenderingHints.KEY_COLOR_RENDERING,		RenderingHints.VALUE_COLOR_RENDER_SPEED)
				g setRenderingHint (RenderingHints.KEY_ALPHA_INTERPOLATION,	RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED)
			}

			if (background.isDefined) {
				g setPaint	background.get
				g fill		g.getClipBounds
			}
			
			// TODO only paint if figures repaint intersects clip bounds?
			val iter	= figures.iterator
			while (iter.hasNext) {
				iter.next paint g
			}
		}
		finally {
			g1.dispose()
		}
	}

	// repaint on bounds changes
	this addComponentListener new ComponentListener {
		def componentHidden(ev:ComponentEvent)	{}
		def componentMoved(ev:ComponentEvent)	{}
		def componentResized(ev:ComponentEvent)	{ repaint() }
		def componentShown(ev:ComponentEvent)	{}
    }
}
