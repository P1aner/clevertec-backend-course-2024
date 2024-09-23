package ru.clevertec.parser.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.parser.exception.IncorrectJsonStringException;
import ru.clevertec.parser.service.api.JsonToMap;

import java.util.List;
import java.util.Map;

import static ru.clevertec.parser.service.test.JsonStringData.INCORRECT_JSON;
import static ru.clevertec.parser.service.test.JsonStringData.STRING_SIMPLE_OBJECT;

class JsonValueTest {

    @Test
    void parseToMap() {
        JsonToMap jsonToMap = new JsonValue();
        Map<String, Object> map = jsonToMap.parseToMap(STRING_SIMPLE_OBJECT);
        Assertions.assertEquals("4", map.get("intId"));
        Assertions.assertEquals("e", map.get("ch"));
        Assertions.assertEquals("false", map.get("bool"));
        Assertions.assertEquals("4.0", map.get("floatOId"));
        Assertions.assertEquals("1", ((List<?>) map.get("intArr")).getFirst());
        Assertions.assertEquals("2", ((Map<?, ?>) ((List<?>) map.get("users")).getFirst()).get("id"));
    }

    @Test
    void parseToMapWithIncorrect() {
        JsonToMap jsonToMap = new JsonValue();
        Assertions.assertThrows(IncorrectJsonStringException.class, () -> jsonToMap.parseToMap(INCORRECT_JSON));
    }
}