package net.schwissig

import net.schwissig.model.Counter

import javax.imageio.ImageIO
import java.awt.image.RenderedImage

class CounterWriter {

    protected String outputPath

    protected String counterPrefix

    protected List<Counter> counters

    CounterWriter(String outputPath, String counterPrefix, List<Counter> counters) {
        this.outputPath = outputPath
        this.counterPrefix = counterPrefix
        this.counters = counters
    }

    void write() {
        File outputDirectory = new File(outputPath)
        if (!outputDirectory.exists()) {
            outputDirectory.mkdir()
        }

        for (Counter counter : counters) {
            if (counter.getFrontImage()) {
                File frontImage = new File(outputPath, counterPrefix + counter.getName() + "_front.png")
                writeImageFile(frontImage, counter.getFrontImage())
            }

            if (counter.getBackImage()) {
                File backImageFile = new File(outputPath, counterPrefix + counter.getName() + "_back.png")
                writeImageFile(backImageFile, counter.getBackImage())
            }
        }
    }

    private static void writeImageFile(File fileToWrite, RenderedImage imageToWrite) {
        try {
            ImageIO.write(imageToWrite, "png", fileToWrite)
        } catch (IOException e) {
            throw new RuntimeException(e)
        }
    }
}
