package com.galactus.group.errors;

import com.galactus.common.constants.GraphqlConstants;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

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
