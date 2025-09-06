package net.schwissig.processing

import java.awt.Color
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

class ImageEdgeHighlighter {

    static BufferedImage process(int pixelsToHighlight, BufferedImage image) {
        Graphics2D sheetGraphics = image.createGraphics()

        sheetGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        sheetGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)

        int width = image.getWidth()
        int height = image.getHeight()

        for (int i=0; i < pixelsToHighlight; i++) {
            int alpha = (int) (i / pixelsToHighlight * 255)

            Color darkHighlightColor = new Color(255, 255, 255, alpha)
            Color lightHighlightColor = new Color(0, 0, 0, alpha)

            
        }
    }
}
