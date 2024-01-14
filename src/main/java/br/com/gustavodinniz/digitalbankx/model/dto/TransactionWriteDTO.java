package br.com.gustavodinniz.digitalbankx.model.dto;

import java.math.BigDecimal;

import br.com.gustavodinniz.digitalbankx.enumeration.TransactionStatusType;
import br.com.gustavodinniz.digitalbankx.enumeration.TransactionType;
import br.com.gustavodinniz.digitalbankx.model.domain.TransactionDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionWriteDTO {

    private String id;

    private String transactionId;

    private TransactionType transactionType;

    private BigDecimal amount;

    private String dateTime;

    private TransactionStatusType status;

    private String sourceAccount;

    private String sourceAccountDocument;

    private String destinationAccount;

    private String destinationAccountDocument;

    public static TransactionWriteDTO valueOf(TransactionDomain transactionDomain) {
        return TransactionWriteDTO.builder()
                .id(transactionDomain.getId())
                .transactionId(transactionDomain.getTransactionId())
                .transactionType(transactionDomain.getTransactionType())
                .amount(transactionDomain.getAmount())
                .dateTime(String.valueOf(transactionDomain.getDateTime()))
                .status(transactionDomain.getStatus())
                .sourceAccount(transactionDomain.getSourceAccount().getAccountNumber())
                .sourceAccountDocument(transactionDomain.getSourceAccount().getDocument())
                .destinationAccount(
                        transactionDomain.getDestinationAccount() == null ? "" : transactionDomain.getDestinationAccount().getAccountNumber())
                .destinationAccountDocument(
                        transactionDomain.getDestinationAccount() == null ? "" : transactionDomain.getDestinationAccount().getDocument())
                .build();
    }
}
