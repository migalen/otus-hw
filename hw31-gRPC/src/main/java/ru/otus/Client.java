package ru.otus;

import ru.otus.service.GenerationServiceImpl;
import ru.otus.service.LogServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws InterruptedException {
        var latch = new CountDownLatch(1);

        var generationServiceImpl = new GenerationServiceImpl(latch);

        var logService = new LogServiceImpl(generationServiceImpl);
        var loggerThread = new Thread(logService);
        log.info("Client starting");
        loggerThread.setDaemon(true);
        loggerThread.start();

        generationServiceImpl.start();

        latch.await();
    }
}
