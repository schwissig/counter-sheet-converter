package net.schwissig.reader

import net.schwissig.model.Counter
import net.schwissig.model.CounterFormat
import net.schwissig.model.CounterSection
import net.schwissig.model.CounterSheet
import net.schwissig.model.CounterSheetData

import java.awt.*
import java.awt.geom.RoundRectangle2D
import java.awt.image.BufferedImage
import java.util.List

class CounterSheetReader {

    protected String workingDir

    protected CounterSheetData counterSheetData

    protected CounterFormat counterFormat

    CounterSheetReader(
            String workingDir,
            CounterSheetData counterSheetData,
            CounterFormat counterFormat
    ) {
        this.workingDir = workingDir
        this.counterSheetData = counterSheetData
        this.counterFormat = counterFormat
    }

    List<Counter> read() {
        List<Counter> counters = new ArrayList<>()

        int counterSheetIndex = 1
        for (CounterSheet counterSheet : counterSheetData.getCounterSheets()) {

            String counterSheetFrontPath = workingDir + "/" + counterSheet.getFrontImageFile()
            String frontSheetFileExt = counterSheetFrontPath.substring(counterSheetFrontPath.lastIndexOf(".") + 1)
            Optional<BufferedImage> frontImage
            try {
                frontImage = ImageReaderFactory.getReader(frontSheetFileExt).readImage(new File(counterSheetFrontPath))
            } catch (IOException e) {
                throw new RuntimeException(e)
            }

            if (frontImage.isPresent()) {

                int sectionIndex = 0
                for (CounterSection counterSection : counterSheet.getCounterSections()) {
                    int dpi = counterSheet.getDpi()
                    int totalCounters = counterSection.getColumns() * counterSection.getRows()

                    for (int counterIndex = 0; counterIndex < totalCounters; counterIndex++) {
                        int column = counterIndex % counterSection.getColumns()
                        int row = counterIndex / counterSection.getColumns()

                        Counter counter = new Counter()

                        BufferedImage counterFrontImage = frontImage.get().getSubimage(
                                (int) (dpi * (counterSection.getX() + (counterSection.getCounterSize() * column))),
                                (int) (dpi * (counterSection.getY() + (counterSection.getCounterSize() * row))),
                                (int) (dpi * counterSection.getCounterSize()),
                                (int) (dpi * counterSection.getCounterSize())
                        )

                        counter.setName("Sheet" + counterSheetIndex +
                                "_Section" + (sectionIndex + 1) +
                                "_Counter" + (counterIndex + 1))

                        if (counterSheet.getCornerRounding() != 0.0) {
                            int cornerRoundingPixels = (int) (dpi * counterSheet.getCornerRounding())
                            counter.setFrontImage(makeRoundedCorner(counterFrontImage, cornerRoundingPixels))
                        } else {
                            counter.setFrontImage(counterFrontImage)
                        }

                        counters.add(counter)
                    }

                    sectionIndex++
                }
            } else {
                throw new RuntimeException(String.format("Front image file not found in file [%s]", counterSheetFrontPath))
            }

            /*String counterSheetBackPath = workingDir + "/" + counterSheet.getBackImageFile()
            String backSheetFileExt = counterSheetBackPath.substring(counterSheetBackPath.lastIndexOf(".") + 1)
            Optional<BufferedImage> backImage
            try {
                backImage = ImageReaderFactory.getReader(backSheetFileExt).readImage(new File(counterSheetBackPath))
            } catch (IOException e) {
                throw new RuntimeException(e)
            }

            if (backImage.isPresent()) {

                int sectionIndex = 0
                for (CounterSection counterSection : counterSheet.getCounterSections()) {
                    int dpi = counterSheet.getDpi()
                    int totalCounters = counterSection.getColumns() * counterSection.getRows()

                    for (int counterIndex = 0; counterIndex < totalCounters; counterIndex++) {
                        int column = counterIndex % counterSection.getColumns()
                        int row = counterIndex / counterSection.getColumns()

                        Counter counter = new Counter()

                        BufferedImage counterBackImage = backImage.get().getSubimage(
                            (int) (dpi * (counterSection.getX() + (counterSection.getCounterSize() * column))),
                            (int) (dpi * (counterSection.getY() + (counterSection.getCounterSize() * row))),
                            (int) (dpi * counterSection.getCounterSize()),
                            (int) (dpi * counterSection.getCounterSize())
                        )

                        counter.setName("Sheet" + counterSheetIndex +
                            "_Section" + (sectionIndex + 1) +
                            "_Counter" + (counterIndex + 1))

                        if (counterSheet.getCornerRounding() != 0.0) {
                            int cornerRoundingPixels = (int) (dpi * counterSheet.getCornerRounding())
                            counter.setBackImage(makeRoundedCorner(counterBackImage, cornerRoundingPixels))
                        } else {
                            counter.setBackImage(counterBackImage)
                        }

                        counters.add(counter)
                    }

                    sectionIndex++
                }
            } else {
                throw new RuntimeException(String.format("Back image file not found in file [%s]", counterSheetBackPath))
            }*/

            counterSheetIndex++
        }

        return counters
    }

    static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth()
        int h = image.getHeight()
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)

        Graphics2D g2 = output.createGraphics()

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)

        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.setColor(Color.WHITE)
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius))

        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop)
        g2.drawImage(image, 0, 0, null)

        g2.dispose()

        return output
    }
}
