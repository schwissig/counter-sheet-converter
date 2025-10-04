package net.schwissig.processing

import java.awt.*
import java.awt.image.BufferedImage

class ImageEdgeHighlighter {

    static BufferedImage process(int pixelsToHighlight, BufferedImage image) {
        if (pixelsToHighlight && pixelsToHighlight > 0) {
            int width = image.getWidth()
            int height = image.getHeight()

            Graphics2D sheetGraphics = image.createGraphics()

            sheetGraphics.setStroke(new BasicStroke(1))
            for (int i = 0; i < pixelsToHighlight; i++) {
                int alpha = 255 - (int) ((i / pixelsToHighlight) * 255)

                Color lightHighlightColor = new Color(255, 255, 255, alpha)
                sheetGraphics.setColor(lightHighlightColor)
                sheetGraphics.drawLine(
                        0 + i,
                        0 + i,
                        0 + i,
                        height - i
                )
                sheetGraphics.drawLine(
                    0 + i,
                    0 + i,
                    width - i,
                    0 + i
                )

                Color darkHighlightColor = new Color(0, 0, 0, alpha)
                sheetGraphics.setColor(darkHighlightColor)
                sheetGraphics.drawLine(
                        width - i,
                        0 + i,
                        width - i,
                        height - i
                )
                sheetGraphics.drawLine(
                        0 + i,
                        height - i,
                        width - i,
                        height - i
                )
            }

            sheetGraphics.dispose()

            return image
        } else {
            return image
        }
    }
}
