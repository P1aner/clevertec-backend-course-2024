package ru.clevertec.parser.service.chain;

import static ru.clevertec.parser.service.JsonValue.COMMA;

public class CommaChain {
    public static void execute(JsonAndMapContainer jsonAndMapContainer) {
        if (jsonAndMapContainer.hasNext() && jsonAndMapContainer.getFirsChar() == COMMA) {
            jsonAndMapContainer.excludeChar();
            if (!jsonAndMapContainer.getValueString().isEmpty()) {
                jsonAndMapContainer.createNewRow();
            }
        }
    }
}
