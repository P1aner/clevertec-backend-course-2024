package ru.clevertec.parser.service;

import org.junit.jupiter.api.Test;
import ru.clevertec.parser.service.command.Command;
import ru.clevertec.parser.service.command.JsonSendCommand;
import ru.clevertec.parser.service.observers.ConsoleObserver;
import ru.clevertec.parser.service.observers.FileObserver;
import ru.clevertec.parser.service.observers.Observer;
import ru.clevertec.parser.service.observers.Subject;
import ru.clevertec.parser.service.test.JsonStringData;
import ru.clevertec.parser.service.test.Root;

public class CommandAndObservers {

    @Test
    public void testMethod() {
        Subject subject = new Subject();
        Observer fileObserver = new FileObserver();
        Observer consoleObserver = new ConsoleObserver();

        subject.addObserver(consoleObserver);
        subject.addObserver(fileObserver);

        Command command = new JsonSendCommand(subject, JsonStringData.STRING_SIMPLE_OBJECT, Root.class);
        command.execute();
    }
}
