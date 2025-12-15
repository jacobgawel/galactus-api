package com.galactus.controllers.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GroupQueryResolver {

    @QueryMapping
    public String greeting(@Argument String name) {
        return name;
    }

}
