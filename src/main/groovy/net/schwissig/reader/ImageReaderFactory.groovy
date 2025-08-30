package net.schwissig.reader

class ImageReaderFactory {

    static IImageReader getReader(String fileExt) {
        switch(fileExt.toLowerCase()) {
            case "pdf" -> {
                new PdfBoxImageReader()
            }
            case "png" -> {
                throw new UnsupportedOperationException("PNG not yet supported.");
            }
            default -> throw new IllegalArgumentException("Could not file valid image reader.");
        }
    }
}
