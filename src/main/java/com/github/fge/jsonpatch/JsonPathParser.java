package com.github.fge.jsonpatch;

public class JsonPathParser {

    private static final String ARRAY_ELEMENT_REGEX = "\\.(\\d+)\\.";
    private static final String ARRAY_ELEMENT_LAST_REGEX = "\\.(\\d+)$";

    public static String tmfStringToJsonPath(String path) {
        if (!path.startsWith("/") && !path.isEmpty()) {
            return "$." + path;
        }
        if ("/".equals(path)) {
            return "$";
        }
        final String[] pointerAndQuery = path
                .replaceAll("(\\w)\\?", "$1#")
                .split("#", -1);
        if (pointerAndQuery.length > 2) {
            // TODO use different exception
            throw new RuntimeException("Invalid query, only one `?` allowed.");
        }

        final String jsonPath = "$" + pointerAndQuery[0].replace('/', '.')
                .replaceAll(ARRAY_ELEMENT_REGEX, ".[$1].")
                .replaceAll(ARRAY_ELEMENT_REGEX, ".[$1].") // has to be repeated due to positive lookahead not working properly
                .replaceAll(ARRAY_ELEMENT_LAST_REGEX, ".[$1]");
        final String jsonPathWithQuery = addQueryIfApplicable(jsonPath, pointerAndQuery);
        return jsonPathWithQuery;
    }

    private static String addQueryIfApplicable(String jsonPath, String[] pointerAndQuery) {
        if (pointerAndQuery.length == 2) {
            String preparedFilter = pointerAndQuery[1]
                    .replaceAll("]", "] empty false") // add empty false to nested array expressions
                    .replaceAll("(\\w)=(\\w)", "$1==$2") // replace single equals with double
                    .replaceAll("==([\\w .]+)", "=='$1'") // surround strings with single quotes
                    .replaceFirst("\\w+", "@") // jsonpath expression should start with @ as the name of item
                    .replaceAll("([&|])\\w+", " $1$1 @"); // replace single | and & with doubles
            String filterWithBooleansAndNumbers = preparedFilter
                    .replaceAll("@([\\w.]+)=='(true|false)'", "(@$1==$2 || @$1=='$2')") // prepare a statement for boolean and boolean as string
                    .replaceAll("@([\\w.]+)=='(\\d+)'", "(@$1==$2 || @$1=='$2')") // prepare a statement for an integer and integer as string
                    .replaceAll("@([\\w.]+)=='(\\d+\\.\\d+)'", "(@$1==$2 || @$1=='$2')"); // prepare a statement for float and float as string
            return jsonPath.replaceFirst("(\\w+)", "$1[?(" + filterWithBooleansAndNumbers + ")]");
        } else {
            return jsonPath;
        }
    }
}
