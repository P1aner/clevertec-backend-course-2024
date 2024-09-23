package ru.clevertec.parser.service.chain;


import java.util.Set;

import static ru.clevertec.parser.utils.DefaultJsonChars.COLON;
import static ru.clevertec.parser.utils.DefaultJsonChars.COMMA;
import static ru.clevertec.parser.utils.DefaultJsonChars.CURLY_BRACE_START;
import static ru.clevertec.parser.utils.DefaultJsonChars.QUOTE;
import static ru.clevertec.parser.utils.DefaultJsonChars.SQUARE_BRACKET_START;

public class OtherChain implements Chain {
    private static final Set<Character> set = Set.of(SQUARE_BRACKET_START,
            COMMA,
            COLON,
            QUOTE,
            CURLY_BRACE_START);

    @Override
    public void execute(JsonAndMapContainer jsonAndMapContainer) {
        if (jsonAndMapContainer.hasNext()) {
            char firsChar;
            do {
                firsChar = jsonAndMapContainer.excludeChar();
                jsonAndMapContainer.getValueString().append(firsChar);
                if (!jsonAndMapContainer.hasNext()) break;
                firsChar = jsonAndMapContainer.getFirsChar();
            } while (!set.contains(firsChar));
        }
        if (!jsonAndMapContainer.getIsKey() && !jsonAndMapContainer.getValueString().toString().isBlank()) {
            jsonAndMapContainer.createNewRow();
        }
    }
}
