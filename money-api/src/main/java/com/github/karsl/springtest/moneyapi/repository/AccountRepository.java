package com.github.karsl.springtest.moneyapi.repository;

import com.github.karsl.springtest.moneyapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
