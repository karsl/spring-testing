package com.github.karsl.springtest.moneyapi.controller;

import com.github.karsl.springtest.moneyapi.controller.dto.ErrorResponse;
import com.github.karsl.springtest.moneyapi.controller.dto.RequestDeposit;
import com.github.karsl.springtest.moneyapi.controller.dto.RequestTransfer;
import com.github.karsl.springtest.moneyapi.controller.dto.RequestWithdraw;
import com.github.karsl.springtest.moneyapi.controller.dto.ResponseGetBalance;
import com.github.karsl.springtest.moneyapi.exception.AccountNotFoundException;
import com.github.karsl.springtest.moneyapi.exception.NotEnoughBalanceException;
import com.github.karsl.springtest.moneyapi.exception.SameSourceAndTargetAccountException;
import com.github.karsl.springtest.moneyapi.service.MoneyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/money", produces = "application/json", consumes = "application/json")
@Slf4j
@AllArgsConstructor
public class MoneyController {

  private final MoneyService moneyService;

  @Operation(summary = "Retrieve balance of account", responses = {
      @ApiResponse(responseCode = "200", description = "Retrieve balance successfully", content = @Content(schema = @Schema(implementation = ResponseGetBalance.class))),
      @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
  @GetMapping("/{accountId}")
  public ResponseEntity<ResponseGetBalance> getBalance(@PathVariable @Min(0) Long accountId)
      throws AccountNotFoundException {
    return ResponseEntity.ok().body(
        ResponseGetBalance.builder().amount(moneyService.getBalance(accountId)).build());
  }

  @Operation(summary = "Withdraw money from account", responses = {
      @ApiResponse(responseCode = "200", description = "Withdrew money successfully"),
      @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "422", description = "Not enough balance", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
  @PatchMapping
  @ResponseStatus(HttpStatus.OK)
  public void withdraw(@Valid @RequestBody RequestWithdraw request)
      throws AccountNotFoundException, NotEnoughBalanceException {
    moneyService.withdraw(request.getAccountId(), request.getAmount());
  }

  @Operation(summary = "Deposit money into account", responses = {
      @ApiResponse(responseCode = "200", description = "Deposit money successfully"),
      @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public void deposit(@Valid @RequestBody RequestDeposit request) throws AccountNotFoundException {
    moneyService.deposit(request.getAccountId(), BigDecimal.valueOf(request.getAmount()));
  }

  @Operation(summary = "Transfer money between accounts", responses = {
      @ApiResponse(responseCode = "200", description = "Deposit money successfully"),
      @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "412", description = "Source and target accounts were same", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "422", description = "Source account doesn't have enough balance", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void transfer(@Valid @RequestBody RequestTransfer request)
      throws AccountNotFoundException, NotEnoughBalanceException {
    if (request.getSourceAccountId().equals(request.getTargetAccountId()))
      throw new SameSourceAndTargetAccountException();
    else
      moneyService.transfer(request.getSourceAccountId(), request.getTargetAccountId(),
        request.getAmount());
  }

  @ExceptionHandler(AccountNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleAccountNotFound(AccountNotFoundException e) {
    return e.map();
  }

  @ExceptionHandler(NotEnoughBalanceException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ErrorResponse handleNotEnoughBalance(NotEnoughBalanceException e) {
    return e.map();
  }

  @ExceptionHandler(SameSourceAndTargetAccountException.class)
  @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
  public ErrorResponse handleSameSourceAndDestinationAccount(SameSourceAndTargetAccountException e) {
    return e.map();
  }

}
