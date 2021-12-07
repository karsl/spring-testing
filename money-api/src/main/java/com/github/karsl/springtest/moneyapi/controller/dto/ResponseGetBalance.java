package com.github.karsl.springtest.moneyapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseGetBalance {

  @Schema(minimum = "0", description = "Balance of account", example = "10.0")
  private BigDecimal amount;

}
