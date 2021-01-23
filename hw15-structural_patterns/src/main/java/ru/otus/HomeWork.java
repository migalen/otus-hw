package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.listener.homework.MessageHistory;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.homework.ProcessorReplaceFields11And12;
import ru.otus.processor.homework.ProcessorThrowExceptionEvenSecond;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения.
       4. Сделать Listener для ведения истории: старое сообщение - новое (подумайте, как сделать, чтобы сообщения не портились)
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
        var processors = List.of(
                new ProcessorReplaceFields11And12(),
                new ProcessorThrowExceptionEvenSecond(LocalDateTime::now)
        );

        var complexProcessor = new ComplexProcessor(processors, ex -> System.out.println(ex.getMessage()));
        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        var message = new Message.Builder(2L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(new ObjectForMessage())
                .build();
        System.out.println("message:" + message);

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        var newData = new ArrayList<String>();
        newData.add("NEW_DATA");
        result.getField13().setData(newData);
        System.out.println("result after change:" + result);

        System.out.println("history of messages:");
        MessageHistory messageHistory = historyListener.restore();
        System.out.println(messageHistory);
        complexProcessor.removeListener(historyListener);
    }
}
