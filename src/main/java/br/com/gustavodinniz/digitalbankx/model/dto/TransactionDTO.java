package br.com.gustavodinniz.digitalbankx.model.dto;

import br.com.gustavodinniz.digitalbankx.enumeration.TransactionStatusType;
import br.com.gustavodinniz.digitalbankx.enumeration.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private TransactionType transactionType;

    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    private TransactionStatusType status;

    private String sourceAccount;

    private String destinationAccount;
}
