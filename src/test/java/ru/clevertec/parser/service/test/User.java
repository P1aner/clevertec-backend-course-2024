package ru.clevertec.parser.service.test;

import java.util.List;

public class User {
    private String id;
    private String name;
    private List<Prop> prop;

    public List<Prop> getProp() {
        return prop;
    }

    public String getId() {
        return id;
    }
}
