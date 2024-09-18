package ru.clevertec.parser.service.test;

public class JsonStringData {
    private JsonStringData() {
    }

    public static final String STRING_SIMPLE_OBJECT = """
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

    public static final String STRING_HARD_OBJECT = """
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

    public static final String JSON_WITH_NULL = """
            {
              "intId": 4
            }
            """;

    public static final String INCORRECT_JSON = """
                            {
                              "intId": 4
            {
                            }
            """;
}
