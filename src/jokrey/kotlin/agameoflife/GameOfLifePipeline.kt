package jokrey.kotlin.agameoflife

import jokrey.utilities.animation.engine.AnimationEngine
import jokrey.utilities.animation.pipeline.AnimationDrawer
import jokrey.utilities.animation.pipeline.AnimationPipeline
import jokrey.utilities.animation.util.AEColor
import jokrey.utilities.animation.util.AEPoint
import jokrey.utilities.animation.util.AERect

class GameOfLifePipeline(drawer: AnimationDrawer?) : AnimationPipeline(drawer) {
    override fun drawBackground(drawBounds: AERect?, engine: AnimationEngine?) {
        if(drawBounds!=null) {
            drawer.fillRect(AEColor.DARK_GRAY, AERect(0.0, 0.0, pixelSize.width, pixelSize.height))
            drawer.fillRect(AEColor.WHITE, AERect(drawBounds.getX(), drawBounds.getY(), drawBounds.width, drawBounds.height))
        }
    }
    override fun drawForeground(drawBounds: AERect?, engine: AnimationEngine?) {
        super.drawForeground(drawBounds, engine)
        if(drawBounds!=null && squareEqualsPixels>3) {
            val numberOfLinesW = (drawBounds.width / squareEqualsPixels).toInt() + 1
            val numberOfLinesH = (drawBounds.height / squareEqualsPixels).toInt() + 1
            for (x in 0..numberOfLinesW) {
                val p1 = AEPoint(drawBounds.x + x.toDouble() * squareEqualsPixels - 1, drawBounds.y - 1)
                val p2 = AEPoint(p1.x, p1.y + drawBounds.h)
                drawer.drawLine(AEColor.BLACK, p1, p2)
            }
            for (y in 0..numberOfLinesH) {
                val p1 = AEPoint(drawBounds.x - 1, drawBounds.y + y.toDouble() * squareEqualsPixels - 1)
                val p2 = AEPoint(p1.x+drawBounds.w, p1.y)
                drawer.drawLine(AEColor.BLACK, p1, p2)
            }
        }
    }
}