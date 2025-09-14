package net.schwissig

import com.google.gson.Gson
import net.schwissig.model.Counter
import net.schwissig.model.CounterSheet
import net.schwissig.model.CounterSheetData
import net.schwissig.processing.ImageEdgeHighlighter
import net.schwissig.processing.ImageRounder
import net.schwissig.reader.CounterSheetReader

/**
 * Converts counter sheet images (front and back pairs) to
 */
class CounterSheetConverter {

    static void convert(String counterSheetDirPath, String counterOutputPath) throws FileNotFoundException {
        File jsonFile = new File(counterSheetDirPath)

        // Read the JSON metadata file.
        CounterSheetData counterSheetData = new Gson().fromJson(new FileReader(jsonFile), CounterSheetData.class)

        // Process counter per sheet.
        for (CounterSheet counterSheet : counterSheetData.getCounterSheets()) {
            // Read counters from counter sheet.
            List<Counter> counters = new CounterSheetReader(jsonFile.getParent(), counterSheetData, null).read()

            int pixelHighlightingDepth = (int) (counterSheetData.getHighlightingDepth() * counterSheet.getDpi())
            int pixelsToRound = (int) (counterSheetData.getCornerRounding() * counterSheet.getDpi())

            for (Counter counter : counters) {
                if (counter.getFrontImage()) {
                    // Process counter highlighting and shadow effect.
                    counter.setFrontImage(ImageEdgeHighlighter.process(pixelHighlightingDepth, counter.getFrontImage()))

                    // Process counter corner rounding.
                    counter.setFrontImage(ImageRounder.process(pixelsToRound, counter.getFrontImage()))
                }
                if (counter.getBackImage()) {
                    // Process counter highlighting and shadow effect.
                    counter.setBackImage(ImageEdgeHighlighter.process(pixelHighlightingDepth, counter.getBackImage()))

                    // Process counter corner rounding.
                    counter.setBackImage(ImageRounder.process(pixelsToRound, counter.getBackImage()))
                }
            }

            // Write counters to files.
            new CounterWriter(counterOutputPath, counters).write()
        }
    }
}

