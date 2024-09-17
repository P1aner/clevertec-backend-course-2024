package ru.clevertec.parser.service.chain;

import ru.clevertec.parser.service.JsonValue;

import static ru.clevertec.parser.service.JsonValue.SQUARE_BRACKET_START;
import static ru.clevertec.parser.service.JsonValue.excludeArrayString;

public class SquareBracketChain {
    public static void execute(JsonAndMapContainer jsonAndMapContainer) {
        if (jsonAndMapContainer.hasNext() && jsonAndMapContainer.getFirsChar() == SQUARE_BRACKET_START) {
            jsonAndMapContainer.excludeChar();
            jsonAndMapContainer.createNewRow(JsonValue.stringJsonArrayToList(excludeArrayString(jsonAndMapContainer.getDefaultValue()).trim()));
        }
    }
}
