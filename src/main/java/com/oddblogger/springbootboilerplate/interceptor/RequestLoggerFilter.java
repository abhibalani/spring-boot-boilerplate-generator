package com.oddblogger.springbootboilerplate.interceptor;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@Slf4j
public class RequestLoggerFilter implements Filter {

  /**
   * Method to filter requests.

   * @param request request
   * @param response response
   * @param chain chain
   * @throws IOException io exception
   * @throws ServletException servlet exception
   */
  public void doFilter(
      ServletRequest request, ServletResponse response, FilterChain chain
  ) throws IOException, ServletException {
    UUID uniqueId = UUID.randomUUID();
    MDC.put("requestId", uniqueId.toString());
    log.info("Request IP address is {}", request.getRemoteAddr());
    log.info("Request content type is {}", request.getContentType());
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(
        httpServletResponse
    );
    chain.doFilter(request, responseWrapper);
    responseWrapper.setHeader("requestId", uniqueId.toString());
    responseWrapper.copyBodyToResponse();
    log.info("Response header is set with uuid {}", responseWrapper.getHeader("requestId"));
  }
}
