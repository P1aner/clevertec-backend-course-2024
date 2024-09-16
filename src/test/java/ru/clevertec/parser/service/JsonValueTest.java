package ru.clevertec.parser.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.parser.exception.IncorrectJsonStringException;
import ru.clevertec.parser.service.api.JsonToMap;

import java.util.List;
import java.util.Map;

class JsonValueTest {

    @Test
    void parseToMap() {
        String json = """
                {
                  "intId": 4,
                  "ch": "e",
                  "bool": false,
                  "floatOId": 4.0,
                  "intArr": [
                    1,
                    2,
                    3,
                    4
                  ],
                  "users": [
                    {
                      "id": "2",
                      "name": "Test",
                      "prop": [
                        {
                          "prop": 1
                        },
                        {
                          "prop": 2
                        }
                      ]
                    }
                      ]
                    }
                  ]
                }
                """;
        JsonToMap jsonToMap = new JsonValue();
        Map<String, Object> map = jsonToMap.parseToMap(json);
        Assertions.assertEquals("4", map.get("intId"));
        Assertions.assertEquals("e", map.get("ch"));
        Assertions.assertEquals("false", map.get("bool"));
        Assertions.assertEquals("4.0", map.get("floatOId"));
        Assertions.assertEquals("1", ((List) map.get("intArr")).get(0));
        Assertions.assertEquals("2", ((Map) ((List) map.get("users")).get(0)).get("id"));
    }

    @Test
    void parseToMapWithIncorrect() {
        String json = """
                                {
                                  "intId": 4
                {
                                }
                """;
        JsonToMap jsonToMap = new JsonValue();
        Assertions.assertThrows(IncorrectJsonStringException.class, () -> {
            jsonToMap.parseToMap(json);
        });
    }
}