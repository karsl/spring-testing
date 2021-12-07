package com.github.karsl.springtest.moneyapi.controller.dto;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class RequestTransfer {

  @Min(0)
  private Long sourceAccountId;

  @Min(0)
  private Long targetAccountId;

  @DecimalMin(value = "0.0", inclusive = false)
  private BigDecimal amount;

}
