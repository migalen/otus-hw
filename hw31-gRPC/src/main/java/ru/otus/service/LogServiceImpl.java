package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class LogServiceImpl implements Runnable {

    private final GenerationServiceImpl generationServiceImpl;

    private static final Logger log = LoggerFactory.getLogger(LogServiceImpl.class);

    private int currentValue = 0;
    private int lastSavedValue = 0;
    public LogServiceImpl(GenerationServiceImpl generationServiceImpl) {
        this.generationServiceImpl = generationServiceImpl;
    }

    @Override
    public void run() {
        while (true) {
            int lastGenerated = generationServiceImpl.getLastGenerated();

            int extra = 0;
            if (lastGenerated != lastSavedValue) {
                extra = lastGenerated;
                lastSavedValue = lastGenerated;
            }

            currentValue = currentValue + extra + 1;

            log();
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void log() {
        log.info("currentValue:{}", currentValue);
    }
}
