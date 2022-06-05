package com.blog.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {



    public static final String AUTHORIZATION_HEADER="Authorization";
    private ApiKey apiKeys()
    {
        return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
    }
    private List<SecurityContext>getSecurityContexts()
    {
        return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
    }
    private List<SecurityReference>securityReferences()
    {
        AuthorizationScope scope =new AuthorizationScope("global","accessEverything");
        return Arrays.asList(new SecurityReference("JWT",new AuthorizationScope[]{scope}));
    }


    @Bean
    public Docket api(){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .securityContexts(getSecurityContexts())
                .securitySchemes(Arrays.asList(apiKeys()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();

    }


    private ApiInfo getInfo() {

        return new ApiInfo("Blog App API Documentation",
                "API Documentation","1.0.0",
                "Terms and Service",
                new Contact("Swapnil Sikarwar","https://affinity.ltd/team-member/6","swapnilsikarwar53@gmail.com"),
                "licence",
                "test",
                Collections.emptyList());
    }

}
