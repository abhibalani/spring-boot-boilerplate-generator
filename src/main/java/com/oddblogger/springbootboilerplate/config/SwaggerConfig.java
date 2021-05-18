package com.oddblogger.springbootboilerplate.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Autowired
  AppProperty appProperty;

  /**
   * Bean for swagger ui.

   * @return Docket for swagger ui.
   */
  @Bean
  public Docket notificationServiceApis() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .securityContexts(Collections.singletonList(securityContext()))
        .securitySchemes(Collections.singletonList(apiToken()))
        .enable(appProperty.getEnableSwagger())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.oddblogger.springbootboilerplate"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "Spring Boot Boilerplate",
        "Spring Boot Boilerplate APIs.",
        "1.0",
        "",
        new Contact("Support", "https://oddblogger.com", "contact@oddblogger.com"),
        "",
        "",
        Collections.emptyList());
  }


  private ApiKey apiToken() {
    return new ApiKey("Api Token", "x-api-token", "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth()).build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Collections.singletonList(new SecurityReference("apiToken", authorizationScopes));
  }


}
