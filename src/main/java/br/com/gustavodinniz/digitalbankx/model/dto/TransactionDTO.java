package br.com.gustavodinniz.digitalbankx.model.dto;

import java.math.BigDecimal;

import br.com.gustavodinniz.digitalbankx.enumeration.TransactionStatusType;
import br.com.gustavodinniz.digitalbankx.enumeration.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private String transactionId;

    private TransactionType transactionType;

    private BigDecimal amount;

    private String dateTime;

    private TransactionStatusType status;

    private String sourceAccount;

    private String destinationAccount;

}
