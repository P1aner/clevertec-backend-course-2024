package ru.clevertec.parser.service.chain;

import ru.clevertec.parser.service.JsonValue;
import ru.clevertec.parser.utils.StringExtractor;

import java.util.ArrayList;
import java.util.List;

import static ru.clevertec.parser.utils.DefaultJsonChars.COMMA;
import static ru.clevertec.parser.utils.DefaultJsonChars.CURLY_BRACE_START;
import static ru.clevertec.parser.utils.DefaultJsonChars.QUOTE;
import static ru.clevertec.parser.utils.DefaultJsonChars.SQUARE_BRACKET_START;

public record SquareBracketChain(Chain nextElement) implements Chain {

    @Override
    public void execute(JsonAndMapContainer jsonAndMapContainer) {
        if (jsonAndMapContainer.hasNext() && jsonAndMapContainer.getFirsChar() == SQUARE_BRACKET_START) {
            jsonAndMapContainer.excludeChar();
            String arrayString = StringExtractor.extractArrayString(jsonAndMapContainer.getDefaultValue()).trim();
            jsonAndMapContainer.createNewRow(stringJsonArrayToList(arrayString));
        }
        if (jsonAndMapContainer.hasNext()) {
            nextElement.execute(jsonAndMapContainer);
        }
    }

    public List<Object> stringJsonArrayToList(String string) {
        List<Object> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder(string.trim());
        StringBuilder value = new StringBuilder();

        while (!stringBuilder.isEmpty()) {
            char charAt = stringBuilder.charAt(0);
            stringBuilder.deleteCharAt(0);
            if (charAt == QUOTE) {
                list.add(StringExtractor.extractStringValue(stringBuilder).trim());
                value = new StringBuilder();
            } else if (charAt == CURLY_BRACE_START) {
                list.add(new JsonValue().parseToMap(new StringBuilder(StringExtractor.extractObjectString(stringBuilder))));
                value = new StringBuilder();
            } else if (charAt == SQUARE_BRACKET_START) {
                list.add(stringJsonArrayToList(StringExtractor.extractArrayString(stringBuilder)));
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

}
