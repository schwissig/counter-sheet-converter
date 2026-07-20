# Counter Sheet Converter
This app is used to convert a counter sheet image into the individual counter images present in the counter sheet.

## How to Use
1. Install Java 17 JRE if it is not installed.
2. Verify you can run Java by opening a Terminal/PowerShell window and typing the command "java".
3. Build the artifact by running the ```shadowJar``` Gradle task.
4. Enter the command to run the program, with given inputs: ```java -jar build/counter-sheet-converter-<version>-all.jar```

Please see the `src/test/resources` directory for an example of the format for a JSON data file to be used for the program input.

## Change Log

### 1.1.0
1. Added a user interface for easier accessibility.

### 1.0.1
1. Added highlighting ability for counters using the top-level "highlightingDepth" variable, set to a depth of inches.
