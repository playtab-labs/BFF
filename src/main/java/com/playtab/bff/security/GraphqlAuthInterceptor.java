package com.playtab.bff.security;

import graphql.language.Document;
import graphql.language.Field;
import graphql.language.OperationDefinition;
import graphql.language.Selection;
import graphql.parser.Parser;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Mono;

@Component
public class GraphqlAuthInterceptor implements WebGraphQlInterceptor {

    private static final Set<String> PUBLIC_FIELDS = Set.of(
            "health",
            "performers",
            "schedulesByDay",
            "foodTrucks",
            "pubs",
            "mdItems",
            "mdItemDetail",
            "notices",
            "noticeDetail"
    );

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        AuthenticatedUser user = resolveUser();

        if (user != null) {
            request.configureExecutionInput((input, builder) ->
                    builder.graphQLContext(ctx ->
                            ctx.put(AuthenticatedUser.REQUEST_ATTRIBUTE, user)
                    ).build()
            );
            return chain.next(request);
        }

        // 인증되지 않은 요청: public 필드만 허용
        if (requiresAuth(request.getDocument())) {
            return chain.next(request).map(response ->
                    response.transform(builder ->
                            builder.errors(List.of(
                                    graphql.GraphqlErrorBuilder.newError()
                                            .message("Authentication required")
                                            .errorType(graphql.ErrorType.ExecutionAborted)
                                            .build()
                            )).data(null)
                    )
            );
        }

        return chain.next(request);
    }

    private boolean requiresAuth(String document) {
        List<String> fieldNames = Parser.parse(document)
                .getDefinitionsOfType(OperationDefinition.class)
                .stream()
                .flatMap(op -> op.getSelectionSet().getSelections().stream())
                .filter(Field.class::isInstance)
                .map(sel -> ((Field) sel).getName())
                .toList();

        return fieldNames.stream()
                .noneMatch(name -> name.startsWith("__"))
                && !PUBLIC_FIELDS.containsAll(fieldNames);
    }

    private AuthenticatedUser resolveUser() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        HttpServletRequest request = attributes.getRequest();
        return (AuthenticatedUser) request.getAttribute(AuthenticatedUser.REQUEST_ATTRIBUTE);
    }
}
