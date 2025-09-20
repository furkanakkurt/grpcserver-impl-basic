package com.furkan.grpcserver.service;

import com.furkanakkurt.grpcserver.AgeRequest;
import com.furkanakkurt.grpcserver.AgeResponse;
import com.furkanakkurt.grpcserver.AgeServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.time.Year;

@Service
public class AgeService extends AgeServiceGrpc.AgeServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(AgeService.class);

    @Override
    public void getAge(AgeRequest request, StreamObserver<AgeResponse> streamObserver) {
        log.info("Starting getAge() service");

        int year = request.getBirthYear();

        int age = Year.now().getValue() - year;

        AgeResponse response = AgeResponse.newBuilder().setAge(age).build();

        streamObserver.onNext(response);
        streamObserver.onCompleted();

        log.info("Completed the getAge() service");
    }
}
