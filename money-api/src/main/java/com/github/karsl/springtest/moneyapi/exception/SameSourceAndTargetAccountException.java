package com.github.karsl.springtest.moneyapi.exception;

import com.github.karsl.springtest.moneyapi.controller.dto.ErrorResponse;
import com.github.karsl.springtest.moneyapi.controller.dto.MappableToErrorResponse;

public class SameSourceAndTargetAccountException extends IllegalArgumentException implements
    MappableToErrorResponse {

  @Override
  public ErrorResponse map() {
    return ErrorResponse.builder()
        .name("Same Source and Target Accounts")
        .description("Source and destination accounts must not be same.")
        .build();
  }
}
