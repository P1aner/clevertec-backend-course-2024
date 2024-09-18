package ru.clevertec.parser.service.chain;

import static ru.clevertec.parser.utils.DefaultJsonChars.COMMA;

public record CommaChain(Chain nextElement) implements Chain {

    @Override
    public void execute(JsonAndMapContainer jsonAndMapContainer) {
        if (jsonAndMapContainer.hasNext() && jsonAndMapContainer.getFirsChar() == COMMA) {
            jsonAndMapContainer.excludeChar();
            if (!jsonAndMapContainer.getValueString().isEmpty()) {
                jsonAndMapContainer.createNewRow();
            }
        }
        if (jsonAndMapContainer.hasNext()) {
            nextElement.execute(jsonAndMapContainer);
        }
    }
}
