package com.furkan.grpcserver.interceptor;

import io.grpc.*;

import java.util.UUID;

public class GlobalExceptionInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata headers,
                                                                 ServerCallHandler<ReqT, RespT> next) {
        ServerCall.Listener<ReqT> delegate = null;
        try {
            delegate = next.startCall(serverCall, headers);
        } catch (Exception ex) {
            return handleInterceptorException(ex, serverCall);
        }
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(delegate) {
            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (Exception ex) {
                    handleEndpointException(ex, serverCall);
                }
            }
        };
    }

    private static <ReqT, RespT> void handleEndpointException(Exception ex, ServerCall<ReqT, RespT> serverCall) {
        String ticket = "endpoint-exception-" + UUID.randomUUID();
        serverCall.close(Status.INTERNAL
                .withCause(ex)
                .withDescription(ex.getMessage() + ", Ticket raised: " + ticket), new Metadata());
    }

    private <ReqT, RespT> ServerCall.Listener<ReqT> handleInterceptorException(Throwable t, ServerCall<ReqT, RespT> serverCall) {
        String ticket = "interceptor-exception-" + UUID.randomUUID();
        serverCall.close(Status.INTERNAL
                .withCause(t)
                .withDescription("An exception occurred in a **subsequent** interceptor:" + ", Ticket raised: " + ticket), new Metadata());

        return new ServerCall.Listener<ReqT>() {};
    }
}
