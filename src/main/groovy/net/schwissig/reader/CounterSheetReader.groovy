package net.schwissig.reader

import net.schwissig.model.Counter
import net.schwissig.model.CounterFormat
import net.schwissig.model.CounterSection
import net.schwissig.model.CounterSheet
import net.schwissig.model.CounterSheetData

import java.awt.image.BufferedImage

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

            String counterSheetFrontPath = workingDir + File.separator + counterSheet.getFrontImageFile()
            String frontSheetFileExt = counterSheetFrontPath.substring(counterSheetFrontPath.lastIndexOf(".") + 1)

            String counterSheetBackPath = workingDir + File.separator + counterSheet.getBackImageFile()
            String backSheetFileExt = counterSheetBackPath.substring(counterSheetBackPath.lastIndexOf(".") + 1)

            Optional<BufferedImage> frontImage
            Optional<BufferedImage> backImage
            try {
                frontImage = ImageReaderFactory.getReader(frontSheetFileExt).readImage(
                        new File(counterSheetFrontPath),
                        counterSheet.getDpi(),
                        counterSheet.getFrontImagePage() ? counterSheet.getFrontImagePage() - 1 : 0
                )
                backImage = ImageReaderFactory.getReader(backSheetFileExt).readImage(
                        new File(counterSheetBackPath),
                        counterSheet.getDpi(),
                        counterSheet.getBackImagePage() ? counterSheet.getBackImagePage() - 1 : 0
                )
            } catch (IOException e) {
                throw new RuntimeException(e)
            }

            int sectionIndex = 0
            for (CounterSection counterSection : counterSheet.getCounterSections()) {
                int dpi = counterSheet.getDpi()
                int totalCounters = counterSection.getColumns() * counterSection.getRows()
                double sheetWidth = counterSheet.getWidth()

                for (int counterIndex = 0; counterIndex < totalCounters; counterIndex++) {
                    int column = counterIndex % counterSection.getColumns()
                    int row = (int) (counterIndex / counterSection.getColumns())

                    Counter counter = new Counter()

                    counter.setName("Sheet" + counterSheetIndex + "_Section" + (sectionIndex + 1) + "_Counter" + (counterIndex + 1))

                    BufferedImage frontCounterImage
                    if (frontImage.isPresent()) {
                        frontCounterImage = frontImage.get().getSubimage(
                                (int) (dpi * (counterSection.getX() + (counterSection.getCounterSize() * column))),
                                (int) (dpi * (counterSection.getY() + (counterSection.getCounterSize() * row))),
                                (int) (dpi * counterSection.getCounterSize()),
                                (int) (dpi * counterSection.getCounterSize())
                        )
                        counter.setFrontImage(frontCounterImage)
                    } else {
                        throw new RuntimeException(String.format("Front image file not found in file [%s]", counterSheetFrontPath))
                    }

                    BufferedImage backCounterImage
                    if (backImage.isPresent()) {
                        backCounterImage = backImage.get().getSubimage(
                                (int) (dpi * (sheetWidth - (counterSection.getX() + (counterSection.getCounterSize() * (column + 1))))),
                                (int) (dpi * (counterSection.getY() + (counterSection.getCounterSize() * row))),
                                (int) (dpi * counterSection.getCounterSize()),
                                (int) (dpi * counterSection.getCounterSize())
                        )
                        counter.setBackImage(backCounterImage)
                    } else {
                        throw new RuntimeException(String.format("Back image file not found in file [%s]", counterSheetBackPath))
                    }

                    counters.add(counter)
                }

                sectionIndex++
            }

            counterSheetIndex++
        }

        return counters
    }
}
