package sc2d

import java.awt.Shape
import java.awt.Composite
import java.awt.Graphics2D
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D
import java.awt.geom.AffineTransform

trait Figure {
	def pick(point:Point2D):Boolean

	// def intersects(that:Figure):Boolean
	def bounds:Rectangle2D

	def paint(g:Graphics2D):Unit

	// TODO do we want  separation of Paintable and Pickable?
	final def pickOnly:Figure	= PickOnly(this)

	final def withClip(clip:Shape):Figure						= WithClip		(clip,		this)
	final def withComposite(composite:Composite):Figure			= WithComposite	(composite,	this)
	final def withTransform(transform:AffineTransform):Figure	= WithTransform	(transform,	this)
	final def withHints(hints:Hints):Figure						= WithHints		(hints,		this)
}
