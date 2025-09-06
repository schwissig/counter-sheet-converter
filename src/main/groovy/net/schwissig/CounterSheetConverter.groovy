package net.schwissig

import com.google.gson.Gson
import net.schwissig.model.Counter
import net.schwissig.model.CounterSheet
import net.schwissig.model.CounterSheetData
import net.schwissig.reader.CounterSheetReader

/**
 * Converts counter sheet images (front and back pairs) to
 */
class CounterSheetConverter {

    static void convert(String counterSheetDirPath, String counterOutputPath) throws FileNotFoundException {
        File jsonFile = new File(counterSheetDirPath)

        CounterSheetData counterSheetData = new Gson().fromJson(new FileReader(jsonFile), CounterSheetData.class)

        // Process counter per sheet.
        for (CounterSheet counterSheet : counterSheetData.getCounterSheets()) {
            // Read counters from counter sheet.
            List<Counter> counters = new CounterSheetReader(jsonFile.getParent(), counterSheetData, null).read()

            // Manipulate counters
            int pixelsToRound = (int) (counterSheetData.getCornerRounding() * counterSheet.getDpi())
            if (pixelsToRound > 0) {
                for (Counter counter : counters) {
                    if (counter.getFrontImage()) {
                        counter.setFrontImage(ImageRounder.process(pixelsToRound, counter.getFrontImage()))
                    }
                    if (counter.getBackImage()) {
                        counter.setBackImage(ImageRounder.process(pixelsToRound, counter.getBackImage()))
                    }
                }
            }



            // Write counters to files.
            new CounterWriter(counterOutputPath, counters).write()
        }
    }
}

