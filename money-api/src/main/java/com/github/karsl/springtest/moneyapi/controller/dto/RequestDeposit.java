package com.github.karsl.springtest.moneyapi.controller.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class RequestDeposit {

  @Min(0)
  private Long accountId;

  @DecimalMin(value = "0.0", inclusive = false)
  private Double amount;

}
