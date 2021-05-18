package com.oddblogger.springbootboilerplate.config;

import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@Getter
@Setter
@Slf4j
public class AppProperty {

  public static final String ENABLE_SWAGGER = "enable.swagger";
  public static final String DEBUG = "enable.debug";
  public static final String SWAGGER_USERNAME = "swagger.username";
  public static final String SWAGGER_PASSWORD = "swagger.password";
  public static final String CONNECTION_REQUEST_TIMEOUT = "connection.request.timeout";
  public static final String CONNECTION_TIMEOUT = "connection.timeout";
  public static final String READ_TIMEOUT = "read.timeout";
  public static final String HTTP_MAX_REQUESTS = "http.max.requests";
  public static final String HTTP_MAX_REQUESTS_PER_ROUTE = "http.max.requests.per.route";

  @Autowired
  Environment env;

  Boolean enableSwagger;
  Boolean enableDebug;
  String swaggerUsername;
  String swaggerPassword;
  Integer connectionRequestTimeout;
  Integer connectionTimeout;
  Integer readTimeout;
  Integer httpMaxRequests;
  Integer httpMaxRequestsPerRoute;

  @PostConstruct
  void loadEnvVariables() {
    this.checkProperties();

    log.info("Loading property values ...");

    this.setEnableDebug(Boolean.valueOf(env.getProperty(DEBUG)));
    this.setEnableSwagger(Boolean.valueOf(env.getProperty(ENABLE_SWAGGER)));
    this.setSwaggerUsername(env.getProperty(SWAGGER_USERNAME));
    this.setSwaggerPassword(env.getProperty(SWAGGER_PASSWORD));
    this.setConnectionRequestTimeout(5000);
    this.setConnectionTimeout(10000);
    this.setReadTimeout(20000);
    this.setHttpMaxRequests(20);
    this.setHttpMaxRequestsPerRoute(8);

    if (!ObjectUtils.isEmpty(env.getProperty(CONNECTION_REQUEST_TIMEOUT))) {
      this.setConnectionRequestTimeout(Integer.valueOf(env.getProperty(CONNECTION_REQUEST_TIMEOUT)));
    }
    if (!ObjectUtils.isEmpty(env.getProperty(CONNECTION_TIMEOUT))) {
      this.setConnectionTimeout(Integer.valueOf(env.getProperty(CONNECTION_TIMEOUT)));
    }
    if (!ObjectUtils.isEmpty(env.getProperty(READ_TIMEOUT))) {
      this.setReadTimeout(Integer.valueOf(env.getProperty(READ_TIMEOUT)));
    }

    if (!ObjectUtils.isEmpty((env.getProperty(HTTP_MAX_REQUESTS)))) {
      this.setHttpMaxRequests(Integer.valueOf(env.getProperty(HTTP_MAX_REQUESTS)));
    }

    if (!ObjectUtils.isEmpty((env.getProperty(HTTP_MAX_REQUESTS_PER_ROUTE)))) {
      this.setHttpMaxRequestsPerRoute(Integer.valueOf(env.getProperty(HTTP_MAX_REQUESTS_PER_ROUTE)));
    }

  }

  private void checkProperties() {
    log.info("Checking required property values ...");
    this.checkProperty(ENABLE_SWAGGER, false);
  }

  private void checkProperty(String propertyName, boolean isRequired) {
    String property = env.getProperty(propertyName);

    if (property == null && !isRequired) {
      log.warn("Property key or value missing for: {}", propertyName);
      return;
    }

    if (property == null || (property.isEmpty() && isRequired)) {
      throw new IllegalArgumentException(
          "Property key or value missing for: " + propertyName + ". Check AppProperty.class for "
              + "required properties."
      );
    }
  }

  private void checkProperty(String propertyName) {
    this.checkProperty(propertyName, true);
  }
}
