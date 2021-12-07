package com.github.karsl.springtest.moneyapi.controller.dto;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class RequestWithdraw {

  @Min(0)
  private final Long accountId;

  @DecimalMin(value = "0.0", inclusive = false)
  private final BigDecimal amount;

}
