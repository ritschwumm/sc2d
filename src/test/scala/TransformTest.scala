package sc2d

import minitest._

import java.awt.Color
import java.awt.Rectangle
import java.awt.geom.AffineTransform

object TransformTest extends SimpleTestSuite {
	test("transforming twice should apply transformations in the correct order") {
		val t1	= AffineTransform.getTranslateInstance(100, 50)
		val t2	= AffineTransform.getScaleInstance(20, 10)

		val a	= FillShape(new Rectangle(10, 20, 30, 40), Color.red)
		val b	= a.withTransform(t1)
		val c	= b.withTransform(t2)

		/*
		// 10,20,31,41
		println(a.bounds)
		// 110,70,31,41
		println(b.bounds)
		// 2200,700,620,410
		println(c.bounds)
		*/

		assertEquals(
			c.bounds,
			new Rectangle(2200, 700, 620, 410)
		)
	}
}
