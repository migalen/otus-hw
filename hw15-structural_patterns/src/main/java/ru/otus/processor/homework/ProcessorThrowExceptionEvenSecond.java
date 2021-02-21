package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorThrowExceptionEvenSecond implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorThrowExceptionEvenSecond(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {

        int second = dateTimeProvider.getDate().getSecond();
        System.out.printf("ProcessorThrowExceptionEvenSecond() - second = %d%n", second);
        if (second % 2 == 0) {
            throw new RuntimeException("EvenSecondException");
        }
        return message;
    }
}
