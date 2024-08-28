package ru.clevertec.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.clevertec.parser.test.Root;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, JsonProcessingException {
        String s = "{  \"result\": \"0\",  \"albCnt\": \"2\",  \"albs\": [    [      \"id\", \"100\",      \"name\", \"ой альбом 1\"    ],    [      \"id\", \"2\",      \"name\", \"ой альбом 2\"    ]  ],  \"sites\": [    {      \"id\": \"233\",      \"name\": \"мя сайта 233\"    },    {      \"id\": \"239\",      \"name\": \"мя сайта 239\"    },    {      \"id\": \"244\",      \"name\": \"мя сайта 244\",      \"albCnt\": \"2\",      \"albs\": [        {          \"id\": \"172\",          \"name\": \"льбом сайта 172\"        },        {          \"id\": \"88\",          \"name\": \"льбом сайта 88\"        }      ]    },    {      \"id\": \"571\",      \"name\": \"мя сайта 571\"    }  ]}";
        Map<String, Object> stringObjectMap = JSONValue.parseToMap(s);
        Root parse = ClassParser.parse(stringObjectMap, Root.class);
    }
}
