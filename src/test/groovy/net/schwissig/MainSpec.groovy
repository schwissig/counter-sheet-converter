package net.schwissig

import spock.lang.Specification

class MainSpec extends Specification {

    def "Objective Schmidt counter sheets test"() {
        given:
        String[] args = [
                "projects/counter-sheet-converter/src/test/resources/counterSheets/objective_schmidt/os-counter-sheet-data.json",
                "projects/counter-sheet-converter/output/counterSheets"
        ]

        when:
        Main.main(args)

        then:
        notThrown(Exception.class)
    }
}
