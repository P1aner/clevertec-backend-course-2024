package ru.clevertec.parser.service.command;

public interface Command {
    <T> T execute();
}