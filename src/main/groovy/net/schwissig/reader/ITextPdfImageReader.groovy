package net.schwissig.reader

import java.awt.image.BufferedImage

class ITextPdfImageReader implements IImageReader {

    @Override
    public Optional<BufferedImage> readImage(File sourceFile) {

        /*new File(dest).getParentFile().mkdirs();
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(IMAGE_TYPES), new PdfWriter(new ByteArrayOutputStream()));
        IEventListener listener = new Listing_15_31_MyImageRenderListener(DEST);
        PdfCanvasProcessor parser = new PdfCanvasProcessor(listener);
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            parser.processPageContent(pdfDoc.getPage(i));
        }
        pdfDoc.close();*/

        return Optional.empty();
    }
}
