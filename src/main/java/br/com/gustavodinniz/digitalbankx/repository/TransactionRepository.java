package br.com.gustavodinniz.digitalbankx.repository;

import br.com.gustavodinniz.digitalbankx.model.domain.AccountDomain;
import br.com.gustavodinniz.digitalbankx.model.domain.TransactionDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionDomain, String> {
}
