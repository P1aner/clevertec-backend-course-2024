package ru.clevertec.parser.utils;

import ru.clevertec.parser.exception.IncorrectJsonStringException;

import static ru.clevertec.parser.utils.DefaultJsonChars.CURLY_BRACE_END;
import static ru.clevertec.parser.utils.DefaultJsonChars.CURLY_BRACE_START;
import static ru.clevertec.parser.utils.DefaultJsonChars.QUOTE;
import static ru.clevertec.parser.utils.DefaultJsonChars.SQUARE_BRACKET_END;
import static ru.clevertec.parser.utils.DefaultJsonChars.SQUARE_BRACKET_START;

public class StringExtractor {

    public static String extractObjectString(StringBuilder allStringJson) {
        return extractStringValue(allStringJson, CURLY_BRACE_START, CURLY_BRACE_END);
    }

    public static String extractStringValue(StringBuilder allStringJson) {
        return extractStringValue(allStringJson, QUOTE, QUOTE);
    }

    public static String extractArrayString(StringBuilder allStringJson) {
        return extractStringValue(allStringJson, SQUARE_BRACKET_START, SQUARE_BRACKET_END);
    }

    public static String extractStringValue(StringBuilder allStringJson, char startChar, char endChar) {
        StringBuilder excludedObject = new StringBuilder();
        int bracesCount = 1;
        char charAt;
        while (bracesCount > 0 && !allStringJson.isEmpty()) {
            charAt = allStringJson.charAt(0);
            if (charAt == endChar) bracesCount--;
            else if (charAt == startChar) bracesCount++;
            excludedObject.append(charAt);
            allStringJson.deleteCharAt(0);
        }
        if (bracesCount > 0) throw new IncorrectJsonStringException("Closing symbol not found");
        excludedObject.deleteCharAt(excludedObject.length() - 1);
        return excludedObject.toString();
    }
}
