package ru.clevertec.parser.service.test;

import ru.clevertec.parser.annotation.JSONField;

public class Prop {
    @JSONField(name = "prop")
    private int pr;

    public int getPr() {
        return pr;
    }
}