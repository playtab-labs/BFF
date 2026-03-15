package com.playtab.bff.config;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * TODO: 추후 BFF에서 JWT 검증 후 claims(identityId, role 등)를 추출하여
 *       gRPC metadata로 전달하는 방식으로 변경 예정.
 *       현재는 HTTP Authorization 헤더를 gRPC metadata로 그대로 전달합니다.
 */
@Component
public class GrpcAuthInterceptor implements ClientInterceptor {

    private static final Metadata.Key<String> AUTHORIZATION_KEY =
            Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method,
            CallOptions callOptions,
            Channel next
    ) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                String authorization = resolveAuthorizationHeader();
                if (authorization != null) {
                    headers.put(AUTHORIZATION_KEY, authorization);
                }
                super.start(responseListener, headers);
            }
        };
    }

    private String resolveAuthorizationHeader() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        HttpServletRequest request = attributes.getRequest();
        return request.getHeader("Authorization");
    }
}
