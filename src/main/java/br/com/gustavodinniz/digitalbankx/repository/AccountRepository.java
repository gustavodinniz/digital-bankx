package br.com.gustavodinniz.digitalbankx.repository;

import br.com.gustavodinniz.digitalbankx.model.domain.AccountDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountDomain, String> {
}
