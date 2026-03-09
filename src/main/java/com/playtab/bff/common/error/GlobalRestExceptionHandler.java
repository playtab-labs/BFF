package com.playtab.bff.common.error;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleGrpcException(
            StatusRuntimeException ex,
            HttpServletRequest request
    ) {
        Status.Code grpcCode = ex.getStatus().getCode();
        HttpStatus httpStatus = mapGrpcToHttp(grpcCode);

        String message = ex.getStatus().getDescription();
        if (message == null || message.isBlank()) {
            message = grpcCode.name();
        }

        if (httpStatus.is5xxServerError()) {
            log.error("gRPC error: code={}, message={}, path={}", grpcCode, message, request.getRequestURI());
        } else {
            log.warn("gRPC error: code={}, message={}, path={}", grpcCode, message, request.getRequestURI());
        }

        ApiErrorResponse body = new ApiErrorResponse(
                OffsetDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                grpcCode.name(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(httpStatus).body(body);
    }

    private HttpStatus mapGrpcToHttp(Status.Code code) {
        return switch (code) {
            case INVALID_ARGUMENT -> HttpStatus.BAD_REQUEST;
            case FAILED_PRECONDITION -> HttpStatus.BAD_REQUEST;
            case OUT_OF_RANGE -> HttpStatus.BAD_REQUEST;

            case UNAUTHENTICATED -> HttpStatus.UNAUTHORIZED;
            case PERMISSION_DENIED -> HttpStatus.FORBIDDEN;

            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case ABORTED -> HttpStatus.CONFLICT;

            case RESOURCE_EXHAUSTED -> HttpStatus.TOO_MANY_REQUESTS;

            case CANCELLED -> HttpStatus.BAD_REQUEST;
            case DEADLINE_EXCEEDED -> HttpStatus.GATEWAY_TIMEOUT;
            case UNAVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE;
            case UNIMPLEMENTED -> HttpStatus.NOT_IMPLEMENTED;

            case INTERNAL, UNKNOWN, DATA_LOSS -> HttpStatus.INTERNAL_SERVER_ERROR;
            case OK -> HttpStatus.OK;
        };
    }
}