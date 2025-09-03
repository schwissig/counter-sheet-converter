package net.schwissig

import com.google.gson.Gson
import net.schwissig.model.Counter
import net.schwissig.model.CounterSheetData
import net.schwissig.reader.CounterSheetReader

/**
 * Converts counter sheet images (front and back pairs) to
 */
class CounterSheetConverter {

    static void convert(String counterSheetDirPath, String counterOutputPath) throws FileNotFoundException {
        File jsonFile = new File(counterSheetDirPath)

        CounterSheetData counterSheetData = new Gson().fromJson(new FileReader(jsonFile), CounterSheetData.class)

        // Read counters from counter sheet.
        List<Counter> counters = new CounterSheetReader(jsonFile.getParent(), counterSheetData, null).read()

        // Write counters to files.
        new CounterWriter(counterOutputPath, counters).write()
    }
}

