package com.playtab.bff.common.error;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GraphqlExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GraphqlExceptionHandler.class);

    @GraphQlExceptionHandler(StatusRuntimeException.class)
    public GraphQLError handleGrpcException(StatusRuntimeException ex, DataFetchingEnvironment env) {
        Status.Code grpcCode = ex.getStatus().getCode();
        ErrorType errorType = mapGrpcToGraphqlError(grpcCode);

        String message = ex.getStatus().getDescription();
        if (message == null || message.isBlank()) {
            message = grpcCode.name();
        }

        if (isServerError(grpcCode)) {
            log.error("gRPC error in GraphQL: code={}, message={}, field={}",
                    grpcCode, message, env.getField().getName());
        } else {
            log.warn("gRPC error in GraphQL: code={}, message={}, field={}",
                    grpcCode, message, env.getField().getName());
        }

        return GraphqlErrorBuilder.newError(env)
                .message(message)
                .errorType(errorType)
                .extensions(java.util.Map.of("grpcCode", grpcCode.name()))
                .build();
    }

    private ErrorType mapGrpcToGraphqlError(Status.Code code) {
        return switch (code) {
            case INVALID_ARGUMENT, FAILED_PRECONDITION, OUT_OF_RANGE -> ErrorType.BAD_REQUEST;
            case UNAUTHENTICATED -> ErrorType.UNAUTHORIZED;
            case PERMISSION_DENIED -> ErrorType.FORBIDDEN;
            case NOT_FOUND -> ErrorType.NOT_FOUND;
            default -> ErrorType.INTERNAL_ERROR;
        };
    }

    private boolean isServerError(Status.Code code) {
        return switch (code) {
            case INTERNAL, UNKNOWN, DATA_LOSS, UNAVAILABLE, DEADLINE_EXCEEDED -> true;
            default -> false;
        };
    }
}