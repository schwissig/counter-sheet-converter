package net.schwissig.processing

import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.RoundRectangle2D
import java.awt.image.BufferedImage

import static java.awt.RenderingHints.KEY_ANTIALIASING
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON

class ImageRounder {

    /**
     * Round a given corner of an image
     *
     * @param pixelsToRound Pixels to round corners.
     * @param image Image to round corner of.
     * @return Image with corners potentially rounded, based on inputs.
     */
    static BufferedImage process(int pixelsToRound, BufferedImage image) {
        if (pixelsToRound && pixelsToRound > 0) {
            return makeRoundedCorner(image, pixelsToRound)
        } else {
            return image
        }
    }

    static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth()
        int h = image.getHeight()
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)

        Graphics2D sheetGraphics = output.createGraphics()

        sheetGraphics.setComposite(AlphaComposite.Src)
        sheetGraphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
        sheetGraphics.setColor(Color.WHITE)
        sheetGraphics.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius))

        sheetGraphics.setComposite(AlphaComposite.SrcAtop)
        sheetGraphics.drawImage(image, 0, 0, null)

        sheetGraphics.dispose()

        return output
    }
}
