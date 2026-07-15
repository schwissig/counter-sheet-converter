package net.schwissig

import net.schwissig.model.Counter

import javax.imageio.ImageIO
import java.awt.image.RenderedImage

class CounterWriter {

    protected String outputPath

    protected List<Counter> counters

    CounterWriter(String outputPath, List<Counter> counters) {
        this.outputPath = outputPath
        this.counters = counters
    }

    void write() {
        File outputDirectory = new File(outputPath)
        if (!outputDirectory.exists()) {
            outputDirectory.mkdir()
        }

        for (Counter counter : counters) {
            if (counter.getFrontImage()) {
                File frontImage = new File(outputPath, counter.getName() + "_front.png")
                writeImageFile(frontImage, counter.getFrontImage())
            }

            if (counter.getBackImage()) {
                File backImageFile = new File(outputPath, counter.getName() + "_back.png")
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
