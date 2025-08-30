package net.schwissig

class Utils {

    static String normalizeFileSeparator(String path) {
        return path
                .replace("\\", File.separator)
                .replace("/", File.separator)
    }
}
