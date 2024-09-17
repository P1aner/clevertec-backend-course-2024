package ru.clevertec.parser.service.chain;

import static ru.clevertec.parser.service.JsonValue.COLON;

public class ColonChain {
    public static void execute(JsonAndMapContainer jsonAndMapContainer) {
        if (jsonAndMapContainer.hasNext() && jsonAndMapContainer.getFirsChar() == COLON) {
            jsonAndMapContainer.excludeChar();
            jsonAndMapContainer.setValueString(new StringBuilder());
            jsonAndMapContainer.setIsKey(false);
        }
    }
}
