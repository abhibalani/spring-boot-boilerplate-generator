package com.oddblogger.springbootboilerplate.config;

import java.util.concurrent.TimeUnit;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

  @Autowired
  AppProperty appProperty;

  /**
   * Bean for rest template.
   *
   * @param builder builder.
   * @return rest template.
   */
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    PoolingHttpClientConnectionManager connectionManager =
        new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(appProperty.getHttpMaxRequests());
    connectionManager.setDefaultMaxPerRoute(appProperty.getHttpMaxRequestsPerRoute());

    CloseableHttpClient httpClient =
        HttpClientBuilder
            .create()
            .setConnectionManager(connectionManager)
            .evictIdleConnections(30000, TimeUnit.MILLISECONDS)
            .build();
    HttpComponentsClientHttpRequestFactory factory =
        new HttpComponentsClientHttpRequestFactory(httpClient);
    factory.setConnectionRequestTimeout(appProperty.getConnectionRequestTimeout());
    factory.setConnectTimeout(appProperty.getConnectionTimeout());
    factory.setReadTimeout(appProperty.getReadTimeout());
    return new RestTemplate(factory);
  }

}
