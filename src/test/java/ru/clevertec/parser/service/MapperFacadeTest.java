package ru.clevertec.parser.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.parser.service.api.JsonToObject;
import ru.clevertec.parser.service.test.Root;

import static ru.clevertec.parser.service.test.JsonStringData.JSON_WITH_NULL;
import static ru.clevertec.parser.service.test.JsonStringData.STRING_HARD_OBJECT;

class MapperFacadeTest {

    @Test
    void parseToObject() {
        JsonToObject classParser = new MapperFacadeProxy(new MapperFacade());
        Root root = classParser.parseToObject(STRING_HARD_OBJECT, Root.class);
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
        Assertions.assertEquals("2", root.getUsers().getFirst().getId());
        Assertions.assertEquals(1, root.getUsers().getFirst().getProp().getFirst().getPr());
        Assertions.assertEquals("2", root.getMap().get("1"));
    }

    @Test
    void parseToObjectWithNull() {
        JsonToObject classParser = new MapperFacade();
        Root root = classParser.parseToObject(JSON_WITH_NULL, Root.class);
        Assertions.assertEquals(4, root.getIntId());
        Assertions.assertNull(root.getIntOId());
    }
}