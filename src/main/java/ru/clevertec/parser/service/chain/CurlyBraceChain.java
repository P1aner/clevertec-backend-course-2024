package ru.clevertec.parser.service.chain;

import ru.clevertec.parser.service.JsonValue;
import ru.clevertec.parser.utils.StringExtractor;

import static ru.clevertec.parser.utils.DefaultJsonChars.CURLY_BRACE_START;

public record CurlyBraceChain(Chain nextElement) implements Chain {

    @Override
    public void execute(JsonAndMapContainer jsonAndMapContainer) {
        if (jsonAndMapContainer.hasNext() && jsonAndMapContainer.getFirsChar() == CURLY_BRACE_START) {
            jsonAndMapContainer.excludeChar();
            StringBuilder jsonStringWithoutCurlyBraces = new StringBuilder(StringExtractor.extractObjectString(jsonAndMapContainer.getDefaultValue()).trim());
            jsonAndMapContainer.createNewRow(new JsonValue().parseToMap(jsonStringWithoutCurlyBraces));
        }
        if (jsonAndMapContainer.hasNext()) {
            nextElement.execute(jsonAndMapContainer);
        }
    }
}
