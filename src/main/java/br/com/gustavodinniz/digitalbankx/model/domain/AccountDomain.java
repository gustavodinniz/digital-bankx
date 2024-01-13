package br.com.gustavodinniz.digitalbankx.model.domain;

import br.com.gustavodinniz.digitalbankx.enumeration.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "account")
public class AccountDomain {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "document", unique = true, nullable = false)
    private String document;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", columnDefinition = "varchar")
    private AccountType accountType;
}
