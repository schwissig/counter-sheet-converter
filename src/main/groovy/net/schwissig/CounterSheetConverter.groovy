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
        String userHome = System.getProperty("user.home")

        String normalizedJsonFilePath = Utils.normalizeFileSeparator(counterSheetDirPath)

        File jsonFile = normalizedJsonFilePath.startsWith(File.separator) ?
                new File(userHome + normalizedJsonFilePath) :
                new File(userHome + File.separator + normalizedJsonFilePath)

        CounterSheetData counterSheetData = new Gson().fromJson(new FileReader(jsonFile), CounterSheetData.class)

        // Read counters from counter sheet.
        List<Counter> counters = new CounterSheetReader(jsonFile.getParent(), counterSheetData, null).read()

        // Write counters to files.
        String normalizedOutputPath = Utils.normalizeFileSeparator(counterOutputPath)

        String absoluteOutputPath = normalizedOutputPath.startsWith(File.separator) ?
                userHome + normalizedOutputPath :
                userHome + File.separator + normalizedOutputPath

        absoluteOutputPath = absoluteOutputPath.endsWith(File.separator) ?: absoluteOutputPath + File.separator

        new CounterWriter(absoluteOutputPath, counters).write()
    }
}

