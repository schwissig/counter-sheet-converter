# Counter Sheet Converter
This app is used to convert a counter sheet image into the individual counter images present in the counter sheet.

## How to Use
1. Install Java 17 JRE if it is not installed.
2. Verify you can run Java by opening a Terminal/PowerShell window and typing the command "java".
3. Enter the command to run the program, with given inputs: ```java -Xms6g -jar counter-sheet-converter-<version>-all.jar <data-file.json> <output-directory>```

Please see the `src/test/resources` directory for an example of the format for a JSON data file to be used for the program input.

## Change Log

### 1.0.1
1. Added highlighting ability for counters using the top-level "highlightingDepth" variable, set to a depth of inches.
