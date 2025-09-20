package com.furkan.grpcserver.interceptor;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingInterceptor implements ServerInterceptor {

    private static final Logger log = LoggerFactory.getLogger("grpc-server");

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        logMessage(serverCall);
        return serverCallHandler.startCall(serverCall, metadata);
    }

    private <ReqT, RespT> void logMessage(ServerCall<ReqT, RespT> serverCall) {
        log.info("Execution started for {}", (serverCall.getMethodDescriptor()));
    }
}
