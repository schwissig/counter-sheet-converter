package net.schwissig.reader

import java.awt.image.BufferedImage

interface IImageReader {

    /**
     * @param sourceFile File to read the image from.
     * @return {@link BufferedImage} of the file read.
     */
    Optional<BufferedImage> readImage(File sourceFile) throws IOException;
}