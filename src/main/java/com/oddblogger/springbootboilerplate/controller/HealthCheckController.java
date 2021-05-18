package com.oddblogger.springbootboilerplate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HealthCheckController {

  /**
   * Method for health check of system.

   * @return String
   */
  @GetMapping(value = "")
  public ResponseEntity<Object> healthCheck() {
    return ResponseEntity.ok().body("Service is running.");
  }
}
