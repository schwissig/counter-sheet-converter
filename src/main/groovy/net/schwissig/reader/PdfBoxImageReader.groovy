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
    Optional<BufferedImage> readImage(File sourceFile) throws IOException {
        PDDocument pdDocument = Loader.loadPDF(sourceFile);
        PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
        Optional<BufferedImage> image = Optional.of(pdfRenderer.renderImageWithDPI(0, 600f));
        pdDocument.close();

        return image;

        /*if (!renderedImages.isEmpty()) {
            return Optional.of(convertToBufferedImage(renderedImages.get(0)));
        } else {
            return Optional.empty();
        }*/
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

    /*List<RenderedImage> getImagesFromPDF(PDDocument document) throws IOException {
        List<RenderedImage> images = new ArrayList<>();
        for (PDPage page : document.getPages()) {
            images.addAll(getImagesFromResources(page.getResources()));
        }

        return images;
    }*/

    /*static BufferedImage convertToBufferedImage(RenderedImage renderedImage) {
        // Check if the RenderedImage is already a BufferedImage
        if (renderedImage instanceof BufferedImage) {
            return (BufferedImage) renderedImage;
        }

        // Get the color model and raster from the RenderedImage
        ColorModel cm = renderedImage.getColorModel();
        int width = renderedImage.getWidth();
        int height = renderedImage.getHeight();
        WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        Hashtable<String, Object> properties = new Hashtable<>();

        // Get the property names from the RenderedImage
        String[] keys = renderedImage.getPropertyNames();
        if (keys != null) {
            for (String key : keys) {
                properties.put(key, renderedImage.getProperty(key));
            }
        }

        // Create a new BufferedImage with the same color model and raster
        BufferedImage bufferedImage = new BufferedImage(cm, raster, isAlphaPremultiplied, properties);

        // Copy the pixel data from the RenderedImage to the BufferedImage
        renderedImage.copyData(raster);

        return bufferedImage;
    }*/
}
