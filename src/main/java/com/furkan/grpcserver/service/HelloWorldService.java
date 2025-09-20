package com.furkan.grpcserver.service;

import com.furkanakkurt.grpcserver.HelloRequest;
import com.furkanakkurt.grpcserver.HelloResponse;
import com.furkanakkurt.grpcserver.HelloWorldServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldService.class);

    @Override
    public void greet(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        log.info("Starting the greet function");

        try {
            HelloResponse response;
            if (request.getName().startsWith("F")) {
                response = HelloResponse.newBuilder().setMessage("FURKAN").build();
            } else {
                response = HelloResponse.newBuilder().setMessage("SELAM YABANCI").build();
            }

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Exception in greet() {}", e.getMessage());
        }

        log.info("Returned");
    }

    @Override
    public void streamGreet(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        log.info("Starting the streamGreet() function");

        int count = 0;
        while (count < 10) {
            HelloResponse reply = HelloResponse.newBuilder().setMessage("Hello(" + count + ") ==> " + request.getName()).build();
            responseObserver.onNext(reply);
            count++;
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                responseObserver.onError(e);
                return;
            }
        }
        responseObserver.onCompleted();

        log.info("Completed the streamGreet() function");
    }
}
