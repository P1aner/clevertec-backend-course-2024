package ru.clevertec.parser.service;


import ru.clevertec.parser.service.api.JsonToMap;
import ru.clevertec.parser.service.chain.Chain;
import ru.clevertec.parser.service.chain.ColonChain;
import ru.clevertec.parser.service.chain.CommaChain;
import ru.clevertec.parser.service.chain.CurlyBraceChain;
import ru.clevertec.parser.service.chain.JsonAndMapContainer;
import ru.clevertec.parser.service.chain.OtherChain;
import ru.clevertec.parser.service.chain.QuoteChain;
import ru.clevertec.parser.service.chain.SquareBracketChain;

import java.util.Map;

public class JsonValue implements JsonToMap {

    private final Chain startChain;

    {
        Chain otherChain = new OtherChain();
        Chain squareBracketChain = new SquareBracketChain(otherChain);
        Chain curlyBraceChain = new CurlyBraceChain(squareBracketChain);
        Chain commaChain = new CommaChain(curlyBraceChain);
        Chain colonChain = new ColonChain(commaChain);
        startChain = new QuoteChain(colonChain);
    }

    @Override
    public Map<String, Object> parseToMap(String jsonString) {
        String substring = jsonString.trim().substring(1, jsonString.trim().length() - 1);
        String replace = substring.replace('\n', ' ');
        StringBuilder jsonStringWithoutCurlyBraces = new StringBuilder(replace);
        return parseToMap(jsonStringWithoutCurlyBraces);
    }

    public Map<String, Object> parseToMap(StringBuilder jsonStringWithoutCurlyBraces) {
        JsonAndMapContainer jsonAndMapContainer = new JsonAndMapContainer(jsonStringWithoutCurlyBraces);
        while (jsonAndMapContainer.hasNext()) {
            startChain.execute(jsonAndMapContainer);
        }
        return jsonAndMapContainer.getMap();
    }
}