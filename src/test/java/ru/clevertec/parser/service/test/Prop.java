package ru.clevertec.parser.service.test;

import ru.clevertec.parser.annotation.JsonField;

public class Prop {
    @JsonField(name = "prop")
    private int pr;

    public int getPr() {
        return pr;
    }
}