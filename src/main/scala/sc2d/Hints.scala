package sc2d

import java.util.{ Map as JMap }
import java.awt.Toolkit
import java.awt.RenderingHints

import scala.jdk.CollectionConverters.*

object Hints {
	import RenderingHints.*

	val empty:Hints	= Hints(Map.empty)

	def of(values:(RenderingHints.Key, AnyRef)*):Hints	= Hints(values.toMap)

	def desktop:Option[Hints]	=
		Option(Toolkit.getDefaultToolkit getDesktopProperty "awt.font.desktophints") map { it =>
			Hints(it.asInstanceOf[JMap[RenderingHints.Key, AnyRef]].asScala.toMap)
		}

	// KEY_STROKE_CONTROL	-> VALUE_STROKE_DEFAULT, VALUE_STROKE_NORMALIZE, VALUE_STROKE_PURE

	val highQuality:Hints	=
		of(
			KEY_ANTIALIASING		-> VALUE_ANTIALIAS_ON,
			KEY_TEXT_ANTIALIASING	-> VALUE_TEXT_ANTIALIAS_ON,
			KEY_INTERPOLATION		-> VALUE_INTERPOLATION_BICUBIC,
			KEY_COLOR_RENDERING		-> VALUE_COLOR_RENDER_QUALITY,
			KEY_ALPHA_INTERPOLATION	-> VALUE_ALPHA_INTERPOLATION_QUALITY
		)

	val lowQuality:Hints	=
		of(
			KEY_ANTIALIASING		-> VALUE_ANTIALIAS_OFF,
			KEY_TEXT_ANTIALIASING	-> VALUE_TEXT_ANTIALIAS_OFF,
			KEY_INTERPOLATION		-> VALUE_INTERPOLATION_NEAREST_NEIGHBOR,
			KEY_COLOR_RENDERING		-> VALUE_COLOR_RENDER_SPEED,
			KEY_ALPHA_INTERPOLATION	-> VALUE_ALPHA_INTERPOLATION_SPEED
		)
}

final case class Hints(values:Map[RenderingHints.Key, AnyRef]) {
	val asJava:JMap[RenderingHints.Key, AnyRef]	= values.asJava
}
