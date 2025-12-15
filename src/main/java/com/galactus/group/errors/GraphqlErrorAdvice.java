package com.galactus.group.errors;

import com.galactus.common.constants.GraphqlConstants;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

/**
 * Global GraphQL exception handler for the API.
 *
 * <p>
 * This class centralizes error handling for GraphQL resolvers (Query/Mutation/DataFetchers).
 * Instead of letting domain exceptions bubble up as generic {@code INTERNAL_ERROR} responses,
 * we convert known exceptions into structured {@link graphql.GraphQLError} instances using
 * {@link org.springframework.graphql.execution.GraphQlSource.Builder#exceptionResolvers}
 * and Spring GraphQL's {@link org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler}.
 * </p>
 **/
@ControllerAdvice
@SuppressWarnings("unused")
public class GraphqlErrorAdvice {
    @GraphQlExceptionHandler(GroupNotFoundException.class)
    public GraphQLError handleGroupNotFound(GroupNotFoundException ex, DataFetchingEnvironment env) {
        return GraphqlErrorBuilder.newError(env)
                .errorType(ErrorType.NOT_FOUND)
                .message(ex.getMessage())
                .extensions(Map.of("code", GraphqlConstants.NOT_FOUND))
                .build();
    }
}
