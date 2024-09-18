package ru.clevertec.parser.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.parser.service.api.JsonToObject;
import ru.clevertec.parser.service.test.JsonStringData;
import ru.clevertec.parser.service.test.Root;

class MapperFacadeTest {

    @Test
    void parseToObject() {
        JsonToObject classParser = new MapperFacadeProxy(new MapperFacade());
        Root root = classParser.parseToObject(JsonStringData.STRING_HARD_OBJECT, Root.class);
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
        JsonToObject classParser = new MapperFacade();
        Root root = classParser.parseToObject(JsonStringData.JSON_WITH_NULL, Root.class);
        Assertions.assertEquals(4, root.getIntId());
        Assertions.assertNull(root.getIntOId());
    }
}