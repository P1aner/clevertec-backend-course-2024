package ru.clevertec.parser.service.chain;

import static ru.clevertec.parser.utils.DefaultJsonChars.COLON;

public record ColonChain(Chain nextElement) implements Chain {

    @Override
    public void execute(JsonAndMapContainer jsonAndMapContainer) {
        if (jsonAndMapContainer.hasNext() && jsonAndMapContainer.getFirsChar() == COLON) {
            jsonAndMapContainer.excludeChar();
            jsonAndMapContainer.setValueString(new StringBuilder());
            jsonAndMapContainer.setIsKey(false);
        }
        if (jsonAndMapContainer.hasNext()) {
            nextElement.execute(jsonAndMapContainer);
        }
    }
}
