package ru.otus.service;

import ru.otus.generated.InMessage;
import ru.otus.generated.OutMessage;
import ru.otus.generated.RemoteServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class GenerationServiceImpl {

    private final CountDownLatch latch;

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8090;

    private static final Logger log = LoggerFactory.getLogger(GenerationServiceImpl.class);

    private int lastGeneratedValue = 0;

    public GenerationServiceImpl(CountDownLatch latch) {
        this.latch = latch;
    }

    public void start() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        RemoteServiceGrpc.RemoteServiceStub newStub = RemoteServiceGrpc.newStub(channel);
        var inMessage = InMessage.newBuilder().setFirstValue(0).setLastValue(50).build();

        newStub.generate(inMessage, new StreamObserver<>() {
            @Override
            public void onNext(OutMessage value) {
                lastGeneratedValue = value.getGeneratedValue();
                log.info("new value:{}", lastGeneratedValue);
            }

            @Override
            public void onError(Throwable t) {
                log.error("error: {}", t);
            }

            @Override
            public void onCompleted() {
                log.info("request completed");
                channel.shutdown();
                latch.countDown();
            }
        });
    }

    public synchronized int getLastGenerated() {
        return lastGeneratedValue;
    }
}
