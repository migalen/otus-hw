package ru.otus;

import ru.otus.service.RemoteServiceImpl;

import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    private static final int SERVER_PORT = 8090;

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws Exception {

        io.grpc.Server server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(new RemoteServiceImpl()).build();
        server.start();
        log.info("server start");
        server.awaitTermination();
    }
}
