package ru.clevertec.parser.service.chain;


import static ru.clevertec.parser.utils.DefaultJsonChars.COLON;
import static ru.clevertec.parser.utils.DefaultJsonChars.COMMA;
import static ru.clevertec.parser.utils.DefaultJsonChars.CURLY_BRACE_START;
import static ru.clevertec.parser.utils.DefaultJsonChars.QUOTE;
import static ru.clevertec.parser.utils.DefaultJsonChars.SQUARE_BRACKET_START;

public class OtherChain implements Chain {

    @Override
    public void execute(JsonAndMapContainer jsonAndMapContainer) {
        if (jsonAndMapContainer.hasNext()) {
            char firsChar;
            do {
                firsChar = jsonAndMapContainer.excludeChar();
                jsonAndMapContainer.getValueString().append(firsChar);
                if (!jsonAndMapContainer.hasNext()) break;
                firsChar = jsonAndMapContainer.getFirsChar();
            } while (firsChar != SQUARE_BRACKET_START &&
                    firsChar != COMMA &&
                    firsChar != COLON &&
                    firsChar != QUOTE &&
                    firsChar != CURLY_BRACE_START);
        }
        if (!jsonAndMapContainer.getIsKey() && !jsonAndMapContainer.getValueString().toString().isBlank()) {
            jsonAndMapContainer.createNewRow();
        }
    }
}
