package net.schwissig

import net.schwissig.model.Counter

import javax.imageio.ImageIO

class CounterWriter {

    protected String outputPath

    protected List<Counter> counters

    CounterWriter(String outputPath, List<Counter> counters) {
        this.outputPath = outputPath
        this.counters = counters
    }

    void write() {
        for (Counter counter : counters) {
            File frontImage = new File(outputPath, counter.getName() + "_front.png")
            try {
                ImageIO.write(counter.getFrontImage(), "png", frontImage)
            } catch (IOException e) {
                throw new RuntimeException(e)
            }

            /*File backImage = new File(outputPath + counter.getName() + "_back.png")
            try {
                ImageIO.write(counter.getBackImage(), "png", backImage)
            } catch (IOException e) {
                throw new RuntimeException(e)
            }*/
        }
    }
}
