package br.com.gustavodinniz.digitalbankx.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gustavodinniz.digitalbankx.model.domain.AccountDomain;

public interface AccountRepository extends JpaRepository<AccountDomain, String> {

    Optional<AccountDomain> findByAccountNumber(String accountNumber);

}
