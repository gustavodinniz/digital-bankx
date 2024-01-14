package br.com.gustavodinniz.digitalbankx.model.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.gustavodinniz.digitalbankx.enumeration.TransactionStatusType;
import br.com.gustavodinniz.digitalbankx.enumeration.TransactionType;
import br.com.gustavodinniz.digitalbankx.model.dto.TransactionDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class TransactionDomain {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, columnDefinition = "varchar")
    private TransactionType transactionType;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar")
    private TransactionStatusType status;

    @ManyToOne
    @JoinColumn(name = "source_account_id", nullable = false)
    private AccountDomain sourceAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private AccountDomain destinationAccount;

    public static TransactionDomain valueOf(TransactionDTO transactionDTO) {
        return TransactionDomain.builder()
                .transactionId(transactionDTO.getTransactionId())
                .transactionType(transactionDTO.getTransactionType())
                .amount(transactionDTO.getAmount())
                .dateTime(LocalDateTime.parse(transactionDTO.getDateTime()))
                .status(transactionDTO.getStatus())
                .build();
    }
}
