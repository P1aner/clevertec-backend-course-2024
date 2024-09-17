package ru.clevertec.parser.service.chain;

import static ru.clevertec.parser.service.JsonValue.QUOTE;
import static ru.clevertec.parser.service.JsonValue.excludeStringValue;

public class QuoteChain {

    public static void execute(JsonAndMapContainer jsonAndMapContainer) {
        if (jsonAndMapContainer.hasNext() && jsonAndMapContainer.getFirsChar() == QUOTE) {
            jsonAndMapContainer.excludeChar();
            String substring = excludeStringValue(jsonAndMapContainer.getDefaultValue());
            if (jsonAndMapContainer.getIsKey()) {
                jsonAndMapContainer.getKeyString().append(substring);
                jsonAndMapContainer.revertKey();
            } else {
                jsonAndMapContainer.getValueString().append(substring);
                jsonAndMapContainer.createNewRow();
            }
        }
    }
}
