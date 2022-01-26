package com.github.fge.jsonpatch;

public class JsonPathParser {

    public static String tmfStringToJsonPath(String path) {
        if ("/".equals(path)) {
            return "$";
        }
        final String jsonPath = "$" + path.replace('/', '.')
                .replaceAll("\\.(\\d+)\\.", ".[$1].")
                .replaceAll("\\.(\\d+)$", ".[$1]");
        return jsonPath;
    }
}
