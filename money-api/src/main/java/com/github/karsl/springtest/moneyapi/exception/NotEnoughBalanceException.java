package com.github.karsl.springtest.moneyapi.exception;

import com.github.karsl.springtest.moneyapi.controller.dto.ErrorResponse;
import com.github.karsl.springtest.moneyapi.controller.dto.MappableToErrorResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotEnoughBalanceException extends Exception implements MappableToErrorResponse {

  private Long accountId;

  @Override
  public ErrorResponse map() {
    return ErrorResponse.builder()
        .name("Not enough balance")
        .description("Account " + accountId + " doesn't have enough balance to complete this operation")
        .build();
  }
}
