package net.schwissig

import spock.lang.Specification

class CounterSheetConverterSpec extends Specification {

    /*def "Objective Schmidt counter sheets test"() {
        given:
        String userHome = System.getProperty("user.home")

        String jsonDataFile = userHome + "/projects/counter-sheet-converter/src/test/resources/counterSheets/objective_schmidt/os-counter-sheet-data.json"
        String outputDir = userHome + "/projects/counter-sheet-converter/output/counterSheets"

        when:
        CounterSheetConverter.convert(jsonDataFile, outputDir)

        then:
        notThrown(Exception.class)
    }*/

    def "JSON data file does not exist"() {
        given:
        String userHome = System.getProperty("user.home")

        String jsonDataFile = userHome + "/asdf.json"
        String outputDir = userHome + "/projects/counter-sheet-converter/output/counterSheets"

        when:
        CounterSheetConverter.convert(jsonDataFile, outputDir)

        then:
        FileNotFoundException exception = thrown(FileNotFoundException.class)
        exception.getMessage().contains(jsonDataFile)
    }
}
