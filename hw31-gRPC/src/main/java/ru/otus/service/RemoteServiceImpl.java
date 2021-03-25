package ru.otus.service;

import ru.otus.generated.InMessage;
import ru.otus.generated.OutMessage;
import ru.otus.generated.RemoteServiceGrpc;

import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class RemoteServiceImpl extends RemoteServiceGrpc.RemoteServiceImplBase {

    @Override
    public void generate(InMessage request, StreamObserver<OutMessage> responseObserver) {
        IntStream.rangeClosed(request.getFirstValue() + 1, request.getLastValue())
                .forEach(generatedValue -> send(responseObserver, generatedValue));
        responseObserver.onCompleted();
    }

    private void send(StreamObserver<OutMessage> responseObserver, int generatedValue) {
        responseObserver.onNext(OutMessage.newBuilder().setGeneratedValue(generatedValue).build());
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(2));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
