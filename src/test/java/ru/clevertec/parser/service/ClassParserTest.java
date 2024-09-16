package ru.clevertec.parser.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.parser.service.api.JsonToObject;
import ru.clevertec.parser.service.test.Root;

class ClassParserTest {

    @Test
    void parseToObject() {
        String json = """
                {
                  "intId": 4,
                  "longId": 4,
                  "doubleId": 4,
                  "byteId": 4,
                  "shortId": 4,
                  "floatId": 4.0,
                  "ch": "e",
                  "bool": false,
                  "intOId": 4,
                  "longOId": 4,
                  "doubleOId": 4,
                  "byteOId": 4,
                  "shortOId": 4,
                  "floatOId": 4.0,
                  "chO": "e",
                  "boolO": false,
                  "useridString": "user_id_value",
                  "intArr": [
                    1,
                    2,
                    3,
                    4
                  ],
                  "intList": [
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
                    },
                    {
                      "id": "6",
                      "name": "Test name",
                      "prop": [
                        {
                          "prop": 1
                        },
                        {
                          "prop": 2
                        }
                      ]
                    }
                  ],
                  "map": {
                    "1": "2",
                    "3": "4"
                  }
                }
                """;
        JsonToObject classParser = new ClassParser();
        Root root = classParser.parseToObject(json, Root.class);
        Assertions.assertFalse(root.isBool());
        Assertions.assertEquals(false, root.getBoolO());
        Assertions.assertEquals(4, root.getByteId());
        Assertions.assertEquals((byte) 4, root.getByteOId());
        Assertions.assertEquals(4, root.getShortId());
        Assertions.assertEquals((short) 4, root.getShortOId());
        Assertions.assertEquals(4, root.getIntId());
        Assertions.assertEquals(4, root.getIntOId());
        Assertions.assertEquals(4L, root.getLongId());
        Assertions.assertEquals(4L, root.getLongOId());
        Assertions.assertEquals('e', root.getCh());
        Assertions.assertEquals('e', root.getChO());
        Assertions.assertEquals(4.0F, root.getFloatId());
        Assertions.assertEquals(4.0F, root.getFloatOId());
        Assertions.assertEquals(4.0, root.getDoubleId());
        Assertions.assertEquals(4.0, root.getDoubleOId());
        Assertions.assertEquals("user_id_value", root.getUseridString());
        Assertions.assertEquals(1, root.getIntArr()[0]);
        Assertions.assertEquals("2", root.getUsers().get(0).getId());
        Assertions.assertEquals(1, root.getUsers().get(0).getProp().get(0).getPr());
        Assertions.assertEquals("2", root.getMap().get("1"));
    }

    @Test
    void parseToObjectWithNull() {
        String json = """
                {
                  "intId": 4
                }
                """;
        JsonToObject classParser = new ClassParser();
        Root root = classParser.parseToObject(json, Root.class);
        Assertions.assertEquals(4, root.getIntId());
        Assertions.assertNull(root.getIntOId());
    }
}