package net.schwissig.reader

import java.awt.image.BufferedImage

interface IImageReader {

    /**
     * @param sourceFile File to read the image from.
     * @param dpi        DPI to use when reading the file.
     * @param pageIndex  Page index of the page to read the image from.
     * @return {@link BufferedImage} of the file read.
     */
    Optional<BufferedImage> readImage(File sourceFile, float dpi, int pageIndex) throws IOException;

    /**
     * @param sourceFile File to read the image from.
     * @param dpi        DPI to use when reading the file.
     * @return {@link BufferedImage} of the file read.
     */
    Optional<BufferedImage> readImage(File sourceFile, float dpi) throws IOException;
}