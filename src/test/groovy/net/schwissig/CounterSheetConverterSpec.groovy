package net.schwissig

import spock.lang.Specification

class CounterSheetConverterSpec extends Specification {

    def "Objective Schmidt counter sheets test"() {
        given:
        String jsonDataFile = "projects/counter-sheet-converter/src/test/resources/counterSheets/objective_schmidt/os-counter-sheet-data.json"
        String outputDir = "projects/counter-sheet-converter/output/counterSheets"

        when:
        CounterSheetConverter.convert(jsonDataFile, outputDir)

        then:
        notThrown(Exception.class)
    }

    def "JSON data file does not exist"() {
        given:
        String jsonDataFile = "asdf.json"
        String outputDir = "projects/counter-sheet-converter/output/counterSheets"

        when:
        CounterSheetConverter.convert(jsonDataFile, outputDir)

        then:
        FileNotFoundException exception = thrown(FileNotFoundException.class)
        exception.getMessage().contains(jsonDataFile)
    }
}
