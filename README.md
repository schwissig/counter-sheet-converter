# Counter Sheet Converter
This app is used to convert a counter sheet image into the individual counter images present in the counter sheet.

## How to Use
1. Install Java 17 JRE if it is not installed.
2. Verify you can run Java by opening a Terminal/PowerShell window and typing the command "java".
3. Build the artifact by running the ```shadowJar``` Gradle task.
4. Enter the command to run the program, with given inputs: ```java -jar counter-sheet-converter-<version>-all.jar```

Please see the `src/test/resources` directory for an example of the format for a JSON data file to be used for the program input.

## Notes
1. See the example of the JSON data file format in the accompanying ```Objective_Schmidt_Sample.zip``` file.
2. The horizontal alignment of the ```backImageFile``` is based on the ```width``` value of the counter sheet PDF file. This sometimes doesn't always match the exact PDF document width. Adjust it lower if the back images do not align.
3. Values in the JSON data file are based on inches.
4. Adjust the ```dpi``` value for higher or lower resolution counter images.

## Change Log

### 1.1.1
1. Make sure all counter names are unique, which is a requirement for all images in Vassal modules.

### 1.1.0
1. Added a user interface for easier accessibility.

### 1.0.1
1. Added highlighting ability for counters using the top-level "highlightingDepth" variable, set to a depth of inches.