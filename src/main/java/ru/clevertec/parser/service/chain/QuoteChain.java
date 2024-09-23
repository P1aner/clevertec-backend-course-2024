package ru.clevertec.parser.service.chain;

import ru.clevertec.parser.utils.StringExtractor;

import static ru.clevertec.parser.utils.DefaultJsonChars.QUOTE;


public record QuoteChain(Chain nextElement) implements Chain {

    @Override
    public void execute(JsonAndMapContainer jsonAndMapContainer) {
        if (jsonAndMapContainer.hasNext() && jsonAndMapContainer.getFirsChar() == QUOTE) {
            jsonAndMapContainer.excludeChar();
            String substring = StringExtractor.extractStringValue(jsonAndMapContainer.getDefaultValue());
            if (jsonAndMapContainer.getIsKey()) {
                jsonAndMapContainer.getKeyString().append(substring);
                jsonAndMapContainer.revertKey();
            } else {
                jsonAndMapContainer.getValueString().append(substring);
                jsonAndMapContainer.createNewRow();
            }
        }
        if (jsonAndMapContainer.hasNext()) {
            nextElement.execute(jsonAndMapContainer);
        }
    }
}
