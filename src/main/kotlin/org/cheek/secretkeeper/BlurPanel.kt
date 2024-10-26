package org.cheek.secretkeeper

import java.awt.Graphics
import java.awt.image.BufferedImage
import java.awt.image.ConvolveOp
import java.awt.image.Kernel
import javax.swing.JPanel
import com.intellij.util.ui.ImageUtil

class BlurPanel : JPanel() {
    private var blurredImage: BufferedImage? = null

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        if (blurredImage == null || blurredImage?.width != width || blurredImage?.height != height) {
            createBlurredImage()
        }
        blurredImage?.let { g.drawImage(it, 0, 0, null) }
    }

    private fun createBlurredImage() {
        val image = ImageUtil.createImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val g2d = image.createGraphics()
        g2d.color = background
        g2d.fillRect(0, 0, width, height)
        g2d.dispose()

        val kernel = floatArrayOf(
            1/9f, 1/9f, 1/9f,
            1/9f, 1/9f, 1/9f,
            1/9f, 1/9f, 1/9f,
        )
        val op = ConvolveOp(Kernel(3, 3, kernel), ConvolveOp.EDGE_NO_OP, null)
        blurredImage = op.filter(image, null)
    }
}