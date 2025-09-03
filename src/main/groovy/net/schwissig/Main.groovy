package net.schwissig

class Main {

    static void main(String[] args) {
        if (args.length != 2) {
            println "Error: Not enough arguments."
            println "Usage:"
            println "   java -jar counter-sheet-converter.jar <counter-sheet-data.json> <output-directory>"
            return
        }

        try {
            CounterSheetConverter.convert(args[0], args[1])
        } catch (Exception e) {
            println "Error: ${e.getMessage()}"
        }
    }
}
