package com.github.karsl.springtest.moneyapi.service;

import com.github.karsl.springtest.moneyapi.entity.Account;
import com.github.karsl.springtest.moneyapi.exception.AccountNotFoundException;
import com.github.karsl.springtest.moneyapi.exception.NotEnoughBalanceException;
import com.github.karsl.springtest.moneyapi.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyService {

  @Autowired
  private AccountRepository accountRepository;

  public BigDecimal getBalance(Long accountId) throws AccountNotFoundException {
    return getAccountById(accountId).getBalance();
  }

  public void deposit(Long accountId, BigDecimal amount) throws AccountNotFoundException {
    Account account = getAccountById(accountId);

    account.setBalance(account.getBalance().add(amount));
    accountRepository.save(account);
  }

  public void withdraw(Long accountId, BigDecimal amount) throws AccountNotFoundException, NotEnoughBalanceException {
    Account account = getAccountById(accountId);

    account.setBalance(account.getBalance().subtract(amount));

    if (account.getBalance().compareTo(BigDecimal.ZERO) < 0)
      throw new NotEnoughBalanceException(accountId);
    else
      accountRepository.save(account);
  }

  public void transfer(Long sourceAccountId, Long targetAccountId, BigDecimal amount) throws AccountNotFoundException, NotEnoughBalanceException {
    Account sourceAccount = getAccountById(sourceAccountId);
    Account targetAccount = getAccountById(targetAccountId);

    if (sourceAccount.getBalance().compareTo(amount) < 0)
      throw new NotEnoughBalanceException(sourceAccountId);
    else {
      sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
      targetAccount.setBalance(targetAccount.getBalance().add(amount));

      accountRepository.save(sourceAccount);
      accountRepository.save(targetAccount);
    }
  }

  private Account getAccountById(Long accountId) throws AccountNotFoundException {
    Optional<Account> maybeAccount = accountRepository.findById(accountId);

    if (maybeAccount.isEmpty())
      throw new AccountNotFoundException(accountId);
    else
      return maybeAccount.get();
  }

}
