package ru.clevertec.parser.service.chain;

import ru.clevertec.parser.service.JsonValue;

import static ru.clevertec.parser.service.JsonValue.CURLY_BRACE_START;
import static ru.clevertec.parser.service.JsonValue.excludeObjectString;

public class CurlyBraceChain {
    public static void execute(JsonAndMapContainer jsonAndMapContainer) {
        if (jsonAndMapContainer.hasNext() && jsonAndMapContainer.getFirsChar() == CURLY_BRACE_START) {
            jsonAndMapContainer.excludeChar();
            jsonAndMapContainer.createNewRow(JsonValue.parseToMap(new StringBuilder(excludeObjectString(jsonAndMapContainer.getDefaultValue()).trim())));
        }
    }
}
