package ru.otus.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.listener.homework.MessageHistory;
import ru.otus.model.Message;
import ru.otus.listener.Listener;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.ProcessorThrowExceptionEvenSecond;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.spy;

class ComplexProcessorTest {

    @Test
    @DisplayName("Тестируем вызовы процессоров")
    void handleProcessorsTest() {
        //given
        var message = new Message.Builder(1L).field7("field7").build();

        var processor1 = mock(Processor.class);
        when(processor1.process(eq(message))).thenReturn(message);

        var processor2 = mock(Processor.class);
        when(processor2.process(eq(message))).thenReturn(message);

        var processors = List.of(processor1, processor2);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
        });

        //when
        var result = complexProcessor.handle(message);

        //then
        verify(processor1, times(1)).process(eq(message));
        verify(processor2, times(1)).process(eq(message));
        assertThat(result).isEqualTo(message);
    }

    @Test
    @DisplayName("Тестируем обработку исключения")
    void handleExceptionTest() {
        //given
        var message = new Message.Builder(1L).field8("field8").build();

        var processor1 = mock(Processor.class);
        when(processor1.process(eq(message))).thenThrow(new RuntimeException("Test Exception"));

        var processor2 = mock(Processor.class);
        when(processor2.process(eq(message))).thenReturn(message);

        var processors = List.of(processor1, processor2);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
            throw new TestException(ex.getMessage());
        });

        //when
        assertThatExceptionOfType(TestException.class).isThrownBy(() -> complexProcessor.handle(message));

        //then
        verify(processor1, times(1)).process(eq(message));
        verify(processor2, never()).process(eq(message));
    }

    @Test
    @DisplayName("Тестируем уведомления")
    void notifyTest() {
        //given
        var message = new Message.Builder(1L).field9("field9").build();

        var listener = mock(Listener.class);

        var complexProcessor = new ComplexProcessor(new ArrayList<>(), (ex) -> {
        });

        complexProcessor.addListener(listener);

        //when
        complexProcessor.handle(message);
        complexProcessor.removeListener(listener);
        complexProcessor.handle(message);

        //then
        verify(listener, times(1)).onUpdated(eq(message), eq(message));
    }

    // todo: 3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
    @Test
    @DisplayName("Тестируем процессор, который выбрасывает исключение в четную секунду")
    void evenSecondExceptionProcessorTest() {
        //given
        var message = new Message.Builder(1L).build();

        var processor =
                spy(new ProcessorThrowExceptionEvenSecond(
                        ()-> LocalDateTime.now().withSecond(2)));

        var complexProcessor = new ComplexProcessor(Collections.singletonList(processor), (ex) -> {
            throw new TestException(ex.getMessage());
        });

        //when
        assertThatExceptionOfType(TestException.class).isThrownBy(() -> complexProcessor.handle(message));

        //then
        verify(processor, times(1)).process(eq(message));
    }

    // todo: 4. Сделать Listener для ведения истории: старое сообщение - новое (подумайте, как сделать, чтобы сообщения не портились)
    @Test
    @DisplayName("Тестируем ведение истории, проверяем, что сообщения не портятся")
    void HistoryListenerMessageMutationTest() {
        //given
        String initial = "initial";

        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.getData().add(initial);
        var message = new Message.Builder(1L).field13(objectForMessage).build();

        var processor = spy(new ProcessorThrowExceptionEvenSecond(LocalDateTime::now));
        var complexProcessor = new ComplexProcessor(Collections.singletonList(processor), e -> {});

        HistoryListener historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        //when
        complexProcessor.handle(message);

        String mutated = "mutated";
        List<String> data = message.getField13().getData();
        data.clear();
        data.add(mutated);

        MessageHistory messageHistory = historyListener.restore();
        String oldMsgData = messageHistory.getOldMsg().getField13().getData().get(0);
        String newMsgData = messageHistory.getNewMsg().getField13().getData().get(0);

        //then
        assertThat(message.getField13().getData().get(0)).isEqualTo(mutated);
        assertThat(oldMsgData).isEqualTo(initial);
        assertThat(newMsgData).isEqualTo(initial);

        verify(processor, times(1)).process(any(Message.class));
    }

    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }


}