package com.playtab.bff.graphql;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SystemGraphqlController {

    @QueryMapping
    public String health() {
        return "OK";
    }
}