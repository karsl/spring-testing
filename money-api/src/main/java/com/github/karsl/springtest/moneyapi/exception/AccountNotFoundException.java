package com.github.karsl.springtest.moneyapi.exception;

import com.github.karsl.springtest.moneyapi.controller.dto.ErrorResponse;
import com.github.karsl.springtest.moneyapi.controller.dto.MappableToErrorResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountNotFoundException extends Exception implements MappableToErrorResponse {

  private Long accountId;

  @Override
  public ErrorResponse map() {
    return ErrorResponse.builder()
        .name("Account not found")
        .description("Account with id " + accountId + " not found.")
        .build();
  }

}
