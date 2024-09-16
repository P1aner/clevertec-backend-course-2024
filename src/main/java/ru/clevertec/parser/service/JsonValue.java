package ru.clevertec.parser.service;


import ru.clevertec.parser.exception.IncorrectJsonStringException;
import ru.clevertec.parser.service.api.JsonToMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonValue implements JsonToMap {

    public static final char CURLY_BRACE_START = '{';
    public static final char CURLY_BRACE_END = '}';
    public static final char QUOTE = '"';
    public static final char SQUARE_BRACKET_START = '[';
    public static final char SQUARE_BRACKET_END = ']';
    public static final char COMMA = ',';
    public static final char COLON = ':';

    public Map<String, Object> parseToMap(String jsonString) {
        Map<String, Object> map = new HashMap<>();
        StringBuilder jsonStringWithoutCurlyBraces = new StringBuilder(jsonString.trim().substring(1, jsonString.trim().length() - 1));
        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        boolean isKey = true;

        for (int i = 0; i < jsonStringWithoutCurlyBraces.length(); i++) {
            char charAt = jsonStringWithoutCurlyBraces.charAt(i);
            if (charAt == QUOTE) {
                String s = excludeStringValue(jsonStringWithoutCurlyBraces, i);
                String substring = s.substring(1, s.length() - 1);
                i += s.length() - 1;
                if (isKey) {
                    key.append(substring);
                    isKey = false;
                } else {
                    value.append(substring);
                    map.put(key.toString().trim(), value.toString().trim());
                    key = new StringBuilder();
                    value = new StringBuilder();
                    isKey = true;
                }
            } else if (charAt == COLON) {
                value = new StringBuilder();
                isKey = false;
            } else if (charAt == COMMA) {
                if (!value.isEmpty()) {
                    map.put(key.toString().trim(), value.toString().trim());
                    key = new StringBuilder();
                    value = new StringBuilder();
                    isKey = true;
                }
            } else if (charAt == CURLY_BRACE_START) {
                String excluded = excludeObjectString(jsonStringWithoutCurlyBraces, i);
                i += excluded.length() - 1;
                map.put(key.toString().trim(), parseToMap(excluded.trim()));
                key = new StringBuilder();
                value = new StringBuilder();
                isKey = true;
            } else if (charAt == SQUARE_BRACKET_START) {
                String arrayString = excludeArrayString(jsonStringWithoutCurlyBraces, i);
                i += arrayString.length() - 1;
                String substring = arrayString.substring(1, arrayString.length() - 1);
                map.put(key.toString().trim(), stringJsonArrayToList(substring.trim()));
                key = new StringBuilder();
                value = new StringBuilder();
                isKey = true;
            } else {
                value.append(charAt);
            }
            if (!isKey) {
                map.put(key.toString().trim(), value.toString().trim());
            }
        }
        return map;
    }


    private List<Object> stringJsonArrayToList(String string) {
        List<Object> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder(string.trim());
        StringBuilder value = new StringBuilder();

        for (int i = 0; i < stringBuilder.length(); i++) {
            char charAt = stringBuilder.charAt(i);
            if (charAt == QUOTE) {
                String s = excludeStringValue(stringBuilder, i);
                String substring = s.substring(1, s.length() - 1);
                value.append(substring);
                i += s.length() - 1;
                list.add(value.toString().trim());
                value = new StringBuilder();
            } else if (charAt == COMMA && !value.isEmpty()) {
                list.add(value.toString().trim());
                value = new StringBuilder();
            } else if (charAt == CURLY_BRACE_START) {
                String excluded = excludeObjectString(stringBuilder, i);
                i += excluded.length() - 1;
                list.add(parseToMap(excluded));
                value = new StringBuilder();
            } else if (charAt == SQUARE_BRACKET_START) {
                String str = excludeArrayString(stringBuilder, i);
                i += str.length() - 1;
                String substring = str.substring(1, str.length() - 1);
                list.add(stringJsonArrayToList(substring));
                value = new StringBuilder();
            } else {
                value.append(charAt);
            }
        }
        if (!value.isEmpty()) {
            list.add(value.toString().trim());
        }
        return list;
    }

    private String excludeObjectString(StringBuilder allStringJson, int startCharOfString) {
        return excludeStringValue(allStringJson, startCharOfString, CURLY_BRACE_START, CURLY_BRACE_END);
    }

    private String excludeStringValue(StringBuilder allStringJson, int startCharOfString) {
        return excludeStringValue(allStringJson, startCharOfString, QUOTE, QUOTE);
    }

    private String excludeArrayString(StringBuilder allStringJson, int startCharOfString) {
        return excludeStringValue(allStringJson, startCharOfString, SQUARE_BRACKET_START, SQUARE_BRACKET_END);
    }

    private String excludeStringValue(StringBuilder allStringJson, int startCharOfString, char startChar, char endChar) {
        StringBuilder excludedObject = new StringBuilder();
        char charAt = allStringJson.charAt(startCharOfString);
        excludedObject.append(charAt);
        int bracesCount = 1;
        while (bracesCount > 0) {
            startCharOfString++;
            if (startCharOfString >= allStringJson.length())
                throw new IncorrectJsonStringException("Closing symbol not found");
            charAt = allStringJson.charAt(startCharOfString);
            if (charAt == endChar) bracesCount--;
            else if (charAt == startChar) bracesCount++;
            excludedObject.append(charAt);
        }
        return excludedObject.toString();
    }
}