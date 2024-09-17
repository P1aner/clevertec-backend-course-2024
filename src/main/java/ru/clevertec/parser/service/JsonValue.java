package ru.clevertec.parser.service;


import ru.clevertec.parser.exception.IncorrectJsonStringException;
import ru.clevertec.parser.service.api.JsonToMap;
import ru.clevertec.parser.service.chain.ColonChain;
import ru.clevertec.parser.service.chain.CommaChain;
import ru.clevertec.parser.service.chain.CurlyBraceChain;
import ru.clevertec.parser.service.chain.JsonAndMapContainer;
import ru.clevertec.parser.service.chain.OtherChain;
import ru.clevertec.parser.service.chain.QuoteChain;
import ru.clevertec.parser.service.chain.SquareBracketChain;

import java.util.ArrayList;
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

    @Override
    public Map<String, Object> parseToMap(String jsonString) {
        String substring = jsonString.trim().substring(1, jsonString.trim().length() - 1);
        String replace = substring.replace('\n', ' ');
        StringBuilder jsonStringWithoutCurlyBraces = new StringBuilder(replace);
        return parseToMap(jsonStringWithoutCurlyBraces);
    }


    public static Map<String, Object> parseToMap(StringBuilder jsonStringWithoutCurlyBraces) {
        JsonAndMapContainer jsonAndMapContainer = new JsonAndMapContainer(jsonStringWithoutCurlyBraces);
        while (jsonAndMapContainer.hasNext()) {//todo цепочка
            QuoteChain.execute(jsonAndMapContainer);
            ColonChain.execute(jsonAndMapContainer);
            CommaChain.execute(jsonAndMapContainer);
            CurlyBraceChain.execute(jsonAndMapContainer);
            SquareBracketChain.execute(jsonAndMapContainer);
            OtherChain.execute(jsonAndMapContainer);
        }
        return jsonAndMapContainer.getMap();
    }


    public static List<Object> stringJsonArrayToList(String string) {
        List<Object> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder(string.trim());
        StringBuilder value = new StringBuilder();

        while (!stringBuilder.isEmpty()) {
            char charAt = stringBuilder.charAt(0);
            stringBuilder.deleteCharAt(0);
            if (charAt == QUOTE) {
                list.add(excludeStringValue(stringBuilder).trim());
                value = new StringBuilder();
            } else if (charAt == CURLY_BRACE_START) {
                list.add(parseToMap(new StringBuilder(excludeObjectString(stringBuilder))));
                value = new StringBuilder();
            } else if (charAt == SQUARE_BRACKET_START) {
                list.add(stringJsonArrayToList(excludeArrayString(stringBuilder)));
                value = new StringBuilder();
            } else if (charAt == COMMA && !value.isEmpty()) {
                list.add(value.toString().trim());
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

    public static String excludeObjectString(StringBuilder allStringJson) {
        return excludeStringValue(allStringJson, CURLY_BRACE_START, CURLY_BRACE_END);
    }

    public static String excludeStringValue(StringBuilder allStringJson) {
        return excludeStringValue(allStringJson, QUOTE, QUOTE);
    }

    public static String excludeArrayString(StringBuilder allStringJson) {
        return excludeStringValue(allStringJson, SQUARE_BRACKET_START, SQUARE_BRACKET_END);
    }

    public static String excludeStringValue(StringBuilder allStringJson, char startChar, char endChar) {
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