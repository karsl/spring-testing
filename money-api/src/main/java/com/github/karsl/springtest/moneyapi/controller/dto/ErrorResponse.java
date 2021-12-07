package com.github.karsl.springtest.moneyapi.controller.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {

  private String name;
  private String description;

}
