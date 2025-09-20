package com.furkan.grpcserver.interceptor;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthInterceptor implements ServerInterceptor {

    private static final Logger log = LoggerFactory.getLogger("auth-interceptor");

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String authorizationHeader = metadata.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER));
        log.info("Authorization header: {}", authorizationHeader);
        return serverCallHandler.startCall(serverCall, metadata);
    }
}
