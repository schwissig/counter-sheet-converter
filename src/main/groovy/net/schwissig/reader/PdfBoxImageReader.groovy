package net.schwissig.reader

import org.apache.pdfbox.Loader
import org.apache.pdfbox.cos.COSName
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDResources
import org.apache.pdfbox.pdmodel.graphics.PDXObject
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.apache.pdfbox.rendering.PDFRenderer

import java.awt.image.BufferedImage
import java.awt.image.RenderedImage

class PdfBoxImageReader implements IImageReader {

    /**
     * {@inheritDoc}
     */
    @Override
    Optional<BufferedImage> readImage(File sourceFile, float dpi, int pageIndex) throws IOException {

        PDDocument pdDocument = Loader.loadPDF(sourceFile)
        PDFRenderer pdfRenderer = new PDFRenderer(pdDocument)
        Optional<BufferedImage> image = Optional.of(pdfRenderer.renderImageWithDPI(pageIndex, dpi))
        pdDocument.close()

        return image
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Optional<BufferedImage> readImage(File sourceFile, float dpi) throws IOException {
        return readImage(sourceFile, dpi, 0)
    }

    private List<RenderedImage> getImagesFromResources(PDResources resources) throws IOException {
        List<RenderedImage> images = new ArrayList<>();

        for (COSName xObjectName : resources.getXObjectNames()) {
            PDXObject xObject = resources.getXObject(xObjectName);

            if (xObject instanceof PDFormXObject) {
                images.addAll(getImagesFromResources(((PDFormXObject) xObject).getResources()));
            } else if (xObject instanceof PDImageXObject) {
                images.add(((PDImageXObject) xObject).getImage());
            }
        }

        return images;
    }
}
